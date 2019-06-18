/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Edgar - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.view.model.provider.xmi;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator of the bundle.
 *
 * @author Jonas
 *
 */
public class Activator extends Plugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.model.provider.xmi"; //$NON-NLS-1$

	private static Activator activator;

	private static ServiceReference<ReportService> reportServiceReference;

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		activator = this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		activator = null;
	}

	/**
	 * Returns the {@link ReportService}.
	 *
	 * @return the {@link ReportService}
	 */
	public static ReportService getReportService() {
		if (activator == null) {
			return null;
		}
		final Bundle bundle = activator.getBundle();
		if (bundle == null) {
			return null;
		}
		final BundleContext bundleContext = bundle.getBundleContext();
		if (bundleContext == null) {
			return null;
		}
		if (reportServiceReference == null) {
			reportServiceReference = bundleContext.getServiceReference(ReportService.class);
		}
		if (reportServiceReference == null) {
			return null;
		}
		return bundleContext.getService(reportServiceReference);
	}

	/**
	 * Returns the instance of this Activator.
	 *
	 * @return the saved instance
	 */
	public static Activator getInstance() {
		return activator;
	}
}
