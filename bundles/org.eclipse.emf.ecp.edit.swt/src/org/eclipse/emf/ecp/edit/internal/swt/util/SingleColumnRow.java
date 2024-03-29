/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.swt.widgets.Control;

/**
 * @author Eugen Neufeld
 *
 */
@Deprecated
public class SingleColumnRow implements RenderingResultRow<Control> {

	private final Control control;

	/**
	 * A {@link RenderingResultRow} which holds one {@link Control}.
	 *
	 * @param control the Control for the Column
	 */
	public SingleColumnRow(Control control) {
		this.control = control;
	}

	/**
	 * @return the Control
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow#getMainControl()
	 */
	@Override
	@Deprecated
	public Control getMainControl() {
		return getControl();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow#getControls()
	 */
	@Override
	public Set<Control> getControls() {
		return Collections.singleton(control);
	}

}
