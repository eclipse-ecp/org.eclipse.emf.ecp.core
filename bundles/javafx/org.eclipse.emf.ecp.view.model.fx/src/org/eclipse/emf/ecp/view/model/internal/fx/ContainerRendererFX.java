package org.eclipse.emf.ecp.view.model.internal.fx;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class ContainerRendererFX<T extends VContainedContainer> extends
	RendererFX<T> {

	private GridDescriptionFX gridDescription;

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public ContainerRendererFX(T vElement, ViewModelContext viewContext, ReportService reportService) {
		super(vElement, viewContext, reportService);
	}

	protected abstract GridPane getGridPane();

	@Override
	public GridDescriptionFX getGridDescription() {
		if (gridDescription == null) {
			gridDescription = GridDescriptionFXFactory.INSTANCE.createSimpleGrid(1, 1, this);
		}
		return gridDescription;
	}

	protected GridPane renderGrid() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		final GridPane grid = getGridPane();
		final T element = getVElement();

		int maxColumns = 1;
		for (final VContainedElement composite : element.getChildren()) {
			final RendererFX<VElement> compositeRenderer = RendererFactory.INSTANCE
				.getRenderer(composite, getViewModelContext());
			final GridDescriptionFX rendererGrid = compositeRenderer.getGridDescription();
			final int columns = rendererGrid.getColumns();
			if (columns > maxColumns) {
				maxColumns = columns;
			}
		}

		int gridRow = -1;
		for (final VContainedElement composite : element.getChildren()) {
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
		return grid;
	}

}
