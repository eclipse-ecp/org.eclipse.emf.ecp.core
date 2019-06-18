/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.renderer;

import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VContainer;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;

/**
 * Spreadsheet renderer for {@link VContainer}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsSpreadsheetContainerRenderer extends
	EMFFormsAbstractSpreadsheetRenderer<VContainer> {

	private final EMFFormsSpreadsheetRendererFactory rendererFactory;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param rendererFactory The EMFFormsSpreadsheetRendererFactory to use
	 * @param reportService The {@link ReportService}
	 */
	public EMFFormsSpreadsheetContainerRenderer(EMFFormsSpreadsheetRendererFactory rendererFactory,
		ReportService reportService) {
		this.rendererFactory = rendererFactory;
		this.reportService = reportService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer#render(org.apache.poi.ss.usermodel.Workbook,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.context.ViewModelContext,
	 *      org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget)
	 */
	@Override
	public int render(Workbook workbook, VContainer vContainer,
		ViewModelContext viewModelContext,
		EMFFormsSpreadsheetRenderTarget eMFFormsSpreadsheetRenderTarget) {
		int numberRenderedColumns = 0;
		for (final VContainedElement containedElement : vContainer
			.getChildren()) {
			try {
				final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = rendererFactory
					.getRendererInstance(containedElement, viewModelContext);
				final int renderedColumns = renderer.render(workbook,
					containedElement, viewModelContext, new EMFFormsSpreadsheetRenderTarget(
						eMFFormsSpreadsheetRenderTarget.getSheetName(),
						eMFFormsSpreadsheetRenderTarget.getRow(), eMFFormsSpreadsheetRenderTarget.getColumn()));
				eMFFormsSpreadsheetRenderTarget.setColumn(eMFFormsSpreadsheetRenderTarget.getColumn()
					+ renderedColumns);
				numberRenderedColumns += renderedColumns;
			} catch (final EMFFormsNoRendererException ex) {
				reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
			}
		}
		return numberRenderedColumns;
	}
}
