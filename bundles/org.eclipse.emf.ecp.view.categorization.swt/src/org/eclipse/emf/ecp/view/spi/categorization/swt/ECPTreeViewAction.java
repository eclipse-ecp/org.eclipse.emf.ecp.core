/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.categorization.swt;

import org.eclipse.emf.ecp.view.spi.categorization.model.ECPAction;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.TreeEditor;

/**
 * This {@link ECPAction} implementation is specific for a TreeViewer. The action will be initialized with the
 * {@link TreeViewer}, the current {@link TreeEditor} and the current {@link TreeSelection}.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class ECPTreeViewAction implements ECPAction {

	private TreeViewer treeViewer;
	private TreeSelection treeSelection;
	private TreeEditor treeEditor;

	/**
	 * Initialize the tree action.
	 *
	 * @param treeViewer the {@link TreeViewer} showing this action
	 * @param treeSelection the current {@link TreeSelection}
	 * @param treeEditor the current {@link TreeEditor}
	 */
	public final void init(TreeViewer treeViewer,
		TreeSelection treeSelection, TreeEditor treeEditor) {
		this.treeViewer = treeViewer;
		this.treeSelection = treeSelection;
		this.treeEditor = treeEditor;
	}

	/**
	 * The {@link TreeViewer} showing this action.
	 *
	 * @return the {@link TreeViewer}
	 */
	protected TreeViewer getTreeViewer() {
		return treeViewer;
	}

	/**
	 * Current {@link TreeSelection}.
	 *
	 * @return the {@link TreeSelection}
	 */
	protected TreeSelection getTreeSelection() {
		return treeSelection;
	}

	/**
	 * Current {@link TreeEditor}.
	 *
	 * @return the {@link TreeEditor}
	 */
	protected TreeEditor getTreeEditor() {
		return treeEditor;
	}

}
