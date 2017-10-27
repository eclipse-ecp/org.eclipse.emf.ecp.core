/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.internal.tooling;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.template.tooling"; //$NON-NLS-1$

	private static Activator plugin;

	private ServiceReference<ReportService> reportServiceReference;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * {@inheritDoc}
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
	 * Log exceptions.
	 *
	 * @param exception the {@link Exception} to log
	 */
	public static void log(Exception exception) {
		plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, exception.getMessage(), exception));
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportServiceReference == null) {
			reportServiceReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ReportService.class);
		}
		return plugin.getBundle().getBundleContext().getService(reportServiceReference);
	}

	/**
	 * Returns the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @return The {@link EMFFormsDatabindingEMF}
	 */
	public EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		final ServiceReference<EMFFormsDatabindingEMF> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsDatabindingEMF.class);

		final EMFFormsDatabindingEMF service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}

}
