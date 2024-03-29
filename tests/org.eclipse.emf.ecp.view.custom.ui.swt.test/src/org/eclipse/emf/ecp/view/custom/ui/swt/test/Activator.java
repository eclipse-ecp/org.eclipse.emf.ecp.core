/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.custom.ui.swt.test;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator of the custom.ui.swt.test bundle.
 *
 * @author Lucas Koehler
 *
 */
public class Activator extends Plugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.custom.ui.swt.test"; //$NON-NLS-1$

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
		if (reportServiceReference != null) {
			plugin.getBundle().getBundleContext().ungetService(reportServiceReference);
		}
		if (emfFormsDatabindingEMFServiceReference != null) {
			plugin.getBundle().getBundleContext().ungetService(emfFormsDatabindingEMFServiceReference);
		}
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
	private ServiceReference<EMFFormsDatabindingEMF> emfFormsDatabindingEMFServiceReference;

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
		if (emfFormsDatabindingEMFServiceReference == null) {
			emfFormsDatabindingEMFServiceReference = plugin.getBundle().getBundleContext()
				.getServiceReference(EMFFormsDatabindingEMF.class);
		}
		return plugin.getBundle().getBundleContext().getService(emfFormsDatabindingEMFServiceReference);
	}
}
