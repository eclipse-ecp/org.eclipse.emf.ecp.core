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

import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A Factory to create {@link GridDescriptionFX}.
 *
 * @author Eugen Neufeld
 *
 */
public final class GridDescriptionFXFactory {

	/**
	 * The static Instance of the Factory.
	 */
	public static final GridDescriptionFXFactory INSTANCE = new GridDescriptionFXFactory();

	private GridDescriptionFXFactory() {

	}

	/**
	 * Creates an empty grid description.
	 *
	 * @return the {@link GridDescriptionFX}
	 */
	public GridDescriptionFX createEmptyGridDescription() {
		return new GridDescriptionFX();
	}

	/**
	 * Creates a simple grid based on the number of rows and columns provided.
	 *
	 * @param rows the number of rows in this grid
	 * @param columns the number of columns in this grid
	 * @param renderer the {@link RendererFX}
	 * @return the {@link GridDescriptionFX}
	 */
	public GridDescriptionFX createSimpleGrid(int rows, int columns, RendererFX<? extends VElement> renderer) {
		final List<GridCellFX> gridCells = new ArrayList<GridCellFX>(rows * columns);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				gridCells.add(new GridCellFX(row, column, renderer));
			}
		}
		return new GridDescriptionFX(rows, columns, gridCells);
	}
}
