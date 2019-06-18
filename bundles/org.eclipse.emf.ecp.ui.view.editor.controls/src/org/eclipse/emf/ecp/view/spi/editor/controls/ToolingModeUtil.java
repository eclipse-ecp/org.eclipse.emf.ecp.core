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
package org.eclipse.emf.ecp.view.spi.editor.controls;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Utility class that allows to query whether segment or dmr based tooling is used.
 *
 * @author Lucas Koehler
 * @since 1.20
 *
 */
public final class ToolingModeUtil {

	/**
	 * This flag enables the automatic generation of segments from existing DMRs.
	 */
	public static final String ENABLE_SEGMENT_TOOLING = "-enableSegmentTooling"; //$NON-NLS-1$
	private static Boolean isSegmentToolingEnabled;

	// Utility classes should not be instantiated
	private ToolingModeUtil() {
	}

	/**
	 * Returns true if the <strong>-enableSegmentTooling</strong> program argument was set.
	 *
	 * @return <code>true</code> if the tooling creates segment based DMRs, or <code>false</code> if legacy DMRs are
	 *         created
	 */
	public static boolean isSegmentToolingEnabled() {
		if (isSegmentToolingEnabled == null) {
			final String[] applicationArgs = Platform.getApplicationArgs();
			isSegmentToolingEnabled = false;
			Arrays.stream(applicationArgs).filter(ENABLE_SEGMENT_TOOLING::equals).findFirst()
				.ifPresent(s -> isSegmentToolingEnabled = true);
			if (isSegmentToolingEnabled) {
				report(new AbstractReport("Segment based view model tooling is enabled.", IStatus.INFO)); //$NON-NLS-1$
			}
		}
		return isSegmentToolingEnabled;
	}

	private static void report(AbstractReport report) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ToolingModeUtil.class).getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext
			.getServiceReference(ReportService.class);
		final ReportService reportService = bundleContext.getService(serviceReference);
		reportService.report(report);
		bundleContext.ungetService(serviceReference);
	}
}
