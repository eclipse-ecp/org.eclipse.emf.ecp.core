/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.internal.diagnostician;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author jfaltermeier
 */
public class Activator extends Plugin {

	/**
	 * The PlugIn ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.validation.diagnostician"; //$NON-NLS-1$

	private static Activator instance;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getInstance() {
		return instance;
	}

	/**
	 * Logs messages.
	 *
	 * @param message the message
	 */
	public static void log(String message) {
		instance.getLog().log(new Status(IStatus.INFO, PLUGIN_ID, message));
	}

	/**
	 * Logs messages and {@link Throwable}.
	 *
	 * @param message the message
	 * @param t the throwable
	 */
	public static void log(String message, Throwable t) {
		if (t instanceof CoreException) {
			final CoreException coreException = (CoreException) t;
			instance.getLog().log(coreException.getStatus());
		} else {
			instance.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, t));
		}
	}

	/**
	 * Logs {@link IStatus}.
	 *
	 * @param status the {@link IStatus}
	 */
	public static void log(IStatus status) {
		instance.getLog().log(status);
	}

	/**
	 * Logs {@link Throwable}.
	 *
	 * @param t the {@link Throwable}
	 * @return the message of the created status
	 */
	public static String log(Throwable t) {
		final IStatus status = getStatus(t);
		log(status);
		return status.getMessage();
	}

	/**
	 * Gets a {@link IStatus} for a throwable.
	 *
	 * @param t the {@link Throwable}
	 * @return the created {@link IStatus}
	 */
	public static IStatus getStatus(Throwable t) {
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
