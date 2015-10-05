/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
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
 * @author jfaltermeier
 *
 */
public final class DefaultDNDProvider implements DNDProvider {
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

	private int getDNDOperations() {
		return DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
	}

	private Transfer[] getDNDTransferTypes() {
		return new Transfer[] { LocalTransfer.getInstance() };
	}

	@Override
	public boolean hasDND() {
		return true;
	}
}