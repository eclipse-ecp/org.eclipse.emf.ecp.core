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
package org.eclipse.emfforms.internal.core.services.structuralchange.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.internal.core.services.databinding.index.IndexedSetting;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link StructuralChangeSegmentTesterIndex}.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
public class StructuralChangeSegmentTesterIndex_Test {

	private StructuralChangeSegmentTesterIndex changeTester;
	private ReportService reportService;
	private EMFFormsSegmentResolver segmentResolver;

	/**
	 * Sets up a new mapping change tester for every test case.
	 */
	@Before
	public void setUp() throws Exception {
		changeTester = new StructuralChangeSegmentTesterIndex();
		reportService = mock(ReportService.class);
		changeTester.setReportService(reportService);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		changeTester.setEMFFormsSegmentResolver(segmentResolver);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedRemoveIndexedObject() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);

		final Notification notification = new TestNotification(Notification.REMOVE, c1, null, 1, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 1);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(1);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Tests that a change is indicated if a list element with lower index (e.g. 0) than the index of the segment (e.g.
	 * 1) is removed. This should trigger a change because remaining elements are moving up the list when an element
	 * with a lower index is removed. This is only true if there was an element at the index's position before the old
	 * element's removal.
	 * <p>
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedRemoveOnLowerIndexRelevant() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.REMOVE, c0, null, 0, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 1);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(1);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		domain.getCList().remove(0);
		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Tests that a change is indicated if a list element with lower index (e.g. 0) than the index of the segment (e.g.
	 * 1) is removed. This should trigger a change because remaining elements are moving up the list when an element
	 * with a lower index is removed. This is only true if there was an element at the index's position before the old
	 * element's removal.
	 * <p>
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedRemoveOnLowerIndexIrrelevant() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.REMOVE, c0, null, 0, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 2);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(2);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		domain.getCList().remove(0);
		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Tests that a change is indicated if a list element with lower index (e.g. 0) than the index of the segment (e.g.
	 * 1) is added. This should trigger a change because the old elements are moving down the list when an element
	 * with a lower index is added. This is only true, if the list is (now) big enough that there (now) is an element at
	 * the index's position.
	 * <p>
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedAddOnLowerIndexRelevant() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C newC = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.ADD, null, newC, 0, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 2);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(2);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		domain.getCList().add(0, newC);
		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Tests that a change is indicated if a list element with lower index (e.g. 0) than the index of the segment (e.g.
	 * 1) is added. This should trigger a change because the old elements are moving down the list when an element
	 * with a lower index is added. This is only true, if the list is (now) big enough that there (now) is an element at
	 * the index's position.
	 * <p>
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedAddOnLowerIndexIrrelevant() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		final C newC = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.ADD, null, newC, 0, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 3);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(3);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		domain.getCList().add(0, newC);
		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedAddOnIndex() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.ADD, null, c1, 1, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 1);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(1);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedTouchAtIndex() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.RESOLVE, c1, c1, 1, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 1);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(1);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertFalse(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedAddMany() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();
		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final LinkedList<C> added = new LinkedList<C>();
		added.add(c0);
		added.add(c1);

		final Notification notification = new TestNotification(Notification.ADD_MANY, null, added,
			Notification.NO_INDEX,
			domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 4);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(4);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedRemoveMany() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();

		final LinkedList<C> removed = new LinkedList<C>();
		removed.add(c0);
		removed.add(c1);

		final Notification notification = new TestNotification(Notification.REMOVE_MANY, removed, null,
			Notification.NO_INDEX,
			domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 4);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(4);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testIsStructureChangedMove() throws DatabindingFailedException {
		final EReference listFeature = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C c0 = TestFactory.eINSTANCE.createC();
		final C c1 = TestFactory.eINSTANCE.createC();

		domain.getCList().add(c0);
		domain.getCList().add(c1);

		final Notification notification = new TestNotification(Notification.MOVE, 1, c1,
			0, domain, listFeature);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final IndexedSetting setting = new IndexedSetting(domain, listFeature, 4);
		final VIndexDomainModelReferenceSegment segment = VIndexdmrFactory.eINSTANCE
			.createIndexDomainModelReferenceSegment();
		segment.setDomainModelFeature(listFeature.getName());
		segment.setIndex(4);
		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);

		final boolean isChanged = changeTester.isStructureChanged(segment, domain, mcn);

		assertTrue(isChanged);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final double score = changeTester.isApplicable(mock(VIndexDomainModelReferenceSegment.class));
		assertEquals(5d, score, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableNullSegment() {
		final double score = changeTester.isApplicable(null);
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.structuralchange.index.StructuralChangeSegmentTesterIndex#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final double score = changeTester.isApplicable(mock(VFeatureDomainModelReferenceSegment.class));
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
	}

	private class TestNotification extends NotificationImpl {
		private final EObject notifier;
		private final EStructuralFeature feature;

		TestNotification(int eventType, Object oldValue, Object newValue, int position, EObject notifier,
			EStructuralFeature feature) {
			super(eventType, oldValue, newValue, position);
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
