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
 * Edgar Mueller - initial API and implementation
 * Martin Fleck - Bug 490708: Add layout provider service
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
import org.eclipse.emfforms.spi.swt.core.layout.LayoutProvider;
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

	private ServiceReference<ReportService> reportServiceReference;

	private ServiceReference<EMFFormsEditSupport> editSupportServiceReference;

	private ServiceReference<LayoutProvider> layoutServiceReference;

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
		if (editSupportServiceReference != null) {
			context.ungetService(editSupportServiceReference);
		}
		if (reportServiceReference != null) {
			context.ungetService(reportServiceReference);
		}
		if (registerDebugConsumerService != null) {
			registerDebugConsumerService.unregister();
		}
		if (registerInvalidGridConsumerService != null) {
			registerInvalidGridConsumerService.unregister();
		}
		if (layoutServiceReference != null) {
			context.ungetService(layoutServiceReference);
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
		reportServiceReference = bundleContext.getServiceReference(ReportService.class);
		return bundleContext.getService(reportServiceReference);
	}

	/**
	 * Returns the {@link EMFFormsEditSupport} service.
	 *
	 * @return The {@link EMFFormsEditSupport}
	 */
	public EMFFormsEditSupport getEMFFormsEditSupport() {
		editSupportServiceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(EMFFormsEditSupport.class);

		return plugin.getBundle().getBundleContext().getService(editSupportServiceReference);
	}

	/**
	 * Returns the {@link LayoutProvider} service.
	 *
	 * @return The {@link LayoutProvider}
	 */
	public LayoutProvider getLayoutProvider() {
		layoutServiceReference = plugin.getBundle().getBundleContext()
			.getServiceReference(LayoutProvider.class);
		return plugin.getBundle().getBundleContext()
			.getService(layoutServiceReference);
	}

}
