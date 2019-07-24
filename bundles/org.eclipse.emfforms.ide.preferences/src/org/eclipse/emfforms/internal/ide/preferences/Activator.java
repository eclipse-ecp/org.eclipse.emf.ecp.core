/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.ide.preferences;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Plugin activator for Bundle org.eclipse.emfforms.ide.preferences.
 *
 * @author Lucas Koehler
 *
 */
public class Activator extends Plugin {

	/**
	 * The constant holding the id of this plugin.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emfforms.ide.preferences"; //$NON-NLS-1$

	private static Activator plugin;
	private ServiceReference<ReportService> reportServiceReference;

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		plugin = this;
		super.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
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

}
