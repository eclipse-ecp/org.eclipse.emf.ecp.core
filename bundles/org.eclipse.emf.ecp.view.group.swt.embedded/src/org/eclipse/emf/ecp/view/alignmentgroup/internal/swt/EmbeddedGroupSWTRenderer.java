/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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

import javax.inject.Inject;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.reporting.StatusReport;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
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

	private final EMFFormsRendererFactory rendererFactory;
	private final EMFDataBindingContext dbc;

	/**
	 * Default Constructor.
	 *
	 * @param vElement the view element to be rendered
	 * @param viewContext The view model context
	 * @param reportService the ReportService to use
	 * @param rendererFactory the EMFFormsRendererFactory to use
	 */
	@Inject
	public EmbeddedGroupSWTRenderer(final VGroup vElement, final ViewModelContext viewContext,
		ReportService reportService, EMFFormsRendererFactory rendererFactory) {
		super(vElement, viewContext, reportService);
		this.rendererFactory = rendererFactory;
		dbc = new EMFDataBindingContext();
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
			AbstractSWTRenderer<VElement> renderer;
			try {
				renderer = rendererFactory.getRendererInstance(containedElement,
					getViewModelContext());
			} catch (final EMFFormsNoRendererException ex) {
				getReportService().report(
					new StatusReport(
						new Status(IStatus.INFO, "org.eclipse.emf.ecp.view.group.swt.embedded", String.format( //$NON-NLS-1$
							"No Renderer for %s found.", containedElement.eClass().getName()), ex))); //$NON-NLS-1$
				continue;
			}
			final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = rendererFactory
				.getAdditionalRendererInstances(containedElement, getViewModelContext());
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
				gc.setHorizontalAlignment(rendererGridCell.getHorizontalAlignment());
				gc.setHorizontalFill(rendererGridCell.isHorizontalFill());
				gc.setHorizontalGrab(rendererGridCell.isHorizontalGrab());
				gc.setHorizontalSpan(rendererGridCell.getHorizontalSpan());
				gc.setVerticalAlignment(rendererGridCell.getVerticalAlignment());
				gc.setVerticalFill(rendererGridCell.isVerticalFill());
				gc.setVerticalGrab(rendererGridCell.isVerticalGrab());
				gc.setPreferredSize(rendererGridCell.getPreferredSize());

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
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#renderControl(org.eclipse.emfforms.spi.swt.core.layout.GridCell,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption {
		if (cell.getRenderer().equals(this)) {
			final Control heading = createHeadingControl(parent);

			switch (getVElement().getLabelAlignment()) {
			case LABEL_ALIGNED:
				if (cell.getColumn() == 0) {
					bindValue(heading);
					heading.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_view_group_title"); //$NON-NLS-1$
				}
				break;
			case INPUT_ALIGNED:
				if (cell.getColumn() + 1 == currentGridDescription.getColumns()) {
					// bindValue(l);
					heading.setData(CUSTOM_VARIANT, "org_eclipse_emf_ecp_view_group_title"); //$NON-NLS-1$
				}
				break;
			default:
				break;
			}
			return heading;
		}
		return cell.getRenderer().render(cell, parent);
	}

	/**
	 * Creates the control, which is used as a heading for the embedded group.
	 *
	 * @param parent The parent to render the heading control on
	 * @return the heading {@link Control}
	 */
	protected Control createHeadingControl(Composite parent) {
		final Label heading = new Label(parent, SWT.NONE);
		heading.setBackground(parent.getBackground());
		return heading;
	}

	/** Creates a binding that synchronizes the value of the target {@link Label} with the model value. */
	private void bindValue(Control target) {
		final ISWTObservableValue targetValue = WidgetProperties.text().observe(target);
		final IObservableValue modelValue = EMFEditObservables.observeValue(
			AdapterFactoryEditingDomain.getEditingDomainFor(getVElement()), getVElement(),
			VViewPackage.eINSTANCE.getElement_Label());
		dbc.bindValue(targetValue, modelValue);
	}

	@Override
	protected void dispose() {
		if (dbc != null) {
			dbc.dispose();
		}
		super.dispose();
	}
}
