/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.internal.ide.util.messages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Lucas Koehler
 * @generated
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.internal.ide.util.messages.messages"; //$NON-NLS-1$
	public static String EcoreHelper_invalidEcore;
	public static String EcoreHelper_invalidEcorePath;
	public static String ViewModelHelper_couldNotFindEcorePath_message;
	public static String ViewModelHelper_couldNotFindEcorePath_nameAndLocation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
