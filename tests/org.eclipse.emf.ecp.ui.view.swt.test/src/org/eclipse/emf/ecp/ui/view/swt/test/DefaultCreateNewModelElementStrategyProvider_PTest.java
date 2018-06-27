/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * eugen - initial API and implementation
 * Christian W. Damus - bug 529138
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.test;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.CreateNewModelElementStrategy.Provider;
import org.eclipse.emf.ecp.ui.view.swt.reference.DefaultCreateNewModelElementStrategyProvider;
import org.eclipse.emf.ecp.ui.view.swt.reference.EClassSelectionStrategy;
import org.eclipse.emfforms.bazaar.Bazaar;
import org.eclipse.emfforms.bazaar.BazaarContext;
import org.eclipse.emfforms.bazaar.Bid;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.common.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author eugen
 *
 */
public class DefaultCreateNewModelElementStrategyProvider_PTest {

	private DefaultCreateNewModelElementStrategyProvider provider;
	private static BundleContext bundleContext;
	private ServiceReference<Provider> serviceReference;
	private Bazaar<CreateNewModelElementStrategy> bazaar;

	private BazaarContext context;

	@BeforeClass
	public static void prepare() {
		final Bundle bundle = FrameworkUtil.getBundle(DefaultCreateNewModelElementStrategyProvider_PTest.class);
		bundleContext = bundle.getBundleContext();
	}

	@Before
	public void setup() {
		serviceReference = bundleContext.getServiceReference(CreateNewModelElementStrategy.Provider.class);
		provider = (DefaultCreateNewModelElementStrategyProvider) bundleContext.getService(serviceReference);
		final Bazaar.Builder<CreateNewModelElementStrategy> builder = Bazaar.Builder.with(singleton(provider));
		bazaar = builder.build();
		final Map<String, ?> values = new HashMap<String, Object>();
		values.put(EObject.class.getName(), null);
		values.put(EReference.class.getName(), null);
		context = BazaarContext.Builder.with(values).build();
	}

	@Test
	public void testDefault() {
		final CreateNewModelElementStrategy createProduct = bazaar
			.createProduct(context);

		final Optional<EObject> createNewModelElement = createProduct.createNewModelElement(null,
			EcorePackage.eINSTANCE.getEClass_EAllAttributes());
		assertTrue(createNewModelElement.isPresent());
	}

	@Test
	public void testEClassSelectionStrategy() {
		final EClassSelectionStrategy strategy = forceEClass(EcorePackage.eINSTANCE.getEPackage());
		final TestEClassSelectionStrategyProvider selectionStrategyProvider = new TestEClassSelectionStrategyProvider(
			POSITIVE_INFINITY, strategy);
		provider.addEClassSelectionStrategyProvider(selectionStrategyProvider);

		final CreateNewModelElementStrategy createProduct = bazaar
			.createProduct(context);

		final Optional<EObject> createNewModelElement = createProduct.createNewModelElement(null,
			EcorePackage.eINSTANCE.getEClass_EAllAttributes());
		assertTrue(EPackage.class.isInstance(createNewModelElement.get()));

		inOrder(strategy).verify(strategy, calls(1)).collectEClasses(isNull(EObject.class),
			same(EcorePackage.eINSTANCE.getEClass_EAllAttributes()),
			anyCollectionOf(EClass.class));
		provider.removeEClassSelectionStrategyProvider(selectionStrategyProvider);
	}

	/**
	 * Regression test for <a href="http://eclip.se/529138">bug 529138</a>, usage of
	 * child creation descriptors from the EMF.Edit item providers in creation of
	 * new objects.
	 *
	 * @see <a href="http://eclip.se/529138">bug 529138</a>
	 */
	@Test
	public void createNewElementWithChildCreationDescriptor() {

		// Set up an item provider to create a template-based child descriptor.
		// Create a container object in the context of an editing domain in order to let the
		// framework get child creation descriptors
		final EEnum template = EcoreFactory.eINSTANCE.createEEnum();
		template.setName("NewEnum1");
		final Resource res = new EMFEditNewChildFactoryBuilder()
			.addTemplate(EPackage.class, EcorePackage.Literals.EPACKAGE__ECLASSIFIERS, template)
			.buildResource();
		final EPackage testPackage = EcoreFactory.eINSTANCE.createEPackage();
		res.getContents().add(testPackage);

		// We don't want a user class selection prompt dialog, so force the EEnum class
		final EClassSelectionStrategy strategy = forceEClass(EcorePackage.Literals.EENUM);
		final TestEClassSelectionStrategyProvider selectionStrategyProvider = new TestEClassSelectionStrategyProvider(
			POSITIVE_INFINITY, strategy);
		provider.addEClassSelectionStrategyProvider(selectionStrategyProvider);

		// Run the test
		final CreateNewModelElementStrategy createProduct = bazaar.createProduct(context);

		final Optional<EObject> newObject = createProduct.createNewModelElement(testPackage,
			EcorePackage.Literals.EPACKAGE__ECLASSIFIERS);
		assertThat(newObject.isPresent(), is(true));
		assertThat(newObject.get(), instanceOf(EEnum.class));
		final EEnum type = (EEnum) newObject.get();

		// The item provider configured the new object
		assertThat(type.getName(), is("NewEnum1"));
		provider.removeEClassSelectionStrategyProvider(selectionStrategyProvider);
	}

	//
	// Test framework
	//

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

	public class TestEClassSelectionStrategyProvider implements EClassSelectionStrategy.Provider {
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
