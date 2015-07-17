package org.eclipse.emf.emfstore.fx.internal.projects;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.client.ESWorkspace;
import org.eclipse.emf.emfstore.client.observer.ESLoginObserver;
import org.eclipse.emf.emfstore.client.observer.ESLogoutObserver;
import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.eclipse.emf.emfstore.internal.server.exceptions.FatalESException;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

public final class ESRemoteProjectTreeCell extends TreeCell<Object> {

	private static List<String> localURLs = Arrays.asList("localhost", "127.0.0.1");

	private class LoginHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent t) {
			final ESServer server = (ESServer) getTreeItem().getValue();
			String password = null;
			String user = null;

			if (server.getLastUsersession() != null) {
				password = server.getLastUsersession().getPassword();
				user = server.getLastUsersession().getUsername();
			}
			if (password == null || password.isEmpty() || user == null
				|| user.isEmpty()) {
				final LoginStage stage = new LoginStage(server.getLastUsersession());
				stage.showAndWait();
				if (stage.getPassword() == null) {
					return;
				}

				user = stage.getName();
				password = stage.getPassword();
				try {
					final ESUsersession login = server.login(user, password);
					login.setSavePassword(stage.isSavePassword());
				} catch (final ESException e) {
					showError(e);
				}
			} else {
				try {
					server.getLastUsersession().refresh();
				} catch (final ESException e) {
					showError(e);
				}
			}
		}
	}

	private class LogoutHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent t) {
			final ESServer server = (ESServer) getTreeItem().getValue();

			try {
				server.getLastUsersession().logout();
			} catch (final ESException e) {
				showError(e);
			}
		}
	}

	private class CheckoutNameStage extends Stage {
		private String result;

		public CheckoutNameStage() {
			super();
			initModality(Modality.WINDOW_MODAL);
			setTitle("Enter Name for LocalProject");
			final TextField tf = new TextField();
			final Button button = new Button("Create");
			button.setMaxWidth(Double.MAX_VALUE);
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					result = tf.getText();
					close();
				}
			});

			setScene(new Scene(VBoxBuilder.create().children(tf, button)
				.build()));
		}
	}

	private final ContextMenu remoteProjectMenu = new ContextMenu();
	private final ContextMenu serverMenu = new ContextMenu();
	private final BooleanProperty loggedIn = new SimpleBooleanProperty(false);

	// private ContextMenu serverMenu = new ContextMenu();

	@SuppressWarnings("restriction")
	public ESRemoteProjectTreeCell() {
		{
			final MenuItem addServerMenuItem = new MenuItem();
			final ImageView image = new ImageView(Activator.getContext().getBundle()
				.getResource("icons/server_add.png").toExternalForm());
			addServerMenuItem.setGraphic(HBoxBuilder.create()
				.alignment(Pos.CENTER_LEFT)
				.children(image, new Label("Add Server")).build());
			remoteProjectMenu.getItems().add(addServerMenuItem);
			serverMenu.getItems().add(addServerMenuItem);
		}
		{
			final MenuItem addMenuItem = new MenuItem();
			final ImageView image = new ImageView(Activator.getContext().getBundle()
				.getResource("icons/checkout.png").toExternalForm());
			addMenuItem.setGraphic(new HBox(image, new Label("Checkout")));
			remoteProjectMenu.getItems().add(addMenuItem);
			addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent t) {
					final ESRemoteProject remoteProject = (ESRemoteProject) getTreeItem()
						.getValue();
					final CheckoutNameStage stage = new CheckoutNameStage();
					stage.showAndWait();
					if (stage.result == null) {
						return;
					}
					try {
						remoteProject.checkout(stage.result, remoteProject
							.getServer().getLastUsersession(),
							new NullProgressMonitor());
					} catch (final ESException e) {
						showError(e);
					}
				}
			});
		}
		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESLoginObserver() {

				@Override
				public void loginCompleted(ESUsersession session) {
					if (!getItem().equals(session.getServer())) {
						return;
					}
					loggedIn.set(true);
				}
			});
		ESWorkspaceProviderImpl.getObserverBus().register(
			new ESLogoutObserver() {

				@Override
				public void logoutCompleted(ESUsersession session) {
					if (!getItem().equals(session.getServer())) {
						return;
					}
					loggedIn.set(false);
				}
			});
	}

	private void showError(Exception e) {
		final Stage errorStage = new Stage();
		errorStage.initModality(Modality.WINDOW_MODAL);
		errorStage.setTitle("Error");
		errorStage.setScene(new Scene(VBoxBuilder.create().children(new Label(e.getMessage()))
			.build()));
		errorStage.showAndWait();
	}

	@Override
	public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);

		if (item != null) {

			String cellText = null;
			Node graphics = null;
			if (ESWorkspace.class.isInstance(item)) {
				cellText = "Known Server";
				// Label label = new Label("Known Server");
				// ImageView image = new
				// ImageView(Activator.getContext().getBundle().getResource("icons/remoteProject.png").toExternalForm());
				// graphics = HBoxBuilder.create().alignment(Pos.CENTER_LEFT)
				// .children(label).build();

			} else if (ESRemoteProject.class.isInstance(item)) {
				cellText =
					ESRemoteProject.class.cast(item).getProjectName();
				graphics = new ImageView(Activator.getContext()
					.getBundle().getResource("icons/remoteProject.png")
					.toExternalForm());
				// graphics = HBoxBuilder.create().alignment(Pos.CENTER_LEFT)
				// .children(image, label).build();
				setContextMenu(remoteProjectMenu);
			} else if (ESServer.class.isInstance(item)) {
				final ESServer server = (ESServer) item;
				{
					cellText = server.getName() + "("
						+ server.getURL() + ":" + server.getPort() + ")";
					graphics = new ImageView(Activator.getContext()
						.getBundle().getResource("icons/server.png")
						.toExternalForm());
					// graphics = HBoxBuilder.create().alignment(Pos.CENTER_LEFT)
					// .children(image, label).build();
				}
				if (localURLs.contains(server.getURL()) && EMFStoreController.getInstance() == null)
				{
					final MenuItem startServerItem = new MenuItem();
					final ImageView image = new ImageView(Activator.getContext()
						.getBundle().getResource("icons/server_go.png")
						.toExternalForm());
					startServerItem.setGraphic(HBoxBuilder.create()
						.alignment(Pos.CENTER_LEFT)
						.children(image, new Label("Start Server")).build());
					serverMenu.getItems().add(0, startServerItem);
					startServerItem.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							try {
								EMFStoreController.runAsNewThread();
								startServerItem.setVisible(false);
							} catch (final FatalESException ex) {
								showError(ex);
							}
						}
					});
				}

				{
					final MenuItem logInItem = new MenuItem();
					final ImageView image = new ImageView(Activator.getContext()
						.getBundle().getResource("icons/logIn.png")
						.toExternalForm());
					logInItem.setGraphic(HBoxBuilder.create()
						.alignment(Pos.CENTER_LEFT)
						.children(image, new Label("Login")).build());
					serverMenu.getItems().add(logInItem);
					logInItem.setOnAction(new LoginHandler());
					logInItem.visibleProperty().bind(loggedIn.not());
				}
				{
					final MenuItem logOutItem = new MenuItem();
					final ImageView image = new ImageView(Activator.getContext()
						.getBundle().getResource("icons/logOut.png")
						.toExternalForm());
					logOutItem.setGraphic(HBoxBuilder.create()
						.alignment(Pos.CENTER_LEFT)
						.children(image, new Label("Logout")).build());
					serverMenu.getItems().add(logOutItem);
					logOutItem.setOnAction(new LogoutHandler());

					logOutItem.visibleProperty().bind(loggedIn);
				}
				if (server.getLastUsersession() != null) {
					loggedIn.set(server.getLastUsersession().isLoggedIn());
				}
				setContextMenu(serverMenu);
			}

			// if (graphics != null)
			setGraphic(graphics);
			setText(cellText);
		}
	}
}