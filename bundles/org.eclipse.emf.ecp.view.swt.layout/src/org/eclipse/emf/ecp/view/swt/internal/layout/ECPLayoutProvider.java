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
import org.eclipse.emf.ecp.view.spi.model.VControl;
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
import org.eclipse.swt.widgets.Text;

/**
 * A default layout provider.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPLayoutProvider extends AbstractLayoutProvider {

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
		if (VControl.class.isInstance(vElement)) {
			// last column of control
			if (gridCell.getColumn() + gridCell.getHorizontalSpan() == controlGridDescription.getColumns()) {
				return getControlGridData(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
					- currentRowGridDescription.getColumns(), VControl.class.cast(vElement), domainModel, control);
			} else if (controlGridDescription.getColumns() == 3 && gridCell.getColumn() == 0) {
				return getLabelGridData();
			} else if (controlGridDescription.getColumns() == 3 && gridCell.getColumn() == 1) {
				return getValidationGridData();
			} else if (controlGridDescription.getColumns() == 2 && gridCell.getColumn() == 0) {
				return getValidationGridData();
			}
		}
		// we have some kind of container -> render with necessary span

		return GridDataFactory
			.fillDefaults()
			.align(gridCell.isHorizontalFill() ? SWT.FILL : SWT.BEGINNING,
				gridCell.isVerticalFill() ? SWT.FILL : SWT.CENTER)
			.grab(gridCell.isHorizontalGrab(), gridCell.isVerticalGrab())
			.span(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
				- currentRowGridDescription.getColumns(), 1)
			.create();

	}

	private GridData getLabelGridData() {
		return GridDataFactory.fillDefaults().grab(false, false)
			.align(SWT.BEGINNING, SWT.CENTER).create();
	}

	private GridData getValidationGridData() {
		return GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER)
			.hint(16, 17).grab(false, false).create();
	}

	private GridData getControlGridData(int xSpan, VControl vControl, EObject domainModel, Control control) {
		GridDataFactory gdf = GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
			.grab(true, false).span(xSpan, 1);

		if (Text.class.isInstance(control) && vControl.getDomainModelReference() != null) {
			if (isMultiLine(vControl.getDomainModelReference(), domainModel)) {
				gdf = gdf.hint(50, 200); // set x hint to enable wrapping
			}
		}

		return gdf.create();
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
