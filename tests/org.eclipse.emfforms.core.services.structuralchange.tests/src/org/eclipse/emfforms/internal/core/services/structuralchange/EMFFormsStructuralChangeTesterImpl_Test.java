/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link EMFFormsStructuralChangeTesterImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsStructuralChangeTesterImpl_Test {

	private EMFFormsStructuralChangeTesterImpl changeTester;
	private ReportService reportService;
	private EMFFormsSegmentResolver segmentResolver;

	@Before
	public void setUp() {
		changeTester = new EMFFormsStructuralChangeTesterImpl();
		reportService = mock(ReportService.class);
		changeTester.setReportService(reportService);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		changeTester.setEMFFormsSegmentResolver(segmentResolver);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.EMFFormsStructuralChangeTesterImpl#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedSegmentTesterUsage() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference eReference = TestPackage.eINSTANCE.getB_C();
		final Setting setting = ((InternalEObject) domain).eSetting(eReference);
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(eReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segment);

		final C oldValue = TestFactory.eINSTANCE.createC();
		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.SET, oldValue, newValue, domain, eReference);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		final StructuralChangeSegmentTester tester0 = mock(StructuralChangeSegmentTester.class);
		final StructuralChangeSegmentTester tester1 = mock(StructuralChangeSegmentTester.class);

		when(tester0.isApplicable(segment)).thenReturn(1d);
		when(tester1.isApplicable(segment)).thenReturn(2d);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		changeTester.addStructuralChangeSegmentTester(tester0);
		changeTester.addStructuralChangeSegmentTester(tester1);

		changeTester.isStructureChanged(dmr, domain, mcn);

		verify(tester0).isApplicable(segment);
		verify(tester1).isApplicable(segment);
		verify(tester1).isStructureChanged(segment, domain, mcn);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.EMFFormsStructuralChangeTesterImpl#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedConsiderAllNecessarySegments() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final C c = TestFactory.eINSTANCE.createC();
		domain.setC(c);
		final EReference bToC = TestPackage.eINSTANCE.getB_C();
		final EReference cToA = TestPackage.eINSTANCE.getC_A();

		final VFeatureDomainModelReferenceSegment segmentB = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentB.setDomainModelFeature(bToC.getName());
		final VFeatureDomainModelReferenceSegment segmentC = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentC.setDomainModelFeature(cToA.getName());

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segmentB);
		dmr.getSegments().add(segmentC);

		final Setting settingB = ((InternalEObject) domain).eSetting(bToC);
		final Setting settingC = ((InternalEObject) c).eSetting(cToA);

		final A newValue = TestFactory.eINSTANCE.createA();
		final TestNotification raw = new TestNotification(Notification.SET, null, newValue, c, cToA);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		final StructuralChangeSegmentTester tester0 = mock(StructuralChangeSegmentTester.class);
		final StructuralChangeSegmentTester tester1 = mock(StructuralChangeSegmentTester.class);

		when(tester0.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		when(tester1.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		when(tester1.isStructureChanged(segmentB, domain, mcn)).thenReturn(false);
		when(tester1.isStructureChanged(segmentC, c, mcn)).thenReturn(true);
		when(segmentResolver.resolveSegment(segmentB, domain)).thenReturn(settingB);
		when(segmentResolver.resolveSegment(segmentC, c)).thenReturn(settingC);

		changeTester.addStructuralChangeSegmentTester(tester0);
		changeTester.addStructuralChangeSegmentTester(tester1);

		final boolean isChanged = changeTester.isStructureChanged(dmr, domain, mcn);

		assertTrue(isChanged);
		verify(tester1).isStructureChanged(segmentB, domain, mcn);
		verify(tester1).isStructureChanged(segmentC, c, mcn);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.EMFFormsStructuralChangeTesterImpl#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedMultiReferenceRelevantChange() throws DatabindingFailedException {
		final A domain = TestFactory.eINSTANCE.createA();
		final B b = TestFactory.eINSTANCE.createB();
		domain.setB(b);
		final EReference aToB = TestPackage.eINSTANCE.getA_B();
		final EReference bToCList = TestPackage.eINSTANCE.getB_CList();

		final VFeatureDomainModelReferenceSegment segmentB = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentB.setDomainModelFeature(aToB.getName());
		final VFeatureDomainModelReferenceSegment segmentCList = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentCList.setDomainModelFeature(bToCList.getName());

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segmentB);
		dmr.getSegments().add(segmentCList);

		final Setting settingB = ((InternalEObject) domain).eSetting(aToB);
		final Setting settingCList = ((InternalEObject) b).eSetting(bToCList);

		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.ADD, null, newValue, b, bToCList);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		final StructuralChangeSegmentTester tester = mock(StructuralChangeSegmentTester.class);

		when(tester.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		when(tester.isStructureChanged(segmentB, domain, mcn)).thenReturn(false);
		when(tester.isStructureChanged(segmentCList, b, mcn)).thenReturn(true);
		when(segmentResolver.resolveSegment(segmentB, domain)).thenReturn(settingB);
		when(segmentResolver.resolveSegment(segmentCList, b)).thenReturn(settingCList);

		changeTester.addStructuralChangeSegmentTester(tester);

		final boolean isChanged = changeTester.isStructureChanged(dmr, domain, mcn);

		assertTrue(isChanged);
		verify(tester).isStructureChanged(segmentB, domain, mcn);
		verify(tester).isStructureChanged(segmentCList, b, mcn);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.EMFFormsStructuralChangeTesterImpl#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedMultiReferenceNoRelevantChange() throws DatabindingFailedException {
		final A domain = TestFactory.eINSTANCE.createA();
		final B b = TestFactory.eINSTANCE.createB();
		domain.setB(b);
		final EReference aToB = TestPackage.eINSTANCE.getA_B();
		final EReference bToCList = TestPackage.eINSTANCE.getB_CList();

		final VFeatureDomainModelReferenceSegment segmentB = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentB.setDomainModelFeature(aToB.getName());
		final VFeatureDomainModelReferenceSegment segmentCList = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentCList.setDomainModelFeature(bToCList.getName());

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segmentB);
		dmr.getSegments().add(segmentCList);

		final Setting settingB = ((InternalEObject) domain).eSetting(aToB);
		final Setting settingCList = ((InternalEObject) b).eSetting(bToCList);

		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.ADD, null, newValue, b, bToCList);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		final StructuralChangeSegmentTester tester = mock(StructuralChangeSegmentTester.class);

		when(tester.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		when(tester.isStructureChanged(segmentB, domain, mcn)).thenReturn(false);
		when(tester.isStructureChanged(segmentCList, b, mcn)).thenReturn(false);
		when(segmentResolver.resolveSegment(segmentB, domain)).thenReturn(settingB);
		when(segmentResolver.resolveSegment(segmentCList, b)).thenReturn(settingCList);

		changeTester.addStructuralChangeSegmentTester(tester);

		final boolean isChanged = changeTester.isStructureChanged(dmr, domain, mcn);

		assertFalse(isChanged);
		verify(tester).isStructureChanged(segmentB, domain, mcn);
		verify(tester).isStructureChanged(segmentCList, b, mcn);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.EMFFormsStructuralChangeTesterImpl#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedDontConsiderUnnecessarySegments() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final C c = TestFactory.eINSTANCE.createC();
		domain.setC(c);
		final EReference bToC = TestPackage.eINSTANCE.getB_C();
		final EReference cToA = TestPackage.eINSTANCE.getC_A();

		final VFeatureDomainModelReferenceSegment segmentB = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentB.setDomainModelFeature(bToC.getName());
		final VFeatureDomainModelReferenceSegment segmentC = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segmentC.setDomainModelFeature(cToA.getName());

		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segmentB);
		dmr.getSegments().add(segmentC);

		final Setting settingB = ((InternalEObject) domain).eSetting(bToC);

		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.SET, null, newValue, domain, bToC);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		final StructuralChangeSegmentTester tester0 = mock(StructuralChangeSegmentTester.class);
		final StructuralChangeSegmentTester tester1 = mock(StructuralChangeSegmentTester.class);

		when(tester0.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(1d);
		when(tester1.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(2d);
		when(tester1.isStructureChanged(segmentB, domain, mcn)).thenReturn(true);
		when(segmentResolver.resolveSegment(segmentB, domain)).thenReturn(settingB);

		changeTester.addStructuralChangeSegmentTester(tester0);
		changeTester.addStructuralChangeSegmentTester(tester1);

		final boolean isChanged = changeTester.isStructureChanged(dmr, domain, mcn);

		assertTrue(isChanged);
		verify(tester1).isStructureChanged(segmentB, domain, mcn);
		verify(tester1, never()).isStructureChanged(segmentC, c, mcn);
		verify(tester1, never()).isApplicable(segmentC);
	}
}
