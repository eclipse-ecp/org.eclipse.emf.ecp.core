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

import org.eclipse.osgi.util.NLS;

/**
 * @author Lucas Koehler
 *
 */
// BEGIN COMPLEX CODE
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emfforms.internal.ide.preferences.messages"; //$NON-NLS-1$
	public static String EmfFormsMainPreferencePage_legacyMode;
	public static String EmfFormsMainPreferencePage_segmentMode;
	public static String EmfFormsMainPreferencePage_toolingModeDescription;
	public static String EmfFormsMainPreferencePage_toolingModeTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
// END COMPLEX CODE