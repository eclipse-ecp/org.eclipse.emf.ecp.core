/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Interface for creating a {@link CellEditor}.
 *
 * @author Johannes Faltermeier
 *
 */
public interface CellEditorCreator {

	/**
	 * Creates a {@link CellEditor} to be used in the given {@link TableViewer}.
	 * 
	 * @param viewer the viewer
	 * @return the editor
	 */
	CellEditor createCellEditor(TableViewer viewer);

}
