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

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.locale.EMFFormsLocaleProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsCellStyleConstants;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * {@link EMFFormsSpreadsheetValueConverter Converter} for multi {@link EAttribute attributes}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "EMFFormsSpreadsheetMultiAttributeConverter")
public class EMFFormsSpreadsheetMultiAttributeConverter implements EMFFormsSpreadsheetValueConverter {

	private static final String SEPARATOR = " "; //$NON-NLS-1$

	private EMFFormsDatabinding databinding;
	private ReportService reportService;

	private EMFFormsLocaleProvider localeProvider;

	/**
	 * Sets the databinding service.
	 *
	 * @param databinding the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	public void setDatabinding(EMFFormsDatabinding databinding) {
		this.databinding = databinding;
	}

	/**
	 * Sets the EMFFormsLocaleProvider service.
	 *
	 * @param localeProvider the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	protected void setEMFFormsLocaleProvider(EMFFormsLocaleProvider localeProvider) {
		this.localeProvider = localeProvider;
	}

	/**
	 * Sets the report service.
	 *
	 * @param reportService the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY)
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
		if (!EAttribute.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}
		return 0d;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter#setCellValue(org.apache.poi.ss.usermodel.Cell,
	 *      java.lang.Object, org.eclipse.emf.ecore.EStructuralFeature,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void setCellValue(Cell cell, Object fromObject, EStructuralFeature eStructuralFeature,
		ViewModelContext viewModelContext)
			throws EMFFormsConverterException {
		final EDataType eDataType = EAttribute.class.cast(eStructuralFeature).getEAttributeType();
		final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
		final StringBuilder result = new StringBuilder();
		for (final Object value : (List<?>) fromObject) {
			if (result.length() != 0) {
				result.append(SEPARATOR);
			}
			result.append(eFactory.convertToString(eDataType, value));
		}
		String valueString = result.toString();
		if (isDecimalNumber(eDataType.getInstanceClass())) {
			valueString = valueString.replace('.',
				DecimalFormatSymbols.getInstance(localeProvider.getLocale()).getDecimalSeparator());
		}
		cell.setCellValue(valueString);
		cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.TEXT));
	}

	private boolean isDecimalNumber(final Class<?> clazz) {
		return Double.TYPE == clazz
			|| Double.class.isAssignableFrom(clazz)
			|| Float.TYPE == clazz
			|| Float.class.isAssignableFrom(clazz)
			|| BigDecimal.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter#getCellValue(org.apache.poi.ss.usermodel.Cell,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public Object getCellValue(Cell cell, EStructuralFeature eStructuralFeature) throws EMFFormsConverterException {
		String string = cell.getStringCellValue();
		if (string == null || string.length() == 0) {
			return Collections.emptyList();
		}
		final EAttribute eAttribute = EAttribute.class.cast(eStructuralFeature);
		final EDataType eDataType = eAttribute.getEAttributeType();
		if (isDecimalNumber(eDataType.getInstanceClass())) {
			string = string.replace(DecimalFormatSymbols.getInstance(localeProvider.getLocale()).getDecimalSeparator(),
				'.');
		}

		final List<Object> result = new ArrayList<Object>();
		final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
		for (final String element : string.split(SEPARATOR)) {
			try {
				result.add(eFactory.createFromString(eDataType, element));
			} // BEGIN SUPRESS CATCH EXCEPTION
			catch (final RuntimeException ex) {// END SUPRESS CATCH EXCEPTION
				throw new EMFFormsConverterException(
					MessageFormat.format("The cell value {0} could not converted to a model value.", string)); //$NON-NLS-1$
			}
		}

		return result;
	}

}
