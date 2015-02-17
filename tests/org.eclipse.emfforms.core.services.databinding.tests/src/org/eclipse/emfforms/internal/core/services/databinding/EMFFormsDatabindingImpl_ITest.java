/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler- initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * JUnit integration test for {@link EMFFormsDatabindingImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDatabindingImpl_ITest {

	private static BundleContext bundleContext;
	private EMFFormsDatabinding service;
	private ServiceReference<EMFFormsDatabinding> serviceReference;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(EMFFormsDatabindingImpl_ITest.class).getBundleContext();
	}

	@Before
	public void setUp() {
		serviceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testServiceType() {
		assertTrue(EMFFormsDatabindingImpl.class.isInstance(service));

	}

	@Test
	public void testServiceUsageValue() {
		final DomainModelReferenceConverter converter = mock(DomainModelReferenceConverter.class);
		final VFeaturePathDomainModelReference reference = mock(VFeaturePathDomainModelReference.class);
		when(converter.isApplicable(reference)).thenReturn(0d);
		final ServiceRegistration<DomainModelReferenceConverter> converterService = bundleContext.registerService(
			DomainModelReferenceConverter.class, converter, null);
		service.getValueProperty(reference);
		verify(converter).isApplicable(reference);
		verify(converter).convertToValueProperty(reference);
		converterService.unregister();
	}

	@Test
	public void testServiceUsageList() {
		final DomainModelReferenceConverter converter = mock(DomainModelReferenceConverter.class);
		final VFeaturePathDomainModelReference reference = mock(VFeaturePathDomainModelReference.class);
		when(converter.isApplicable(reference)).thenReturn(0d);
		final ServiceRegistration<DomainModelReferenceConverter> converterService = bundleContext.registerService(
			DomainModelReferenceConverter.class, converter, null);
		service.getListProperty(reference);
		verify(converter).isApplicable(reference);
		verify(converter).convertToListProperty(reference);
		converterService.unregister();
	}
}
