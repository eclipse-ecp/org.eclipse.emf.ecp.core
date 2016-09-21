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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * JUnit integration tests for {@link EMFFormsStructuralChangeTesterImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsStructuralChangeTesterImpl_ITest {

	private static BundleContext bundleContext;
	private ServiceReference<EMFFormsStructuralChangeTester> serviceReference;
	private EMFFormsStructuralChangeTester service;
	private EMFFormsSegmentResolver segmentResolver;
	private ReportService reportService;
	private ServiceRegistration<ReportService> reportServiceRegistration;
	private ServiceRegistration<EMFFormsSegmentResolver> segmentResolverRegistration;
	private StructuralChangeSegmentTester segmentTester;
	private ServiceRegistration<StructuralChangeSegmentTester> segmentTesterRegistration;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(EMFFormsStructuralChangeTesterImpl_ITest.class).getBundleContext();
	}

	@Before
	public void setUp() {
		reportService = mock(ReportService.class);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		segmentTester = mock(StructuralChangeSegmentTester.class);

		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);
		segmentResolverRegistration = bundleContext.registerService(EMFFormsSegmentResolver.class, segmentResolver,
			dictionary);
		segmentTesterRegistration = bundleContext.registerService(StructuralChangeSegmentTester.class, segmentTester,
			dictionary);

		serviceReference = bundleContext.getServiceReference(EMFFormsStructuralChangeTester.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		reportServiceRegistration.unregister();
		segmentResolverRegistration.unregister();
		segmentTesterRegistration.unregister();
	}

	@Test
	public void testServiceType() {
		assertTrue(EMFFormsStructuralChangeTesterImpl.class.isInstance(service));
	}

	@Test
	public void testIntegration() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference eReference = TestPackage.eINSTANCE.getB_C();
		final Setting setting = ((InternalEObject) domain).eSetting(eReference);
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(eReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segment);

		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.SET, null, newValue, domain, eReference);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		when(segmentResolver.resolveSegment(segment, domain)).thenReturn(setting);
		when(segmentTester.isApplicable(segment)).thenReturn(1d);
		when(segmentTester.isStructureChanged(segment, domain, mcn)).thenReturn(true);

		final boolean isChanged = service.isStructureChanged(dmr, domain, mcn);

		assertTrue(isChanged);
		verify(segmentResolver).resolveSegment(segment, domain);
		verify(segmentTester).isApplicable(segment);
		verify(segmentTester).isStructureChanged(segment, domain, mcn);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testIntegrationReportService() throws DatabindingFailedException {
		final B domain = TestFactory.eINSTANCE.createB();
		final EReference eReference = TestPackage.eINSTANCE.getB_C();
		final VFeatureDomainModelReferenceSegment segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		segment.setDomainModelFeature(eReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(segment);

		final C newValue = TestFactory.eINSTANCE.createC();
		final TestNotification raw = new TestNotification(Notification.SET, null, newValue, domain, eReference);
		final ModelChangeNotification mcn = new ModelChangeNotification(raw);

		when(segmentResolver.resolveSegment(segment, domain)).thenThrow(DatabindingFailedException.class);

		final boolean isChanged = service.isStructureChanged(dmr, domain, mcn);

		assertFalse(isChanged);
		verify(segmentResolver).resolveSegment(segment, domain);
		verify(reportService).report(any(AbstractReport.class));
	}
}
