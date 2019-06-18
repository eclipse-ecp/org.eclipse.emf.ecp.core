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
package org.eclipse.emfforms.spi.core.services.segments;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Allows to query whether runtime generation of segments is enabled for legacy domain model references.
 *
 * @author Lucas Koehler
 *
 */
public final class RuntimeModeUtil {
	/**
	 * This flag enables the automatic generation of segments from existing DMRs.
	 */
	public static final String SEGMENT_GENERATION = "-enableSegmentGeneration"; //$NON-NLS-1$

	private static boolean segmentMode;

	private RuntimeModeUtil() {
		// Utility class should not be instantiated.
	}

	/**
	 * Returns whether runtime segment generation for legacy domain model references is enabled.
	 * 
	 * @return <code>true</code> if segments should be generated and <code>false</code> otherwise
	 */
	public static boolean isSegmentMode() {
		final String[] applicationArgs = Platform.getApplicationArgs();
		Arrays.stream(applicationArgs).filter(SEGMENT_GENERATION::equals).findFirst()
			.ifPresent(s -> segmentMode = true);
		if (segmentMode) {
			report(new AbstractReport("Segment Generation for legacy DMRs is enabled.", IStatus.INFO)); //$NON-NLS-1$
		}
		return segmentMode;
	}

	private static void report(AbstractReport report) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(RuntimeModeUtil.class).getBundleContext();
		final ServiceReference<ReportService> serviceReference = bundleContext
			.getServiceReference(ReportService.class);
		final ReportService reportService = bundleContext.getService(serviceReference);
		reportService.report(report);
		bundleContext.ungetService(serviceReference);
	}
}
