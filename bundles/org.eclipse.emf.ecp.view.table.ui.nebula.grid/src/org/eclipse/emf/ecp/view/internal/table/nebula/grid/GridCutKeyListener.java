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
import org.eclipse.swt.widgets.Display;

/**
 * @author Stefan Dirix
 * @since 1.11
 *
 */
public class GridCutKeyListener extends GridCopyKeyListener {

	private final EMFFormsDatabindingEMF dataBinding;
	private final VControl vControl;

	/**
	 * Constructor.
	 *
	 * @param display the {@link Display} on which to allocate this command's {@link org.eclipse.swt.dnd.Clipboard}.
	 * @param vControl the {@link VControl}.
	 * @param dataBinding the {@link EMFFormsDatabindingEMF}.
	 */
	public GridCutKeyListener(Display display, VControl vControl, EMFFormsDatabindingEMF dataBinding) {
		super(display);
		this.vControl = vControl;
		this.dataBinding = dataBinding;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.stateMask & SWT.CTRL) != 0 && e.keyCode == 'x') {
			final Grid grid = (Grid) e.widget;
			copySelectionToClipboard(grid);
			KeyListenerUtil.clearSelection(grid, vControl, dataBinding);
		}
	}

}
