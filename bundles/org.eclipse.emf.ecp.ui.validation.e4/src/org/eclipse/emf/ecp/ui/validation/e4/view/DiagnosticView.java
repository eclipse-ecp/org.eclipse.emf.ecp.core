/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.validation.e4.view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecp.internal.ui.validation.ValidationTreeViewerFactory;
import org.eclipse.emf.ecp.ui.validation.ECPValidationResultService;
import org.eclipse.emf.ecp.ui.validation.ECPValidationResultService.ECPValidationResultServiceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * View to display validation results.
 *
 * @author jfaltermeier
 *
 */
public class DiagnosticView {

	private static final String POPUPMENU_VALIDATION_ID = "org.eclipse.emf.ecp.e4.application.popupmenu.validation"; //$NON-NLS-1$

	@Inject
	private ECPValidationResultService service;
	private ECPValidationResultServiceListener listener;
	private TreeViewer diagnosticTree;

	/**
	 * Creates the diagnostic view.
	 *
	 * @param composite the parent {@link Composite}
	 * @param menuService the menu service to register the context menu
	 * @param selectionService the selection service to publish the selection of the tree viewer.
	 */
	@PostConstruct
	public void create(Composite composite, EMenuService menuService,
		final ESelectionService selectionService) {
		diagnosticTree = ValidationTreeViewerFactory.createValidationViewer(composite);
		menuService.registerContextMenu(diagnosticTree.getTree(),
			POPUPMENU_VALIDATION_ID);
		diagnosticTree
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
		listener = new ECPValidationResultServiceListener() {
			@Override
			public void resultChanged(Object diagnostic) {
				diagnosticTree.setInput(diagnostic);
				diagnosticTree.expandAll();
			}
		};
		service.register(listener);
	}

	/**
	 * Sets the focus to the tree.
	 */
	@Focus
	public void setFocus() {
		diagnosticTree.getTree().setFocus();
	}

	/**
	 * Clean up code.
	 */
	@PreDestroy
	public void preDestroy() {
		service.deregister(listener);
	}

}
