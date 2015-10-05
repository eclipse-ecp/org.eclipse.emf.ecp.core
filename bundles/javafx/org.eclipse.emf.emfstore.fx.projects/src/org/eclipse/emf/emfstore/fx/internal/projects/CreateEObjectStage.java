package org.eclipse.emf.emfstore.fx.internal.projects;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.fx.emf.edit.ui.AdapterFactoryTreeCellFactory;
import org.eclipse.fx.emf.edit.ui.AdapterFactoryTreeItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateEObjectStage extends Stage {

	private final class TreeItemExtension extends TreeItem<Object> {
		private final TreeView<Object> treeView;
		private final ObservableList<TreeItem<Object>> children;

		private TreeItemExtension(TreeView<Object> treeView) {
			super();
			this.treeView = treeView;
			children = FXCollections.unmodifiableObservableList(super.getChildren());
			updateChildren();
		}

		private void updateChildren() {
			final ObservableList<TreeItem<Object>> children = super.getChildren();
			children.clear();
			for (final String nsURI : Registry.INSTANCE.keySet()) {
				final EPackage ePackage = Registry.INSTANCE.getEPackage(nsURI);
				final AdapterFactoryTreeItem rootItem = new AdapterFactoryTreeItem(
					ePackage, adapterFactory);
				children.add(rootItem);
			}
		}

		@Override
		public ObservableList<TreeItem<Object>> getChildren() {
			return children;
		}
	}

	private EClass selectedEClass;
	private ComposedAdapterFactory adapterFactory;

	public CreateEObjectStage() {
		super();
		initModality(Modality.WINDOW_MODAL);
		setTitle("Select EClass");

		adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		final TreeView<Object> treeView = new TreeView<>();
		treeView.setMaxHeight(Double.MAX_VALUE);
		treeView.setMaxWidth(Double.MAX_VALUE);

		final TreeItem<Object> root = getRegistryTreeItem(treeView);
		// root.setExpanded(true);

		treeView.setRoot(root);
		treeView.setShowRoot(false);

		final AdapterFactoryTreeCellFactory cellFactory = new AdapterFactoryTreeCellFactory(
			adapterFactory);

		treeView.setCellFactory(cellFactory);

		final Button button = new Button("Create");
		button.setMaxWidth(Double.MAX_VALUE);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				final Object value = treeView.getSelectionModel().getSelectedItem()
					.getValue();
				if (EClass.class.isInstance(value)) {
					selectedEClass = (EClass) value;
					close();
				}
			}
		});
		button.setDisable(true);

		treeView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<TreeItem<Object>>() {

				@Override
				public void changed(
					ObservableValue<? extends TreeItem<Object>> observable,
					TreeItem<Object> oldValue, TreeItem<Object> newValue) {
					if (newValue == null) {
						return;
					}
					final Object value = newValue.getValue();
					if (!EClass.class.isInstance(value)) {
						button.setDisable(true);
						return;
					}
					final EClass eClass = (EClass) value;
					button.setDisable(eClass.isAbstract() || eClass.isInterface());

				}
			});
		setScene(new Scene(VBoxBuilder.create().children(treeView, button)
			.build()));
		VBox.setVgrow(treeView, Priority.ALWAYS);
		// dialogStage.show();
	}

	private TreeItem<Object> getRegistryTreeItem(final TreeView<Object> treeView) {
		final TreeItem<Object> result = new TreeItemExtension(treeView);
		return result;
	}

	@Override
	public void close() {
		adapterFactory.dispose();
		super.close();
	}

	public EObject getResult() {
		if (selectedEClass == null) {
			return null;
		}
		return EcoreUtil.create(selectedEClass);
	}
}
