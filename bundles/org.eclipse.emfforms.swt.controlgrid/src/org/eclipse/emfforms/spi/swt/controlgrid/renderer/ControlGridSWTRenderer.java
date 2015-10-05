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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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

	private SWTGridDescription rendererGridDescription;

	private final EMFFormsRendererFactory rendererFactory;

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
		if (cell.getColumn() != 0 || cell.getRow() != 0) {
			throw new IllegalArgumentException(
				MessageFormat.format("The grid cell {0} cannot be rendered.", cell.toString())); //$NON-NLS-1$
		}

		final Map<VControlGridCell, AbstractSWTRenderer<VElement>> renderers = getChildRenderers();

		final Map<AbstractSWTRenderer<VElement>, SWTGridDescription> gridDescriptions = getGridDescriptions(
			renderers.values());

		final Map<SWTGridDescription, Integer> gridDescriptionToRequiredRendererColumnsMap = getRequiredColumnSizesOfRenderers(
			gridDescriptions);

		final int actualSWTColumnCountAvailableForEachRenderer = getColumnsPerRenderer(
			gridDescriptionToRequiredRendererColumnsMap);

		final Set<Integer> columnsAsPerControlGrid = getColumnCountsFromRows();

		final int swtColumnsAsPerControlGrid = computeColumnCountSoThatAllRowsCanBeRendered(columnsAsPerControlGrid);

		final int layoutColumns = swtColumnsAsPerControlGrid * actualSWTColumnCountAvailableForEachRenderer;

		/* create composite with columns */
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(layoutColumns).equalWidth(false).applyTo(composite);

		/* render the rows */
		for (final VControlGridRow row : getVElement().getRows()) {
			if (row.getCells().size() == 0) {
				renderEmptyColumn(composite, layoutColumns);
				continue;
			}
			final int swtColumnsAvailableForRowElement = layoutColumns / row.getCells().size();
			for (final VControlGridCell vCell : row.getCells()) {
				/* render placeholder/controls with no renderer */
				if (!renderers.containsKey(vCell)) {
					renderEmptyColumn(composite, swtColumnsAvailableForRowElement);
				}
				/* render controls */
				else {
					/* render */
					final AbstractSWTRenderer<VElement> renderer = renderers.get(vCell);
					final SWTGridDescription swtGridDescription = gridDescriptions.get(renderer);

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
				}
			}
		}

		/* finalize */
		for (final AbstractSWTRenderer<VElement> renderer : renderers.values()) {
			renderer.finalizeRendering(composite);
		}

		composite.layout(true, true);

		return composite;
	}

	private void applyLayout(final Composite composite, final int swtColumnsAvailableForRowElement,
		final SWTGridDescription swtGridDescription, int cellsWithoutHorizontalGrab, int cellsWithHorizontalGrab,
		int spanForSpanningCells, int spanForLastSpanningCell)
			throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		int withHorizontalGrabLeft = cellsWithHorizontalGrab;
		int withoutHorizontalGrabLeft = cellsWithoutHorizontalGrab;
		for (final SWTGridCell swtGridCell : swtGridDescription.getGrid()) {
			final Control control = swtGridCell.getRenderer().render(swtGridCell, composite);
			if (swtGridCell.isHorizontalGrab()) {
				final int hSpan = withHorizontalGrabLeft == 1 ? spanForLastSpanningCell
					: spanForSpanningCells;
				withHorizontalGrabLeft--;
				GridDataFactory
					.fillDefaults()
					.span(hSpan, 1)
					.grab(true, false)
					.align(SWT.FILL, SWT.CENTER)
					.applyTo(control);
			} else if (cellsWithHorizontalGrab == 0 && withoutHorizontalGrabLeft == 1) {
				/*
				 * if we have no spanning cells: renderer the last non spanning cell with span to take up the
				 * remaining columns available for the rowelement
				 */
				withoutHorizontalGrabLeft--;
				final int hSpan = swtColumnsAvailableForRowElement - cellsWithoutHorizontalGrab + 1;
				GridDataFactory
					.fillDefaults()
					.span(hSpan, 1)
					.grab(true, false)
					.align(SWT.FILL, SWT.CENTER)
					.applyTo(control);
			} else {
				withoutHorizontalGrabLeft--;
				// XXX minSize is not working... preferred way of solving this would be the next line only
				// GridDataFactory.fillDefaults().span(1, 1).grab(false, false).align(SWT.BEGINNING,
				// SWT.CENTER).minSize(16,SWT.DEFAULT).applyTo(control);
				GridDataFactory gridDataFactory = GridDataFactory
					.fillDefaults()
					.span(1, 1)
					.grab(false, false)
					.align(SWT.BEGINNING, SWT.CENTER);
				if (swtGridDescription.getColumns() == 3 && swtGridCell.getColumn() == 1
					|| swtGridDescription.getColumns() == 2 && swtGridCell.getColumn() == 0) {
					// XXX hacky way to make validation labels visible because as stated above min size is
					// not working
					gridDataFactory = gridDataFactory.hint(16, SWT.DEFAULT);
				}
				gridDataFactory.applyTo(control);
			}
		}
	}

	private void renderEmptyColumn(final Composite composite, final int swtColumnsAvailableForRowElement) {
		final Label label = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().span(swtColumnsAvailableForRowElement, 1).grab(false, false)
			.applyTo(label);
	}

	private int computeColumnCountSoThatAllRowsCanBeRendered(final Set<Integer> columnsAsPerControlGrid) {
		int swtColumnsAsPerControlGrid = 1;
		for (final Integer integer : columnsAsPerControlGrid) {
			if (integer == 0) {
				continue;
			}
			swtColumnsAsPerControlGrid = lcm(swtColumnsAsPerControlGrid, integer);
		}
		return swtColumnsAsPerControlGrid;
	}

	private Set<Integer> getColumnCountsFromRows() {
		final Set<Integer> columnsAsPerControlGrid = new LinkedHashSet<Integer>();
		for (final VControlGridRow row : getVElement().getRows()) {
			columnsAsPerControlGrid.add(row.getCells().size());
		}
		return columnsAsPerControlGrid;
	}

	private int getColumnsPerRenderer(final Map<SWTGridDescription, Integer> requiredColumnSizesOfRenderers) {
		int columnsPerRenderer = 1;
		for (final Integer integer : requiredColumnSizesOfRenderers.values()) {
			columnsPerRenderer = lcm(columnsPerRenderer, integer);
		}
		return columnsPerRenderer;
	}

	private Map<SWTGridDescription, Integer> getRequiredColumnSizesOfRenderers(
		final Map<AbstractSWTRenderer<VElement>, SWTGridDescription> gridDescriptions) {
		final Map<SWTGridDescription, Integer> requiredColumnSizesOfRenderers = new LinkedHashMap<SWTGridDescription, Integer>();
		for (final SWTGridDescription description : gridDescriptions.values()) {
			requiredColumnSizesOfRenderers.put(description, description.getColumns());
		}
		return requiredColumnSizesOfRenderers;

	}

	private Map<VControlGridCell, AbstractSWTRenderer<VElement>> getChildRenderers() {
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

	private Map<AbstractSWTRenderer<VElement>, SWTGridDescription> getGridDescriptions(
		final Collection<AbstractSWTRenderer<VElement>> renderers) {
		final Map<AbstractSWTRenderer<VElement>, SWTGridDescription> gridDescriptions = new LinkedHashMap<AbstractSWTRenderer<VElement>, SWTGridDescription>();
		for (final AbstractSWTRenderer<VElement> renderer : renderers) {
			final SWTGridDescription gridDescription = renderer
				.getGridDescription(GridDescriptionFactory.INSTANCE.createEmptyGridDescription());
			gridDescriptions.put(renderer, gridDescription);
		}
		return gridDescriptions;
	}

	@Override
	protected void dispose() {
		super.dispose();
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
}
