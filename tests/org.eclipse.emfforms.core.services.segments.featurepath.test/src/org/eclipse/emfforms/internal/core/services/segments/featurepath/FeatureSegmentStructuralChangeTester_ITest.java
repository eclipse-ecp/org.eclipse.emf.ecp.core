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
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsSegmentResolver;
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
 * JUnit integration tests for {@link FeatureSegmentStructuralChangeTester}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureSegmentStructuralChangeTester_ITest {

	private static BundleContext bundleContext;
	private ServiceReference<StructuralChangeSegmentTester> serviceReference;
	private StructuralChangeSegmentTester service;
	private EMFFormsSegmentResolver segmentResolver;
	private ReportService reportService;
	private ServiceRegistration<ReportService> reportServiceRegistration;
	private ServiceRegistration<EMFFormsSegmentResolver> segmentResolverRegistration;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(FeatureSegmentStructuralChangeTester_ITest.class).getBundleContext();
	}

	@Before
	public void setUp() {
		reportService = mock(ReportService.class);
		segmentResolver = mock(EMFFormsSegmentResolver.class);

		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);
		segmentResolverRegistration = bundleContext.registerService(EMFFormsSegmentResolver.class, segmentResolver,
			dictionary);

		serviceReference = bundleContext.getServiceReference(StructuralChangeSegmentTester.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		reportServiceRegistration.unregister();
		segmentResolverRegistration.unregister();
	}

	@Test
	public void testServiceType() {
		assertTrue(FeatureSegmentStructuralChangeTester.class.isInstance(service));
	}

	@Test
	public void testIntegration() throws DatabindingFailedException {
		when(segmentResolver.resolveSegment(any(VDomainModelReferenceSegment.class), any(EObject.class)))
			.thenReturn(mock(Setting.class));

		final Notification notification = mock(Notification.class);
		when(notification.isTouch()).thenReturn(false);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);
		final VFeatureDomainModelReferenceSegment segment = mock(VFeatureDomainModelReferenceSegment.class);
		final EObject domain = mock(EObject.class);

		final FeatureSegmentStructuralChangeTester changeTester = (FeatureSegmentStructuralChangeTester) service;

		changeTester.isApplicable(null);
		changeTester.isStructureChanged(segment, domain, mcn);

		verify(reportService).report(any(AbstractReport.class));
		verify(segmentResolver).resolveSegment(segment, domain);
	}

}
