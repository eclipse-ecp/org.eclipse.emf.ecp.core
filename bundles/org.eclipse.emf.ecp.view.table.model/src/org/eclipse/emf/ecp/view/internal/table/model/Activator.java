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
package org.eclipse.emf.ecp.view.internal.table.model;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.common.ServiceObjectTracker;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 *
 * @author Lucas Koehler
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.table.model"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ServiceObjectTracker<ReportService> reportServiceTracker;
	private ServiceObjectTracker<EMFFormsDatabindingEMF> emfformsDatadingTracker;

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
		if (emfformsDatadingTracker != null) {
			emfformsDatadingTracker.dispose();
		}
		if (reportServiceTracker != null) {
			reportServiceTracker.dispose();
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

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportServiceTracker == null) {
			reportServiceTracker = new ServiceObjectTracker<ReportService>(
				plugin.getBundle().getBundleContext(), ReportService.class);
		}
		return reportServiceTracker.getService();
	}

	/**
	 * Returns the {@link EMFFormsDatabinding} service.
	 *
	 * @return The {@link EMFFormsDatabinding}
	 */
	public EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		if (emfformsDatadingTracker == null) {
			emfformsDatadingTracker = new ServiceObjectTracker<EMFFormsDatabindingEMF>(
				plugin.getBundle().getBundleContext(), EMFFormsDatabindingEMF.class);
		}
		return emfformsDatadingTracker.getService();
	}
}
