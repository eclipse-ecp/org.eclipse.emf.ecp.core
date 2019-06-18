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
 * Mat Hansen - initial API and implementation
 * Christian W. Damus - bug 534829
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Mat Hansen
 * @generated
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.view.spi.table.nebula.grid.messages.messages"; //$NON-NLS-1$
	public static String GridTableViewerComposite_hideColumnAction;
	public static String GridTableViewerComposite_showAllColumnsAction;
	public static String GridTableViewerComposite_toggleFilterControlsAction;
	public static String GridTableViewerComposite_toggleRegexFilterControlsAction;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
