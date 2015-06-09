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
package org.eclipse.emfforms.spi.swt.core.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecp.view.model.common.AbstractGridDescription;

/**
 * A {@link SWTGridDescription} describes the grid of the renderer using a list of {@link SWTGridCell GridCells} and the
 * number of rows and columns it has.
 *
 * Use the {@link GridDescriptionFactory} to create {@link SWTGridDescription GridDescriptions}.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public class SWTGridDescription extends AbstractGridDescription<SWTGridCell> {

	/**
	 * Creating an empty grid.
	 */
	public SWTGridDescription() {

	}

	/**
	 * Creating a filled grid.
	 *
	 * @param rows number of rows in this description
	 * @param columns number of columns in this description
	 * @param grid the List of {@link SWTGridCell GridCells} describing the grid
	 */
	public SWTGridDescription(int rows, int columns, List<SWTGridCell> grid) {
		super(rows, columns, grid);
	}

	/**
	 * Creates a copy of the grid.
	 *
	 * @return a copy of the grid
	 */
	public SWTGridDescription copy() {
		final SWTGridDescription gd = new SWTGridDescription(getRows(), getColumns(), new ArrayList<SWTGridCell>(
			getGrid()));
		return gd;
	}
}
