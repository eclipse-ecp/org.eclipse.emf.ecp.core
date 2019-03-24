/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.layout;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * This Layout Optimizer caches the incoming layout request for 200ms before triggering the layout.
 *
 * @author Eugen Neufeld
 * @since 1.12
 *
 */
public class EMFFormsSWTLayoutDelayed implements EMFFormsSWTLayoutOptimizer {

	private Set<Composite> requestedLayouts = new LinkedHashSet<Composite>();
	private Thread thread;
	private ReportService reportService;

	/**
	 * <p>
	 * This method will collect layoutrequest that happen in the same 200ms. When there are multiple layoutrequest for
	 * the same composite in this time frame, the composite will only be layouted once.
	 * </p>
	 * <p>
	 * This will help to improve performance as layout request are usually expensive. Also it might be quite common that
	 * e.g. multiple hide rules are triggered by the same condition.
	 * </p>
	 *
	 * @param parent the composite to layout
	 */
	@Override
	public synchronized void layout(Composite parent) {
		getRequestedLayouts().add(parent);
		layoutDelayed();
	}

	private synchronized void layoutDelayed() {
		if (thread != null || getRequestedLayouts().isEmpty()) {
			return;
		}
		final Display defaultDisplay = Display.getDefault();
		thread = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (final InterruptedException ex) {
				/* silent */
			}
			final Set<Composite> toLayout = exchangeRequestedLayouts();

			defaultDisplay.asyncExec(() -> {
				// BEGIN COMPLEX CODE
				try {
					for (final Composite composite : toLayout) {
						if (composite.isDisposed()) {
							continue;
						}
						composite.layout(true, true);
					}
				} catch (final Exception ex) {
					getReportService().report(new AbstractReport(ex, "An exception occurred during re-layouting")); //$NON-NLS-1$
				} finally {
					// To avoid memory leaks we need to reset the thread in any case.
					thread = null;
					layoutDelayed();
				}
				// END COMPLEX CODE
			});

		});
		thread.start();
	}

	private ReportService getReportService() {
		if (reportService == null) {
			final Bundle bundle = FrameworkUtil.getBundle(EMFFormsSWTLayoutDelayed.class);
			if (bundle == null) {
				return null;
			}
			final BundleContext bundleContext = bundle.getBundleContext();
			if (bundleContext == null) {
				return null;
			}
			final ServiceReference<ReportService> serviceReference = bundleContext
				.getServiceReference(ReportService.class);
			reportService = bundleContext.getService(serviceReference);
		}
		return reportService;
	}

	private synchronized Set<Composite> getRequestedLayouts() {
		return requestedLayouts;
	}

	private synchronized void setRequestedLayouts(Set<Composite> requestedLayouts) {
		this.requestedLayouts = requestedLayouts;
	}

	private synchronized Set<Composite> exchangeRequestedLayouts() {
		final Set<Composite> toLayout = new LinkedHashSet<Composite>(getRequestedLayouts());
		setRequestedLayouts(new LinkedHashSet<Composite>());
		return toLayout;
	}
}
