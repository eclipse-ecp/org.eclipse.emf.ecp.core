/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.swt.internal.layout;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.layout.AbstractLayoutProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * A default layout provider.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPLayoutProvider extends AbstractLayoutProvider {

	/**
	 * Default preferred size that lets SWT compute the size on its own.
	 */
	private static final Point DEFAULT_PREFERRED_SIZE = new Point(-1, -1);

	@Override
	public Layout getColumnLayout(int numColumns, boolean equalWidth) {
		return GridLayoutFactory.fillDefaults().numColumns(numColumns)
			.equalWidth(equalWidth).create();
	}

	@Override
	public Layout getColumnLayout(int numColumns, boolean equalWidth, Point margins) {
		return GridLayoutFactory.fillDefaults().numColumns(numColumns).equalWidth(equalWidth).margins(margins).create();
	}

	@Override
	public Object getLayoutData(SWTGridCell gridCell, SWTGridDescription controlGridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		EObject domainModel, Control control) {
		// convert grid descriptions to grid data
		return GridDataFactory
			.fillDefaults()
			.align(getHorizontalAlignment(gridCell),
				getVerticalAlignment(gridCell))
			.hint(getPreferredSize(gridCell))
			.grab(gridCell.isHorizontalGrab(), gridCell.isVerticalGrab())
			.span(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
				- currentRowGridDescription.getColumns(), 1)
			.create();

	}

	private Point getPreferredSize(SWTGridCell gridCell) {
		return gridCell.getPreferredSize() != null ? gridCell.getPreferredSize() : DEFAULT_PREFERRED_SIZE;
	}

	private int getHorizontalAlignment(SWTGridCell gridCell) {
		if (gridCell.isHorizontalFill()) {
			return SWT.FILL;
		}
		if (gridCell.getHorizontalAlignment() != null) {
			return convertAlignment(gridCell.getHorizontalAlignment());
		}
		// Expectations of default behavior?
		return SWT.BEGINNING;
	}

	private int getVerticalAlignment(SWTGridCell gridCell) {
		if (gridCell.isVerticalFill()) {
			return SWT.FILL;
		}
		if (gridCell.getVerticalAlignment() != null) {
			return convertAlignment(gridCell.getVerticalAlignment());
		}
		// Expectations of default behavior?
		return SWT.CENTER;
	}

	private int convertAlignment(SWTGridCell.Alignment alignment) {
		switch (alignment) {
		case BEGINNING:
			return SWT.BEGINNING;

		case END:
			return SWT.END;

		case CENTER:
		default:
			return SWT.CENTER;
		}
	}

	private GridData getSpanningGridData(int xSpan, int ySpan) {
		return GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
			.grab(true, true).span(xSpan, ySpan).create();
	}

	@Override
	public Object getSpanningLayoutData(int spanX, int spanY) {
		return getSpanningGridData(spanX, spanY);
	}

}
