/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.index;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Optional;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsSegmentResolver;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeSegmentTester;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * JUnit integration tests for {@link IndexSegmentStructuralChangeTester}.
 *
 * @author Lucas Koehler
 *
 */
public class IndexSegmentStructuralChangeTester_ITest {

	private static BundleContext bundleContext;
	private static ServiceRegistration<ReportService> reportServiceRegistration;
	private static ServiceRegistration<EMFFormsSegmentResolver> segmentResolverRegistration;
	private static EMFFormsSegmentResolver segmentResolver;
	private static ReportService reportService;
	private Collection<ServiceReference<StructuralChangeSegmentTester>> serviceReferences;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(IndexSegmentStructuralChangeTester_ITest.class).getBundleContext();
		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);
		reportService = mock(ReportService.class);
		segmentResolver = mock(EMFFormsSegmentResolver.class);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);
		segmentResolverRegistration = bundleContext.registerService(EMFFormsSegmentResolver.class, segmentResolver,
			dictionary);
	}

	@Before
	public void setUp() throws InvalidSyntaxException {
		reset(reportService, segmentResolver);
		serviceReferences = bundleContext.getServiceReferences(StructuralChangeSegmentTester.class, null);
	}

	@AfterClass
	public static void tearDownAfterAll() {
		reportServiceRegistration.unregister();
		segmentResolverRegistration.unregister();
	}

	@Test
	public void testIntegration() throws DatabindingFailedException {
		final Optional<IndexSegmentStructuralChangeTester> indexService = serviceReferences.stream()
			.map(ref -> bundleContext.getService(ref))
			.filter(IndexSegmentStructuralChangeTester.class::isInstance)
			.findFirst()
			.map(IndexSegmentStructuralChangeTester.class::cast);

		assertTrue("IndexSegmentStructuralChangeTester present: ", indexService.isPresent()); //$NON-NLS-1$
		when(segmentResolver.resolveSegment(any(VDomainModelReferenceSegment.class), any(EObject.class)))
			.thenReturn(mock(Setting.class));

		final Notification notification = mock(Notification.class);
		when(notification.isTouch()).thenReturn(false);
		final ModelChangeNotification mcn = new ModelChangeNotification(notification);
		final VIndexDomainModelReferenceSegment segment = mock(VIndexDomainModelReferenceSegment.class);
		final EObject domain = mock(EObject.class);

		final IndexSegmentStructuralChangeTester changeTester = indexService.get();

		changeTester.isApplicable(null);
		changeTester.isStructureChanged(segment, domain, mcn);

		verify(reportService).report(any(AbstractReport.class));
		verify(segmentResolver).resolveSegment(segment, domain);
	}

}
