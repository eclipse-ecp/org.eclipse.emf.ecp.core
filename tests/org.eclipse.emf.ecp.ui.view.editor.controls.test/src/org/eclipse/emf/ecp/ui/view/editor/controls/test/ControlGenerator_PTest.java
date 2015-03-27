/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.editor.controls.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.internal.editor.handler.ControlGenerator;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.group.model.VGroupFactory;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emfforms.internal.core.services.databinding.segment.DMRSegmentConverterImpl;
import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("restriction")
public class ControlGenerator_PTest {

	private EClass rootEClass;
	private VElement elementToFill;
	private Set<EStructuralFeature> features;
	private static DMRSegmentConverter segmentConverter;

	@BeforeClass
	public static void beforeClass() {
		segmentConverter = new DMRSegmentConverterImpl();
	}

	@Before
	public void before() {
		rootEClass = BowlingPackage.eINSTANCE.getFan();
		elementToFill = VViewFactory.eINSTANCE.createView();
		features = new LinkedHashSet<EStructuralFeature>();
		features.add(BowlingPackage.eINSTANCE.getFan_Name());
		features.add(BowlingPackage.eINSTANCE.getFan_Gender());
		features.add(BowlingPackage.eINSTANCE.getFan_FavouriteMerchandise());
	}

	@Test
	public void testAddControlsWithNonViewOrContainer() {
		// setup
		elementToFill = VViewFactory.eINSTANCE.createControl();
		// act
		ControlGenerator.addControls(rootEClass, elementToFill, features);
		// assert no exceptions
	}

	@Test
	public void testAddControlsWithView() throws DatabindingFailedException {
		// setup
		final VView view = (VView) elementToFill;
		// act
		ControlGenerator.addControls(rootEClass, view, features);
		// assert
		assertEquals(features.size(), view.getChildren().size());
		int i = 0;
		final Iterator<EStructuralFeature> iterator = features.iterator();
		while (iterator.hasNext()) {
			assertControl(view.getChildren().get(i++), rootEClass, iterator.next());
		}
	}

	@Test
	public void testAddControlsWithContainer() throws DatabindingFailedException {
		// setup
		elementToFill = VGroupFactory.eINSTANCE.createGroup();
		final VGroup group = (VGroup) elementToFill;
		// act
		ControlGenerator.addControls(rootEClass, group, features);
		// assert
		assertEquals(features.size(), group.getChildren().size());
		int i = 0;
		final Iterator<EStructuralFeature> iterator = features.iterator();
		while (iterator.hasNext()) {
			assertControl(group.getChildren().get(i++), rootEClass, iterator.next());
		}
	}

	@Test
	public void testAddControlsWithBottomUpPath() throws DatabindingFailedException {
		// setup
		final VView view = (VView) elementToFill;
		features.add(BowlingPackage.eINSTANCE.getMerchandise_Name());
		// act
		ControlGenerator.addControls(rootEClass, view, features);
		// assert
		assertEquals(features.size(), view.getChildren().size());
		int i = 0;
		final Iterator<EStructuralFeature> iterator = features.iterator();
		while (iterator.hasNext()) {
			assertControl(view.getChildren().get(i++), rootEClass, iterator.next());
		}
	}

	@Test
	public void testGenerateAllControls() throws IOException, DatabindingFailedException {
		// setup
		VView view = (VView) elementToFill;
		view.setRootEClass(rootEClass);
		final File file = File.createTempFile("view", ".view");
		final URI uri = URI.createFileURI(file.getAbsolutePath());
		final ResourceSet resourceSet = new ResourceSetImpl();
		final Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(view);
		resource.save(null);
		// act
		ControlGenerator.generateAllControls(view);
		// assert
		final Resource resource2 = resourceSet.createResource(uri);
		resource2.load(null);
		view = (VView) resource2.getContents().get(0);
		features.clear();
		features.addAll(rootEClass.getEAllStructuralFeatures());
		assertEquals(features.size(), view.getChildren().size());
		int i = 0;
		final Iterator<EStructuralFeature> iterator = features.iterator();
		while (iterator.hasNext()) {
			assertControl(view.getChildren().get(i++), rootEClass, iterator.next());
		}
		// clean up
		file.delete();
	}

	private static void assertControl(VContainedElement element, EClass eClass, EStructuralFeature feature)
		throws DatabindingFailedException {
		assertNotNull(element);
		assertTrue(element instanceof VControl);
		final VControl control = (VControl) element;
		assertNotNull(control.getDomainModelReference());
		final VDomainModelReference reference = control.getDomainModelReference();
		assertNotNull(reference.getSegments());

		EStructuralFeature structuralFeature = null;
		EClass currentEClass = eClass;
		for (final VDMRSegment segment : reference.getSegments()) {
			final IValueProperty property = segmentConverter.convertToValueProperty(segment,
				currentEClass);
			structuralFeature = (EStructuralFeature) property.getValueType();
			assertNotNull(structuralFeature);
			if (structuralFeature instanceof EReference) {
				currentEClass = EReference.class.cast(structuralFeature).getEReferenceType();
			}
		}
		assertEquals(feature, structuralFeature);
	}
}
