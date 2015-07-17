package org.eclipse.emf.emfstore.fx.internal.projects;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;
import org.eclipse.emf.emfstore.client.observer.ESCheckoutObserver;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.client.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESWorkspaceImpl;
import org.eclipse.emf.emfstore.internal.common.model.IdEObjectCollection;
import org.eclipse.emf.emfstore.internal.common.model.util.IdEObjectCollectionChangeObserver;

class EmfStoreLocalTreeItem extends TreeItem<Object> {

	private final ProjectsView projectsView;
	private final ObservableList<TreeItem<Object>> children;
	private final TreeView<Object> view;

	public EmfStoreLocalTreeItem(ProjectsView projectsView, final ESWorkspace root,
		TreeView<Object> view) {
		super(root);
		this.projectsView = projectsView;
		this.view = view;
		children = FXCollections
			.unmodifiableObservableList(super.getChildren());

		ESWorkspaceImpl.class.cast(root).toInternalAPI().eAdapters()
			.add(new AdapterImpl() {

				@Override
				public void notifyChanged(Notification msg) {
					super.notifyChanged(msg);
					if (ModelPackage.eINSTANCE.getWorkspace_ProjectSpaces()
						.equals(msg.getFeature())) {
						updatedWorkspace(root, getExpandedItems(),
							getSelectedItems(getSelectionModel()), getSelectionModel());
					}
				}

			});
		updatedWorkspace(root, getExpandedItems(),
			getSelectedItems(getSelectionModel()), getSelectionModel());

	}

