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

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

/**
 * A Factory to create {@link SWTGridDescription GridDescriptions}.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public final class GridDescriptionFactory {

	/**
	 * The static Instance of the Factory.
	 */
	public static final GridDescriptionFactory INSTANCE = new GridDescriptionFactory();

	private GridDescriptionFactory() {

	}

	/**
	 * Creates an empty grid description.
	 *
	 * @return the {@link SWTGridDescription}
	 */
	public SWTGridDescription createEmptyGridDescription() {
		return new SWTGridDescription();
	}

	/**
	 * Creates a simple grid based on the number of rows and columns provided.
	 *
	 * @param rows the number of rows in this grid
	 * @param columns the number of columns in this grid
	 * @param renderer the {@link AbstractSWTRenderer}
	 * @return the {@link SWTGridDescription}
	 */
	public SWTGridDescription createSimpleGrid(int rows, int columns, AbstractSWTRenderer<? extends VElement> renderer) {
		final List<SWTGridCell> gridCells = new ArrayList<SWTGridCell>(rows * columns);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				gridCells.add(new SWTGridCell(row, column, renderer));
			}
		}
		return new SWTGridDescription(rows, columns, gridCells);
	}
}
