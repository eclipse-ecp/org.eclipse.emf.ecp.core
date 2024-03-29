/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.diffmerge.internal.context;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator for this plugin.
 *
 * @author Lucas Koehler
 *
 */
public class Activator extends Plugin {
	/**
	 * The constant holding the id of this plugin.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.diffmerge.context"; //$NON-NLS-1$

	private static Activator plugin;
	private ServiceReference<ReportService> reportServiceReference;

	private ServiceReference<EMFFormsDatabinding> emfformsDatabindingServiceReference;

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		plugin = this;
		super.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (emfformsDatabindingServiceReference != null) {
			bundleContext.ungetService(emfformsDatabindingServiceReference);
		}
		if (reportServiceReference != null) {
			bundleContext.ungetService(reportServiceReference);
		}
		plugin = null;
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION

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
		if (reportServiceReference == null) {
			reportServiceReference = getBundle().getBundleContext().getServiceReference(ReportService.class);
		}
		return getBundle().getBundleContext().getService(reportServiceReference);
	}

	/**
	 * Returns the {@link EMFFormsDatabinding} service.
	 *
	 * @return The {@link EMFFormsDatabinding}
	 */
	public EMFFormsDatabinding getEMFFormsDatabinding() {
		if (emfformsDatabindingServiceReference == null) {
			emfformsDatabindingServiceReference = getBundle().getBundleContext()
				.getServiceReference(EMFFormsDatabinding.class);
		}
		return getBundle().getBundleContext().getService(emfformsDatabindingServiceReference);
	}
}
