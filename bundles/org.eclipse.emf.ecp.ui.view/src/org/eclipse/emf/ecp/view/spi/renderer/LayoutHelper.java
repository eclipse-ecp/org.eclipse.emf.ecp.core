/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.renderer;

/**
 * By providing an own implementation, the layout of the application can be influenced.
 *
 * @author Eugen Neufeld
 * @param <LAYOUT> the type of layout object this helper provides.
 * @since 1.2
 */
@Deprecated
public interface LayoutHelper<LAYOUT> {

	/**
	 * The Layout for aligning controls vertically in columns.
	 *
	 * @param numColumns the number of columns to create
	 * @param equalWidth whether the columns should be equal width
	 * @return the layout to use
	 */
	LAYOUT getColumnLayout(int numColumns, boolean equalWidth);

	/**
	 * The Layout data to use to set a spanning on an element.
	 *
	 * @param xSpan the horizontal span
	 * @param ySpan the vertical span
	 * @return the layout data to set
	 */
	Object getSpanningLayoutData(int xSpan, int ySpan);

	/**
	 * The Layout data to use to set for a left column.
	 *
	 * @return the layout data to set
	 */
	Object getLeftColumnLayoutData();

	/**
	 * The Layout data to use to set for a right column.
	 *
	 * @param xSpan the horizontal span
	 * @return the layout data to set
	 */
	Object getRightColumnLayoutData(int xSpan);

	/**
	 * The Layout data to use to set for a validation column.
	 *
	 * @return the layout data to set
	 */
	Object getValidationColumnLayoutData();

}
