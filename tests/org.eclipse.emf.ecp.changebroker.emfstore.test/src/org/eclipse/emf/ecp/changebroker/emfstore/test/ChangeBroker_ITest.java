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
package org.eclipse.emf.ecp.changebroker.emfstore.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.emf.ecp.changebroker.emfstore.internal.EMFStoreNotificationProvider;
import org.eclipse.emf.ecp.changebroker.spi.ChangeBroker;
import org.eclipse.emf.ecp.changebroker.spi.NotificationProvider;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Tests the integration with the ChangeBroker service
 *
 * @author jfaltermeier
 *
 */
public class ChangeBroker_ITest {

	@SuppressWarnings("restriction")
	@Test
	public void testServiceAvailable() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		final ServiceReference<ChangeBroker> serviceReference = bundleContext.getServiceReference(ChangeBroker.class);
		assertNotNull("Null service reference", serviceReference); //$NON-NLS-1$
		final ChangeBroker service = bundleContext.getService(serviceReference);
		assertNotNull("ChangeBroker is null", service); //$NON-NLS-1$
		assertTrue(org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl.class.isInstance(service));
		final org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl broker = org.eclipse.emf.ecp.changebroker.internal.ChangeBrokerImpl.class
			.cast(service);
		final Set<NotificationProvider> notificationProviders = broker.getNotificationProviders();
		assertEquals(1, notificationProviders.size());
		assertTrue(EMFStoreNotificationProvider.class.isInstance(notificationProviders.iterator().next()));
		bundleContext.ungetService(serviceReference);

	}

}
