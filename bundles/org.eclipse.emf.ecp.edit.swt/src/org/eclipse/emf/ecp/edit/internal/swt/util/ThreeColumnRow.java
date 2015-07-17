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
package org.eclipse.emf.ecp.edit.internal.swt.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.widgets.Control;

/**
 * @author Eugen
 *
 */
@Deprecated
public class ThreeColumnRow implements RenderingResultRow<Control> {

	private final Control leftControl;
	private final Control middleControl;
	private final Control rightControl;

	/**
	 * A {@link RenderingResultRow} which holds two {@link Control Controls}.
	 *
	 * @param leftControl the Control for left Column
	 * @param middleControl the Control for middle Column
	 * @param rightControl the Control for right Column
	 */
	public ThreeColumnRow(Control leftControl, Control middleControl, Control rightControl) {
		this.leftControl = leftControl;
		this.rightControl = rightControl;
		this.middleControl = middleControl;
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
	 * @return the rightControl
	 */
	public Control getMiddleControl() {
		return middleControl;
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
		controls.add(middleControl);
		controls.add(rightControl);
		return controls;
	}

}
