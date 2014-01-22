/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.swt;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for Diff UI.
 * 
 * @author Eugen Neufeld
 * @generated
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.diffmerge.swt.messages"; //$NON-NLS-1$
	public static String DiffDialog_Confirm;
	public static String DiffDialog_DifferenceGroup;
	public static String DiffDialog_leftObject;
	public static String DiffDialog_mainObject;
	public static String DiffDialog_replaceWithLeft;
	public static String DiffDialog_replaceWithRight;
	public static String DiffDialog_rightObject;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
