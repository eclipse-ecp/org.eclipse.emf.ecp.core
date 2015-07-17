/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.spreadsheet.core;

/**
 * Helper class which describes where a value should be rendered to.
 *
 * @author Eugen Neufeld
 */
public final class EMFFormsSpreadsheetRenderTarget {

	private final String sheetName;
	private final int row;
	private int column;

	/**
	 * Default Constructor.
	 *
	 * @param sheetName The name of the sheet
	 * @param row The row id to render on
	 * @param column The column id to render on
	 */
	public EMFFormsSpreadsheetRenderTarget(String sheetName, int row, int column) {
		this.sheetName = sheetName;
		this.row = row;
		this.column = column;
	}

	/**
	 * Retrieve the current column id.
	 *
	 * @return The column id
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Set the column id.
	 *
	 * @param column The column id to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Retrieve the sheet name.
	 *
	 * @return The sheet name
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * Retrieve the row id.
	 *
	 * @return The row id
	 */
	public int getRow() {
		return row;
	}

}
