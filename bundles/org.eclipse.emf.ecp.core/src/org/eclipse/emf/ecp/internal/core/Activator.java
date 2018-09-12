/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 * Eugen Neufeld - JavaDoc
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.eclipse.emfforms.common.ServiceObjectTracker;
import org.osgi.framework.BundleContext;

/**
 * This is the Activator for the ECP Core plugin.
 *
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public final class Activator extends Plugin {
	/**
	 * The constant holding the id of this plugin.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.core"; //$NON-NLS-1$

	private static Activator instance;

	private static ServiceObjectTracker<ECPRepositoryManager> repositorManagerTracker;
	private static ServiceObjectTracker<ECPProjectManager> projectManagerTracker;
	private static ServiceObjectTracker<ECPProviderRegistry> providerRegistryTracker;
	private static ServiceObjectTracker<ECPObserverBus> observerBusTracker;

	/**
	 * Default constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		super.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (repositorManagerTracker != null) {
			repositorManagerTracker.dispose();
		}
		if (projectManagerTracker != null) {
			projectManagerTracker.dispose();
		}
		if (providerRegistryTracker != null) {
			providerRegistryTracker.dispose();
		}
		if (observerBusTracker != null) {
			observerBusTracker.dispose();
		}
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION
	/**
	 * Returns the instance of this Activator.
	 *
	 * @return the saved instance
	 */
	public static Activator getInstance() {
		return instance;
	}

	/**
	 * Logs and Info message.
	 *
	 * @param message the message to log
	 */
	public static void log(String message) {
		instance.getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
	}

	/**
	 * Logs a message with a specific status.
	 *
	 * @param status the {@link IStatus} value
	 * @param message the message to log
	 */
	public static void log(int status, String message) {
		instance.getLog().log(new Status(status, PLUGIN_ID, message));
	}

	private static void log(IStatus status) {
		instance.getLog().log(status);
	}

	/**
	 * Logs a {@link Throwable}.
	 *
	 * @param t the {@link Throwable} to log
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
	 * Helper method to get the {@link ECPProjectManager}.
	 *
	 * @return the {@link ECPProjectManager}
	 */
	public static synchronized ECPProjectManager getECPProjectManager() {
		if (projectManagerTracker == null) {
			final BundleContext bundleContext = instance.getBundle().getBundleContext();
			projectManagerTracker = new ServiceObjectTracker<ECPProjectManager>(bundleContext, ECPProjectManager.class);
		}
		return projectManagerTracker.getService();
	}

	/**
	 * Helper method to get the {@link ECPRepositoryManager}.
	 *
	 * @return the {@link ECPRepositoryManager}
	 */
	public static synchronized ECPRepositoryManager getECPRepositoryManager() {
		if (repositorManagerTracker == null) {
			final BundleContext bundleContext = instance.getBundle().getBundleContext();
			repositorManagerTracker = new ServiceObjectTracker<ECPRepositoryManager>(bundleContext,
				ECPRepositoryManager.class);
		}
		return repositorManagerTracker.getService();
	}

	/**
	 * Helper method to get the {@link ECPProviderRegistry}.
	 *
	 * @return the {@link ECPProviderRegistry}
	 */
	public static synchronized ECPProviderRegistry getECPProviderRegistry() {
		if (providerRegistryTracker == null) {
			final BundleContext bundleContext = instance.getBundle().getBundleContext();
			providerRegistryTracker = new ServiceObjectTracker<ECPProviderRegistry>(bundleContext,
				ECPProviderRegistry.class);
		}
		return providerRegistryTracker.getService();
	}

	/**
	 * Helper method to get the {@link ECPObserverBus}.
	 *
	 * @return the {@link ECPObserverBus}
	 */
	public static synchronized ECPObserverBus getECPObserverBus() {
		if (observerBusTracker == null) {
			final BundleContext bundleContext = instance.getBundle().getBundleContext();
			observerBusTracker = new ServiceObjectTracker<ECPObserverBus>(bundleContext, ECPObserverBus.class);
		}
		return observerBusTracker.getService();
	}
}