	@SuppressWarnings("restriction")
	public EmfStoreLocalTreeItem(ProjectsView projectsView, final ESLocalProject root,
		TreeView<Object> view) {
		super(root);
		this.projectsView = projectsView;
		this.view = view;

		children = FXCollections
			.unmodifiableObservableList(super.getChildren());
		updateProject(root, getExpandedItems(),
			getSelectedItems(getSelectionModel()), getSelectionModel());

		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESCheckoutObserver() {

				@Override
				public void checkoutDone(ESLocalProject project) {

					updatedWorkspace(ESWorkspaceProvider.INSTANCE.getWorkspace(), getExpandedItems(),
						getSelectedItems(getSelectionModel()), getSelectionModel());
				}
			});
		ESLocalProjectImpl.class.cast(root).toInternalAPI().getProject()
			.addIdEObjectCollectionChangeObserver(new IdEObjectCollectionChangeObserver() {

				@Override
				public void notify(Notification notification,
					IdEObjectCollection collection, EObject modelElement) {
					// TODO Auto-generated method stub

				}

				@Override
				public void modelElementRemoved(IdEObjectCollection collection,
					EObject eObject) {
					updateProject(root, getExpandedItems(), getSelectedItems(getSelectionModel()), getSelectionModel());
				}

				@Override
				public void modelElementAdded(IdEObjectCollection collection,
					EObject eObject) {
					updateProject(root, getExpandedItems(), getSelectedItems(getSelectionModel()), getSelectionModel());
				}

				@Override
				public void collectionDeleted(IdEObjectCollection collection) {
					// TODO Auto-generated method stub

				}
			});
	}

	@Override
	public ObservableList<TreeItem<Object>> getChildren() {
		return children;
	}

	private MultipleSelectionModel<?> getSelectionModel() {
		return view.getSelectionModel();
	}

	private List<Object> getExpandedItems() {
		// ObservableList<TreeItem<Object>> childTreeItems = super.getChildren();
		final ArrayList<Object> expandedItems = new ArrayList<>();
		// // remember the expanded items
		// for (TreeItem<Object> childTreeItem : childTreeItems) {
		// if (childTreeItem.isExpanded())
		// expandedItems.add(childTreeItem.getValue());
		// }
		return expandedItems;
	}

	//
	private List<Object> getSelectedItems(MultipleSelectionModel<?> selectionModel) {
		// ObservableList<TreeItem<Object>> childTreeItems = super.getChildren();
		// List<?> selection = selectionModel.getSelectedItems();
		//
		final ArrayList<Object> selectedItems = new ArrayList<>();
		// ArrayList<TreeItem<?>> selectedTreeItems = new ArrayList<>();
		//
		// // remember the selected items
		// for (Object selectedTreeItem : selection) {
		// for (TreeItem<Object> childTreeItem : childTreeItems) {
		// if (selectedTreeItem == childTreeItem) {
		// selectedTreeItems.add(childTreeItem);
		// selectedItems.add(childTreeItem.getValue());
		// }
		// }
		// }
		//
		// // clear the selection
		// for (TreeItem<?> selectedTreeItem : selectedTreeItems) {
		// int treeItemIndex = selectionModel.getSelectedItems().indexOf(
		// selectedTreeItem);
		// int selectionIndex = selectionModel.getSelectedIndices().get(
		// treeItemIndex);
		// selectionModel.clearSelection(selectionIndex);
		// }
		//
		return selectedItems;
	}

	// private void updateTree(Object child, TreeItem<?> treeItem,
	// List<Object> expandedItems, List<Object> selectedItems,
	// SelectionModel<?> selectionModel) {
	// // expand the new tree items
	// if (expandedItems.contains(child))
	// treeItem.setExpanded(true);
	//
	// // restore the selection
	// if (selectedItems.contains(child)
	// && "javafx.scene.control.TreeView$TreeViewBitSetSelectionModel"
	// .equals(selectionModel.getClass().getName())) {
	// try {
	// Method m = selectionModel.getClass().getDeclaredMethod(
	// "select", new Class[] { TreeItem.class });
	// m.setAccessible(true);
	// m.invoke(selectionModel, treeItem);
	// } catch (Exception e) {
	// // do nothing
	// }
	// }
	// }

	private void updatedWorkspace(ESWorkspace selectedValue,
		List<Object> expandedItems, List<Object> selectedItems,
		SelectionModel<?> selectionModel) {
		final ObservableList<TreeItem<Object>> childTreeItems = super.getChildren();
		childTreeItems.clear();
		for (final ESLocalProject localProject : selectedValue.getLocalProjects()) {
			final EmfStoreLocalTreeItem treeItem = new EmfStoreLocalTreeItem(
				projectsView, localProject, view);

			childTreeItems.add(treeItem);

			// updateTree(localProject, treeItem, expandedItems, selectedItems,
			// selectionModel);

		}
	}

	// private void updateEObjects(Object selectedValue,
	// ArrayList<Object> expandedItems, ArrayList<Object> selectedItems,
	// MultipleSelectionModel<?> selectionModel) {
	// Object adapter = this.projectsView.adapterFactory.adapt(selectedValue,
	// ITreeItemContentProvider.class);
	// ITreeItemContentProvider provider = (adapter instanceof ITreeItemContentProvider) ? (ITreeItemContentProvider)
	// adapter
	// : null;
	//
	// ObservableList<TreeItem<Object>> childTreeItems = super.getChildren();
	// for (Object child : provider.getChildren(getValue())) {
	// AdapterFactoryTreeItem treeItem = new AdapterFactoryTreeItem(child,
	// view, this.projectsView.adapterFactory);
	//
	// childTreeItems.add(treeItem);
	// // updateTree(child, treeItem, expandedItems, selectedItems,
	// // selectionModel);
	// }
	// }

	private void updateProject(ESLocalProject selectedValue,
		List<Object> expandedItems, List<Object> selectedItems,
		MultipleSelectionModel<?> selectionModel) {
		final ObservableList<TreeItem<Object>> childTreeItems = super.getChildren();
		childTreeItems.clear();
		for (final Object child : selectedValue.getModelElements()) {
			final AdapterFactoryTreeItem treeItem = new AdapterFactoryTreeItem(child,
				view, projectsView.adapterFactory);

			childTreeItems.add(treeItem);
			// updateTree(child, treeItem, expandedItems, selectedItems,
			// selectionModel);
		}
	}

}