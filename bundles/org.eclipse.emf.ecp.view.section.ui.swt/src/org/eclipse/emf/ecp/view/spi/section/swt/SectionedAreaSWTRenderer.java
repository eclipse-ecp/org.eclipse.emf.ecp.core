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
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;
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

/**
 * Renderer for {@link VSectionedArea}.
 *
 * @author jfaltermeier
 *
 */
public class SectionedAreaSWTRenderer extends
	AbstractSWTRenderer<VSectionedArea> {

	private static final String CUSTOM_VARIANT_VALUE = "org_eclipse_emf_ecp_ui_section"; //$NON-NLS-1$

	private SWTGridDescription gridDescription;

	private AbstractSectionSWTRenderer rootRenderer;

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
		rootRenderer = AbstractSectionSWTRenderer.class.cast(getSWTRendererFactory()
			.getRenderer(child, getViewModelContext()));
		if (rootRenderer == null) {
			return columnComposite;
		}
		final Collection<AbstractAdditionalSWTRenderer<VElement>> additionalRenderers = getSWTRendererFactory()
			.getAdditionalRenderer(child, getViewModelContext());
		SWTGridDescription gridDescription = rootRenderer
			.getGridDescription(GridDescriptionFactory.INSTANCE
				.createEmptyGridDescription());
		controlGridDescription = gridDescription;

		for (final AbstractAdditionalSWTRenderer<VElement> additionalRenderer : additionalRenderers) {
			gridDescription = additionalRenderer
				.getGridDescription(gridDescription);
		}
		rowGridDescription = gridDescription;
		maximalGridDescription = gridDescription;
		final Set<AbstractSWTRenderer<?>> allRenderer = new LinkedHashSet<AbstractSWTRenderer<?>>();
		allRenderer.add(rootRenderer);
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer#finalizeRendering(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void finalizeRendering(Composite parent) {
		/* after all children have been rendered, we can init the visibility state */
		rootRenderer.initCollapseState();
		super.finalizeRendering(parent);
	}

}
