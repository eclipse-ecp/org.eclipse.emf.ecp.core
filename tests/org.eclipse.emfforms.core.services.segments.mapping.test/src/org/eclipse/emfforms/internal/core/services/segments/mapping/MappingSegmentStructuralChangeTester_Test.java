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
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.A;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.mappingsegment.model.VMappingsegmentFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for {@link MappingSegmentStructuralChangeTester}.
 *
 * @author Lucas Koehler
 *
 */
public class MappingSegmentStructuralChangeTester_Test {

	private MappingSegmentStructuralChangeTester changeTester;
	private ReportService reportService;
	private EMFFormsSegmentResolver segmentResolver;

	/**
	 * Sets up a new mapping change tester for every test case.
	 */
	@Before
	public void setUp() throws Exception {
		changeTester = new MappingSegmentStructuralChangeTester();
		reportService = mock(ReportService.class);
		changeTester.setReportService(reportService);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		changeTester.setEMFFormsSegmentResolver(segmentResolver);
	}

	@Test
	public void testIsApplicable() {
		final double score = changeTester.isApplicable(mock(VMappingDomainModelReferenceSegment.class));
		assertEquals(5d, score, 0d);
	}

	@Test
	public void testIsApplicableSegmentNull() {
		final double score = changeTester.isApplicable(null);
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
		verify(reportService).report(any(AbstractReport.class));
	}

	@Test
	public void testIsApplicableWrongSegmentType() {
		final double score = changeTester.isApplicable(mock(VFeatureDomainModelReferenceSegment.class));
		assertEquals(StructuralChangeSegmentTester.NOT_APPLICABLE, score, 0d);
	}

	@Test
	public void testIsStructureChangedRemove() throws DatabindingFailedException {
		final int index = 1;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final Notification notification = new NotificationImpl(Notification.REMOVE, map.get(index), null, index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(map.get(index).getKey());
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, map.get(index).getKey());
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		map.remove(index);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertTrue(isChanged);
		assertEquals(1, map.size());
	}

	@Test
	public void testIsStructureChangedRemoveNoChange() throws DatabindingFailedException {
		final int index = 0;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final Notification notification = new NotificationImpl(Notification.REMOVE, map.get(index), null, index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(key1);
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, key1);
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		map.remove(index);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertFalse(isChanged);
		assertEquals(1, map.size());
	}

	@Test
	public void testIsStructureChangedAdd() throws DatabindingFailedException {
		final int index = 1;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final Notification notification = new NotificationImpl(Notification.ADD, null, map.get(index), index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(map.get(index).getKey());
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, map.get(index).getKey());
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertTrue(isChanged);
		assertEquals(2, map.size());
	}

	@Test
	public void testIsStructureChangedAddNoChange() throws DatabindingFailedException {
		final int index = 1;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final Notification notification = new NotificationImpl(Notification.ADD, null, map.get(index), index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(key0);
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, key0);
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertFalse(isChanged);
		assertEquals(2, map.size());
	}

	@Test
	public void testIsStructureChangedWrongFeature() throws DatabindingFailedException {
		final int index = 0;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();
		final EReference notificationFeature = TestPackage.eINSTANCE.getC_EClassToString();

		map.put(key0, value0);

		final Notification notification = new NotificationImpl(Notification.ADD, null, map.get(index), index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return notificationFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(map.get(index).getKey());
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, map.get(index).getKey());
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertFalse(isChanged);
		assertEquals(1, map.size());
	}

	@Test
	public void testIsStructureChangedAddMany() throws DatabindingFailedException {
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final List<Map.Entry<EClass, A>> newObjects = new LinkedList<Map.Entry<EClass, A>>();
		newObjects.add(map.get(0));
		newObjects.add(map.get(1));

		final Notification notification = new NotificationImpl(Notification.ADD_MANY, null, newObjects) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(key0);
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, key0);
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertTrue(isChanged);
		assertEquals(2, map.size());
	}

	@Test
	public void testIsStructureChangedRemoveMany() throws DatabindingFailedException {
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final EClass key1 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final A value1 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);
		map.put(key1, value1);

		final List<Map.Entry<EClass, A>> oldObjects = new LinkedList<Map.Entry<EClass, A>>();
		oldObjects.add(map.get(0));
		oldObjects.add(map.get(1));

		final Notification notification = new NotificationImpl(Notification.REMOVE_MANY, oldObjects, null) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(key0);
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, key0);
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		map.remove(1);
		map.remove(0);
		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertTrue(isChanged);
		assertEquals(0, map.size());
	}

	@Test
	public void testIsStructureChangedTouch() throws DatabindingFailedException {
		final int index = 0;
		final C domain = TestFactory.eINSTANCE.createC();
		final EClass key0 = EcoreFactory.eINSTANCE.createEClass();
		final A value0 = TestFactory.eINSTANCE.createA();
		final EMap<EClass, A> map = domain.getEClassToA();
		final EReference mapFeature = TestPackage.eINSTANCE.getC_EClassToA();

		map.put(key0, value0);

		final Notification notification = new NotificationImpl(Notification.RESOLVE, map.get(index), map.get(index),
			index) {

			@Override
			public Object getNotifier() {
				return domain;
			}

			@Override
			public Object getFeature() {
				return mapFeature;
			}

		};
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);

		final VMappingDomainModelReferenceSegment mappingSegment = VMappingsegmentFactory.eINSTANCE
			.createMappingDomainModelReferenceSegment();
		mappingSegment.setMappedClass(map.get(index).getKey());
		mappingSegment.setDomainModelFeature(mapFeature.getName());

		final MappedSetting mappedSetting = new MappedSetting(domain, mapFeature, map.get(index).getKey());
		when(segmentResolver.resolveSegment(mappingSegment, domain)).thenReturn(mappedSetting);

		final boolean isChanged = changeTester.isStructureChanged(mappingSegment, domain, mcn);

		assertFalse(isChanged);
		assertEquals(1, map.size());
	}
}
