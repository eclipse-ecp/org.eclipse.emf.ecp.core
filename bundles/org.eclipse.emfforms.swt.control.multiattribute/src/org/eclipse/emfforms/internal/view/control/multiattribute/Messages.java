/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.control.multiattribute;

import org.eclipse.osgi.util.NLS;

/**
 * Messages file.
 *
 * @generated
 *
 * @author David Soto Setzke
 * @author Johannes Faltermeier
 *
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emfforms.internal.view.control.multiattribute.messages"; //$NON-NLS-1$
	/**
	 * Add Existing Tooltip.
	 */
	public static String MultiAttributeSWTRenderer_addExistingTooltip;
	public static String MultiAttributeSWTRenderer_AddFailed;
	/**
	 * Add New Tooltip.
	 */
	public static String MultiAttributeSWTRenderer_addNewTooltip;
	public static String MultiAttributeSWTRenderer_deleteTooltip;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
