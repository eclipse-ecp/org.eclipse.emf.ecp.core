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

import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.eclipse.emf.ecp.internal.core.util.observer.ECPObserverBusImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * This is the factory for5 creating the ECPObserverBus service.
 *
 * @author neilmack
 *
 */
public class ECPObserverBusFactory implements ServiceFactory<ECPObserverBus> {

	/**
	 * The session provider used to retrieve the current session.
	 */
	private SessionProvider sessionProvider;

	/**
	 * a map of sessions to services.
	 */
	private final Map<String, ECPObserverBus> sessionRegistry =
		new HashMap<String, ECPObserverBus>();

	/**
	 * default constructor.
	 */
	public ECPObserverBusFactory() {
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
			final BundleContext bundleContext =
				FrameworkUtil.getBundle(getClass()).getBundleContext();
			final ServiceReference<SessionProvider> serviceReference =
				bundleContext.getServiceReference(SessionProvider.class);
			sessionProvider = bundleContext.
				getService(serviceReference);
		}
		return sessionProvider;
	}

	/**
	 * this method returns the ECPObserverBus service for the current session.
	 * It is called by the OSGI framework.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 *
	 * @return the service
	 */
	@Override
	public final ECPObserverBus getService(final Bundle bundle,
		final ServiceRegistration<ECPObserverBus> registration) {

		ECPObserverBus ecpObserverBus;
		final String sessionId = getSessionProvider().getSessionId();

		if (sessionRegistry.containsKey(sessionId)) {
			ecpObserverBus = sessionRegistry.get(sessionId);
		} else {
			ecpObserverBus = new ECPObserverBusImpl();
			sessionRegistry.put(sessionId, ecpObserverBus);
		}
		return ecpObserverBus;
	}

	/**
	 * this methodis called to unget a serive from a service registration.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 * @param service the observerbus
	 */
	@Override
	public void ungetService(final Bundle bundle,
		final ServiceRegistration<ECPObserverBus> registration,
		final ECPObserverBus service) {

	}

}
