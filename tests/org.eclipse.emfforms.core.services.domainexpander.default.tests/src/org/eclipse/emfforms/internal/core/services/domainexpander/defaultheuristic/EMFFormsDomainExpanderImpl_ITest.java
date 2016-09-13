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
 * Lucas Koehler - adaption to segments
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
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
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		final VDomainModelReferenceSegment segment1 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final VDomainModelReferenceSegment segment2 = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		dmr.getSegments().add(segment1);
		dmr.getSegments().add(segment2);
		final EObject domainObject = mock(EObject.class);

		final EMFFormsDMRSegmentExpander segmentExpander = mock(EMFFormsDMRSegmentExpander.class);
		when(segmentExpander.isApplicable(segment1)).thenReturn(Double.MAX_VALUE);

		bundleContext.registerService(EMFFormsDMRSegmentExpander.class, segmentExpander, null);

		service.prepareDomainObject(dmr, domainObject);

		verify(segmentExpander, atLeastOnce()).isApplicable(segment1);
		verify(segmentExpander).prepareDomainObject(segment1, domainObject);

	}
}
