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
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.ui.json.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Stefan Dirix
 * @generated
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.emf2web.ui.json.internal.handler.messages.messages"; //$NON-NLS-1$
	public static String ExportJSONFormsHandler_ErrorResolvedViews;
	public static String ExportJSONFormsHandler_ExportCanceled;
	public static String ExportJSONFormsHandler_ViewsNotResolved;
	public static String ExportJSONFormsHandler_ViewsSkipped;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
