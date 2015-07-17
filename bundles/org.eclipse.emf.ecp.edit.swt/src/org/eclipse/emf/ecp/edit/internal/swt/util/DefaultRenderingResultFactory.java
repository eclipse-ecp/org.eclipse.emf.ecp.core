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

import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRow;
import org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRowFactory;
import org.eclipse.swt.widgets.Control;

/**
 * @author Eugen Neufeld
 *
 */
@Deprecated
public final class DefaultRenderingResultFactory implements RenderingResultRowFactory<Control> {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.renderer.RenderingResultRowFactory#createRenderingResultRow(org.eclipse.swt.widgets.Control[])
	 */
	@Override
	public RenderingResultRow<Control> createRenderingResultRow(Control... controls) {
		if (controls == null) {
			throw new IllegalArgumentException("Controls must not be null."); //$NON-NLS-1$
		}
		if (controls.length == 0) {
			throw new IllegalArgumentException("Controls must not be empty."); //$NON-NLS-1$
		}
		if (controls.length == 1) {
			return new SingleColumnRow(controls[0]);
		}
		if (controls.length == 2) {
			return new DoubleColumnRow(controls[0], controls[1]);
		}
		if (controls.length == 3) {
			return new ThreeColumnRow(controls[0], controls[1], controls[2]);
		}
		throw new IllegalArgumentException(
			"DefaultRenderingResultFactory cannot handle more then three controls per row."); //$NON-NLS-1$
	}

}
