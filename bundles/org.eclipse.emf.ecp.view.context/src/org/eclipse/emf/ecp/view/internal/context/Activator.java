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
package org.eclipse.emf.ecp.view.internal.context;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.view.model.common.internal.reporting.ReportServiceImpl;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportEntity;
import org.eclipse.emf.ecp.view.model.common.spi.reporting.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Class Activator.
 */
public class Activator extends Plugin {

	/**
	 * The constant holding the id of this plugin.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.context"; //$NON-NLS-1$

	/** The instance. */
	private static Activator instance;

	private ServiceReference<ReportService> reportServiceReference;

	/**
	 * Default constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		bundleContext.registerService(ReportService.class, new ReportServiceImpl(), null);
		instance = this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION
	/**
	 * Returns the instance of this Activator.
	 * 
	 * @return the saved instance
	 */
	public static Activator getInstance() {
		return instance;
	}

	/**
	 * Logs a {@link Throwable}.
	 * 
	 * @param t the {@link Throwable} to log
	 */
	public static void log(Throwable t) {
		getInstance().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, t.getMessage(), t));
	}

	/**
	 * Logs a {@link ReportEntity}.
	 * 
	 * @param report
	 *            the {@link ReportEntity} to be logged
	 */
	public static void log(ReportEntity report) {
		getInstance().getLog().log(
			new Status(IStatus.ERROR, // TODO RS: always ERROR?
				PLUGIN_ID,
				report.getMessage(),
				report.getException()));
	}

	/**
	 * Returns the {@link ReportService}.
	 * 
	 * @return the {@link ReportService}
	 */
	public ReportService getReportService() {
		if (reportServiceReference == null) {
			reportServiceReference = instance.getBundle().getBundleContext()
				.getServiceReference(ReportService.class);
		}
		return instance.getBundle().getBundleContext().getService(reportServiceReference);
	}

}
