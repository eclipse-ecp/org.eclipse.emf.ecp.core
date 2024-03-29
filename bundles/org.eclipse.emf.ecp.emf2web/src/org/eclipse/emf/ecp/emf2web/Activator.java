/*******************************************************************************
 * Copyright (c) 2014-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.emf2web;

import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.emf2web"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	// The bundle context
	private BundleContext bundleContext;

	private ServiceReference<ReportService> reportServiceReference;

	private ServiceReference<EMFFormsDatabindingEMF> databindingServiceReference;

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
		bundleContext = context;
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
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path.
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Returns the {@link BundleContext} for this bundle.
	 *
	 * @return
	 * 		The {@link BundleContext} for this bundle.
	 */
	public BundleContext getBundleContext() {
		return bundleContext;
	}

	/**
	 * Returns the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @return The {@link EMFFormsDatabindingEMF}
	 */
	public EMFFormsDatabindingEMF getEMFFormsDatabinding() {
		if (databindingServiceReference == null) {
			databindingServiceReference = plugin.getBundle().getBundleContext()
				.getServiceReference(EMFFormsDatabindingEMF.class);
		}
		return plugin.getBundle().getBundleContext().getService(databindingServiceReference);
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
}
