/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor;

/**
 * Interface for creating a {@link CellEditor}.
 *
 * @author Johannes Faltermeier
 *
 */
public interface CellEditorCreator {

	/**
	 * Creates a {@link CellEditor} to be used in the given {@link AbstractTableViewer}.
	 *
	 * @param viewer the viewer
	 * @return the editor
	 */
	CellEditor createCellEditor(AbstractTableViewer viewer);

}
