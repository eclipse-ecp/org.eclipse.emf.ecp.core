/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.index;

import static org.junit.Assert.assertTrue;

import org.eclipse.emfforms.spi.core.services.databinding.DMRSegmentConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Integration tests for {@link DMRIndexSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class DMRIndexSegmentConverter_ITest {

	private static BundleContext bundleContext;
	private DMRSegmentConverter service;
	private ServiceReference<DMRSegmentConverter> serviceReference;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(DMRIndexSegmentConverter_ITest.class)
			.getBundleContext();
	}

	@Before
	public void setUp() {
		serviceReference = bundleContext.getServiceReference(DMRSegmentConverter.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testServiceType() {
		assertTrue(DMRIndexSegmentConverter.class.isInstance(service));

	}

}
