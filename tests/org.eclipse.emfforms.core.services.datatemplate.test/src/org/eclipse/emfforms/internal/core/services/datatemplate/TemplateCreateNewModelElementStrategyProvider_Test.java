/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.core.services.datatemplate.TemplateProvider;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AdminUser;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditFactory;
import org.eclipse.emfforms.core.services.datatemplate.test.model.audit.AuditPackage;
import org.eclipse.emfforms.datatemplate.DataTemplateFactory;
import org.eclipse.emfforms.datatemplate.Template;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
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
		strategyProvider = new TemplateCreateNewModelElementStrategyProvider();
	}

	@Test
	public void testHandles_availableTemplates() {
		final TemplateProvider canProvide = mock(TemplateProvider.class);
		when(canProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(true);
		final LinkedHashSet<Template> canProvideTemplates = new LinkedHashSet<Template>(2);
		final Template template = mock(Template.class);
		canProvideTemplates.add(template);
		when(canProvide.provideTemplates(any(EObject.class), any(EReference.class))).thenReturn(canProvideTemplates);

		final TemplateProvider cannotProvide = mock(TemplateProvider.class);
		when(cannotProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.registerTemplateProvider(canProvide);
		strategyProvider.unregisterTemplateProvider(cannotProvide);

		final EObject eObject = mock(EObject.class);
		final EReference eReference = mock(EReference.class);
		assertTrue(strategyProvider.handles(eObject, eReference));
	}

	@Test
	public void testHandles_noAvailableTemplates() {
		final TemplateProvider cannotProvide = mock(TemplateProvider.class);
		when(cannotProvide.canProvideTemplates(any(EObject.class), any(EReference.class))).thenReturn(false);
		strategyProvider.unregisterTemplateProvider(cannotProvide);

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
	 * Simply test that a non-null strategy is created. The strategy's behavior is tested in separate test cases.
	 */
	@Test
	public void testCreateNewModelELementStrategy() {
		final ComponentContext componentContext = mock(ComponentContext.class);
		when(componentContext.getProperties()).thenReturn(new Hashtable<String, Object>());

		strategyProvider.activate(componentContext);

		final CreateNewModelElementStrategy strategy = strategyProvider.createCreateNewModelElementStrategy();
		assertNotNull(strategy);
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
}
