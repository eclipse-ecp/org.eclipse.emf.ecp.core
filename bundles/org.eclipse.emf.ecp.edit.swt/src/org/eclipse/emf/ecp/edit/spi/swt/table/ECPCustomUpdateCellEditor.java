/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.spi.swt.table;

import org.eclipse.jface.viewers.ViewerCell;

/**
 * If a {@link ECPCellEditor} additionally implements this interface, the cell editor instance will be notified when
 * a cell's label and image need to be updated.
 *
 * @author Alexandra Buzila
 * @since 1.10
 *
 */
public interface ECPCustomUpdateCellEditor {

	/**
	 * Update the label and image for the given cell.
	 *
	 * @param cell the {@link ViewerCell}
	 * @param value the new value of the cell
	 */
	void updateCell(ViewerCell cell, Object value);
}
