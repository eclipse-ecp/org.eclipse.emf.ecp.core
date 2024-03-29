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
 * Eugen Neufeld - initial API and implementation
 * Lucas Köhler - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

/**
 * A {@link AbstractGridCell} has a row, a column and a renderer it is rendered by a renderer.
 *
 * @author Eugen Neufeld
 * @author Lucas Köhler
 * @param <RENDERER> the renderer type (e.g. SWT or JavaFX renderer)
 */
public abstract class AbstractGridCell<RENDERER extends AbstractRenderer<?>> {

	private final int row;
	private final int column;
	private RENDERER renderer;

	private int horizontalSpan = 1;
	private boolean verticalGrab = true;
	private boolean verticalFill = true;
	private boolean horizontalGrab = true;
	private boolean horizontalFill = true;
	private Alignment horizontalAlignment = Alignment.BEGINNING;
	private Alignment verticalAlignment = Alignment.CENTER;

	/**
	 * Either horizontal or vertical alignment within the cell.
	 * For filling the complete horizontal or vertical space, use {@link AbstractGridCell#setHorizontalFill(boolean)} or
	 * {@link AbstractGridCell#setVerticalFill(boolean)} respectively.
	 *
	 * @author Martin Fleck
	 * @since 1.9
	 *
	 */
	public enum Alignment {
		/**
		 * Align cell at the horizontal (left) or vertical (top) beginning.
		 */
		BEGINNING,
		/**
		 * Align cell at the horizontal or vertical center.
		 */
		CENTER,
		/**
		 * Align cell at the horizontal (right) or vertical (bottom) end.
		 */
		END
	}

	/**
	 * Default constructor to create a grid cell.
	 *
	 * @param row the row of the cell
	 * @param column the column of the cell
	 * @param renderer the {@link AbstractRenderer} that renderes the cell
	 */
	public AbstractGridCell(int row, int column, RENDERER renderer) {
		super();
		this.row = row;
		this.column = column;
		this.renderer = renderer;
	}

	/**
	 * The id of the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * The id of the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return the renderer
	 */
	public RENDERER getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer the renderer to set
	 */
	public void setRenderer(RENDERER renderer) {
		this.renderer = renderer;
	}

	/**
	 * Return the horizontalSpan of this control. Default is 1.
	 *
	 * @return the horizontalSpan
	 */
	public int getHorizontalSpan() {
		return horizontalSpan;
	}

	/**
	 * Set the horizontal span of this cell.
	 *
	 * @param horizontalSpan the horizontalSpan to set
	 */
	public void setHorizontalSpan(int horizontalSpan) {
		this.horizontalSpan = horizontalSpan;
	}

	/**
	 * Returns whether this cell will take all available vertical space.
	 *
	 * @return the verticalGrab
	 */
	public boolean isVerticalGrab() {
		return verticalGrab;
	}

	/**
	 * True if all available vertical space should be taken.
	 *
	 * @param verticalGrab the verticalGrab to set
	 */
	public void setVerticalGrab(boolean verticalGrab) {
		this.verticalGrab = verticalGrab;
	}

	/**
	 * Returns whether the control should fill the available vertical space.
	 *
	 * @return the verticalFill
	 */
	public boolean isVerticalFill() {
		return verticalFill;
	}

	/**
	 * True if the available vertical space should be filled.
	 *
	 * @param verticalFill the verticalFill to set
	 */
	public void setVerticalFill(boolean verticalFill) {
		this.verticalFill = verticalFill;
	}

	/**
	 * Returns whether this cell will take all available horizontal space.
	 *
	 * @return the horizontalGrab
	 */
	public boolean isHorizontalGrab() {
		return horizontalGrab;
	}

	/**
	 * True if all available horizontal space should be taken.
	 *
	 * @param horizontalGrab the horizontalGrab to set
	 */
	public void setHorizontalGrab(boolean horizontalGrab) {
		this.horizontalGrab = horizontalGrab;
	}

	/**
	 * Returns whether the control should fill the available horizontal space.
	 *
	 * @return the horizontalFill
	 */
	public boolean isHorizontalFill() {
		return horizontalFill;
	}

	/**
	 * True if the available horizontal space should be filled.
	 *
	 * @param horizontalFill the horizontalFill to set
	 */
	public void setHorizontalFill(boolean horizontalFill) {
		this.horizontalFill = horizontalFill;
	}

	/**
	 * Returns the horizontal alignment of the control that is used if horizontal fill is set to false. Default is
	 * {@link Alignment#BEGINNING}.
	 *
	 * @return the horizontalAlignment
	 * @since 1.9
	 */
	public Alignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	/**
	 * Set the horizontal alignment. This alignment is only considered if horizontal fill is set to false.
	 *
	 * @param horizontalAlignment the horizontalAlignment to set
	 * @since 1.9
	 */
	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	/**
	 * Returns the vertical alignment of the control that is used if vertical fill is set to false. Default is
	 * {@link Alignment#CENTER}.
	 *
	 * @return the verticalAlignment
	 * @since 1.9
	 */
	public Alignment getVerticalAlignment() {
		return verticalAlignment;
	}

	/**
	 * Set the vertical alignment. This alignment is only considered if vertical fill is set to false.
	 *
	 * @param verticalAlignment the verticalAlignment to set
	 * @since 1.9
	 */
	public void setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + (renderer == null ? 0 : renderer.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("unchecked")
		final AbstractGridCell<RENDERER> other = (AbstractGridCell<RENDERER>) obj;
		if (column != other.column) {
			return false;
		}
		if (renderer == null) {
			if (other.renderer != null) {
				return false;
			}
		} else if (!renderer.equals(other.renderer)) {
			return false;
		}
		return true;
	}

}
