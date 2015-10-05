package org.eclipse.emf.ecp.view.horizontal.fx;

import org.eclipse.emf.ecp.view.model.internal.fx.GridCellFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFX;
import org.eclipse.emf.ecp.view.model.internal.fx.GridDescriptionFXFactory;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFX;
import org.eclipse.emf.ecp.view.model.internal.fx.RendererFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.horizontal.model.VHorizontalLayout;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emfforms.spi.common.report.ReportService;

import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HorizontalLayoutPageRendererFX extends
	RendererFX<VHorizontalLayout> {

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 */
	public HorizontalLayoutPageRendererFX(VHorizontalLayout vElement, ViewModelContext viewContext,
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
		// TODO Auto-generated method stub
		if (cell.getColumn() != 0) {
			return null;
		}

		final VHorizontalLayout vHorizontal = getVElement();
		final Pagination pagination = new Pagination(vHorizontal.getChildren()
			.size(), 0);
		pagination.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer param) {
				final VBox box = new VBox();
				final RendererFX<VElement> compositeRenderer = RendererFactory.INSTANCE
					.getRenderer(vHorizontal.getChildren().get(param), getViewModelContext());
				final GridDescriptionFX rendererGrid = compositeRenderer.getGridDescription();
				final int rows = rendererGrid.getRows();
				final int columns = rendererGrid.getColumns();

				for (int i = 0; i < rows; i++) {
					final HBox hBox = new HBox();
					for (int j = 0; j < columns; j++) {
						Node node = null;
						try {
							node = compositeRenderer.render(rendererGrid.getGrid().get(i * columns + j));
						} catch (final NoRendererFoundException e) {
							e.printStackTrace();
						} catch (final NoPropertyDescriptorFoundExeption e) {
							e.printStackTrace();
						}
						hBox.getChildren().add(node);
					}
					box.getChildren().add(hBox);
				}
				return box;
			}
		});
		return pagination;
	}

}
