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

import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.internal.core.ECPProviderRegistryImpl;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * This is the factory for creating the ECPProviderRegistry service.
 *
 * @author neilmack
 *
 */
public class ECPProviderRegistryFactory implements
ServiceFactory<ECPProviderRegistry> {

	/**
	 * The session provider used to retrieve the current session.
	 */
	private SessionProvider sessionProvider;
	/**
	 * a map of sessions to services.
	 */
	private final Map<String, ECPProviderRegistry> sessionRegistry =
		new HashMap<String, ECPProviderRegistry>();

	/**
	 * default constructor.
	 */
	public ECPProviderRegistryFactory() {
		init();
	}

	/**
	 * initialise the factory.
	 */
	public void init() {
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
	 * this method returns the ECPProviderRegistry service for the current session.
	 * It is called by the OSGI framework.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 *
	 * @return the service
	 */
	@Override
	public ECPProviderRegistry getService(Bundle bundle,
		ServiceRegistration<ECPProviderRegistry> registration) {

		ECPProviderRegistry ecpProviderRegistry;
		final String sessionId = getSessionProvider().getSessionId();
		if (sessionRegistry.containsKey(sessionId)) {
			ecpProviderRegistry = sessionRegistry.get(sessionId);
		} else {
			ecpProviderRegistry = new ECPProviderRegistryImpl();
			sessionRegistry.put(sessionId, ecpProviderRegistry);
			((Lifecycle) ecpProviderRegistry).activate();

		}
		return ecpProviderRegistry;
	}

	/**
	 * this method is called to unget a service from a service registration.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 * @param service the service
	 *
	 */
	@Override
	public void ungetService(Bundle bundle,
		ServiceRegistration<ECPProviderRegistry> registration,
		ECPProviderRegistry service) {
		// TODO Auto-generated method stub

	}

}
