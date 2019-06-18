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
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common;

import java.util.LinkedHashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;

/**
 * Helper class to track {@link ServiceObjects} and the services retrieved from them.
 *
 * @param <T> The type to track
 * @since 1.18
 */
public class ServiceObjectTracker<T> {
	private final ServiceObjects<T> serviceObjects;
	private final Set<T> trackedServices = new LinkedHashSet<T>();

	/**
	 * Constructor.
	 *
	 * @param bundleContext The {@link BundleContext} to use
	 * @param clazz The Class of the service to retrieve
	 */
	public ServiceObjectTracker(BundleContext bundleContext, Class<T> clazz) {
		super();
		final ServiceReference<T> serviceRef = bundleContext.getServiceReference(clazz);
		serviceObjects = bundleContext.getServiceObjects(serviceRef);
	}

	/**
	 * Retrieves the Service.
	 *
	 * @return The Service or null
	 * @see ServiceObjects#getService()
	 */
	public T getService() {
		final T service = serviceObjects.getService();
		if (service != null) {
			trackedServices.add(service);
		}
		return service;
	}

	/**
	 * Disposes this services which leads to ungetting all retrieved services.
	 *
	 * @see ServiceObjects#ungetService(Object)
	 */
	public void dispose() {
		for (final T service : trackedServices) {
			serviceObjects.ungetService(service);
		}
	}
}