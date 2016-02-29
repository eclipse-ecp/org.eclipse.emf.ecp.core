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
package org.eclipse.emf.ecp.edit.spi.swt.table;

/**
 * A {@link ECPCellEditor} may optionally implement the ECPCellEditorComparator interface. Implementing the comparator
 * interface will indicate to the users of the EPCCellEditor that the sorting algorithm shall use the compare method of
 * the editor for the given column.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public interface ECPCellEditorComparator {

	/**
	 * This method is called in order to compare the two given objects. The results are used to show the elements in the
	 * resulting order in the table.
	 *
	 * @param e1 the first object of the comparison
	 * @param e2 the second object of the comparison
	 * @param direction {@link org.eclipse.swt.SWT#NONE SWT.NONE}, {@link org.eclipse.swt.SWT#UP SWT.UP} or
	 *            {@link org.eclipse.swt.SWT#DOWN SWT.DOWN} according to the indication displayed at
	 *            the table column.
	 * @return a negative number if the first element is to be shown before the
	 *         second element; the value <code>0</code> if the first element is
	 *         equal to the second element; and a positive number if the first
	 *         element is to be shown after the second element.
	 *         <p>
	 *         <b>NOTE</b> Keep in mind to take the given direction into account
	 *         </p>
	 */
	int compare(Object e1, Object e2, int direction);

}
