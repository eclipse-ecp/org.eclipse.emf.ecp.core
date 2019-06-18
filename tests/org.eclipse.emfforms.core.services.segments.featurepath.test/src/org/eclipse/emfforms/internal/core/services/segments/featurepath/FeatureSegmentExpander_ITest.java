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

import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * JUnit integration tests for the {@link FeatureSegmentExpander}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureSegmentExpander_ITest {

	private static BundleContext bundleContext;
	private ServiceReference<EMFFormsDMRSegmentExpander> serviceReference;
	private EMFFormsDMRSegmentExpander service;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(FeatureSegmentExpander_ITest.class).getBundleContext();

	}

	@Before
	public void setUp() {
		serviceReference = bundleContext.getServiceReference(EMFFormsDMRSegmentExpander.class);
		service = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testServiceTypeAndRegistration() {
		assertTrue(FeatureSegmentExpander.class.isInstance(service));
	}

}
