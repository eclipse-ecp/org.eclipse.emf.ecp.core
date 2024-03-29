/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.edit.spi.swt.table;

/**
 * If a {@link ECPCellEditor} additionally implements this interface, the cell editor instance will be notified about
 * the edited row element before the editor gets activated.
 *
 * @author Johannes Faltermeier
 * @since 1.9
 *
 */
public interface ECPElementAwareCellEditor {

	/**
	 * Called to notify that a new element will be edited.
	 * 
	 * @param element the row element
	 */
	void updateRowElement(Object element);

}
