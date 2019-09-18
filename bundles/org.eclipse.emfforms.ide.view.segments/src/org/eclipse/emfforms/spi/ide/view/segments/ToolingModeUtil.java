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
package org.eclipse.emfforms.spi.ide.view.segments;

import org.eclipse.emfforms.spi.ide.preferences.EmfFormsPreferences;

/**
 * Utility class that allows to query whether segment or dmr based tooling is used.
 *
 * @author Lucas Koehler
 * @since 1.22
 *
 */
public final class ToolingModeUtil {

	/**
	 * This flag enables the automatic generation of segments from existing DMRs.
	 *
	 * @deprecated The tooling mode is no longer defined via runtime parameter but by a workspace preference. See
	 *             {@link EmfFormsPreferences#isSegmentTooling()}
	 */
	@Deprecated
	public static final String ENABLE_SEGMENT_TOOLING = "-enableSegmentTooling"; //$NON-NLS-1$

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
		return EmfFormsPreferences.isSegmentTooling();
	}
}
