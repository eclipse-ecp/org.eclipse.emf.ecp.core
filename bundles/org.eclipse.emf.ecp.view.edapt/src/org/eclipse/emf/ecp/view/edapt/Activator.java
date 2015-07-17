/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The Activator for the edapt view model migration.
 *
 * @author jfaltermeier
 *
 */
public class Activator extends Plugin {

	/**
	 * The constant holding the id of this plugin.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.core"; //$NON-NLS-1$

	private static Activator instance;

	/**
	 * Default constructor.
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		super.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		super.stop(bundleContext);
	}

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

}
