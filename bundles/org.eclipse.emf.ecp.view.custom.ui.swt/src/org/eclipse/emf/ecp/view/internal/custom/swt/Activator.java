/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.custom.swt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
@SuppressWarnings("deprecation")
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.ui.view.custom.swt"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

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

	// Not API, delegating to reuse same images
	/**
	 * Get an Image based on an path. This just delegates to
	 * {@link org.eclipse.emf.ecp.edit.internal.swt.Activator#getImage(String)}.
	 *
	 * @param path the path of the image to load
	 * @return the loaded image
	 */
	@SuppressWarnings("restriction")
	public static Image getImage(String path) {
		return org.eclipse.emf.ecp.edit.internal.swt.Activator.getImage(path);

	}

	/**
	 * Helper for logging {@link Throwable Throwables}.
	 *
	 * @param throwable the {@link Throwable} to log
	 */
	public static void log(Throwable throwable) {
		plugin.getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, throwable.getMessage(), throwable));
	}

	private ServiceReference<ECPControlFactory> controlFactoryReference;

	private ServiceReference<ReportService> reportServiceReference;

	/**
	 * Returns the {@link ECPControlFactory}.
	 *
	 * @return the {@link ECPControlFactory}
	 */
	public ECPControlFactory getECPControlFactory() {
		if (controlFactoryReference == null) {
			controlFactoryReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ECPControlFactory.class);
		}
		return plugin.getBundle().getBundleContext().getService(controlFactoryReference);
	}

	/**
	 * Frees the {@link ECPControlFactory} from use, allowing the OSGi Bundle to be shutdown.
	 */
	public void ungetECPControlFactory() {
		if (controlFactoryReference == null) {
			return;
		}
		plugin.getBundle().getBundleContext().ungetService(controlFactoryReference);
		controlFactoryReference = null;
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

	/**
	 * Returns the {@link EMFFormsLabelProvider} service.
	 *
	 * @return The {@link EMFFormsLabelProvider}
	 */
	public EMFFormsLabelProvider getEMFFormsLabelProvider() {
		final ServiceReference<EMFFormsLabelProvider> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsLabelProvider.class);

		final EMFFormsLabelProvider service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}
}
