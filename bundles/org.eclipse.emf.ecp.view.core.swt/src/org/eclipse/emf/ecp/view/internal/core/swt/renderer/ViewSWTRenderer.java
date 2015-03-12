/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edagr Mueller - initial API and implementation
 * Eugen Neufeld - Refactoring
 * Johannes Falterimeier - Refactoring
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt.renderer;

import java.util.Collection;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer;
import org.eclipse.emf.ecp.view.spi.model.VContainedContainer;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.reporting.ReportService;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.swt.core.EMFFormsRendererFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

/**
 * The Class ViewSWTRenderer.
 */
public class ViewSWTRenderer extends ContainerSWTRenderer<VView> {

	private final EMFFormsEditSupport emfFormsEditSupport;

	/**
	 * Default constructor.
	 *
	 * @param vElement the view model element to be rendered
	 * @param viewContext the view context
	 * @param reportService the {@link ReportService}
	 * @param factory the {@link EMFFormsRendererFactory}
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 * @param emfFormsEditSupport The {@link EMFFormsEditSupport}
	 */
	public ViewSWTRenderer(VView vElement, ViewModelContext viewContext, ReportService reportService,
		EMFFormsRendererFactory factory, EMFFormsDatabinding emfFormsDatabinding,
		EMFFormsEditSupport emfFormsEditSupport) {
		super(vElement, viewContext, reportService, factory, emfFormsDatabinding);
		this.emfFormsEditSupport = emfFormsEditSupport;
	}

	@Override
	protected final void setLayoutDataForControl(SWTGridCell gridCell,
		SWTGridDescription controlGridDescription,
		SWTGridDescription currentRowGridDescription, SWTGridDescription fullGridDescription, VElement vElement,
		Control control) {
		if (VControl.class.isInstance(vElement)) {
			// last column of control
			if (gridCell.getColumn() + gridCell.getHorizontalSpan() == controlGridDescription.getColumns()) {
				getControlGridData(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
					- currentRowGridDescription.getColumns(), VControl.class.cast(vElement), control).applyTo(control);
			} else if (controlGridDescription.getColumns() == 3 && gridCell.getColumn() == 0) {
				GridDataFactory.fillDefaults().grab(false, false)
					.align(SWT.BEGINNING, SWT.CENTER).applyTo(control);
			} else if (controlGridDescription.getColumns() == 3 && gridCell.getColumn() == 1) {
				GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER)
					.hint(16, 17).grab(false, false).applyTo(control);
			} else if (controlGridDescription.getColumns() == 2 && gridCell.getColumn() == 0) {
				GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER)
					.hint(16, 17).grab(false, false).applyTo(control);
			}
		} else if (VContainedContainer.class.isInstance(vElement)) {
			GridDataFactory
				.fillDefaults()
				.align(gridCell.isHorizontalFill() ? SWT.FILL : SWT.BEGINNING,
					gridCell.isVerticalFill() ? SWT.FILL : SWT.CENTER)
				.grab(gridCell.isHorizontalGrab(), false)
				.span(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
					- currentRowGridDescription.getColumns(), 1).applyTo(control);
		}
		else {
			// we have some kind of container -> render with necessary span
			GridDataFactory
				.fillDefaults()
				.align(gridCell.isHorizontalFill() ? SWT.FILL : SWT.BEGINNING,
					gridCell.isVerticalFill() ? SWT.FILL : SWT.CENTER)
				.grab(gridCell.isHorizontalGrab(), gridCell.isVerticalGrab())
				.span(gridCell.getHorizontalSpan() + fullGridDescription.getColumns()
					- currentRowGridDescription.getColumns(), 1).applyTo(control);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getChildren()
	 */
	@Override
	protected Collection<VContainedElement> getChildren() {
		return getVElement().getChildren();
	}

	@Override
	protected Layout getLayout(int numControls, boolean equalWidth) {
		return GridLayoutFactory.fillDefaults().numColumns(numControls).equalWidth(equalWidth).create();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.core.swt.ContainerSWTRenderer#getCustomVariant()
	 */
	@Override
	protected String getCustomVariant() {
		return "org_eclipse_emf_ecp_ui_layout_view"; //$NON-NLS-1$
	}

	private GridDataFactory getControlGridData(int xSpan, VControl vControl, Control control) {
		GridDataFactory gdf =
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).span(xSpan, 1);

		if (Text.class.isInstance(control) && vControl.getDomainModelReference() != null) {
			if (getEMFFormsEditSupport().isMultiLine(vControl.getDomainModelReference(),
				getViewModelContext().getDomainModel())) {
				gdf = gdf.hint(50, 200); // set x hint to enable wrapping
			}
		}

		return gdf;
	}

	private EMFFormsEditSupport getEMFFormsEditSupport() {
		return emfFormsEditSupport;
	}
}
