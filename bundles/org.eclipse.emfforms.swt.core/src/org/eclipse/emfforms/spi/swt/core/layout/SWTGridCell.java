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

import org.eclipse.emf.ecp.view.model.common.AbstractGridCell;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;

/**
 * A {@link SWTGridCell} has a row, a column and a renderer it is rendered by.
 *
 * @author Eugen Neufeld
 * @since 1.3
 *
 */
public class SWTGridCell extends AbstractGridCell<AbstractSWTRenderer<?>> {

	/**
	 * Default constructor to create a grid cell.
	 *
	 * @param row the row of the cell
	 * @param column the column of the cell
	 * @param renderer the {@link AbstractSWTRenderer} that renderes the cell
	 */
	public SWTGridCell(int row, int column, AbstractSWTRenderer<? extends VElement> renderer) {
		super(row, column, renderer);
	}
}
