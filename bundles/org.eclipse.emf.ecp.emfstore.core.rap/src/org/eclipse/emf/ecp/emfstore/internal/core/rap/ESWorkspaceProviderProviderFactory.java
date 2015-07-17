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
package org.eclipse.emf.ecp.emfstore.internal.core.rap;

import org.eclipse.emf.ecp.core.rap.SessionProvider;
import org.eclipse.emf.ecp.emfstore.core.internal.ESWorkspaceProviderProvider;
import org.eclipse.emf.ecp.emfstore.core.internal.ESWorkspaceProviderProviderImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * This is the factory for creating the ESWorkspaceProviderProvider service.
 *
 * @author neilmack
 *
 */
public class ESWorkspaceProviderProviderFactory implements ServiceFactory<ESWorkspaceProviderProvider> {

	/**
	 * The session provider used to retrieve the current session.
	 */
	private SessionProvider sessionProvider;

	/**
	 * default constructor.
	 */
	public ESWorkspaceProviderProviderFactory() {
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
			final BundleContext bundleContext =
				FrameworkUtil.getBundle(getClass()).getBundleContext();
			final ServiceReference<SessionProvider> serviceReference =
				bundleContext.getServiceReference(SessionProvider.class);
			sessionProvider = bundleContext.getService(serviceReference);
		}
		return sessionProvider;
	}

	/**
	 * this method returns the ESWorkspaceProviderProvider
	 * service for the current session.
	 * It is called by the OSGI framework.
	 *
	 * @param bundle the OSGI bundle
	 * @param registration the service registration
	 *
	 * @return the service
	 */
	@Override
	public final ESWorkspaceProviderProvider getService(final Bundle bundle,
		final ServiceRegistration<ESWorkspaceProviderProvider> registration) {
		ESWorkspaceProviderProvider esWorkspaceProviderProvider;
		final String sessionId = getSessionProvider().getSessionId();

		esWorkspaceProviderProvider =
			new ESWorkspaceProviderProviderImpl(sessionId);
		return esWorkspaceProviderProvider;
	}

	@Override
	public void ungetService(final Bundle bundle,
		final ServiceRegistration<ESWorkspaceProviderProvider> registration,
		final ESWorkspaceProviderProvider service) {
		// TODO Auto-generated method stub

	}

}
