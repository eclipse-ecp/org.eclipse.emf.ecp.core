/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

/**
 * Comparator for the table viewer.
 *
 * @author jonas
 *
 */
public interface TableViewerComparator {

	/**
	 * Sets the current column.
	 * 
	 * @param i the column index
	 */
	void setColumn(int i);

	/**
	 * Returns the alignment direction.
	 *
	 * @return the alignment direction
	 * @see org.eclipse.swt.SWT#UP
	 * @see org.eclipse.swt.SWT#DOWN
	 * @see org.eclipse.swt.SWT#NONE
	 */
	int getDirection();

}
