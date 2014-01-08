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
package org.eclipse.emf.ecp.edit.internal.swt.table;

import org.eclipse.osgi.util.NLS;

public final class TableMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecp.edit.internal.swt.table.messages"; //$NON-NLS-1$

	public static String NumberCellEditor_InvalidNumber;
	public static String NumberCellEditor_NumberYouEnteredIsInvalid;
	public static String NumberCellEditor_ValueIsNull;

	private TableMessages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, TableMessages.class);
	}

}
