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
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecp.view.internal.section.ui.swt.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
import org.eclipse.emf.ecp.view.spi.swt.AbstractAdditionalSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.SWTRendererFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.GridDescriptionFactory;
import org.eclipse.emf.ecp.view.spi.swt.layout.LayoutProviderHelper;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.swt.reporting.RenderingFailedReport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Renderer for {@link VSectionedArea}.
 *
 * @author jfaltermeier
 *
 */
public class SectionedAreaSWTRenderer extends
	AbstractSWTRenderer<VSectionedArea> {

	/**
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param factory the {@link SWTRendererFactory}
	 */
	public SectionedAreaSWTRenderer(VSectionedArea vElement, ViewModelContext viewContext, SWTRendererFactory factory) {
		super(vElement, viewContext, factory);
		// TODO Auto-generated constructor stub
	}

	private static final String CUSTOM_VARIANT_VALUE = "org_eclipse_emf_ecp_ui_section"; //$NON-NLS-1$

	private SWTGridDescription gridDescription;

	@Override
	public SWTGridDescription getGridDescription(
		SWTGridDescription gridDescription) {
		if (this.gridDescription == null) {
			this.gridDescription = GridDescriptionFactory.INSTANCE
				.createSimpleGrid(1, 1, this);
		}
		return this.gridDescription;
	}

	@Override
	protected Control renderControl(SWTGridCell gridCell, Composite parent)
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		if (gridCell.getColumn() != 0) {
			return null;
		}
		final Composite columnComposite = new Composite(parent, SWT.NONE);
		columnComposite.setData(CUSTOM_VARIANT, CUSTOM_VARIANT_VALUE);
		columnComposite.setBackground(parent.getBackground());

		SWTGridDescription maximalGridDescription = null;
		SWTGridDescription rowGridDescription = null;
		SWTGridDescription controlGridDescription = null;
		final VSection child = getVElement().getRoot();
		final AbstractSWTRenderer<VElement> renderer = getSWTRendererFactory()
			.getRenderer(child, getViewModelContext());
		if (renderer == null) {
			return columnComposite;
		}
		final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getSWTRendererFactory()
			.getAdditionalRenderer(child, getViewModelContext());
		SWTGridDescription gridDescription = renderer
			.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
		controlGridDescription = gridDescription;

		for (final AbstractAdditionalSWTRenderer<VElement> additionalRenderer : additionalRenderers) {
			gridDescription = additionalRenderer
				.getGridDescription(gridDescription);
		}
		rowGridDescription = gridDescription;
		maximalGridDescription = gridDescription;
		final Set<AbstractSWTRenderer<VElement>> allRenderer = new LinkedHashSet<AbstractSWTRenderer<VElement>>();
		allRenderer.add(renderer);
		allRenderer.addAll(additionalRenderers);

		if (maximalGridDescription == null) {
			return columnComposite;
		}
		columnComposite.setLayout(LayoutProviderHelper.getColumnLayout(
			maximalGridDescription.getColumns(), false));

		try {
			final SWTGridDescription gridDescription2 = rowGridDescription;
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
				setLayoutDataForControl(childGridCell, controlGridDescription,
					gridDescription2, maximalGridDescription, childGridCell
						.getRenderer().getVElement(), control);

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

	@Override
	protected void setLayoutDataForControl(SWTGridCell gridCell,
		SWTGridDescription gridDescription,
		SWTGridDescription currentRowGridDescription,
		SWTGridDescription fullGridDescription, VElement vElement,
		Control control) {

		final Object layoutData = LayoutProviderHelper.getLayoutData(gridCell,
			gridDescription, currentRowGridDescription,
			fullGridDescription, vElement, control);
		if (!GridData.class.isInstance(layoutData)) {
			return;
		}
		if (gridCell.getColumn() == 0) {
			GridData.class.cast(layoutData).widthHint = 300;
		} else if (gridCell.getColumn() == 1) {
			GridData.class.cast(layoutData).widthHint = 20;
		} else if (gridCell.getColumn() == 2) {
			GridData.class.cast(layoutData).widthHint = 20;
		} else if (gridCell.getColumn() == 3) {
			if (SectionLeafSWTRenderer.class.isInstance(gridCell
				.getRenderer())) {
				GridData.class.cast(layoutData).grabExcessHorizontalSpace = false;
				GridData.class.cast(layoutData).horizontalAlignment = SWT.BEGINNING;
			}
			GridData.class.cast(layoutData).widthHint = 500;
		}
		control.setLayoutData(layoutData);
	}

}
