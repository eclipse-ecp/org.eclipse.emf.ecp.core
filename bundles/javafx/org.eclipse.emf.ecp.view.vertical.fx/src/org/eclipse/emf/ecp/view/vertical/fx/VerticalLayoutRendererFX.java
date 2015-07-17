package org.eclipse.emf.ecp.view.vertical.fx;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import org.eclipse.emf.ecp.view.model.internal.fx.ContainerRendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;

public class VerticalLayoutRendererFX extends ContainerRendererFX<VVerticalLayout> {
	@Override
	protected Node renderNode(GridCellFX cell) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getColumn() != 0) {
			return null;
		}
		;

		return renderGrid();
	}

	@Override
	protected GridPane getGridPane() {
		final GridPane grid = new GridPane();
		grid.getStyleClass().add("vertical");
		return grid;
	}

}
