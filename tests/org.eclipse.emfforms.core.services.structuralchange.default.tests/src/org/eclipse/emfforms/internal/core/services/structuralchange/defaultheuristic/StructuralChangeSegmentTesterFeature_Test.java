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
package org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link StructuralChangeSegmentTesterFeature}.
 *
 * @author Lucas Koehler
 *
 */
public class StructuralChangeSegmentTesterFeature_Test {

	private StructuralChangeSegmentTesterFeature changeTester;
	private ReportService reportService;
	private EMFFormsSegmentResolver segmentResolver;

	@Before
	public void setUp() {
		changeTester = new StructuralChangeSegmentTesterFeature();
		reportService = mock(ReportService.class);
		changeTester.setReportService(reportService);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		changeTester.setEMFFormsSegmentResolver(segmentResolver);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChanged() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final C c = TestFactory.eINSTANCE.createC();
		final Setting setting = ((InternalEObject) domain).eSetting(feature);

		final Notification rawNotification = new TestNotification(Notification.SET, null, c, domain, feature);
		final ModelChangeNotification mcn = new ModelChangeNotification(rawNotification);

		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());
		domain.setC(c);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedTouch() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final C c = TestFactory.eINSTANCE.createC();
		final Setting setting = ((InternalEObject) domain).eSetting(feature);

		final Notification rawNotification = new TestNotification(Notification.SET, c, c, domain, feature);
		final ModelChangeNotification mcn = new ModelChangeNotification(rawNotification);

		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());
		domain.setC(c);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedAttribute() throws DatabindingFailedException {
		final D domain = TestFactory.eINSTANCE.createD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_X();
		final Setting setting = ((InternalEObject) domain).eSetting(feature);

		final Notification rawNotification = new TestNotification(Notification.SET, null, "Test", domain, feature); //$NON-NLS-1$
		final ModelChangeNotification mcn = new ModelChangeNotification(rawNotification);

		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());
		domain.setX("Test"); //$NON-NLS-1$
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedWrongNotifier() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final B wrongNotifier = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final C c = TestFactory.eINSTANCE.createC();
		final Setting setting = ((InternalEObject) domain).eSetting(feature);

		final Notification rawNotification = new TestNotification(Notification.SET, null, c, wrongNotifier, feature);
		final ModelChangeNotification mcn = new ModelChangeNotification(rawNotification);

		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());
		domain.setC(c);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedWrongFeature() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference segmentFeature = TestPackage.eINSTANCE.getB_C();
		final EReference notificationFeature = TestPackage.eINSTANCE.getB_E();
		final C c = TestFactory.eINSTANCE.createC();
		final Setting setting = ((InternalEObject) domain).eSetting(segmentFeature);

		final Notification rawNotification = new TestNotification(Notification.SET, null, c, domain,
			notificationFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(rawNotification);

		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(segmentFeature.getName());
		domain.setC(c);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final double score = changeTester.isApplicable(mock(VFeatureDomainModelReferenceSegment.class));
		assertEquals(1d, score, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableNullSegment() {
		final double score = changeTester.isApplicable(null);
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic.StructuralChangeSegmentTesterFeature#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final double score = changeTester.isApplicable(mock(VDomainModelReferenceSegment.class));
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
	}

	private class TestNotification extends NotificationImpl {
		private final EObject notifier;
		private final EStructuralFeature feature;

		TestNotification(int eventType, Object oldValue, Object newValue, EObject notifier,
			EStructuralFeature feature) {
			super(eventType, oldValue, newValue);
			this.notifier = notifier;
			this.feature = feature;
		}

		@Override
		public Object getNotifier() {
			return notifier;
		}

		@Override
		public Object getFeature() {
			return feature;
		}
	}
}
