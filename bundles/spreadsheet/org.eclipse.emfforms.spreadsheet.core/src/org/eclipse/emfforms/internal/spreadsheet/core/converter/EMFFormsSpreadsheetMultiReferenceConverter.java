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
package org.eclipse.emfforms.internal.spreadsheet.core.converter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsCellStyleConstants;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * {@link EMFFormsSpreadsheetValueConverter Converter} for multi {@link EReference references}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "EMFFormsSpreadsheetMultiReferenceConverter")
public class EMFFormsSpreadsheetMultiReferenceConverter implements EMFFormsSpreadsheetValueConverter {

	private static final String SEPARATOR = "\n\n\n"; //$NON-NLS-1$

	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	/**
	 * Sets the databinding service.
	 *
	 * @param databinding the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	public void setDatabinding(EMFFormsDatabindingEMF databinding) {
		this.databinding = databinding;
	}

	/**
	 * Sets the report service.
	 *
	 * @param reportService the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, unbind = "-")
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public double isApplicable(EObject domainObject, VDomainModelReference dmr) {
		final EStructuralFeature feature = EMFFormsSpreadsheetValueConverterHelper.getFeature(domainObject, dmr,
			databinding,
			reportService);
		if (feature == null) {
			return NOT_APPLICABLE;
		}
		if (!EReference.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}
		return 0d;
	}

	@SuppressWarnings("unchecked")
	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter#setCellValue(org.apache.poi.ss.usermodel.Cell,
	 *      java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void setCellValue(Cell cell, Object values, EStructuralFeature eStructuralFeature,
		ViewModelContext viewModelContext)
		throws EMFFormsConverterException {
		if (values == null) {
			return;
		}
		try {
			final StringBuilder result = new StringBuilder();
			for (final EObject value : (List<EObject>) values) {
				if (result.length() != 0) {
					result.append(SEPARATOR);
				}
				result.append(XMIStringConverterHelper.getSerializedEObject(value));
			}
			cell.setCellValue(result.toString());
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.TEXT));
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}
		return;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter#getCellValue(org.apache.poi.ss.usermodel.Cell,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public Object getCellValue(Cell cell, EStructuralFeature eStructuralFeature) throws EMFFormsConverterException {
		final String string = cell.getStringCellValue();
		if (string == null || string.length() == 0) {
			return Collections.emptyList();
		}
		final List<EObject> result = new ArrayList<EObject>();
		final String[] split = string.split(SEPARATOR);
		for (final String element : split) {
			try {
				result.add(XMIStringConverterHelper.deserializeObject(element.trim()));
			} catch (final IOException ex) {
				throw new EMFFormsConverterException(
					MessageFormat.format("The cell value {0} could not be deserialized to a model value.", string)); //$NON-NLS-1$
			}
		}

		return result;
	}

}
