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
package org.eclipse.emf.ecp.view.spi.model.impl;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.model.internal.reporting.LogConsumer;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public class Activator extends Plugin {
	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.model"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static Activator plugin;

	private ServiceReference<ReportService> reportServiceReference;

	private ServiceRegistration<ReportServiceConsumer> registerLogConsumerService;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		final LogConsumer logConsumer = new LogConsumer();
		registerLogConsumerService = context.registerService(ReportServiceConsumer.class, logConsumer, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registerLogConsumerService.unregister();
		plugin = null;
		super.stop(context);
	}

	// END SUPRESS CATCH EXCEPTION

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Logs exception.
	 *
	 * @param e the {@link Exception} to log
	 */
	public static void logException(Exception e) {
		getDefault().getLog().log(
			new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
	}

	/**
	 * Logs messages.
	 *
	 * @param severity the severity to use for logging
	 * @param message the message to log
	 */
	public static void logMessage(int severity, String message) {
		getDefault().getLog().log(
			new Status(severity, Activator.getDefault().getBundle().getSymbolicName(), message));
	}

	/**
	 * Logs a {@link AbstractReport}.
	 *
	 * @param report
	 *            the {@link AbstractReport} to be logged
	 * @since 1.6
	 */
	public static void log(AbstractReport report) {
		getDefault().getLog().log(
			new Status(report.getSeverity(),
				PLUGIN_ID,
				report.getMessage(),
				report.getException()));
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 * @since 1.6
	 */
	public ReportService getReportService() {
		if (reportServiceReference == null) {
			reportServiceReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ReportService.class);
		}
		return plugin.getBundle().getBundleContext().getService(reportServiceReference);
	}
}
