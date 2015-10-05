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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * By providing an own implementation, the layout of the application can be influenced.
 *
 * @author Eugen Neufeld
 * @since 1.3
 */
public interface LayoutProvider {

	/**
	 * The Layout for aligning controls vertically in columns.
	 *
	 * @param numColumns the number of columns to create
	 * @param equalWidth whether the columns should be equal width
	 * @return the layout to use
	 */
	Layout getColumnLayout(int numColumns, boolean equalWidth);

	/**
	 * The Layout for aligning controls vertically in columns.
	 *
	 * @param numColumns the number of columns to create
	 * @param equalWidth whether the columns should be equal width
	 * @param margins the margins of the layout
	 * @return the layout to use
	 * @since 1.7
	 */
	Layout getColumnLayout(int numColumns, boolean equalWidth, Point margins);

	/**
	 * The LayoutData to use.
	 *
	 * @param gridCell the current {@link SWTGridCell}
	 * @param controlGridDescription the {@link SWTGridDescription} of the rendered {@link VElement}
	 * @param currentRowGridDescription the {@link SWTGridDescription} of the current row
	 * @param fullGridDescription the {@link SWTGridDescription} of the whole container
	 * @param vElement the {@link VElement} which is currently rendered
	 * @param domainModel The domain model object whose feature is currently rendered
	 * @param control the rendered {@link Control}
	 * @return the Object being the layout data to set
	 * @since 1.6
	 */
	Object getLayoutData(SWTGridCell gridCell, SWTGridDescription controlGridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		EObject domainModel, Control control);

	/**
	 * A simple spanning Layout.
	 *
	 * @param spanX the horizontal span
	 * @param spanY the vertical span
	 * @return the created Layout Data
	 */
	Object getSpanningLayoutData(int spanX, int spanY);
}
