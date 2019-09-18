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
package org.eclipse.emfforms.spi.ide.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emfforms.internal.ide.preferences.Activator;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Utility class providing access to EMF Forms's eclipse preferences.
 *
 * @author Lucas Koehler
 *
 */
public final class EmfFormsPreferences {

	/** Default value of the segment tooling preference. */
	public static final boolean SEGMENT_TOOLING_DEFAULT = false;

	private static final String SEGMENT_TOOLING = "segmentTooling";

	private EmfFormsPreferences() {
		// hide constructor
	}

	/**
	 * Returns whether segment IDE tooling is enabled.
	 *
	 * @return <code>true</code> if segment tooling is enabled
	 */
	public static boolean isSegmentTooling() {
		return getNode().getBoolean(SEGMENT_TOOLING, SEGMENT_TOOLING_DEFAULT);
	}

	/**
	 * Set whether segment IDE tooling is enabled.
	 *
	 * @param segmentTooling <code>true</code> if segment tooling is enabled
	 */
	public static void setSegmentTooling(boolean segmentTooling) {
		final IEclipsePreferences node = getNode();
		node.putBoolean(SEGMENT_TOOLING, segmentTooling);
		try {
			node.flush();
			node.sync();
		} catch (final BackingStoreException ex) {
			Activator.getDefault().getReportService()
				.report(new AbstractReport(ex, "Could not sync EMFForms Preferences."));
		}
	}

	private static IEclipsePreferences getNode() {
		return InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
	}
}
