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
 * Christian W. Damus - bug 547271
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.generator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EClassifierExtendedMetaData;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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

	/**
	 * Verify that controls' generated UUIDs actually are unique within the view (there is no
	 * guarantee that they are unique globally, but the {@link ViewProvider} does not need that).
	 */
	@Test
	public void controlsHaveUniqueUUIDs() {
		final EClass eClass = mock(EClass.class,
			withSettings().extraInterfaces(InternalEObject.class, EClassifierExtendedMetaData.Holder.class));
		when(eClass.eContainer()).thenReturn(this.eClass.eContainer());
		when(eClass.getEPackage()).thenReturn(this.eClass.getEPackage());
		when(eClass.getName()).thenReturn("Mock");
		when(eClass.getESuperTypes()).thenReturn(ECollections.emptyEList());
		when(((EClassifierExtendedMetaData.Holder) eClass).getExtendedMetaData())
			.thenReturn(mock(EClassifierExtendedMetaData.class));

		// Repeat the exact same feature multiple times to create controls
		final EList<EStructuralFeature> features = new EObjectContainmentWithInverseEList<>(EStructuralFeature.class,
			(InternalEObject) eClass, EcorePackage.ECLASS__ESTRUCTURAL_FEATURES,
			EcorePackage.ESTRUCTURAL_FEATURE__ECONTAINING_CLASS);
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setEType(refType);
		ref.setName("ref");
		final EList<EReference> references = new EObjectEList<>(EReference.class, (InternalEObject) eClass,
			EcorePackage.ECLASS__EREFERENCES);
		references.addAll(Collections.nCopies(500, ref));
		features.addAll(references);
		when(eClass.getEStructuralFeatures()).thenReturn(features);
		when(eClass.getEAllStructuralFeatures()).thenReturn(ECollections.unmodifiableEList(features));
		when(eClass.getEReferences()).thenReturn(references);
		when(eClass.getEAllReferences()).thenReturn(references);
		when(eClass.getEAttributes()).thenReturn(ECollections.emptyEList());
		when(eClass.getEAllAttributes()).thenReturn(ECollections.emptyEList());
		final EObject object = EcoreUtil.create(eClass);

		final VView view = viewProvider.provideViewModel(object, viewProperties);

		final Set<String> uuids = new HashSet<>();
		controls(view).forEach(control -> {
			final String uuid = control.getUuid();
			assertThat("No UUID generated", uuid, notNullValue());
			assertThat("Non-unique UUID: " + uuid, uuids.add(uuid));
		});

		assertThat("Didn't get a control for each occurrence of the feature", uuids.size(),
			is(eClass.getEAllStructuralFeatures().size()));
	}

	/**
	 * Verify that the item provider is used to determine read-only state of controls.
	 */
	@Test
	public void readOnlyControls() {
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setEType(refType);
		ref.setName("ref");
		eClass.getEStructuralFeatures().add(ref);

		final Map<Resource, Boolean> readOnly = new HashMap<>();
		final EditingDomain domain = new AdapterFactoryEditingDomain(mock(AdapterFactory.class),
			new BasicCommandStack(), readOnly);
		final ResourceSet rset = domain.getResourceSet();
		final Resource resource = new ResourceImpl(URI.createURI("test:/resource"));
		final EObject object = EcoreUtil.create(eClass);
		resource.getContents().add(object);
		rset.getResources().add(resource);
		readOnly.put(resource, true);

		final VView view = viewProvider.provideViewModel(object, viewProperties);
		final int[] count = { 0 };
		controls(view)
			.peek(__ -> count[0]++)
			.forEach(control -> assertThat("Control not read-only", control.isEffectivelyReadonly(), is(true)));
		assertThat("No controls found", count[0], not(0));
	}

	private void assertView(final VView view) {
		assertNotNull(view);
		assertNotNull(view.getUuid());
		assertFalse(view.getUuid().isEmpty());
		assertEquals(1, view.getChildren().size());
	}

	private Stream<VControl> controls(VElement viewModel) {
		return StreamSupport
			.stream(Spliterators.spliteratorUnknownSize(EcoreUtil.getAllContents(Collections.singleton(viewModel)),
				Spliterator.DISTINCT | Spliterator.NONNULL | Spliterator.ORDERED), false)
			.filter(VControl.class::isInstance).map(VControl.class::cast);
	}
}
