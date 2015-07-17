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
 * Lucas Köhler - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import java.util.List;

/**
 * A {@link AbstractGridDescription} describes the grid of the renderer using a list of {@link AbstractGridCell
 * GridCells} and the number of rows and columns it has.
 *
 * @author Eugen Neufeld
 * @author Lucas Köhler
 * @param <GRIDCELL> the grid cell type (e.g. SWT or JavaFX grid cell)
 *
 */
public abstract class AbstractGridDescription<GRIDCELL extends AbstractGridCell<?>> {

	private List<GRIDCELL> grid;
	private int rows;

	private int columns;

	/**
	 * Creating an empty grid.
	 */
	public AbstractGridDescription() {

	}

	/**
	 * Creating a filled grid.
	 *
	 * @param rows number of rows in this description
	 * @param columns number of columns in this description
	 * @param grid the List of {@link AbstractGridCell GridCells} describing the grid
	 */
	public AbstractGridDescription(int rows, int columns, List<GRIDCELL> grid) {
		this.grid = grid;
		this.rows = rows;
		this.columns = columns;
	}

	/**
	 * The {@link AbstractGridCell GridCells} describing the grid.
	 *
	 * @return the grid
	 */
	public List<GRIDCELL> getGrid() {
		return grid;
	}

	/**
	 * Number of rows in this Grid.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Number of columns in this grid.
	 *
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * List of {@link AbstractGridCell GridCells}.
	 *
	 * @param grid the grid to set
	 */
	public void setGrid(List<GRIDCELL> grid) {
		this.grid = grid;
	}

	/**
	 * Sets the number of rows.
	 *
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Sets the number of columns.
	 *
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
	}
}