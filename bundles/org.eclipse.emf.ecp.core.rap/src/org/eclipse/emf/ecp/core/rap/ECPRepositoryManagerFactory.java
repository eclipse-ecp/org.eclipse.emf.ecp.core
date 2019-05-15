/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.rap;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.internal.core.ECPRepositoryManagerImpl;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.rap.rwt.service.UISessionEvent;
import org.eclipse.rap.rwt.service.UISessionListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * This is the factory for creating the ECPRepositoryManager service.
 *
 * @author neilmack
 *
 */
public class ECPRepositoryManagerFactory implements
	PrototypeServiceFactory<ECPRepositoryManager>, UISessionListener {

	/**
	 * The session provider used to retrieve the current session.
	 */
	private SessionProvider sessionProvider;
	/**
	 * a map of sessions to services.
	 */
	private final Map<String, ECPRepositoryManager> sessionRegistry = new HashMap<String, ECPRepositoryManager>();

	/**
	 * default constructor.
	 */
	public ECPRepositoryManagerFactory() {
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
	 * this method returns the ECPRepositoryManager service for the
	 * current session.
	 * It is called by the OSGI framework.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 *
	 * @return the service
	 */
	@Override
	public final ECPRepositoryManager getService(final Bundle bundle,
		final ServiceRegistration<ECPRepositoryManager> registration) {
		ECPRepositoryManager ecpRepositoryManager;
		final String sessionId = getSessionProvider().getSessionId();
		getSessionProvider().registerListenerWithSession(this);
		if (sessionRegistry.containsKey(sessionId)) {
			ecpRepositoryManager = sessionRegistry.get(sessionId);
		} else {
			ecpRepositoryManager = new ECPRepositoryManagerImpl();
			sessionRegistry.put(sessionId, ecpRepositoryManager);
			((Lifecycle) ecpRepositoryManager).activate();
		}
		return ecpRepositoryManager;
	}

	/**
	 * this method is called to unget a service from a service registration.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 * @param service the service
	 */
	@Override
	public void ungetService(final Bundle bundle,
		final ServiceRegistration<ECPRepositoryManager> registration,
		final ECPRepositoryManager service) {

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.rap.rwt.service.UISessionListener#beforeDestroy(org.eclipse.rap.rwt.service.UISessionEvent)
	 */
	@Override
	public void beforeDestroy(UISessionEvent event) {
		sessionRegistry.remove(event.getUISession().toString());

	}

}
