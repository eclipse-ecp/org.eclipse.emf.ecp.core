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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.graphics.Point;

/**
 * Util class for common functionality.
 *
 * @author Stefan Dirix
 * @since 1.11
 *
 */
public final class KeyListenerUtil {

	/**
	 * Private Constructor for Util classes.
	 */
	private KeyListenerUtil() {
	}

	/**
	 * Clears the selection from the grid.
	 *
	 * @param grid the {@link Grid}.
	 * @param vControl the {@link VControl}.
	 * @param dataBinding the {@link EMFFormsDatabindingEMF}.
	 */
	@SuppressWarnings("restriction")
	public static void clearSelection(Grid grid, VControl vControl, EMFFormsDatabindingEMF dataBinding) {
		if (grid.getCellSelection().length == 0 || !vControl.isEnabled() || vControl.isReadonly()) {
			return;
		}

		for (final Point itemCoord : grid.getCellSelection()) {
			final int column = itemCoord.x;
			final int row = itemCoord.y;

			final VDomainModelReference dmr = (VDomainModelReference) grid.getColumn(column)
				.getData(AbstractTableViewerComposite.DMR);

			if (dmr == null || vControl instanceof VTableControl
				&& org.eclipse.emf.ecp.view.internal.table.swt.TableConfigurationHelper
					.isReadOnly((VTableControl) vControl, dmr)) {
				continue;
			}

			final EObject eObject = (EObject) grid.getItem(row).getData();

			try {
				final Setting setting = dataBinding.getSetting(dmr, eObject);
				setting.unset();
			} catch (final DatabindingFailedException ex) {
				// ignore
			}
		}
	}
}
