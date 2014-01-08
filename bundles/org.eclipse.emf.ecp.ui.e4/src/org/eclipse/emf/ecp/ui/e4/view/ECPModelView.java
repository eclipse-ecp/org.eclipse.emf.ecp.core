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
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.model.ModelContentProvider;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.ui.common.TreeViewerFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Because {@link EMenuService} is still internal.
 */
@SuppressWarnings("restriction")
/**
 * Model Explorer View Part.
 * @author Jonas
 *
 */
public class ECPModelView {

	private static final String POPUPMENU_NAVIGATOR = "org.eclipse.emf.ecp.e4.application.popupmenu.navigator"; //$NON-NLS-1$
	private TreeViewer modelExplorerTree;
	private ModelContentProvider contentProvider;

	/**
	 * Creates the model explorer view.
	 * 
	 * @param composite the parent {@link Composite}
	 * @param menuService the menu service to register the context menu
	 * @param selectionService the selection service to publish the selection of the tree viewer.
	 */
	@PostConstruct
	public void create(Composite composite, EMenuService menuService, final ESelectionService selectionService) {
		modelExplorerTree = TreeViewerFactory.createModelExplorerViewer(composite, false, null);
		menuService.registerContextMenu(modelExplorerTree.getTree(),
			POPUPMENU_NAVIGATOR);
		contentProvider = (ModelContentProvider) modelExplorerTree.getContentProvider();
		modelExplorerTree.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) event.getSelection();
					final Object firstElement = structuredSelection.getFirstElement();

					if (firstElement instanceof ECPProject) {
						final ECPProject project = (ECPProject) firstElement;
						if (!project.isOpen()) {
							project.open();
						}
					}
					else if (firstElement instanceof EObject) {
						final ECPContainer context = ECPUtil.getModelContext(contentProvider,
							structuredSelection.toArray());
						ECPHandlerHelper.openModelElement(firstElement, (ECPProject) context);

					}
				}
			}
		});
		modelExplorerTree.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				final ISelection selection = event.getSelection();
				if (IStructuredSelection.class.isInstance(selection)) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					if (!structuredSelection.isEmpty()) {
						if (structuredSelection.toList().size() == 1) {
							selectionService.setSelection(structuredSelection.getFirstElement());
						} else {
							selectionService.setSelection(structuredSelection.toList());
						}
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
		modelExplorerTree.getTree().setFocus();
	}

}
