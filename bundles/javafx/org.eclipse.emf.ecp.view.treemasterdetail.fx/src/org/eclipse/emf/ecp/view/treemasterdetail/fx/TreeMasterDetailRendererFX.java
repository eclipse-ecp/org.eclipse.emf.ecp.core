package org.eclipse.emf.ecp.view.treemasterdetail.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.util.fx.EMFUtil;
import org.eclipse.emf.ecp.view.model.fx.ECPFXView;
import org.eclipse.emf.ecp.view.model.fx.ECPFXViewRenderer;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFXFactory;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFX;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

// TODO api
@SuppressWarnings("restriction")
public class TreeMasterDetailRendererFX extends RendererFX<VTreeMasterDetail> {
	private GridDescriptionFX gridDescription;

	@Override
	public GridDescriptionFX getGridDescription() {
		if (gridDescription == null) {
			gridDescription = GridDescriptionFXFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return gridDescription;
	}

	@Override
	protected Node renderNode(GridCellFX gridCell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final GridPane grid = new GridPane();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
			ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final TreeView<Object> treeView = new TreeView<>();
		final AdapterFactoryTreeItem rootItem = new AdapterFactoryTreeItem(
			getViewModelContext().getDomainModel(), treeView, adapterFactory);
		treeView.setRoot(rootItem);
		treeView.setShowRoot(true);
		final AdapterFactoryTreeCellFactory cellFactory = new AdapterFactoryTreeCellFactory(
			adapterFactory);
		final ContextMenu createItemsContextMenu = new ContextMenu();
		cellFactory.addCellUpdateListener(new ICellUpdateListener() {

			@Override
			public void updateItem(Cell<?> cell, Object item, boolean empty) {
				if (EObject.class.isInstance(item)) {
					final MenuItem createChildrenMenu = EMFUtil
						.getCreateChildrenMenu((EObject) item,
							new Callback<Void, Void>() {

								@Override
								public Void call(Void param) {
									treeView.selectionModelProperty()
										.getValue().selectNext();
									return null;
								}
							});
					if (createChildrenMenu == null) {
						return;
					}
					createItemsContextMenu.getItems().clear();
					createItemsContextMenu.getItems().add(createChildrenMenu);
					cell.setContextMenu(createItemsContextMenu);

				}
			}
		});
		treeView.setCellFactory(cellFactory);

		final ScrollPane sp = new ScrollPane();

		treeView.getSelectionModel().selectedItemProperty()
			.addListener(new ChangeListener<TreeItem<Object>>() {

				@Override
				public void changed(
					ObservableValue<? extends TreeItem<Object>> ov,
					TreeItem<Object> old_val, TreeItem<Object> new_val) {

					ECPFXView render = null;
					if (new_val.getValue() == getViewModelContext()
						.getDomainModel()) {
						final VTreeMasterDetail renderable = getVElement();
						render = ECPFXViewRenderer.INSTANCE.render(
							renderable.getDetailView(),
							(EObject) new_val.getValue());
					} else {
						render = ECPFXViewRenderer.INSTANCE
							.render((EObject) new_val.getValue());
					}

					final GridPane node = (GridPane) render.getFXNode();
					sp.setContent(node);
					node.prefWidthProperty().bind(
						sp.widthProperty().subtract(2));
					node.prefHeightProperty().bind(
						sp.heightProperty().subtract(2));
				}
			});

		treeView.getSelectionModel().select(0);

		grid.add(treeView, 0, 0);
		grid.add(sp, 1, 0);

		GridPane.setHgrow(sp, Priority.ALWAYS);
		GridPane.setVgrow(sp, Priority.ALWAYS);
		GridPane.setVgrow(treeView, Priority.ALWAYS);

		return grid;
	}

}
