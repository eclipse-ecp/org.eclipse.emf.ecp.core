package org.eclipse.emf.emfstore.fx.internal.projects;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import javax.annotation.PostConstruct;

import org.eclipse.emf.emfstore.client.ESWorkspaceProvider;

public class RemoteProjectsView {

	@PostConstruct
	public void postConstruct(BorderPane parent) {
		final TreeView<Object> remoteProjectsView = new TreeView<>();
		remoteProjectsView.setShowRoot(false);
		final EmfStoreRemoteProjectTreeItem remoteProjectTreeItem = new EmfStoreRemoteProjectTreeItem(
			ESWorkspaceProvider.INSTANCE.getWorkspace());
		remoteProjectsView.setRoot(remoteProjectTreeItem);
		remoteProjectsView
			.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
				@Override
				public TreeCell<Object> call(TreeView<Object> param) {
					return new ESRemoteProjectTreeCell();
				}
			});

		parent.setCenter(remoteProjectsView);
	}
}
