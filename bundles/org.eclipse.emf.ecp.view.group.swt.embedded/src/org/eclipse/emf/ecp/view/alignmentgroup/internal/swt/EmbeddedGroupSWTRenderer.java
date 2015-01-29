/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.alignmentgroup.internal.swt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.AbstractAdditionalSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * A Group renderer, which doesn't enforce its own layout.
 *
 * @author Eugen Neufeld
 *
 */
public class EmbeddedGroupSWTRenderer extends AbstractSWTRenderer<VGroup> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public EmbeddedGroupSWTRenderer(VGroup vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
	}

	private SWTGridDescription currentGridDescription;

	@Override
	public SWTGridDescription getGridDescription(SWTGridDescription gridDescription) {
		currentGridDescription = new SWTGridDescription();
		final List<SWTGridCell> grid = new ArrayList<SWTGridCell>();
		currentGridDescription.setRows(getVElement().getChildren().size() + 1);
		currentGridDescription.setGrid(grid);

		int row = 1;
		final Map<Integer, List<SWTGridCell>> gridCellsPerRow = new LinkedHashMap<Integer, List<SWTGridCell>>();
		for (final VContainedElement containedElement : getVElement().getChildren()) {
			gridCellsPerRow.put(row, new ArrayList<SWTGridCell>());
			final AbstractSWTRenderer<VElement> renderer = getSWTRendererFactory().getRenderer(containedElement,
				getViewModelContext());
			final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getSWTRendererFactory()
				.getAdditionalRenderer(containedElement, getViewModelContext());
			SWTGridDescription rendererGridDescription = renderer.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
			for (final AbstractAdditionalSWTRenderer<VElement> additionalRenderer : additionalRenderers) {
				rendererGridDescription = additionalRenderer.getGridDescription(rendererGridDescription);
			}
			if (currentGridDescription.getColumns() < rendererGridDescription.getColumns()) {
				currentGridDescription.setColumns(rendererGridDescription.getColumns());
			}
			for (final SWTGridCell rendererGridCell : rendererGridDescription.getGrid()) {
				final SWTGridCell gc = new SWTGridCell(row, rendererGridCell.getColumn(),
					rendererGridCell.getRenderer());
				currentGridDescription.getGrid().add(gc);
				gridCellsPerRow.get(row).add(gc);
			}
			row++;
		}
		for (int i = 0; i < currentGridDescription.getColumns(); i++) {
			final SWTGridCell gridCell = new SWTGridCell(0, i, this);
			gridCell.setVerticalGrab(false);
			gridCell.setVerticalFill(false);
			gridCell.setHorizontalFill(false);
			gridCell.setHorizontalGrab(false);
			grid.add(i, gridCell); // label;
		}

		for (final Integer gdRow : gridCellsPerRow.keySet()) {
			final List<SWTGridCell> rowGridCells = gridCellsPerRow.get(gdRow);
			if (currentGridDescription.getColumns() > rowGridCells.size()) {
				// span last cell -> x columns - #cells + 1 as the current cell already was subtracted
				rowGridCells.get(rowGridCells.size() - 1).setHorizontalSpan(
					currentGridDescription.getColumns() - rowGridCells.size() + 1);
			}
		}
		return currentGridDescription;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#renderControl(org.eclipse.emf.ecp.view.spi.swt.layout.GridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getRenderer().equals(this)) {
			final Label l = new Label(parent, SWT.NONE);
			l.setBackground(parent.getBackground());
			String text = getVElement().getName();
			if (text == null)
			{
				text = ""; //$NON-NLS-1$
			}
			switch (getVElement().getLabelAlignment()) {
			case LABEL_ALIGNED:
				if (cell.getColumn() == 0) {
					l.setText(text);
					l.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_view_group_title"); //$NON-NLS-1$
				}
				break;
			case INPUT_ALIGNED:
				if (cell.getColumn() + 1 == currentGridDescription.getColumns()) {
					l.setText(text);
					l.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_view_group_title"); //$NON-NLS-1$
				}
				break;
			default:
				break;
			}
			return l;
		}
		return cell.getRenderer().render(cell, parent);
	}
}
