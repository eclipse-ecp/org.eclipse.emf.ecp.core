/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.core.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. **/
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.emfstore.core"; //$NON-NLS-1$

	/** The shared instance. **/
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/** {@inheritDoc} **/
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/** {@inheritDoc} **/
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private static void log(IStatus status) {
		plugin.getLog().log(status);
	}

	/**
	 * Logs a message with a specific status.
	 *
	 * @param status the {@link IStatus} value
	 * @param message the message to log
	 */
	public static void log(int status, String message) {
		plugin.getLog().log(new Status(status, PLUGIN_ID, message));
	}

	/**
	 * Logs a {@link Throwable}.
	 *
	 * @param t the {@link Throwable}
	 * @return the message of the {@link Throwable}
	 */
	public static String log(Throwable t) {
		final IStatus status = getStatus(t);
		log(status);
		return status.getMessage();
	}

	private static IStatus getStatus(Throwable t) {
		if (t instanceof CoreException) {
			final CoreException coreException = (CoreException) t;
			return coreException.getStatus();
		}

		String msg = t.getLocalizedMessage();
		if (msg == null || msg.length() == 0) {
			msg = t.getClass().getName();
		}

		return new Status(IStatus.ERROR, PLUGIN_ID, msg, t);
	}

	/**
	 * Helper method to obtain the relevant ESWorkspaceProvider.
	 *
	 * @return the {@link ESWorkspaceProviderImpl}
	 */
	public static ESWorkspaceProviderImpl getESWorkspaceProviderInstance() {
		// TODO Auto-generated method stub
		ESWorkspaceProviderProvider esWorkspaceProviderProvider = null;
		final ServiceReference<ESWorkspaceProviderProvider> serviceRef = plugin.getBundle().getBundleContext()
			.getServiceReference(ESWorkspaceProviderProvider.class);
		esWorkspaceProviderProvider = plugin.getBundle().getBundleContext().getService(serviceRef);
		// because we are using a service factory for the RAP implementation we must unget
		// the service so that the service factory is called again on each call. otherwise
		// the service factor will keep returning the same cached instance as the reference
		// count of the service will remain greater than zero
		plugin.getBundle().getBundleContext().ungetService(serviceRef);
		return esWorkspaceProviderProvider.getESWorkspaceProviderInstance();
	}
}
