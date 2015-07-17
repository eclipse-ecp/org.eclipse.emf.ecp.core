/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl;
import org.eclipse.emf.ecp.changebroker.spi.ChangeBroker;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class ChangeBroker_ITest {

	@Test
	public void testServiceAvailable() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		final ServiceReference<ChangeBroker> serviceReference = bundleContext.getServiceReference(ChangeBroker.class);
		assertNotNull("Null service reference", serviceReference); //$NON-NLS-1$
		final ChangeBroker service = bundleContext.getService(serviceReference);
		assertNotNull("ChangeBroker is null", service); //$NON-NLS-1$
		assertTrue(ChangeBrokerImpl.class.isInstance(service));
		bundleContext.ungetService(serviceReference);
	}

}
