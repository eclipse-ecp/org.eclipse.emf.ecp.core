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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.widgets.Control;

/**
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class DoubleColumnRow implements RenderingResultRow<Control> {

	private final Control leftControl;
	private final Control rightControl;

	/**
	 * A {@link RenderingResultRow} which holds two {@link Control Controls}.
	 *
	 * @param leftControl the Control for left Column
	 * @param rightControl the Control for right Column
	 */
	public DoubleColumnRow(Control leftControl, Control rightControl) {
		this.leftControl = leftControl;
		this.rightControl = rightControl;
	}

	/**
	 * @return the leftControl
	 */
	public Control getLeftControl() {
		return leftControl;
	}

	/**
	 * @return the rightControl
	 */
	public Control getRightControl() {
		return rightControl;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow#getMainControl()
	 */
	@Override
	@Deprecated
	public Control getMainControl() {
		return getRightControl();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow#getControls()
	 */
	@Override
	public Set<Control> getControls() {
		final Set<Control> controls = new LinkedHashSet<Control>(2);
		controls.add(leftControl);
		controls.add(rightControl);
		return controls;
	}

}
