package org.eclipse.emf.ecp.view.group.fx;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.ecp.view.model.internal.fx.ContainerRendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

public class GroupRendererFX extends ContainerRendererFX<VGroup> {
	@Override
	protected Node renderNode(GridCellFX cell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getColumn() != 0) {
			return null;
		}

		final TitledPane groupPane = new TitledPane();
		groupPane.setCollapsible(false);
		final GridPane grid = renderGrid();

		if (grid.getChildren().size() == 1) {
			GridPane.setVgrow(grid.getChildren().get(0), Priority.ALWAYS);
		}

		String text = getVElement().getName();
		if (text == null) {
			text = "";
		}
		groupPane.setText(text);
		groupPane.setContent(grid);
		groupPane.setMaxHeight(Double.MAX_VALUE);

		return groupPane;
	}

	@Override
	protected GridPane getGridPane() {
		final GridPane grid = new GridPane();
		grid.getStyleClass().add("vertical");
		return grid;
	}
}
