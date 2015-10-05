package org.eclipse.emf.ecp.view.group.fx;

import org.eclipse.emf.ecp.view.model.internal.fx.ContainerRendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class GroupRendererFX extends ContainerRendererFX<VGroup> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public GroupRendererFX(VGroup vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

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

		String text = getVElement().getLabel();
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
