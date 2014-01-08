/*******************************************************************************
 * Copyright (c) 2013 EclipseSource.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * EclipseSource - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.reference;

import org.eclipse.osgi.util.NLS;

public final class ActionMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.edit.internal.swt.reference.messages"; //$NON-NLS-1$

	public static String AddReferenceAction_Link;

	public static String DeleteReferenceAction_Confirmation;
	public static String DeleteReferenceAction_DeleteModelQuestion;
	public static String DeleteReferenceAction_DeleteReference;
	public static String DeleteReferenceAction_Yes;
	public static String DeleteReferenceAction_No;
	public static String DeleteReferenceAction_Questionmark;

	public static String NewReferenceAction_CreateAndLinkNew;

	private ActionMessages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
	}

}
