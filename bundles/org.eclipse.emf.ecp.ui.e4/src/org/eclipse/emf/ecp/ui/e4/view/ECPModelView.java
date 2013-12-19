package org.eclipse.emf.ecp.ui.e4.view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
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

public class ECPModelView {

	private TreeViewer modelExplorerTree;
	private ModelContentProvider contentProvider;

	public ECPModelView() {

	}

	@PostConstruct
	public void create(Composite composite, EMenuService menuService, final ESelectionService selectionService,
		final EPartService partService) {
		modelExplorerTree = TreeViewerFactory.createModelExplorerViewer(composite, false, null);
		menuService.registerContextMenu(modelExplorerTree.getTree(),
			"org.eclipse.emf.ecp.e4.application.popupmenu.navigator");
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
						// MPart
						// part=partService.createPart("org.eclipse.emf.ecp.e4.application.partdescriptor.editor");
						// part.setLabel(modelElement.eClass().getName());
						// partService.showPart(part, PartState.ACTIVATE);
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

	@Focus
	public void setFocus() {
		modelExplorerTree.getTree().setFocus();
	}

	@PreDestroy
	public void dispose() {

	}
}
