/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bugs 543461, 546974
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.Labelizer;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.bazaar.StaticBid;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.core.services.datatemplate.TemplateFilterService;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditFactory;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.Template;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.service.component.ComponentContext;

/**
 * Test cases for the TemplateCreateNewModelElementStrategyProvider and its provided strategy.
 *
 * @author Lucas Koehler
 *
 */
public class TemplateCreateNewModelElementStrategyProvider_Test {

	private TemplateCreateNewModelElementStrategyProvider strategyProvider;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final ComponentContext context = mock(ComponentContext.class);
		when(context.getProperties()).thenReturn(new Hashtable<>());

		strategyProvider = new TemplateCreateNewModelElementStrategyProvider();
		strategyProvider.activate(context);
	}

	@Test
	public void testHandles_availableTemplates() {
		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);

		final TemplateProvider cannotProvide = mock(TemplateProvider.class);
		when(cannotProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.registerTemplateProvider(canProvide);
		strategyProvider.registerTemplateProvider(cannotProvide);

		final EObject eObject = mock(EObject.class);
		final EReference eReference = mock(EReference.class);
		assertTrue(strategyProvider.handles(eObject, eReference));
		final InOrder inOrder = Mockito.inOrder(canProvide, cannotProvide);
		inOrder.verify(canProvide, Mockito.times(1)).canProvideTemplates(any(EObject.class), any(EReference.class));
		inOrder.verify(cannotProvide, Mockito.never()).canProvideTemplates(any(EObject.class), any(EReference.class));
	}

	@Test
	public void testHandles_noAvailableTemplates() {
		final TemplateProvider cannotProvide = mock(TemplateProvider.class);
		when(cannotProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.registerTemplateProvider(cannotProvide);

		final EObject eObject = mock(EObject.class);
		final EReference eReference = mock(EReference.class);
		assertFalse(strategyProvider.handles(eObject, eReference));
	}

	@Test
	public void testCollectAvailableTemplates() {
		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);
		final LinkedHashSet<Template> canProvideTemplates = new LinkedHashSet<Template>(2);
		final Template template = mock(Template.class);
		canProvideTemplates.add(template);
		when(canProvide.provideTemplates(any(EObject.class), any(EReference.class))).thenReturn(canProvideTemplates);

		final TemplateProvider cannotProvide = mock(TemplateProvider.class);
		when(cannotProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.registerTemplateProvider(canProvide);
		strategyProvider.registerTemplateProvider(cannotProvide);

		final EObject eObject = mock(EObject.class);
		final EReference eReference = mock(EReference.class);
		final Set<Template> templates = strategyProvider.collectAvailableTemplates(eObject, eReference);

		assertEquals(1, templates.size());
		assertTrue(templates.contains(template));

		final InOrder canProvideOrder = inOrder(canProvide);
		canProvideOrder.verify(canProvide).canProvideTemplates(same(eObject), same(eReference));
		canProvideOrder.verify(canProvide).provideTemplates(same(eObject), same(eReference));
		verify(cannotProvide).canProvideTemplates(same(eObject), same(eReference));
		verify(cannotProvide, times(0)).provideTemplates(same(eObject), same(eReference));
	}

	/**
	 * Test creation of the strategy and its implementation of consistency of classes
	 * presented to the user with available templates.
	 *
	 * @see <a href="http://eclip.se/543461">bug 543461</a>
	 */
	@Test
	public void testCreateNewModelElementStrategy() {
		final TemplateProvider provider = mock(TemplateProvider.class);
		when(provider.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);

		final LinkedHashSet<Template> templates = new LinkedHashSet<Template>();
		final EDataType datatype = EcoreFactory.eINSTANCE.createEDataType();
		datatype.setName("Date"); //$NON-NLS-1$
		final Template dtTemplate = DataTemplateFactory.eINSTANCE.createTemplate();
		dtTemplate.setInstance(datatype);
		dtTemplate.setName("Example DataType"); //$NON-NLS-1$
		templates.add(dtTemplate);
		final EEnum enum_ = EcoreFactory.eINSTANCE.createEEnum();
		enum_.setName("YesNo"); //$NON-NLS-1$
		final Template enTemplate = DataTemplateFactory.eINSTANCE.createTemplate();
		enTemplate.setInstance(enum_);
		enTemplate.setName("Example Enum"); //$NON-NLS-1$
		templates.add(enTemplate);
		when(provider.provideTemplates(any(EObject.class), any(EReference.class))).thenReturn(templates);
		strategyProvider.registerTemplateProvider(provider);

		final ComponentContext componentContext = mock(ComponentContext.class);
		when(componentContext.getProperties()).thenReturn(new Hashtable<String, Object>());

		strategyProvider.activate(componentContext);

		final CreateNewModelElementStrategy strategy = strategyProvider.createCreateNewModelElementStrategy();
		assertThat(strategy, instanceOf(TemplateCreateNewModelElementStrategyProvider.Strategy.class));

		// Spy on the implementation to verify internals
		final TemplateCreateNewModelElementStrategyProvider.Strategy spy = spy(
			(TemplateCreateNewModelElementStrategyProvider.Strategy) strategy);

		// Mustn't actually try to show a wizard dialog
		doReturn(Optional.of(dtTemplate)).when(spy)
			.showSelectModelInstancesDialog(anySetOf(EClass.class), anySetOf(Template.class));

		final Optional<EObject> created = spy.createNewModelElement(AuditPackage.eINSTANCE,
			EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
		assertThat("Nothing created", created.isPresent(), is(true)); //$NON-NLS-1$
		assertThat("Wrong thing created", created.get(), eEqualTo(datatype)); //$NON-NLS-1$

		// Verify invocation of the wizard, that it didn't offer classes for which there
		// weren't any templates
		final Set<EClass> subClasses = new LinkedHashSet<>();
		subClasses.add(EcorePackage.Literals.EDATA_TYPE);
		subClasses.add(EcorePackage.Literals.EENUM);
		// not EcorePackage.Literals.ECLASS
		Mockito.verify(spy).showSelectModelInstancesDialog(subClasses, templates);
	}

	@Test
	public void testStrategy() {
		final ComponentContext componentContext = mock(ComponentContext.class);
		when(componentContext.getProperties()).thenReturn(new Hashtable<String, Object>());
		strategyProvider.activate(componentContext);

		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);
		final LinkedHashSet<Template> canProvideTemplates = new LinkedHashSet<Template>(2);
		final Template template = DataTemplateFactory.eINSTANCE.createTemplate();
		final AdminUser adminUser = AuditFactory.eINSTANCE.createAdminUser();
		adminUser.setDisplayName("Test"); //$NON-NLS-1$
		template.setInstance(adminUser);
		canProvideTemplates.add(template);
		when(canProvide.provideTemplates(any(EObject.class), any(EReference.class))).thenReturn(canProvideTemplates);
		strategyProvider.registerTemplateProvider(canProvide);

		final EClassSelectionStrategy selectionStrategy = mock(EClassSelectionStrategy.class);
		when(selectionStrategy.collectEClasses(any(EObject.class), any(EReference.class),
			Matchers.<Collection<EClass>> any())).then(new Answer<Collection<EClass>>() {

				@SuppressWarnings("unchecked")
				@Override
				public Collection<EClass> answer(InvocationOnMock invocation) throws Throwable {
					return (Collection<EClass>) invocation.getArguments()[2];
				}

			});
		strategyProvider
			.addEClassSelectionStrategyProvider(new TestEClassSelectionStrategyProvider(1d, selectionStrategy));

		final CreateNewModelElementStrategy strategy = strategyProvider.createCreateNewModelElementStrategy();

		final EObject owner = AuditFactory.eINSTANCE.createUserGroup();
		final EReference reference = AuditPackage.Literals.USER_GROUP__ADMINS;
		final Optional<EObject> resultOptional = strategy.createNewModelElement(owner, reference);

		assertTrue(resultOptional.isPresent());
		assertTrue(resultOptional.get() instanceof AdminUser);
		final AdminUser createdElement = (AdminUser) resultOptional.get();
		assertEquals(adminUser.getDisplayName(), createdElement.getDisplayName());

		verify(selectionStrategy).collectEClasses(same(owner), same(reference), Matchers.<Collection<EClass>> any());
	}

	@Test
	public void testStrategyNoAvailableTemplate() {
		final ComponentContext componentContext = mock(ComponentContext.class);
		when(componentContext.getProperties()).thenReturn(new Hashtable<String, Object>());
		strategyProvider.activate(componentContext);

		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.registerTemplateProvider(canProvide);

		final EClassSelectionStrategy selectionStrategy = mock(EClassSelectionStrategy.class);
		strategyProvider
			.addEClassSelectionStrategyProvider(new TestEClassSelectionStrategyProvider(1d, selectionStrategy));

		final CreateNewModelElementStrategy strategy = strategyProvider.createCreateNewModelElementStrategy();

		final EObject owner = mock(EObject.class);
		final EReference reference = mock(EReference.class);
		final Optional<EObject> resultOptional = strategy.createNewModelElement(owner, reference);

		assertNotNull(resultOptional);
		assertFalse(resultOptional.isPresent());

		verify(selectionStrategy, times(0)).collectEClasses(any(EObject.class), any(EReference.class),
			Matchers.<Collection<EClass>> any());
	}

	@SuppressWarnings("nls")
	@Test
	public void testTemplateFilters() {
		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);
		final Template template1 = mock(Template.class);
		when(template1.getName()).thenReturn("First Template");
		final Template template2 = mock(Template.class);
		when(template2.getName()).thenReturn("Second Template");
		final Template template3 = mock(Template.class);
		when(template3.getName()).thenReturn("Third Template");
		final Set<Template> canProvideTemplates = new LinkedHashSet<>(Arrays.asList(template1, template2, template3));
		when(canProvide.provideTemplates(any(EObject.class), any(EReference.class))).thenReturn(canProvideTemplates);
		strategyProvider.registerTemplateProvider(canProvide);

		final RegexFilterService.Provider filter1 = new RegexFilterService.Provider("^Template$");
		final RegexFilterService.Provider filter2 = new RegexFilterService.Provider("^\\w{5} .+$");
		strategyProvider.addFilterServiceProvider(filter1);
		strategyProvider.addFilterServiceProvider(filter2);

		final EObject eObject = mock(EObject.class);
		final EReference eReference = mock(EReference.class);
		Set<Template> templates = strategyProvider.collectAvailableTemplates(eObject, eReference);

		assertThat("Filters should have excluded everything", templates, not(hasItem(anything())));

		filter1.setRegex("^.+ Template$");
		templates = strategyProvider.collectAvailableTemplates(eObject, eReference);
		assertThat("Filters should have excluded only Second Template", templates,
			both(hasItems(template1, template3)).and(not(hasItem(template2))));

		strategyProvider.removeFilterServiceProvider(filter2);
		templates = strategyProvider.collectAvailableTemplates(eObject, eReference);
		assertThat("Filters should have excluded no templates", templates, hasItems(template1, template2, template3));
	}

	//
	// Test framework
	//

	static class RegexFilterService implements TemplateFilterService {
		private String regex;

		@Override
		public Predicate<? super Template> getTemplateFilter(EObject owner, EReference reference) {
			assertThat(owner, notNullValue());
			assertThat(reference, notNullValue());

			return template -> //
			template.getName().matches(regex);
		}

		//
		// Nested types
		//

		@StaticBid(bid = 10.0)
		static final class Provider implements TemplateFilterService.Provider {
			private final RegexFilterService service = new RegexFilterService();

			Provider(String regex) {
				super();

				setRegex(regex);
			}

			void setRegex(String regex) {
				service.regex = regex;
			}

			@Create
			public TemplateFilterService create() {
				return service;
			}

		}

	}

	class TestEClassSelectionStrategyProvider implements EClassSelectionStrategy.Provider {
		private final Double bid;
		private final EClassSelectionStrategy strategy;

		TestEClassSelectionStrategyProvider(Double bid, EClassSelectionStrategy strategy) {
			super();

			this.bid = bid;
			this.strategy = strategy;
		}

		@Bid
		public Double bid() {
			return bid;
		}

		@Create
		public EClassSelectionStrategy create() {
			return strategy;
		}
	}

	Matcher<EObject> eEqualTo(EObject other) {
		return new CustomTypeSafeMatcher<EObject>("structurally equal to " + Labelizer.get(other).getLabel(other)) { //$NON-NLS-1$
			@Override
			protected boolean matchesSafely(EObject item) {
				return EcoreUtil.equals(item, other);
			}
		};
	}

}
