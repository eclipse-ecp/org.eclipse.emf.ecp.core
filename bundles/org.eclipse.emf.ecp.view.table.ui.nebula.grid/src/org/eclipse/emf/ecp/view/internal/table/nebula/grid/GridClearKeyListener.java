/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * @author Stefan Dirix
 * @since 1.11
 *
 */
public class GridClearKeyListener implements KeyListener {

	private final VControl vControl;
	private final EMFFormsDatabindingEMF dataBinding;

	/**
	 * Constructor.
	 *
	 * @param vControl the {@link VControl}.
	 * @param dataBinding the {@link EMFFormsDatabindingEMF}.
	 */
	public GridClearKeyListener(VControl vControl, EMFFormsDatabindingEMF dataBinding) {
		this.vControl = vControl;
		this.dataBinding = dataBinding;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.keyCode == SWT.DEL) {
			final Grid grid = (Grid) e.widget;
			clearSelection(grid);
		}
	}

	/**
	 * Clears the selection in the given {@code grid}.
	 *
	 * @param grid the {@link Grid}.
	 */
	protected void clearSelection(Grid grid) {
		KeyListenerUtil.clearSelection(grid, vControl, dataBinding);
	}
}
