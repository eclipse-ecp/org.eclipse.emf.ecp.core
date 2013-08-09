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
package org.eclipse.emf.ecp.ui.view.swt;

import org.eclipse.emf.ecp.internal.ui.view.renderer.LayoutHelper;
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
	 * @see org.eclipse.emf.ecp.internal.ui.view.renderer.LayoutHelper#getColumnLayout(int, boolean)
	 */
	public Layout getColumnLayout(int numColumns, boolean equalWidth) {
		return GridLayoutFactory.fillDefaults().numColumns(numColumns).equalWidth(equalWidth).create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.internal.ui.view.renderer.LayoutHelper#getSpanningLayoutData(int, int)
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
	 * @see org.eclipse.emf.ecp.internal.ui.view.renderer.LayoutHelper#getLeftColumnLayoutData()
	 */
	public Object getLeftColumnLayoutData() {
		return GridDataFactory.fillDefaults()
			.align(SWT.BEGINNING, SWT.CENTER)
			.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.internal.ui.view.renderer.LayoutHelper#getRightColumnLayoutData()
	 */
	public Object getRightColumnLayoutData() {
		return GridDataFactory.fillDefaults()
			.align(SWT.FILL, SWT.CENTER)
			.grab(true, false)
			.create();
	}

}
