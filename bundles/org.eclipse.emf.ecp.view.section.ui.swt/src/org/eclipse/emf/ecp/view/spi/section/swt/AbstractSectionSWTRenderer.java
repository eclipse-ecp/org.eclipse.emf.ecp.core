/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.section.swt;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.common.util.RendererUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.swt.core.AbstractAdditionalSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.emfforms.spi.swt.core.layout.GridDescriptionFactory;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

/**
 * Common super class for all section renderer.
 *
 * @author jfaltermeier
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractSectionSWTRenderer extends
	AbstractSWTRenderer<VSection> {

	private final EMFDataBindingContext dbc;
	private final VTViewTemplateProvider viewTemplateProvider;

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.18
	 */
	public AbstractSectionSWTRenderer(VSection vElement, ViewModelContext viewContext, ReportService reportService,
		VTViewTemplateProvider viewTemplateProvider) {
		super(vElement, viewContext, reportService);
		this.viewTemplateProvider = viewTemplateProvider;
		dbc = new EMFDataBindingContext();
	}

	/**
	 * @return the viewTemplateProvider the {@link VTViewTemplateProvider}
	 * @since 1.18
	 */
	protected VTViewTemplateProvider getViewTemplateProvider() {
		return viewTemplateProvider;
	}

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		if (cell.getRenderer() == this) {
			if (cell.getColumn() == 0) {
				return createFirstColumn(parent);
			} else if (cell.getColumn() < 0) {
				return renderEmpty(parent);
			} else {
				/*-1 because label is column 0*/
				return renderChild(parent, getVElement().getChildren().get(cell.getColumn() - 1));
			}
		}
		return cell.getRenderer().render(cell, parent);

	}

	/**
	 * Renders the first column.
	 *
	 * @param parent the parent composite
	 * @return the rendered control
	 */
	protected abstract Control createFirstColumn(Composite parent);

	private Control renderEmpty(Composite parent) {
		return new Label(parent, SWT.NONE);
	}

	// BEGIN COMPLEX CODE
	private Control renderChild(Composite parent, VContainedElement child)
		throws NoRendererFoundException {
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setBackground(parent.getBackground());

		final Map<VContainedElement, Collection<AbstractSWTRenderer<VElement>>> elementRendererMap = new LinkedHashMap<VContainedElement, Collection<AbstractSWTRenderer<VElement>>>();
		SWTGridDescription maximalGridDescription = null;
		final Map<VContainedElement, SWTGridDescription> rowGridDescription = new LinkedHashMap<VContainedElement, SWTGridDescription>();
		final Map<VContainedElement, SWTGridDescription> controlGridDescription = new LinkedHashMap<VContainedElement, SWTGridDescription>();

		if (VControl.class.isInstance(child)) {
			if (VControl.class.cast(child).getDomainModelReference() == null) {
				return columnComposite;
			}
			try {
				getViewModelContext().getService(EMFFormsDatabinding.class)
					.getValueProperty(VControl.class.cast(child).getDomainModelReference(),
						getViewModelContext().getDomainModel());
			} catch (final DatabindingFailedException ex) {
				getReportService().report(new RenderingFailedReport(ex));
				return columnComposite;
			}
		}

		AbstractSWTRenderer<VElement> renderer;
		try {
			renderer = getEMFFormsRendererFactory()
				.getRendererInstance(child, getViewModelContext());
		} catch (final EMFFormsNoRendererException ex) {
			getReportService().report(new RenderingFailedReport(ex));
			return columnComposite;
		}
		final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getEMFFormsRendererFactory()
			.getAdditionalRendererInstances(child, getViewModelContext());
		SWTGridDescription gridDescription = renderer
			.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
		controlGridDescription.put(child, gridDescription);

		for (final AbstractAdditionalSWTRenderer<VElement> additionalRenderer : additionalRenderers) {
			gridDescription = additionalRenderer
				.getGridDescription(gridDescription);
		}
		rowGridDescription.put(child, gridDescription);
		maximalGridDescription = gridDescription;
		final Set<AbstractSWTRenderer<VElement>> allRenderer = new LinkedHashSet<AbstractSWTRenderer<VElement>>();
		allRenderer.add(renderer);
		allRenderer.addAll(additionalRenderers);
		elementRendererMap.put(child, allRenderer);

		if (maximalGridDescription == null) {
			return columnComposite;
		}
		columnComposite.setLayout(getLayout(
			maximalGridDescription.getColumns(), false));

		try {
			final SWTGridDescription gridDescription2 = rowGridDescription
				.get(child);
			if (gridDescription2 == null) {
				return columnComposite;
			}
			for (final SWTGridCell childGridCell : gridDescription2.getGrid()) {

				final Control control = childGridCell.getRenderer().render(
					childGridCell, columnComposite);
				// TODO who should apply the layout
				if (control == null) {
					continue;
				}

				// TODO possible layout issues?
				setLayoutDataForControl(childGridCell,
					controlGridDescription.get(child), gridDescription2,
					maximalGridDescription, childGridCell.getRenderer()
						.getVElement(),
					control);

			}
			for (final SWTGridCell childGridCell : gridDescription2.getGrid()) {
				childGridCell.getRenderer().finalizeRendering(columnComposite);
			}
		} catch (final NoPropertyDescriptorFoundExeption ex) {
			getReportService().report(new RenderingFailedReport(ex));
			return columnComposite;
		}

		return columnComposite;
	}

	// END COMPLEX CODE

	private Layout getLayout(int numControls, boolean equalWidth) {
		return LayoutProviderHelper.getColumnLayout(numControls, equalWidth);
	}

	/**
	 * Adjusts the visibility for all gridcells based on the collapse state.
	 *
	 * @param collapsed the collapse state
	 */
	protected void adjustLayoutData(boolean collapsed) {
		final boolean visible = collapsed;
		for (final SWTGridCell gridCell : getControls().keySet()) {
			final Object layoutData = getControls().get(gridCell)
				.getLayoutData();
			if (GridData.class.isInstance(layoutData)) {
				final GridData gridData = (GridData) layoutData;
				if (gridData != null) {
					gridData.exclude = !visible;
				}
			}
			getControls().get(gridCell).setVisible(visible);
		}
	}

	/**
	 * Access to the EMFFormsRendererFactory.
	 *
	 * @return The {@link EMFFormsRendererFactory}
	 * @since 1.6
	 */
	protected EMFFormsRendererFactory getEMFFormsRendererFactory() {
		return getViewModelContext().getService(EMFFormsRendererFactory.class);
	}

	/**
	 * Sets the LayoutData for the specified control.
	 *
	 * @param gridCell the {@link GridCell} used to render the control
	 * @param gridDescription the {@link GridDescription} of the parent which rendered the control
	 * @param currentRowGridDescription the {@link GridDescription} of the current row
	 * @param fullGridDescription the {@link GridDescription} of the whole container
	 * @param vElement the {@link VElement} to set the layoutData for
	 * @param control the control to set the layout to
	 */
	private void setLayoutDataForControl(SWTGridCell gridCell, SWTGridDescription gridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		Control control) {

		control.setLayoutData(LayoutProviderHelper.getLayoutData(gridCell, gridDescription, currentRowGridDescription,
			fullGridDescription, vElement, getViewModelContext().getDomainModel(), control));

	}

	/**
	 * Returns the {@link EMFDataBindingContext}.
	 *
	 * @return the data binding context
	 * @since 1.13
	 */
	protected EMFDataBindingContext getDataBindingContext() {
		return dbc;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer#dispose()
	 */
	@Override
	protected void dispose() {
		dbc.dispose();
		super.dispose();
	}

	/**
	 * Called by the {@link org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea} when all children have been
	 * renderered. Initialises the collapse state based on {@link VSection#isCollapsed()}.
	 *
	 * @since 1.6
	 */
	protected abstract void initCollapseState();

	/**
	 * @return the left margin
	 * @since 1.16
	 */
	protected int computeLeftMargin() {
		int numberOfParents = 0;
		EObject current = getVElement().eContainer();
		while (!VSectionedArea.class.isInstance(current)) {
			numberOfParents++;
			current = current.eContainer();
		}
		return (numberOfParents + 1) * 16;
	}

	/**
	 * The label width.
	 *
	 * @return the width
	 * @since 1.18
	 */
	protected Optional<Integer> getLabelWidth() {
		final VTLabelWidthStyleProperty styleProperty = RendererUtil.getStyleProperty(
			getViewTemplateProvider(),
			getVElement(),
			getViewModelContext(),
			VTLabelWidthStyleProperty.class);
		if (styleProperty == null || !styleProperty.isSetWidth()) {
			return Optional.empty();
		}
		return Optional.of(styleProperty.getWidth());
	}
}
