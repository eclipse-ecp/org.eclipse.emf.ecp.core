/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.swt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.ui.view.swt.DebugSWTReportConsumer;
import org.eclipse.emf.ecp.ui.view.swt.InvalidGridDescriptionReportConsumer;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelUtil;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.common.report.ReportServiceConsumer;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.ui.view.swt"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ServiceRegistration<ReportServiceConsumer> registerDebugConsumerService;

	private ServiceRegistration<ReportServiceConsumer> registerInvalidGridConsumerService;

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
		if (ViewModelUtil.isDebugMode()) {
			registerDebugConsumerService = context.registerService(ReportServiceConsumer.class,
				new DebugSWTReportConsumer(), null);
			registerInvalidGridConsumerService = context.registerService(ReportServiceConsumer.class,
				new InvalidGridDescriptionReportConsumer(), null);
		}
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (registerDebugConsumerService != null) {
			registerDebugConsumerService.unregister();
		}
		if (registerInvalidGridConsumerService != null) {
			registerInvalidGridConsumerService.unregister();
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
	 * Logs exception.
	 *
	 * @param e
	 *            the {@link Exception} to log
	 */
	public static void log(Exception e) {
		getDefault().getLog().log(
			new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e
				.getMessage(), e));
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 */
	public ReportService getReportService() {
		final BundleContext bundleContext = getBundle().getBundleContext();
		final ServiceReference<ReportService> serviceReference =
			bundleContext.getServiceReference(ReportService.class);
		return bundleContext.getService(serviceReference);
	}

	/**
	 * Returns the {@link EMFFormsEditSupport} service.
	 *
	 * @return The {@link EMFFormsEditSupport}
	 */
	public EMFFormsEditSupport getEMFFormsEditSupport() {
		final ServiceReference<EMFFormsEditSupport> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsEditSupport.class);

		final EMFFormsEditSupport service = plugin.getBundle().getBundleContext().getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);
		return service;
	}

	/**
	 * Returns the {@link EMFFormsRendererFactory} service.
	 *
	 * @return The {@link EMFFormsRendererFactory}
	 */
	public EMFFormsRendererFactory getEMFFormsRendererFactory() {
		final ServiceReference<EMFFormsRendererFactory> serviceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsRendererFactory.class);

		final EMFFormsRendererFactory service = plugin.getBundle().getBundleContext()
			.getService(serviceReference);
		plugin.getBundle().getBundleContext().ungetService(serviceReference);

		return service;
	}

}
