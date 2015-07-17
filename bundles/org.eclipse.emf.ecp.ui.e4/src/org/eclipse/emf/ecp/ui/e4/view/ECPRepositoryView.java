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
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.view;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecp.ui.common.ECPViewerFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * View to display available model repositories.
 *
 * @author Jonas
 *
 */
public class ECPRepositoryView {

	private static final String POPUPMENU_REPOSITORY = "org.eclipse.emf.ecp.e4.application.popupmenu.repository"; //$NON-NLS-1$
	private TreeViewer repositoryTree;

	/**
	 * Creates the repository view.
	 *
	 * @param composite the parent {@link Composite}
	 * @param menuService the menu service to register the context menu
	 * @param selectionService the selection service to publish the selection of the tree viewer.
	 */
	@PostConstruct
	public void create(Composite composite, EMenuService menuService,
		final ESelectionService selectionService) {
		repositoryTree = ECPViewerFactory.createRepositoryExplorerViewer(
			composite, null);
		menuService.registerContextMenu(repositoryTree.getTree(),
			POPUPMENU_REPOSITORY);
		repositoryTree
			.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					final ISelection selection = event.getSelection();
					if (IStructuredSelection.class.isInstance(selection)) {
						final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						if (structuredSelection != null && !structuredSelection.isEmpty()) {
							if (structuredSelection.size() == 1) {
								selectionService
									.setSelection(structuredSelection.getFirstElement());
							}
							else {
								selectionService
									.setSelection(structuredSelection.toList());
							}
						}
						else {
							selectionService.setSelection(null);
						}
					}
				}
			});
	}

	/**
	 * Sets the focus to the tree.
	 */
	@Focus
	public void setFocus() {
		repositoryTree.getTree().setFocus();
	}

}
