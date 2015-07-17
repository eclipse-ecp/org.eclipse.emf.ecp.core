/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
/*
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.ecp.workspace.internal.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author Eike Stepper
 */
public class Activator extends Plugin {
	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.workspace.core"; //$NON-NLS-1$

	private static Activator instance;

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		instance = this;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (WorkspaceProvider.INSTANCE != null) {
			WorkspaceProvider.INSTANCE.dispose();
		}

		instance = null;
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION
	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static final Activator getInstance() {
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
}
