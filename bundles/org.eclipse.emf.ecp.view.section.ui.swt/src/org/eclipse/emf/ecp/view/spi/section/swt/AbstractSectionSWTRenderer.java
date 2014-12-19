/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

import org.eclipse.emf.ecp.view.internal.section.ui.swt.Activator;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.swt.AbstractAdditionalSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
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
 *
 */
public abstract class AbstractSectionSWTRenderer extends
	AbstractSWTRenderer<VSection> {

	@Override
	protected Control renderControl(SWTGridCell cell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		if (cell.getRenderer() == this) {
			switch (cell.getColumn()) {
			case 0:
				return createFirstColumn(parent);

			case 1:
				if (getVElement().getChildren().size() < 3) {
					return renderEmpty(parent);
				}
				return renderChild(parent, getVElement().getChildren().get(0));

			case 2:
				if (getVElement().getChildren().size() < 2) {
					return renderEmpty(parent);
				}
				return renderChild(
					parent,
					getVElement().getChildren().get(
						getVElement().getChildren().size() - 2));

			case 3:
				return renderChild(
					parent,
					getVElement().getChildren().get(
						getVElement().getChildren().size() - 1));
			default:
				throw new IllegalArgumentException(""); //$NON-NLS-1$
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

		if (VControl.class.isInstance(child)
			&& (VControl.class.cast(child).getDomainModelReference() == null || !VControl.class
				.cast(child).getDomainModelReference().getIterator()
				.hasNext())) {
			return columnComposite;
		}
		final AbstractSWTRenderer<VElement> renderer = getSWTRendererFactory()
			.getRenderer(child, getViewModelContext());
		if (renderer == null) {
			// TODO log
			return columnComposite;
		}
		final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getSWTRendererFactory()
			.getAdditionalRenderer(child, getViewModelContext());
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
			if (VControl.class.isInstance(child)
				&& (VControl.class.cast(child).getDomainModelReference() == null || !VControl.class
					.cast(child).getDomainModelReference()
					.getIterator().hasNext())) {
				return columnComposite;
			}
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
						.getVElement(), control);

			}
			for (final SWTGridCell childGridCell : gridDescription2.getGrid()) {
				childGridCell.getRenderer().finalizeRendering(columnComposite);
			}
		} catch (final NoPropertyDescriptorFoundExeption ex) {
			Activator.getDefault().getReportService().report(new RenderingFailedReport(ex));
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
}
