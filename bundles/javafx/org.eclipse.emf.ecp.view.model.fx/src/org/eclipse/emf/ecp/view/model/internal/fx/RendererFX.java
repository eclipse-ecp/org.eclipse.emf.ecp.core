package org.eclipse.emf.ecp.view.model.internal.fx;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.scene.Node;

import org.eclipse.emf.ecp.view.model.common.AbstractRenderer;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;

public abstract class RendererFX<VELEMENT extends VElement> extends AbstractRenderer<VELEMENT> {

	private Map<GridCellFX, Node> nodes;

	/**
	 * Initialize the renderer. This method must be called before the renderer
	 * is used and can only be called once.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 */
	@Override
	public void init(final VELEMENT vElement, final ViewModelContext viewContext) {
		if (getVElement() != null) {
			return;
		}
		super.init(vElement, viewContext);
		nodes = new LinkedHashMap<GridCellFX, Node>();
	}

	/**
	 * Returns the GridDescriptionFX for this Renderer.
	 *
	 * @return the number of controls per row
	 */
	// TODO: When additional renderers are introduced, this method needs a GridDescriptionFX parameter.
	public abstract GridDescriptionFX getGridDescription();

	/**
	 * Renders the passed {@link GridCellFX} of the {@link VElement} of this renderer.
	 *
	 * @param cell the {@link GridCellFX} of the control to render
	 * @return the rendered {@link Node}
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 */
	public Node render(final GridCellFX cell)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		Node node = nodes.get(cell);
		if (node != null) {
			return node;
		}

		node = renderNode(cell);
		if (node == null) {
			// something went wrong, log
			return null;
		}
		nodes.put(cell, node);

		// TODO: do we need some kind of dispose listener like in SWT?

		return node;
	}

	/**
	 * Renders the passed {@link GridCellFX} of the {@link VElement} of this renderer.
	 *
	 * @param cell the {@link GridCellFX} of the control to render
	 * @return the rendered {@link Node}
	 * @throws NoRendererFoundException this is thrown when a renderer cannot be found
	 * @throws NoPropertyDescriptorFoundExeption this is thrown when no property descriptor can be found
	 */
	protected abstract Node renderNode(GridCellFX cell)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption;

	/**
	 * Returns a copy of the {@link GridCellFX} to {@link Node} map.
	 *
	 * @return a copy of the nodes map
	 */
	protected final Map<GridCellFX, Node> getNodes() {
		if (nodes == null) {
			return Collections.emptyMap();
		}
		return new LinkedHashMap<GridCellFX, Node>(nodes);
	}
}
