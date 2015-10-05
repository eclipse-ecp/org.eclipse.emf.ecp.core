/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core.converter;

/**
 * This defines constants for retrieving predefines cell styles from the ViewModelContext.
 *
 * @author Eugen Neufeld
 *
 */
public final class EMFFormsCellStyleConstants {
	private EMFFormsCellStyleConstants() {
	}

	/**
	 * Constant for a cell style that locks the cell.
	 */
	public static final String LOCKED = "CellStyle_Locked"; //$NON-NLS-1$
	/**
	 * Constant for a cell style that locks the cell and wraps the text.
	 */
	public static final String LOCKED_AND_WRAPPED = "CellStyle_LockedWrapped"; //$NON-NLS-1$
	/**
	 * Constant for a cell style that sets the cell format to text.
	 */
	public static final String TEXT = "CellStyle_Text"; //$NON-NLS-1$
	/**
	 * Constant for a cell style that sets the cell format to date.
	 */
	public static final String DATE = "CellStyle_Date"; //$NON-NLS-1$
}
