package org.eclipse.emf.ecp.view.model.internal.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

public class ViewRendererFX extends RendererFX<VView> {

	private GridDescriptionFX gridDescription;

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

		final VView vView = getVElement();
		final GridPane grid = new GridPane();
		grid.getStyleClass().add("vertical");

		int maxColumns = 1;
		for (final VContainedElement composite : vView.getChildren()) {
			final RendererFX<VElement> compositeRenderer = RendererFactory.INSTANCE
				.getRenderer(composite, getViewModelContext());
			final GridDescriptionFX rendererGrid = compositeRenderer.getGridDescription();
			final int columns = rendererGrid.getColumns();
			if (columns > maxColumns) {
				maxColumns = columns;
			}
		}

		int gridRow = -1;
		for (final VContainedElement composite : vView.getChildren()) {
			final RendererFX<VElement> compositeRenderer = RendererFactory.INSTANCE
				.getRenderer(composite, getViewModelContext());
			final GridDescriptionFX rendererGrid = compositeRenderer.getGridDescription();
			final int rows = rendererGrid.getRows();
			final int columns = rendererGrid.getColumns();

			for (int i = 0; i < rows; i++) {
				gridRow++;
				for (int j = 0; j < columns; j++) {
					final Node node = compositeRenderer.render(rendererGrid.getGrid().get(i * columns + j));
					grid.add(node, j, gridRow);
					if (node instanceof Label) {
						GridPane.setHgrow(node, Priority.SOMETIMES);
					} else {
						GridPane.setHgrow(node, Priority.ALWAYS);
					}
					if (VContainedContainer.class.isInstance(composite)) {
						GridPane.setVgrow(node, Priority.ALWAYS);
					}

					if (j == columns - 1) {
						GridPane.setColumnSpan(node, maxColumns - columns + 1);
					}
				}
			}
		}

		if (grid.getChildren().size() == 1) {
			GridPane.setVgrow(grid.getChildren().get(0), Priority.ALWAYS);
		}

		return grid;
	}

}
