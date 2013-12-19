package org.eclipse.emf.ecp.ui.e4.view;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.emf.ecp.ui.common.TreeViewerFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

public class ECPRepositoryView {

	private TreeViewer repositoryTree;

	public ECPRepositoryView() {

	}

	@PostConstruct
	public void create(Composite composite, EMenuService menuService,
		final ESelectionService selectionService) {
		repositoryTree = TreeViewerFactory.createRepositoryExplorerViewer(
			composite, null);
		menuService.registerContextMenu(repositoryTree.getTree(),
			"org.eclipse.emf.ecp.e4.application.popupmenu.repository");
		repositoryTree
			.addSelectionChangedListener(new ISelectionChangedListener() {

				public void selectionChanged(SelectionChangedEvent event) {
					final ISelection selection = event.getSelection();
					if (IStructuredSelection.class.isInstance(selection)) {
						final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						if (!structuredSelection.isEmpty()) {
							selectionService
								.setSelection(structuredSelection
									.getFirstElement());
						}
					}
				}
			});
	}

	@Focus
	public void setFocus() {
		repositoryTree.getTree().setFocus();
	}

	@PreDestroy
	public void dispose() {

	}
}
