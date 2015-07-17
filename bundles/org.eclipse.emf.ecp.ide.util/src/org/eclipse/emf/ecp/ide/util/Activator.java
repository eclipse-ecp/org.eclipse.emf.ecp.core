/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.ecp.ide.util;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.ide.util"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ReportService reportService;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
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

	/**
	 * Logs a message.
	 *
	 * @param severity the severity of the Message
	 * @param message The warning to log
	 */
	public static void log(int severity, String message) {
		plugin.getReportService().report(new AbstractReport(message, severity));
	}

	/**
	 * Return the {@link ReportService}.
	 *
	 * @return The {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportService == null) {
			final ServiceReference<ReportService> serviceReference = getBundle().getBundleContext()
				.getServiceReference(ReportService.class);
			reportService = getBundle().getBundleContext().getService(serviceReference);
		}
		return reportService;
	}
}
