package org.eclipse.emf.emfstore.fx.internal.projects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.observer.ESLoginObserver;
import org.eclipse.emf.emfstore.client.observer.ESLogoutObserver;
import org.eclipse.emf.emfstore.client.observer.ESShareObserver;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

public class EmfStoreRemoteProjectTreeItem extends TreeItem<Object> {
	private ObservableList<TreeItem<Object>> children;

	@SuppressWarnings("restriction")
	public EmfStoreRemoteProjectTreeItem(Object root) {
		super(root);
		children = FXCollections
			.unmodifiableObservableList(super.getChildren());
		updateChildren();
		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESLoginObserver() {

				@Override
				public void loginCompleted(ESUsersession session) {
					try {
						if (!getValue().equals(session.getServer())) {
							return;
						}
						updateServer(session.getServer(), session);
					} catch (final ESException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESShareObserver() {

				@Override
				public void shareDone(ESLocalProject localProject) {
					try {
						final ESRemoteProject remoteProject = localProject
							.getRemoteProject();
						if (!getValue().equals(remoteProject.getServer())) {
							return;
						}
						updateServer(remoteProject.getServer(),
							remoteProject.getServer()
								.getLastUsersession());
					} catch (final ESException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESLoginObserver() {

				@Override
				public void loginCompleted(ESUsersession session) {
					if (!getValue().equals(session.getServer())) {
						return;
					}
					try {
						updateServer(session.getServer(), session);
					} catch (final ESException e) {
						e.printStackTrace();
					}
				}

			});
		ESWorkspaceProviderImpl.getObserverBus().register(new ESLogoutObserver() {

			@Override
			public void logoutCompleted(ESUsersession session) {
				if (!getValue().equals(session.getServer())) {
					return;
				}
				try {
					updateServer(session.getServer(), session);
				} catch (final ESException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateChildren() {

		final Object selectedValue = getValue();
		if (ESWorkspace.class.isInstance(selectedValue)) {
			updateWorkspace((ESWorkspace) selectedValue);
		} else if (ESServer.class.isInstance(selectedValue)) {
			final ESServer server = (ESServer) selectedValue;
			try {
				updateServer(server, server.getLastUsersession());
			} catch (final ESException e) {
				// TODO log
				// e.printStackTrace();

			}
		}
	}

	private void updateServer(ESServer selectedValue, ESUsersession session)
		throws ESException {
		if (session == null) {
			return;
		}
		final ObservableList<TreeItem<Object>> children = super.getChildren();
		children.clear();
		if (session.isLoggedIn()) {
			for (final ESRemoteProject project : selectedValue.getRemoteProjects(session)) {
				children.add(new EmfStoreRemoteProjectTreeItem(project));
			}
		}
	}

	private void updateWorkspace(ESWorkspace workspace) {
		final ObservableList<TreeItem<Object>> children = super.getChildren();
		children.clear();
		for (final ESServer server : workspace.getServers()) {
			children.add(new EmfStoreRemoteProjectTreeItem(server));
		}
	}

	@Override
	public ObservableList<TreeItem<Object>> getChildren() {
		return children;
	}
}
