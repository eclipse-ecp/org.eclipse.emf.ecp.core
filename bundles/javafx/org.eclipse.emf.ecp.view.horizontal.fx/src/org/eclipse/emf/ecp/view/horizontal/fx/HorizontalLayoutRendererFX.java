package org.eclipse.emf.ecp.view.horizontal.fx;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFXFactory;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFactory;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

public class HorizontalLayoutRendererFX extends RendererFX<VHorizontalLayout> {
	private GridDescriptionFX gridDescription;

	private void addColumnConstraint(GridPane grid) {

		final ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		column.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().add(column);
	}

	@Override
	public GridDescriptionFX getGridDescription() {
		if (gridDescription == null) {
			gridDescription = GridDescriptionFXFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return gridDescription;
	}

	@Override
	protected Node renderNode(GridCellFX cell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getColumn() != 0) {
			return null;
		}

		final VHorizontalLayout vHorizontalLayout = getVElement();
		final GridPane grid = new GridPane();
		grid.getStyleClass().add("horizontal");

		int gridColumn = 0;
		for (final VContainedElement composite : vHorizontalLayout.getChildren()) {
			final RendererFX<VElement> compositeRenderer = RendererFactory.INSTANCE
				.getRenderer(composite, getViewModelContext());
			final GridDescriptionFX rendererGrid = compositeRenderer.getGridDescription();
			final int rows = rendererGrid.getRows();
			final int columns = rendererGrid.getColumns();
			final ColumnConstraints cc = new ColumnConstraints();
			cc.setHgrow(Priority.ALWAYS);
			grid.getColumnConstraints().add(cc);

			for (int i = 0; i < rows; i++) {
				final HBox hBox = new HBox();
				for (int j = 0; j < columns; j++) {
					final Node node = compositeRenderer.render(rendererGrid.getGrid().get(i * columns + j));
					hBox.getChildren().add(node);
					if (columns == j - 1) {
						HBox.setHgrow(node, Priority.ALWAYS);
					}
				}
				grid.add(hBox, gridColumn++, 0);
				GridPane.setHgrow(hBox, Priority.ALWAYS);
				if (VContainer.class.isInstance(composite)) {
					GridPane.setVgrow(hBox, Priority.ALWAYS);
				}
				addColumnConstraint(grid);
			}
		}

		return grid;
	}

}
