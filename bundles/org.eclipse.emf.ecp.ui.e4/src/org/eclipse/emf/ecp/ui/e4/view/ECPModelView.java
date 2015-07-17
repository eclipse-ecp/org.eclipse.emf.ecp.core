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
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.model.ModelContentProvider;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.emf.ecp.ui.common.ECPViewerFactory;
import org.eclipse.emf.ecp.ui.e4.editor.ECPE4Editor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Because {@link EMenuService} is still internal.
 */
/**
 * Model Explorer View Part.
 *
 * @author Jonas
 *
 */
public class ECPModelView {

	public static final String P_LINK_WITH_EDITOR = "linkWithEditor"; //$NON-NLS-1$
	private static final String POPUPMENU_NAVIGATOR = "org.eclipse.emf.ecp.e4.application.popupmenu.navigator"; //$NON-NLS-1$
	private TreeViewer modelExplorerTree;
	private ModelContentProvider contentProvider;

	@Inject
	private EPartService partService;

	private MPart modelViewPart;

	/**
	 * Creates the model explorer view.
	 *
	 * @param composite the parent {@link Composite}
	 * @param menuService the menu service to register the context menu
	 * @param selectionService the selection service to publish the selection of the tree viewer.
	 * @param part the current {@link MPart}
	 */
	@PostConstruct
	public void create(Composite composite, EMenuService menuService, final ESelectionService selectionService,
		MPart part) {
		modelViewPart = part;
		if (modelViewPart.getPersistedState().containsKey(P_LINK_WITH_EDITOR)) {
			for (final MToolBarElement toolbarElement : part.getToolbar().getChildren()) {
				// TODO better way?
				if (toolbarElement.getElementId().endsWith("link")) {
					((MToolItem) toolbarElement).setSelected(Boolean.valueOf(modelViewPart.getPersistedState().get(
						P_LINK_WITH_EDITOR)));
				}
			}
		}
		modelExplorerTree = ECPViewerFactory.createModelExplorerViewer(composite, true, null);
		menuService.registerContextMenu(modelExplorerTree.getTree(),
			POPUPMENU_NAVIGATOR);
		contentProvider = (ModelContentProvider) modelExplorerTree.getContentProvider();
		modelExplorerTree.addDoubleClickListener(new IDoubleClickListener() {

			@Override
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

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				final ISelection selection = event.getSelection();
				if (IStructuredSelection.class.isInstance(selection)) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					if (structuredSelection != null && !structuredSelection.isEmpty()) {
						if (structuredSelection.size() == 1) {
							selectionService
								.setSelection(structuredSelection.getFirstElement());
							activateCorrectPart(structuredSelection.getFirstElement());
						}
						else {
							selectionService
								.setSelection(structuredSelection.toList());
						}
					}
				}
			}
		});
	}

	private void activateCorrectPart(Object firstSelection) {
		if (!Boolean.valueOf(modelViewPart.getPersistedState().get(P_LINK_WITH_EDITOR))) {
			return;
		}
		MPart partToHighlite = null;
		for (final MPart part : partService.getParts()) {
			if (part.getContext().get(ECPE4Editor.INPUT) == firstSelection) {
				partToHighlite = part;
				break;
			}
		}
		if (partToHighlite == null) {
			return;
		}
		partService.showPart(partToHighlite, PartState.ACTIVATE);
	}

	@Inject
	public void setActivePart(@Named(IServiceConstants.ACTIVE_PART) @Optional MPart part) {
		if (modelExplorerTree == null) {
			return;
		}
		if (part == null) {
			return;
		}
		if (modelViewPart == null || !Boolean.valueOf(modelViewPart.getPersistedState().get(P_LINK_WITH_EDITOR))) {
			return;
		}
		final Object partInput = part.getContext().get(ECPE4Editor.INPUT);
		if (partInput == null) {
			return;
		}
		modelExplorerTree.setSelection(new StructuredSelection(partInput));
	}

	/**
	 * Sets the focus to the tree.
	 */
	@Focus
	public void setFocus() {
		modelExplorerTree.getTree().setFocus();
	}

}
