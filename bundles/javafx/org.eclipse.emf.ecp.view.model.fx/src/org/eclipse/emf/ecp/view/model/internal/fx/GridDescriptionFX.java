/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.fx;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecp.view.model.common.AbstractGridDescription;

/**
 * A {@link GridDescriptionFX} describes the grid of the JavaFX renderer using a list of {@link GridCellFX GridCells}
 * and the number of rows and columns it has.
 *
 * Use the {@link GridDescriptionFXFactory} to create {@link GridDescriptionFX GridDescriptions}.
 *
 * @author Eugen
 *
 */
public class GridDescriptionFX extends AbstractGridDescription<GridCellFX> {

	/**
	 * Creating an empty grid.
	 */
	public GridDescriptionFX() {

	}

	/**
	 * Creating a filled grid.
	 *
	 * @param rows number of rows in this description
	 * @param columns number of columns in this description
	 * @param grid the List of {@link GridCellFX GridCells} describing the grid
	 */
	public GridDescriptionFX(int rows, int columns, List<GridCellFX> grid) {
		super(rows, columns, grid);
	}

	/**
	 * Creates a copy of the grid.
	 *
	 * @return a copy of the grid
	 */
	public GridDescriptionFX copy() {
		final GridDescriptionFX gd = new GridDescriptionFX(getRows(), getColumns(),
			new ArrayList<GridCellFX>(getGrid()));
		return gd;
	}
}
