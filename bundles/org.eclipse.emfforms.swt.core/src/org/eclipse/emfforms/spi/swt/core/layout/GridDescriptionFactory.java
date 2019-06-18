/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.core.layout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.swt.graphics.Point;

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
	public SWTGridDescription createSimpleGrid(int rows, int columns,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final List<SWTGridCell> gridCells = new ArrayList<SWTGridCell>(rows * columns);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				gridCells.add(new SWTGridCell(row, column, renderer));
			}
		}
		return new SWTGridDescription(rows, columns, gridCells);
	}

	/**
	 * Creates the default compact grid.
	 *
	 * @param label indicates whether a label cell shall be added
	 * @param validation indicates whether a validation cell shall be added
	 * @param renderer the {@link AbstractSWTRenderer}
	 * @return the {@link SWTGridDescription}
	 *
	 * @since 1.17
	 */
	public SWTGridDescription createCompactGrid(boolean label, boolean validation,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final List<SWTGridCell> gridCells = new LinkedList<SWTGridCell>();
		if (label) {
			gridCells.add(createDefaultCompactLabelCell(0, renderer));
		}
		if (validation) {
			final int validationColumn = label ? 1 : 0;
			gridCells.add(createDefaultCompactValidationCell(validationColumn, renderer));
		}
		final int mainColumn = (label ? 1 : 0) + (validation ? 1 : 0);
		gridCells.add(createDefaultCompactMainCell(mainColumn, renderer));
		return new SWTGridDescription(1, mainColumn + 1, gridCells);
	}

	private SWTGridCell createDefaultCompactLabelCell(int column, AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell labelCell = new SWTGridCell(0, column, renderer);
		labelCell.setHorizontalGrab(false);
		labelCell.setVerticalGrab(false);
		labelCell.setHorizontalFill(false);
		labelCell.setHorizontalAlignment(SWTGridCell.Alignment.BEGINNING);
		labelCell.setVerticalFill(false);
		labelCell.setVerticalAlignment(SWTGridCell.Alignment.BEGINNING);
		labelCell.setPreferredSize(null);
		return labelCell;
	}

	private SWTGridCell createDefaultCompactValidationCell(int column,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell validationCell = new SWTGridCell(0, column, renderer);
		validationCell.setHorizontalGrab(false);
		validationCell.setVerticalGrab(false);
		validationCell.setHorizontalFill(false);
		validationCell.setHorizontalAlignment(SWTGridCell.Alignment.CENTER);
		validationCell.setVerticalFill(false);
		validationCell.setVerticalAlignment(SWTGridCell.Alignment.BEGINNING);
		validationCell.setPreferredSize(new Point(16, 17));
		return validationCell;
	}

	private SWTGridCell createDefaultCompactMainCell(int column,
		AbstractSWTRenderer<? extends VElement> renderer) {
		final SWTGridCell mainCell = new SWTGridCell(0, column, renderer);
		mainCell.setHorizontalGrab(true);
		mainCell.setVerticalGrab(true);
		mainCell.setHorizontalFill(true);
		mainCell.setHorizontalAlignment(SWTGridCell.Alignment.CENTER);
		mainCell.setVerticalFill(true);
		mainCell.setVerticalAlignment(SWTGridCell.Alignment.CENTER);
		mainCell.setPreferredSize(null);
		return mainCell;
	}
}
