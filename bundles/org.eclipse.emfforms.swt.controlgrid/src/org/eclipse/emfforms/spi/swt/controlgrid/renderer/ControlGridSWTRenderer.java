/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.controlgrid.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * SWT Renderer for {@link VControlGrid}.
 *
 * @author Johannes Faltermeier
 *
 */
public class ControlGridSWTRenderer extends AbstractSWTRenderer<VControlGrid> {

	private static final int SPACING = 20;

	private final Map<Control, Integer> controlsToAlign = new LinkedHashMap<Control, Integer>();

	private SWTGridDescription rendererGridDescription;

	private final EMFFormsRendererFactory rendererFactory;

	private SWTGridDescription emptyGridDescription;

	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @param rendererFactory the {@link EMFFormsRendererFactory renderer factory}
	 */
	@Inject
	public ControlGridSWTRenderer(
		VControlGrid vElement,
		ViewModelContext viewContext,
		ReportService reportService,
		EMFFormsRendererFactory rendererFactory) {
		super(vElement, viewContext, reportService);
		this.rendererFactory = rendererFactory;
	}

	/**
	 * Returns the {@link EMFFormsRendererFactory}.
	 *
	 * @return the renderer factory
	 */
	protected EMFFormsRendererFactory getRendererFactory() {
		return rendererFactory;
	}

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		if (rendererGridDescription == null) {
			rendererGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, this);
			rendererGridDescription.getGrid().get(0).setVerticalGrab(false);
		}
		return rendererGridDescription;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final Map<VControlGridCell, AbstractSWTRenderer<VElement>> renderers = getChildRenderers();

		final Map<AbstractSWTRenderer<VElement>, SWTGridDescription> gridDescriptions = getGridDescriptions(
			renderers.values());

		emptyGridDescription = createGridDescriptionForEmptyCells(gridDescriptions.values());

		final Map<SWTGridDescription, Integer> gridDescriptionToRequiredRendererColumnsMap = getRequiredColumnSizesOfRenderers(
			gridDescriptions.values());

		final int actualSWTColumnCountAvailableForEachRenderer = getColumnsPerRenderer(
			gridDescriptionToRequiredRendererColumnsMap.values());

		final Set<Integer> columnsAsPerControlGrid = getColumnCountsFromRows();

		final int swtColumnsAsPerControlGrid = computeColumnCountSoThatAllRowsCanBeRendered(columnsAsPerControlGrid);

		final int layoutColumns = computeColumnsForSWTLayout(actualSWTColumnCountAvailableForEachRenderer,
			swtColumnsAsPerControlGrid);

		/* create composite with columns */
		final Composite composite = createControlGridComposite(parent, layoutColumns);

		/* render the rows */
		for (final VControlGridRow row : getVElement().getRows()) {
			if (row.getCells().size() == 0) {
				final Label label = new Label(composite, SWT.NONE);
				GridDataFactory.fillDefaults().span(layoutColumns, 1).grab(false, false)
					.applyTo(label);
				continue;
			}
			/* -1 because of spacing label */
			final int swtColumnsAvailableForRowElement = layoutColumns / row.getCells().size() - 1;
			for (final VControlGridCell vCell : row.getCells()) {
				/* render */
				final AbstractSWTRenderer<VElement> renderer = renderers.get(vCell);
				final SWTGridDescription swtGridDescription = renderer != null
					? gridDescriptions.get(renderer)
					: emptyGridDescription;

				/* analyze grid cells */
				int cellsWithoutHorizontalGrab = 0;
				int cellsWithHorizontalGrab = 0;
				for (final SWTGridCell swtGridCell : swtGridDescription.getGrid()) {
					if (!swtGridCell.isHorizontalGrab()) {
						cellsWithoutHorizontalGrab++;
					} else {
						cellsWithHorizontalGrab++;
					}
				}

				/* distribute space between grabing cells as equal as possible. */
				final int swtColumnsAvailableForGrabingCells = swtColumnsAvailableForRowElement
					- cellsWithoutHorizontalGrab;
				int spanForSpanningCells = 0;
				int spanForLastSpanningCell = 0;
				if (cellsWithHorizontalGrab > 0) {
					spanForSpanningCells = swtColumnsAvailableForGrabingCells / cellsWithHorizontalGrab;
					spanForLastSpanningCell = swtColumnsAvailableForGrabingCells
						- (cellsWithHorizontalGrab - 1) * spanForSpanningCells;
				}

				/* apply layout */
				applyLayout(composite, swtColumnsAvailableForRowElement, swtGridDescription,
					cellsWithoutHorizontalGrab, cellsWithHorizontalGrab, spanForSpanningCells,
					spanForLastSpanningCell);

				/* render spacing label */
				final Label spacing = new Label(composite, SWT.NONE);
				final int xHint = row.getCells().get(row.getCells().size() - 1) != vCell ? getHorizontalSpacing() : 1;
				GridDataFactory.fillDefaults().hint(xHint, SWT.DEFAULT).applyTo(spacing);

			}
		}

		/* finalize */
		for (final AbstractSWTRenderer<VElement> renderer : renderers.values()) {
			renderer.finalizeRendering(composite);
		}

		/* listen for layout changes */
		final Optional<Integer> pixelGridSize = getPixelGridSize();
		if (pixelGridSize.isPresent() && !controlsToAlign.isEmpty()) {
			composite.addControlListener(new AlignNonGrabbingControlsListener(composite, pixelGridSize.get()));
		}

		composite.layout(true, true);

		return composite;
	}

	/**
	 * When this returns a non-empty optional all non-spanning/grabbing controls in a spanning/grabbing parent will have
	 * a width which is a multiple of this pixel grid size. To be more specific, the preferred size of the control will
	 * be rounded up to match this criteria.
	 *
	 * @return the grid size in pixels
	 * @since 1.16
	 */
	protected Optional<Integer> getPixelGridSize() {
		return Optional.empty();
	}

	/**
	 * Multiplies the two column counts giving a required number for columns to use in the SWT composite.
	 *
	 * @param actualSWTColumnCountAvailableForEachRenderer count1
	 * @param swtColumnsAsPerControlGrid count2
	 * @return the column count
	 */
	int computeColumnsForSWTLayout(final int actualSWTColumnCountAvailableForEachRenderer,
		final int swtColumnsAsPerControlGrid) {
		final int layoutColumns = swtColumnsAsPerControlGrid * actualSWTColumnCountAvailableForEachRenderer;
		return layoutColumns;
	}

	/**
	 * Creates a composite with the given number of columns.
	 *
	 * @param parent the parent
	 * @param layoutColumns the columns
	 * @return the new composite
	 */
	Composite createControlGridComposite(Composite parent, final int layoutColumns) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(layoutColumns).equalWidth(false).applyTo(composite);
		return composite;
	}

	/**
	 * Returns the hint for the horizontal spacing.
	 *
	 * @return the spacing
	 * @since 1.8
	 */
	protected int getHorizontalSpacing() {
		return SPACING;
	}

	// BEGIN COMPLEX CODE
	private void applyLayout(final Composite composite, final int swtColumnsAvailableForRowElement,
		final SWTGridDescription swtGridDescription, int cellsWithoutHorizontalGrab, int cellsWithHorizontalGrab,
		int spanForSpanningCells, int spanForLastSpanningCell)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int withHorizontalGrabLeft = cellsWithHorizontalGrab;
		int withoutHorizontalGrabLeft = cellsWithoutHorizontalGrab;
		for (final SWTGridCell swtGridCell : swtGridDescription.getGrid()) {
			/*
			 * Create a wrapper composite, so that the child renderer may take as little space as it wants inside the
			 * wrapper
			 */
			final Composite wrapperComposite = new Composite(composite, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(wrapperComposite);
			final Control control;
			if (swtGridCell.getRenderer() != null) {
				control = swtGridCell.getRenderer().render(swtGridCell, wrapperComposite);
			} else {
				control = new Label(wrapperComposite, SWT.NONE);
			}

			boolean withHorizontalGrab = true;
			GridData gridData;
			if (swtGridCell.isHorizontalGrab()) {
				final int hSpan = withHorizontalGrabLeft == 1 ? spanForLastSpanningCell
					: spanForSpanningCells;
				withHorizontalGrabLeft--;
				gridData = createGridDataForControlWithHorizontalGrab(swtGridDescription, swtGridCell, control,
					hSpan);
			} else if (cellsWithHorizontalGrab == 0 && withoutHorizontalGrabLeft == 1) {
				/*
				 * if we have no spanning cells: renderer the last non spanning cell with span to take up the
				 * remaining columns available for the rowelement
				 */
				withoutHorizontalGrabLeft--;
				final int hSpan = swtColumnsAvailableForRowElement - cellsWithoutHorizontalGrab + 1;
				gridData = createGridDataForControlWithHorizontalGrab(swtGridDescription, swtGridCell, control,
					hSpan);
			} else {
				withHorizontalGrab = false;
				withoutHorizontalGrabLeft--;
				// XXX minSize is not working... preferred way of solving this would be the next line only
				// GridDataFactory.fillDefaults().span(1, 1).grab(false, false).align(SWT.BEGINNING,
				// SWT.CENTER).minSize(16,SWT.DEFAULT).applyTo(control);
				gridData = createGridDataForControlWithoutHorizontalGrab(swtGridDescription, swtGridCell,
					control);
			}

			final GridData controlGriddata = GridDataFactory.createFrom(gridData).span(1, 1).create();

			if (!gridData.grabExcessHorizontalSpace && withHorizontalGrab) {
				/* if the width of a child is limited, we should still span on the wrapper */
				gridData.grabExcessHorizontalSpace = true;
			}
			if (gridData.grabExcessHorizontalSpace) {
				/* set a minimal width hint as this will act as weight for spanning */
				/* if a width hint was set beforehand, the control will still have the set value */
				gridData.widthHint = 1;
			}
			if (!swtGridCell.isHorizontalGrab() && controlGriddata.grabExcessHorizontalSpace) {
				/*
				 * if we force a control to span because of layout-reasons (e.g. when only spanning cells, last cell
				 * will span) we have to make sure that the inner control does not span
				 */
				controlGriddata.grabExcessHorizontalSpace = false;
			}

			if (gridData.grabExcessHorizontalSpace && !controlGriddata.grabExcessHorizontalSpace) {
				/* child placed in grabbing parent. Store in map to align in a nice manner */
				int widthHint;
				if (controlGriddata.widthHint == SWT.DEFAULT) {
					/* if no pref size was specified via layout data use the natural width of the control */
					widthHint = control.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
				} else {
					widthHint = controlGriddata.widthHint;
				}
				controlsToAlign.put(control, widthHint);
				final Optional<Integer> pixelGridSize = getPixelGridSize();
				if (pixelGridSize.isPresent()) {
					controlGriddata.widthHint = computePreferredWidthBasedOnPixelGridSize(
						widthHint,
						pixelGridSize.get());
				}
			}

			wrapperComposite.setLayoutData(gridData);
			control.setLayoutData(controlGriddata);
		}
	}
	// END COMPLEX CODE

	/**
	 * Creates the {@link GridData} which will be set on control which will take a span of 1 column an have no
	 * horizontal grab.
	 *
	 * @param swtGridDescription the {@link SWTGridDescription}
	 * @param swtGridCell the current {@link SWTGridCell} of the description
	 * @param control the {@link Control}
	 * @return the layout data
	 * @since 1.8
	 */
	protected GridData createGridDataForControlWithoutHorizontalGrab(final SWTGridDescription swtGridDescription,
		final SWTGridCell swtGridCell, final Control control) {
		GridData gridData;
		final GridDataFactory gridDataFactory = GridDataFactory
			.fillDefaults()
			.span(1, 1)
			.grab(false, false)
			.align(SWT.FILL, SWT.CENTER);

		if (swtGridCell.getPreferredSize() != null) {
			gridDataFactory.hint(swtGridCell.getPreferredSize());
		}

		gridData = gridDataFactory.create();
		return gridData;
	}

	/**
	 * Creates the {@link GridData} which will be set on control which will take up horizontal space an will span over
	 * the given amount of columns.
	 *
	 * @param swtGridDescription the {@link SWTGridDescription}
	 * @param swtGridCell the current {@link SWTGridCell} of the description
	 * @param control the {@link Control}
	 * @param hSpan the horizontal span
	 * @return the layout data
	 * @since 1.8
	 */
	protected GridData createGridDataForControlWithHorizontalGrab(final SWTGridDescription swtGridDescription,
		final SWTGridCell swtGridCell, final Control control, final int hSpan) {
		GridData gridData;
		gridData = GridDataFactory
			.fillDefaults()
			.span(hSpan, 1)
			.grab(true, false)
			.align(SWT.FILL, SWT.CENTER)
			.create();
		if (swtGridCell.getPreferredSize() != null) {
			gridData.widthHint = swtGridCell.getPreferredSize().x;
			gridData.heightHint = swtGridCell.getPreferredSize().y;
		}
		return gridData;
	}

	/**
	 * Computes the lcm of all column counts.
	 *
	 * @param columnsAsPerControlGrid the counts
	 * @return the lcm
	 */
	/* package */ int computeColumnCountSoThatAllRowsCanBeRendered(final Collection<Integer> columnsAsPerControlGrid) {
		int swtColumnsAsPerControlGrid = 1;
		for (final Integer integer : columnsAsPerControlGrid) {
			if (integer == 0) {
				continue;
			}
			swtColumnsAsPerControlGrid = lcm(swtColumnsAsPerControlGrid, integer);
		}
		return swtColumnsAsPerControlGrid;
	}

	/**
	 * Counts the columns as requested by the control grid.
	 *
	 * @return a set of all wanted columns
	 */
	/* package */ Set<Integer> getColumnCountsFromRows() {
		final Set<Integer> columnsAsPerControlGrid = new LinkedHashSet<Integer>();
		for (final VControlGridRow row : getVElement().getRows()) {
			columnsAsPerControlGrid.add(row.getCells().size());
		}
		return columnsAsPerControlGrid;
	}

	/**
	 * Will compute the lcm of the given integers.
	 *
	 * @param collection the ints
	 * @return the lcm
	 */
	/* package */ int getColumnsPerRenderer(final Collection<Integer> collection) {
		int columnsPerRenderer = 1;
		for (final Integer integer : collection) {
			columnsPerRenderer = lcm(columnsPerRenderer, integer);
		}
		return columnsPerRenderer;
	}

	/**
	 * Returns a map from griddescription to required column size. This will be 1 more than specified by the description
	 * itself, since we will render an additional label after each control to allow adding spacing.
	 *
	 * @param collection the descriptions
	 * @return the map
	 */
	/* package */ Map<SWTGridDescription, Integer> getRequiredColumnSizesOfRenderers(
		final Collection<SWTGridDescription> collection) {
		final Map<SWTGridDescription, Integer> requiredColumnSizesOfRenderers = new LinkedHashMap<SWTGridDescription, Integer>();
		for (final SWTGridDescription description : collection) {
			// +1 because we will renderer an empty spacing label after each control
			requiredColumnSizesOfRenderers.put(description, description.getColumns() + 1);
		}
		return requiredColumnSizesOfRenderers;

	}

	/**
	 * Returns a Map from cell to renderer. If a cell is empty or a renderer could not be created, there will be no
	 * entry.
	 *
	 * @return the map
	 */
	/* package */ Map<VControlGridCell, AbstractSWTRenderer<VElement>> getChildRenderers() {
		final Map<VControlGridCell, AbstractSWTRenderer<VElement>> renderers = new LinkedHashMap<VControlGridCell, AbstractSWTRenderer<VElement>>();
		for (final VControlGridRow row : getVElement().getRows()) {
			for (final VControlGridCell cell : row.getCells()) {
				final VControl control = cell.getControl();
				if (control == null) {
					/* render empty */
					continue;
				}
				try {
					final AbstractSWTRenderer<VElement> renderer = getRendererFactory()
						.getRendererInstance(control, getViewModelContext());
					renderers.put(cell, renderer);
				} catch (final EMFFormsNoRendererException ex) {
					getReportService().report(new AbstractReport(ex));
				}

			}
		}
		return renderers;
	}

	/**
	 * Returns a map from renderer to its grid description.
	 *
	 * @param renderers the renderers
	 * @return the map
	 */
	/* package */ Map<AbstractSWTRenderer<VElement>, SWTGridDescription> getGridDescriptions(
		final Collection<AbstractSWTRenderer<VElement>> renderers) {
		final Map<AbstractSWTRenderer<VElement>, SWTGridDescription> gridDescriptions = new LinkedHashMap<AbstractSWTRenderer<VElement>, SWTGridDescription>();
		for (final AbstractSWTRenderer<VElement> renderer : renderers) {
			final SWTGridDescription gridDescription = renderer
				.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
			gridDescriptions.put(renderer, gridDescription);
		}
		return gridDescriptions;
	}

	/**
	 * @param values the collected grid description of the renderers
	 * @return a {@link SWTGridDescription} which will be used to create empty cells. Please note that the
	 *         {@link SWTGridCell#getRenderer() renderer} of this description will be ignored, so it is fine to pass
	 *         <code>null</code> as a renderer
	 * @since 1.16
	 */
	// BEGIN COMPLEX CODE
	protected SWTGridDescription createGridDescriptionForEmptyCells(Collection<SWTGridDescription> values) {
		final Iterator<SWTGridDescription> iterator = values.iterator();

		/* in case of no reference description, use on cell */
		if (!iterator.hasNext()) {
			return createEmptySingleCellGridDescription();
		}

		final SWTGridDescription first = iterator.next();

		final int rows = first.getRows();
		final int columns = first.getColumns();
		final List<Boolean> grabHorizontal = new ArrayList<Boolean>();
		final List<Point> sizes = new ArrayList<Point>();

		for (final SWTGridCell swtGridCell : first.getGrid()) {
			grabHorizontal.add(swtGridCell.isHorizontalGrab());
			final Point size = swtGridCell.getPreferredSize();
			if (size == null) {
				sizes.add(new Point(-1, -1));
			} else {
				sizes.add(new Point(size.x, size.y));
			}
		}

		while (iterator.hasNext()) {
			final SWTGridDescription next = iterator.next();

			if (rows != next.getRows()) {
				return createEmptySingleCellGridDescription();
			}

			if (columns != next.getColumns()) {
				return createEmptySingleCellGridDescription();
			}

			for (int i = 0; i < next.getGrid().size(); i++) {
				final SWTGridCell swtGridCell = next.getGrid().get(i);
				if (!swtGridCell.isHorizontalGrab()) {
					grabHorizontal.set(i, false);
				}
				final Point sizeToCheck = swtGridCell.getPreferredSize();
				if (sizeToCheck == null) {
					continue;
				}
				final Point curSize = sizes.get(i);
				sizes.set(i, new Point(
					sizeToCheck.x > curSize.x ? sizeToCheck.x : curSize.x,
					sizeToCheck.y > curSize.y ? sizeToCheck.y : curSize.y));
			}
		}

		final SWTGridDescription swtGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(rows, columns,
			null);
		for (int j = 0; j < swtGridDescription.getGrid().size(); j++) {
			final SWTGridCell swtGridCell = swtGridDescription.getGrid().get(j);
			swtGridCell.setHorizontalGrab(grabHorizontal.get(j));
			swtGridCell.setHorizontalFill(grabHorizontal.get(j));
			swtGridCell.setPreferredSize(sizes.get(j));
		}

		return swtGridDescription;
	}
	// END COMPLEX CODE

	private static SWTGridDescription createEmptySingleCellGridDescription() {
		final SWTGridDescription swtGridDescription = GridDescriptionFactory.INSTANCE.createSimpleGrid(1, 1, null);
		swtGridDescription.getGrid().get(0).setHorizontalGrab(false);
		swtGridDescription.getGrid().get(0).setHorizontalFill(false);
		return swtGridDescription;
	}

	/**
	 * Rounds up the given width based on the given grid size.
	 *
	 * @param prefWidth the width to round up
	 * @param pixelGridSize grid size
	 * @return the new width
	 */
	/* package */ int computePreferredWidthBasedOnPixelGridSize(final double prefWidth, int pixelGridSize) {
		return Double.class.cast(Math.ceil(prefWidth / pixelGridSize)).intValue() * pixelGridSize;
	}

	private static int gcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		return gcd(b, a % b);
	}

	private static int lcm(int a, int b) {
		return Math.abs(a * b) / gcd(a, b);
	}

	/**
	 * {@link ControlListener} which makes non-grabbing controls slightly bigger so that they get aligned in a more
	 * structured way.
	 *
	 * @author Johannes Faltermeier
	 *
	 */
	private final class AlignNonGrabbingControlsListener implements ControlListener {
		private final Composite composite;
		private final int pixelGridSize;

		private AlignNonGrabbingControlsListener(Composite composite, int pixelGridSize) {
			this.composite = composite;
			this.pixelGridSize = pixelGridSize;
		}

		@Override
		public void controlMoved(ControlEvent e) {
			/* empty */
		}

		@Override
		public void controlResized(ControlEvent e) {
			for (final Entry<Control, Integer> entry : controlsToAlign.entrySet()) {
				final int availableWidth = entry.getKey().getParent().getSize().x;
				if (availableWidth == 0) {
					continue;
				}
				final int prefWidth = entry.getValue();

				int widthHint = computePreferredWidthBasedOnPixelGridSize(prefWidth, pixelGridSize);
				if (widthHint > availableWidth) {
					widthHint = prefWidth > availableWidth ? prefWidth : availableWidth;
				}
				GridData.class.cast(entry.getKey().getLayoutData()).widthHint = widthHint;
			}
			composite.layout(true, true);
		}
	}
}
