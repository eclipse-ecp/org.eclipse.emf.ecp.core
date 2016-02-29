/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.rap;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.ECPProjectManagerImpl;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * This is the factory for creating the ECPProjectManager service.
 *
 * @author neilmack
 *
 */
public class ECPProjectManagerFactory implements
	ServiceFactory<ECPProjectManager> {

	/**
	 * The session provider used to retrieve the current session.
	 */
	private SessionProvider sessionProvider;
	/**
	 * a map of sessions to services.
	 */
	private final Map<String, ECPProjectManagerImpl> sessionRegistry = new HashMap<String, ECPProjectManagerImpl>();

	/**
	 * default constructor.
	 */
	public ECPProjectManagerFactory() {
		init();
	}

	/**
	 * initialise the factory.
	 */
	public final void init() {
		getSessionProvider();
	}

	/**
	 * this class retrieves the session provider. If the sessionProvider is
	 * not set yet then it is created and set.
	 *
	 * @return the session provider
	 */
	private SessionProvider getSessionProvider() {
		if (sessionProvider == null) {
			final BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
			final ServiceReference<SessionProvider> serviceReference = bundleContext
				.getServiceReference(SessionProvider.class);
			sessionProvider = bundleContext.getService(serviceReference);
		}
		return sessionProvider;
	}

	/**
	 * this method returns the ECPProjectManager service for the
	 * current session.
	 * It is called by the OSGI framework.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 *
	 * @return the service
	 */
	@Override
	public ECPProjectManager getService(Bundle bundle,
		ServiceRegistration<ECPProjectManager> registration) {
		ECPProjectManagerImpl ecpProjectManager;
		final String sessionId = getSessionProvider().getSessionId();
		if (sessionRegistry.containsKey(sessionId)) {
			ecpProjectManager = sessionRegistry.get(sessionId);
		} else {
			ecpProjectManager = new ECPProjectManagerImpl(sessionId);
			ecpProjectManager.setECPObserverBus(ECPUtil.getECPObserverBus());
			sessionRegistry.put(sessionId, ecpProjectManager);
			((Lifecycle) ecpProjectManager).activate();

		}
		return ecpProjectManager;
	}

	/**
	 * this methodis called to unget a serive from a service registration.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 * @param service the service
	 */
	@Override
	public void ungetService(Bundle bundle,
		ServiceRegistration<ECPProjectManager> registration,
		ECPProjectManager service) {

	}

}
