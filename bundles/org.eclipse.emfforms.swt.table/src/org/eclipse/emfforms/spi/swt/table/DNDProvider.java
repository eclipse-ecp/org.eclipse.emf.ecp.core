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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;

/**
 * Interface to influence the D&D support which is added to the table viewer.
 *
 * @author Johannes Faltermeier
 *
 */
public interface DNDProvider {

	/**
	 * Whether DND support should be added to the table.
	 *
	 * @return <code>true</code> if DND will be added, based on the other methods of this interface, or
	 *         <code>false</code> if DND should not be added
	 */
	boolean hasDND();

	/**
	 * Returns the drag operations bits used to setup the
	 * {@link TableViewer#addDragSupport(int, Transfer[], DragSourceListener) drag support} for the viewer.
	 *
	 * @return the drag operations
	 */
	int getDragOperations();

	/**
	 * Returns the drag transfer types used to setup the
	 * {@link TableViewer#addDragSupport(int, Transfer[], DragSourceListener) drag support} for the viewer.
	 *
	 * @return the drag {@link Transfer transfer types}
	 */
	Transfer[] getDragTransferTypes();

	/**
	 * Returns the {@link DragSourceListener} used to setup the
	 * {@link TableViewer#addDragSupport(int, Transfer[], DragSourceListener) drag support} for the viewer.
	 *
	 * @param tableViewer the TableViewer
	 * @return the listener
	 */
	DragSourceListener getDragListener(TableViewer tableViewer);

	/**
	 * Returns the drag operations bits used to setup the
	 * {@link TableViewer#addDropSupport(int, Transfer[], DropTargetListener) drop support} for the viewer.
	 *
	 * @return the drop operations
	 */
	int getDropOperations();

	/**
	 * Returns the drag transfer types used to setup the
	 * {@link TableViewer#addDropSupport(int, Transfer[], DropTargetListener) drop support} for the viewer.
	 *
	 * @return the drop {@link Transfer transfer types}
	 */
	Transfer[] getDropTransferTypes();

	/**
	 * Returns the {@link DragSourceListener} used to setup the
	 * {@link TableViewer#addDropSupport(int, Transfer[], DropTargetListener) drop support} for the viewer.
	 *
	 * @param tableViewer the TableViewer
	 * @return the listener
	 */
	DropTargetListener getDropListener(TableViewer tableViewer);

}