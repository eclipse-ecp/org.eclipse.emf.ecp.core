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
package org.eclipse.emf.ecp.view.model.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link ViewProvider}.
 *
 * @author Lucas Koehler
 */
public class ViewProvider_Test {

	private EClass eClass;
	private EClass refType;
	private ViewProvider viewProvider;
	private VViewModelLoadingProperties viewProperties;

	@Before
	public void setUp() {
		viewProvider = new ViewProvider();
		viewProperties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();

		eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("Test");
		refType = EcoreFactory.eINSTANCE.createEClass();
		refType.setName("RefType");

		final EPackage p = EcoreFactory.eINSTANCE.createEPackage();
		p.setName("p");
		p.setNsPrefix("p");
		p.setNsURI("p");
		p.getEClassifiers().add(eClass);
		p.getEClassifiers().add(refType);
	}

	@Test
	public void provideViewModel_attribute() {
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		attribute.setName("attribute");
		attribute.setEType(EcorePackage.Literals.ESTRING);
		eClass.getEStructuralFeatures().add(attribute);

		final EObject object = EcoreUtil.create(eClass);
		final VView view = viewProvider.provideViewModel(object, viewProperties);
		assertView(view);

		assertTrue(view.getChildren().get(0) instanceof VControl);
		final VControl control = (VControl) view.getChildren().get(0);
		assertNotNull(control.getUuid());
		assertFalse(control.getUuid().isEmpty());
		final VDomainModelReference dmr = control.getDomainModelReference();
		assertTrue(dmr instanceof VFeaturePathDomainModelReference);
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) dmr;
		assertTrue(featureDmr.getDomainModelEReferencePath().isEmpty());
		assertSame(attribute, featureDmr.getDomainModelEFeature());
	}

	@Test
	public void provideViewModel_singleReference() {
		final EReference singleRef = EcoreFactory.eINSTANCE.createEReference();
		singleRef.setName("singleRef");
		singleRef.setEType(refType);
		eClass.getEStructuralFeatures().add(singleRef);

		final EObject object = EcoreUtil.create(eClass);
		final VView view = viewProvider.provideViewModel(object, viewProperties);
		assertView(view);

		assertTrue(view.getChildren().get(0) instanceof VControl);
		final VControl control = (VControl) view.getChildren().get(0);
		assertNotNull(control.getUuid());
		assertFalse(control.getUuid().isEmpty());
		final VDomainModelReference dmr = control.getDomainModelReference();
		assertTrue(dmr instanceof VFeaturePathDomainModelReference);
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) dmr;
		assertTrue(featureDmr.getDomainModelEReferencePath().isEmpty());
		assertSame(singleRef, featureDmr.getDomainModelEFeature());
	}

	@Test
	public void provideViewModel_multiCrossReference() {
		final EReference multiCrossRef = EcoreFactory.eINSTANCE.createEReference();
		multiCrossRef.setName("multiCrossRef");
		multiCrossRef.setEType(refType);
		multiCrossRef.setUpperBound(-1); // multi ref
		multiCrossRef.setContainment(false);
		eClass.getEStructuralFeatures().add(multiCrossRef);

		final EObject object = EcoreUtil.create(eClass);
		final VView view = viewProvider.provideViewModel(object, viewProperties);
		assertView(view);

		assertTrue(view.getChildren().get(0) instanceof VControl);
		final VControl control = (VControl) view.getChildren().get(0);
		assertNotNull(control.getUuid());
		assertFalse(control.getUuid().isEmpty());
		final VDomainModelReference dmr = control.getDomainModelReference();
		assertTrue(dmr instanceof VFeaturePathDomainModelReference);
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) dmr;
		assertTrue(featureDmr.getDomainModelEReferencePath().isEmpty());
		assertSame(multiCrossRef, featureDmr.getDomainModelEFeature());
	}

	/** Verify that the generator generates a table control for multi containment refs. */
	@Test
	public void provideViewModel_multiContainmentReference() {
		final EReference multiContainmentRef = EcoreFactory.eINSTANCE.createEReference();
		multiContainmentRef.setName("multiContainmentRef");
		multiContainmentRef.setEType(refType);
		multiContainmentRef.setUpperBound(-1); // multi ref
		multiContainmentRef.setContainment(true);
		eClass.getEStructuralFeatures().add(multiContainmentRef);

		final EObject object = EcoreUtil.create(eClass);
		final VView view = viewProvider.provideViewModel(object, viewProperties);
		assertView(view);

		assertTrue(view.getChildren().get(0) instanceof VTableControl);
		final VTableControl control = (VTableControl) view.getChildren().get(0);
		assertNotNull(control.getUuid());
		assertFalse(control.getUuid().isEmpty());
		final VDomainModelReference controlDmr = control.getDomainModelReference();
		assertTrue(controlDmr instanceof VTableDomainModelReference);
		final VTableDomainModelReference tableDmr = (VTableDomainModelReference) controlDmr;
		assertTrue(tableDmr.getDomainModelEReferencePath().isEmpty());
		assertNull(tableDmr.getDomainModelEFeature());

		assertTrue(tableDmr.getDomainModelReference() instanceof VFeaturePathDomainModelReference);
		final VFeaturePathDomainModelReference featureDmr = (VFeaturePathDomainModelReference) tableDmr
			.getDomainModelReference();

		assertSame(multiContainmentRef, featureDmr.getDomainModelEFeature());
	}

	private void assertView(final VView view) {
		assertNotNull(view);
		assertNotNull(view.getUuid());
		assertFalse(view.getUuid().isEmpty());
		assertEquals(1, view.getChildren().size());
	}
}
