/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.TableViewer;

/**
 * If a {@link ECPCellEditor} additionally implements this interface, the cell editor instance will get the TableViewer
 * instance it belongs to.
 *
 * @author Eugen Neufeld
 * @since 1.18
 *
 */
public interface ECPViewerAwareCellEditor {

	/**
	 * Sets the table viewer this cell editor is created for.
	 *
	 * @param tableViewer The {@link TableViewer} this cell editor belongs to
	 */
	void setTableViewer(AbstractTableViewer tableViewer);

	/**
	 * Sets the EReference of the table this cell editor is created for.
	 *
	 * @param tableRefrence The {@link EReference} this cell editor is used for
	 */
	void setTableFeature(EReference tableRefrence);
}
