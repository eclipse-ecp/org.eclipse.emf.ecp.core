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
package org.eclipse.emfforms.internal.core.services.databinding.featurepath;

import static org.junit.Assert.assertTrue;

import org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * JUnit integration test for the {@link FeatureDomainModelReferenceSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureDomainModelReferenceSegmentConverter_ITest {

	private static BundleContext bundleContext;
	private DomainModelReferenceSegmentConverterEMF service;
	private ServiceReference<DomainModelReferenceSegmentConverterEMF> serviceReference;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(FeatureDomainModelReferenceSegmentConverter_ITest.class)
			.getBundleContext();
	}

	@Before
	public void setUp() {
		serviceReference = bundleContext
			.getServiceReference(DomainModelReferenceSegmentConverterEMF.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testServiceType() {
		assertTrue(FeatureDomainModelReferenceSegmentConverter.class.isInstance(service));

	}

}
