/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

/**
 * ElementTreeSelectionDialog which also stores the selection along the tree.
 *
 * @author Eugen Neufeld
 *
 */
public class ECPViewEditorTreeSelectionDialog extends ElementTreeSelectionDialog {
	private TreePath treePath;

	/**
	 * Default constructor.
	 *
	 * @param parent the {@link Shell} for creating the dialog
	 * @param labelProvider the {@link ILabelProvider} for the tree
	 * @param contentProvider the {@link ITreeContentProvider} for the tree
	 */
	public ECPViewEditorTreeSelectionDialog(Shell parent, ILabelProvider labelProvider,
		ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
	}

	/**
	 * Returns the {@link TreePath} from the tree viewer.
	 *
	 * @return the path
	 */
	public TreePath getTreePath() {
		if (getTreeViewer() != null) {
			final TreeSelection selection = (TreeSelection) getTreeViewer().getSelection();
			if (!selection.isEmpty()) {
				treePath = selection.getPaths()[0];
			}
		}
		return treePath;
	}
}
