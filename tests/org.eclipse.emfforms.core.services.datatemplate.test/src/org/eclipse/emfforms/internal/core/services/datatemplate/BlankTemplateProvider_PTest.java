/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.datatemplate;

import static java.lang.Double.POSITIVE_INFINITY;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ui.view.swt.reference.DefaultCreateNewModelElementStrategyProvider;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.datatemplate.Template;
import org.eclipse.emfforms.internal.core.services.label.BundleResolver;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.Bundle;

/**
 * Unit tests for {@link BlankTemplateProvider}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings({ "restriction", "nls" })
public class BlankTemplateProvider_PTest {

	private BlankTemplateProvider provider;
	private Resource resource;

	@Test
	public void provideEMFEditConfiguredObject() {
		final EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		resource.getContents().add(ePackage);

		assertThat(provider.canProvideTemplates(ePackage, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS), is(true));
		final Set<Template> templates = provider.provideTemplates(ePackage,
			EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);

		assertThat(templates,
			hasItem(templateThat(both(CoreMatchers.<EObject> instanceOf(EEnum.class))
				.and(named("NewEnum1")))));
	}

	//
	// Test framework
	//

	@Before
	public void createTemplateProvider() {
		provider = new BlankTemplateProvider();
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(BlankTemplateProvider.class,
			MessageKeys.BlankTemplateProvider_blankTemplateLabel)).thenReturn("Blank {0}");
		when(localizationService.getString(any(Bundle.class), any(String.class))).thenAnswer(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				final String classKey = (String) invocation.getArguments()[1];
				return classKey.substring(4, classKey.length() - 5);
			}
		});

		final BundleResolver bundleResolver = mock(BundleResolver.class);
		provider.setBundleResolver(bundleResolver);
		provider.setLocalizationService(localizationService);

		final DefaultCreateNewModelElementStrategyProvider dcnmesp = new DefaultCreateNewModelElementStrategyProvider();
		dcnmesp.addEClassSelectionStrategyProvider(new TestEClassSelectionStrategyProvider(
			POSITIVE_INFINITY, forceEClass(EcorePackage.Literals.EENUM)));
		provider.setDefaultNewElementStrategyProvider(dcnmesp);
	}

	@Before
	public void createResource() {
		final EEnum template = EcoreFactory.eINSTANCE.createEEnum();
		template.setName("NewEnum1");

		resource = new EMFEditNewChildFactoryBuilder()
			.addTemplate(EPackage.class, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS, template)
			.buildResource();
	}

	/**
	 * Obtain a Hamcrest matcher for templates whose instances match the given instance matcher.
	 *
	 * @param instanceMatcher a template instance matcher
	 * @return the template matcher
	 */
	Matcher<Template> templateThat(Matcher<? super EObject> instanceMatcher) {
		return new FeatureMatcher<Template, EObject>(instanceMatcher, "object", "object") {
			@Override
			protected EObject featureValueOf(Template actual) {
				return actual.getInstance();
			}
		};
	}

	/**
	 * Obtain a Hamcrest matcher for objects that the EMF named elements having the given name.
	 *
	 * @param name the element name to match
	 * @return the matcher
	 */
	Matcher<EObject> named(final String name) {
		return new CustomTypeSafeMatcher<EObject>("named " + name) {
			@Override
			protected boolean matchesSafely(EObject item) {
				return item instanceof ENamedElement
					&& name.equals(((ENamedElement) item).getName());
			}
		};
	}

	/**
	 * Create a mock EClass selection strategy (suitable for verification) that forces
	 * selection of the given class.
	 *
	 * @param eClass the class to force
	 * @return the mocl strategy
	 */
	EClassSelectionStrategy forceEClass(EClass eClass) {
		final EClassSelectionStrategy result = mock(EClassSelectionStrategy.class);
		final Collection<EClass> eClasses = Collections.singleton(eClass);
		when(result.collectEClasses(any(EObject.class), any(EReference.class),
			anyCollectionOf(EClass.class))).thenReturn(eClasses);

		return result;
	}

	//
	// Nested types
	//

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
