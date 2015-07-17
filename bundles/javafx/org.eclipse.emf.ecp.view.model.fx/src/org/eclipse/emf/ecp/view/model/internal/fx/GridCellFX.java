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

import org.eclipse.emf.ecp.view.model.common.AbstractGridCell;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A {@link GridCellFX} has a row, a column and a JavaFX renderer it is rendered by.
 *
 * @author Eugen Neufeld
 *
 */
public class GridCellFX extends AbstractGridCell<RendererFX<?>> {

	/**
	 * Default constructor to create a grid cell.
	 *
	 * @param row the row of the cell
	 * @param column the column of the cell
	 * @param renderer the {@link RendererFX} that renders the cell
	 */
	public GridCellFX(int row, int column, RendererFX<? extends VElement> renderer) {
		super(row, column, renderer);
	}
}
