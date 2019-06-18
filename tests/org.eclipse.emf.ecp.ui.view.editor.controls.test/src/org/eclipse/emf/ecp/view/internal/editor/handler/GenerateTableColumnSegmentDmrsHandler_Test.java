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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.test.common.TestUtil;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link GenerateTableColumnSegmentDmrsHandler}.
 *
 * @author Lucas Koehler
 *
 */
public class GenerateTableColumnSegmentDmrsHandler_Test {

	private GenerateTableColumnSegmentDmrsHandler generator;
	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	private Resource resource;
	private VView view;
	private VTableControl tableControl;
	private EClass eClass;
	private EAttribute att1;
	private EAttribute att2;
	private EAttribute att3;

	@Before
	public void setUp() {
		databinding = mock(EMFFormsDatabindingEMF.class);
		reportService = mock(ReportService.class);
		generator = new GenerateTableColumnSegmentDmrsHandler(databinding, reportService);
		resource = TestUtil.createResourceWithEditingDomain();
		view = VViewFactory.eINSTANCE.createView();
		tableControl = VTableFactory.eINSTANCE.createTableControl();
		view.getChildren().add(tableControl);
		resource.getContents().add(view);

		eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("A");

		att1 = EcoreFactory.eINSTANCE.createEAttribute();
		att1.setName("a");
		att2 = EcoreFactory.eINSTANCE.createEAttribute();
		att2.setName("b");
		att3 = EcoreFactory.eINSTANCE.createEAttribute();
		att3.setName("c");
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName("ref");

		eClass.getEStructuralFeatures().add(att1);
		eClass.getEStructuralFeatures().add(att2);
		eClass.getEStructuralFeatures().add(att3);
		eClass.getEStructuralFeatures().add(ref);

		final EPackage p = EcoreFactory.eINSTANCE.createEPackage();
		p.getEClassifiers().add(eClass);
	}

	@Test
	public void generateChildDmrs() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		final VDomainModelReference existingChildDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(existingChildDmr);
		tableControl.setDomainModelReference(multiDmr);

		// mock multi dmr databinding
		final EReference tableReference = mock(EReference.class);
		when(tableReference.getEReferenceType()).thenReturn(eClass);
		mockDmrDatabinding(multiDmr, viewRoot, tableReference);

		// mock existing dmr databinding
		mockDmrDatabinding(existingChildDmr, eClass, att1);

		generator.execute(tableControl);

