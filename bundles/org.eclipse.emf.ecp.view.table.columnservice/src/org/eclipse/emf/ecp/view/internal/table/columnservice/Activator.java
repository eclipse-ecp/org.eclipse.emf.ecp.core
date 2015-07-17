/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.columnservice;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator of the view.table.columnservice bundle.
 *
 * @author Lucas Koehler
 *
 */
public class Activator extends Plugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.table.columnservice"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

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

	private ServiceReference<ReportService> reportServiceReference;

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
	 * Returns the {@link EMFFormsDatabinding} service.
	 *
	 * @return The {@link EMFFormsDatabinding}
	 */
	public EMFFormsDatabinding getEMFFormsDatabinding() {
		final ServiceReference<EMFFormsDatabinding> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsDatabinding.class);

		final EMFFormsDatabinding service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}
}
