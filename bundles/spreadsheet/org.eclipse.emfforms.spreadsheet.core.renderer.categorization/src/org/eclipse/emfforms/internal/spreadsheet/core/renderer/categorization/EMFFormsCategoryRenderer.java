/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core.renderer.categorization;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsAbstractSpreadsheetRenderer;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsNoRendererException;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRenderTarget;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetRendererFactory;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;

/**
 * Spreadsheet renderer for {@link VCategory}.
 *
 * @author Eugen Neufeld
 */
public class EMFFormsCategoryRenderer extends EMFFormsAbstractSpreadsheetRenderer<VCategory> {

	private final EMFFormsSpreadsheetRendererFactory rendererFactory;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param rendererFactory The EMFFormsSpreadsheetRendererFactory to use
	 * @param reportService The {@link ReportService} to use
	 */
	public EMFFormsCategoryRenderer(EMFFormsSpreadsheetRendererFactory rendererFactory,
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
	public int render(Workbook workbook, VCategory vElement, ViewModelContext viewModelContext,
		EMFFormsSpreadsheetRenderTarget renderTarget) {
		int numberRenderedColumns = 0;
		try {
			final EMFFormsAbstractSpreadsheetRenderer<VElement> renderer = rendererFactory.getRendererInstance(
				vElement.getComposite(), viewModelContext);
			final String sheetName = WorkbookUtil.createSafeSheetName(vElement.getLabel());

			numberRenderedColumns += renderer.render(workbook, vElement.getComposite(), viewModelContext,
				new EMFFormsSpreadsheetRenderTarget(sheetName, renderTarget.getRow(), 0));
		} catch (final EMFFormsNoRendererException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}

		return numberRenderedColumns;
	}

}