		verify(reportService, never()).report(any());
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(3, childDmrs.size());
		assertEquals(childDmrs.get(0), existingChildDmr);
		assertChildDmr(childDmrs.get(1), att2);
		assertChildDmr(childDmrs.get(2), att3);

		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertTrue(commandStack.canUndo());
		assertTrue(commandStack.getMostRecentCommand() instanceof AddCommand);
		final AddCommand command = (AddCommand) commandStack.getMostRecentCommand();
		assertSame(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
			command.getFeature());
	}

	@Test
	public void generateChildDmrs_noRootEClass() throws DatabindingFailedException {
		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		final VDomainModelReference existingChildDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(existingChildDmr);
		tableControl.setDomainModelReference(multiDmr);

		generator.execute(tableControl);

		verify(reportService, times(1)).report(any());
		// Check that nothing was done and the existing child dmr is still present
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(1, childDmrs.size());
		assertEquals(childDmrs.get(0), existingChildDmr);
		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertNull(commandStack.getMostRecentCommand());
	}

	@Test
	public void generateChildDmrs_noMultiDmr() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		generator.execute(tableControl);

		// Check that something was reported
		verify(reportService, times(1)).report(any());
	}

	@Test
	public void generateChildDmrs_noMultiSegment() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		tableControl.setDomainModelReference(multiDmr);

		generator.execute(tableControl);

		// Check that something was reported
		verify(reportService, times(1)).report(any());
	}

	@Test
	public void generateChildDmrs_multiDmrResolvesToAttribute() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		final VDomainModelReference existingChildDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(existingChildDmr);
		tableControl.setDomainModelReference(multiDmr);

		// mock multi dmr databinding to resolve to EAttribute
		final EAttribute eAttribute = mock(EAttribute.class);
		mockDmrDatabinding(multiDmr, viewRoot, eAttribute);

		generator.execute(tableControl);

		verify(reportService, times(1)).report(any());
		// Check that nothing was done and the existing child dmr is still present
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(1, childDmrs.size());
		assertEquals(childDmrs.get(0), existingChildDmr);
		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertNull(commandStack.getMostRecentCommand());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void generateChildDmrs_multiDmrDatabindingFailed() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		final VDomainModelReference existingChildDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(existingChildDmr);
		tableControl.setDomainModelReference(multiDmr);

		when(databinding.getValueProperty(same(multiDmr), any(EClass.class)))
			.thenThrow(DatabindingFailedException.class);

		generator.execute(tableControl);

		verify(reportService, times(1)).report(any());
		// Check that nothing was done and the existing child dmr is still present
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(1, childDmrs.size());
		assertEquals(childDmrs.get(0), existingChildDmr);
		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertNull(commandStack.getMostRecentCommand());
	}

	/** Tests that child dmrs are still generated even if the databinding for an existing child dmr fails. */
	@SuppressWarnings("unchecked")
	@Test
	public void generateChildDmrs_existingDmrDatabindingFailed() throws DatabindingFailedException {
		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		final VDomainModelReference existingChildDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		multiSegment.getChildDomainModelReferences().add(existingChildDmr);
		tableControl.setDomainModelReference(multiDmr);

		// mock multi dmr databinding
		final EReference tableReference = mock(EReference.class);
		when(tableReference.getEReferenceType()).thenReturn(eClass);
		mockDmrDatabinding(multiDmr, viewRoot, tableReference);

		// mock existing dmr databinding to throw exception
		when(databinding.getValueProperty(same(existingChildDmr), any(EClass.class)))
			.thenThrow(DatabindingFailedException.class);

		generator.execute(tableControl);

		verify(reportService, times(1)).report(any()); // for failed child dmr databinding
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();

		assertEquals(4, childDmrs.size());
		assertEquals(childDmrs.get(0), existingChildDmr);
		assertChildDmr(childDmrs.get(1), att1);
		assertChildDmr(childDmrs.get(2), att2);
		assertChildDmr(childDmrs.get(3), att3);

		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertTrue(commandStack.canUndo());
		assertTrue(commandStack.getMostRecentCommand() instanceof AddCommand);
		final AddCommand command = (AddCommand) commandStack.getMostRecentCommand();
		assertSame(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
			command.getFeature());
	}

	/**
	 * Tests that DMRs for all EAttributes for a sub class of the base column root are generated.
	 */
	@Test
	public void generateChildDmrs_subClass() throws DatabindingFailedException {
		final EAttribute subAtt = EcoreFactory.eINSTANCE.createEAttribute();
		subAtt.setName("subAtt");
		final EClass subClass = EcoreFactory.eINSTANCE.createEClass();
		subClass.setName("SubClass");
		subClass.getEStructuralFeatures().add(subAtt);
		subClass.getESuperTypes().add(eClass);

		// Create generator that returns sub class
		generator = new GenerateTableColumnSegmentDmrsHandler(databinding, reportService) {

			@Override
			protected Optional<EClass> getColumnDmrRootEClass(EClass baseEClass) {
				return Optional.of(subClass);
			}
		};

		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		tableControl.setDomainModelReference(multiDmr);

		// mock multi dmr databinding
		final EReference tableReference = mock(EReference.class);
		when(tableReference.getEReferenceType()).thenReturn(eClass);
		mockDmrDatabinding(multiDmr, viewRoot, tableReference);

		generator.execute(tableControl);

		verify(reportService, never()).report(any());
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(4, childDmrs.size());
		assertChildDmr(childDmrs.get(0), att1);
		assertChildDmr(childDmrs.get(1), att2);
		assertChildDmr(childDmrs.get(2), att3);
		assertChildDmr(childDmrs.get(3), subAtt);

		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertTrue(commandStack.canUndo());
		assertTrue(commandStack.getMostRecentCommand() instanceof AddCommand);
		final AddCommand command = (AddCommand) commandStack.getMostRecentCommand();
		assertSame(VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES,
			command.getFeature());
	}

	/**
	 * Tests that nothing happens if no subclass (not even the base column root) is used
	 */
	@Test
	public void generateChildDmrs_noSubClass() throws DatabindingFailedException {
		// Create generator that returns sub class
		generator = new GenerateTableColumnSegmentDmrsHandler(databinding, reportService) {

			@Override
			protected Optional<EClass> getColumnDmrRootEClass(EClass baseEClass) {
				return Optional.empty();
			}
		};

		final EClass viewRoot = EcoreFactory.eINSTANCE.createEClass();
		view.setRootEClass(viewRoot);

		final VDomainModelReference multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiDmr.getSegments().add(multiSegment);
		tableControl.setDomainModelReference(multiDmr);

		// mock multi dmr databinding
		final EReference tableReference = mock(EReference.class);
		when(tableReference.getEReferenceType()).thenReturn(eClass);
		mockDmrDatabinding(multiDmr, viewRoot, tableReference);

		generator.execute(tableControl);

		verify(reportService, never()).report(any());
		final EList<VDomainModelReference> childDmrs = multiSegment.getChildDomainModelReferences();
		assertEquals(0, childDmrs.size());

		final CommandStack commandStack = AdapterFactoryEditingDomain.getEditingDomainFor(multiSegment)
			.getCommandStack();
		assertNull(commandStack.getMostRecentCommand());
	}
	// ------------------------
	// TEST HELPERS
	// ------------------------

	private void mockDmrDatabinding(VDomainModelReference dmr, EClass dmrRoot, EStructuralFeature dmrTarget)
		throws DatabindingFailedException {
		final IEMFValueProperty property = mock(IEMFValueProperty.class);
		when(property.getStructuralFeature()).thenReturn(dmrTarget);
		when(databinding.getValueProperty(dmr, dmrRoot)).thenReturn(property);
	}

	private void assertChildDmr(VDomainModelReference childDmr, EStructuralFeature targetFeature) {
		assertEquals(childDmr.getSegments().size(), 1);
		assertTrue("Child dmr segment is a feature segment",
			childDmr.getSegments().get(0) instanceof VFeatureDomainModelReferenceSegment);
		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) childDmr
			.getSegments().get(0);
		assertEquals(targetFeature.getName(), featureSegment.getDomainModelFeature());
	}
}
