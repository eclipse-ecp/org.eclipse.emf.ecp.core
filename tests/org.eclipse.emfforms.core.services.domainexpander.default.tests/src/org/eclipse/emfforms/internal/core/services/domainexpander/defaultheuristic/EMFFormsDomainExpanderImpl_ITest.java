/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * JUnit integration tests for {@link EMFFormsDomainExpanderImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDomainExpanderImpl_ITest {

	private static BundleContext bundleContext;
	private ServiceReference<EMFFormsDomainExpander> serviceReference;
	private EMFFormsDomainExpander service;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(EMFFormsDomainExpanderImpl_ITest.class).getBundleContext();

	}

	@Before
	public void setUp() {
		serviceReference = bundleContext.getServiceReference(EMFFormsDomainExpander.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testServiceType() {
		assertTrue(EMFFormsDomainExpanderImpl.class.isInstance(service));
	}

	@Test
	public void testDMRExpanderUsage() throws EMFFormsExpandingFailedException {
		final EMFFormsDMRExpander dmrExpander = mock(EMFFormsDMRExpander.class);
		final VFeaturePathDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		when(dmrExpander.isApplicable(dmr)).thenReturn(Double.MAX_VALUE);
		bundleContext.registerService(EMFFormsDMRExpander.class, dmrExpander, null);

		service.prepareDomainObject(dmr, mock(EObject.class));

		verify(dmrExpander, atLeastOnce()).isApplicable(dmr);
		verify(dmrExpander).prepareDomainObject(eq(dmr), any(EObject.class));

	}

	@Test
	public void testSegmentExpanderUsage() throws EMFFormsExpandingFailedException {
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		final EObject domainObject1 = mock(EObject.class);
		final EObject domainObject2 = mock(EObject.class);

		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(any(VDomainModelReferenceSegment.class))).thenReturn(Double.MAX_VALUE);
		when(segmentExpander.prepareDomainObject(segment1, domainObject1)).thenReturn(Optional.of(domainObject2));
		when(segmentExpander.needsToExpandLastSegment()).thenReturn(false);

		bundleContext.registerService(EMFFormsDMRSegmentExpander.class, segmentExpander, null);

		service.prepareDomainObject(dmr, domainObject1);

		verify(segmentExpander, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander, atLeastOnce()).isApplicable(segment2);
		verify(segmentExpander).prepareDomainObject(segment1, domainObject1);
		verify(segmentExpander, times(1)).needsToExpandLastSegment();

	}
}
