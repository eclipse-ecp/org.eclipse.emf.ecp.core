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
package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Layout;

/**
 * @author Eugen Neufeld
 * 
 */
public final class DefaultLayoutHelper implements LayoutHelper<Layout> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper#getColumnLayout(int, boolean)
	 */
	public Layout getColumnLayout(int numColumns, boolean equalWidth) {
		return GridLayoutFactory.fillDefaults().numColumns(numColumns).equalWidth(equalWidth).create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper#getSpanningLayoutData(int, int)
	 */
	public Object getSpanningLayoutData(int xSpan, int ySpan) {
		return GridDataFactory.fillDefaults()
			.align(SWT.FILL, SWT.FILL)
			.grab(true, false)
			.span(xSpan, ySpan)
			.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper#getLeftColumnLayoutData()
	 */
	public Object getLeftColumnLayoutData() {
		return GridDataFactory.fillDefaults().grab(false, false)
			.align(SWT.FILL, SWT.CENTER)
			.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper#getRightColumnLayoutData(int)
	 */
	public Object getRightColumnLayoutData(int xSpan) {
		return GridDataFactory.fillDefaults()
			.align(SWT.FILL, SWT.CENTER)
			.grab(true, false).span(xSpan, 1)
			.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.renderer.LayoutHelper#getValidationColumnLayoutData()
	 */
	public Object getValidationColumnLayoutData() {
		return GridDataFactory.fillDefaults()
			.align(SWT.CENTER, SWT.CENTER).hint(16, 17)
			.grab(false, false)
			.create();
	}

}
