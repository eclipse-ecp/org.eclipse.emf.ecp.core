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
package org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DNDProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;

/**
 * Default implementation of a {@link DNDProvider}. This will support local transfers for copy, move and link operations
 * in an EMF environment.
 *
 * @author Johannes Faltermeier
 *
 */
public class DefaultDNDProvider implements DNDProvider {
	@Override
	public int getDragOperations() {
		return getDNDOperations();
	}

	@Override
	public Transfer[] getDragTransferTypes() {
		return getDNDTransferTypes();
	}

	@Override
	public DragSourceListener getDragListener(TreeViewer treeViewer) {
		return new ViewerDragAdapter(treeViewer);
	}

	@Override
	public int getDropOperations() {
		return getDNDOperations();
	}

	@Override
	public Transfer[] getDropTransferTypes() {
		return getDNDTransferTypes();
	}

	@Override
	public DropTargetListener getDropListener(EditingDomain editingDomain, TreeViewer treeViewer) {
		return new EditingDomainViewerDropAdapter(editingDomain, treeViewer);
	}

	/**
	 * The drag&drop operations which will be returned by {@link #getDragOperations()} and {@link #getDragOperations()}.
	 *
	 * @return the operation bits
	 */
	protected int getDNDOperations() {
		return DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
	}

	/**
	 * The transfers which will be returned {@link #getDragTransferTypes()} and {@link #getDropTransferTypes()}.
	 *
	 * @return the transfer types
	 */
	protected Transfer[] getDNDTransferTypes() {
		return new Transfer[] { LocalTransfer.getInstance() };
	}

	@Override
	public boolean hasDND() {
		return true;
	}
}