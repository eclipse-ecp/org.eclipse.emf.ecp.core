package org.eclipse.emf.ecp.view.horizontal.fx;

import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFXFactory;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HorizontalLayoutRendererFX extends RendererFX<VHorizontalLayout> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public HorizontalLayoutRendererFX(VHorizontalLayout vElement, ViewModelContext viewContext,
		ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

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

			for (int i = 0; i < rows; i++) {
				final HBox hBox = new HBox();
				for (int j = 0; j < columns; j++) {
					final Node node = compositeRenderer.render(rendererGrid.getGrid().get(i * columns + j));
					hBox.getChildren().add(node);
					if (columns - 1 == j) {
						HBox.setHgrow(node, Priority.ALWAYS);
					}
				}
				grid.add(hBox, gridColumn++, 0);
				GridPane.setHgrow(hBox, Priority.ALWAYS);
				if (VContainedContainer.class.isInstance(composite)) {
					GridPane.setVgrow(hBox, Priority.ALWAYS);
				}
			}
		}

		return grid;
	}

}
