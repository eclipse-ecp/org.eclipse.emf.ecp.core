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
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsCellStyleConstants;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsConverterException;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter;
import org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * {@link EMFFormsSpreadsheetValueConverter Converter} for single {@link EAttribute attributes}.
 *
 * @author Johannes Faltermeier
 *
 */
@Component(name = "EMFFormsSpreadsheetSingleAttributeConverter")
public class EMFFormsSpreadsheetSingleAttributeConverter implements EMFFormsSpreadsheetValueConverter {

	private static final int DOUBLE_PRECISION = 16;
	private EMFFormsDatabindingEMF databinding;
	private ReportService reportService;

	/**
	 * Sets the databinding service.
	 *
	 * @param databinding the service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY)
	public void setDatabinding(EMFFormsDatabindingEMF databinding) {
		this.databinding = databinding;
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
		if (feature.isMany()) {
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
	public void setCellValue(Cell cell, Object value, EStructuralFeature eStructuralFeature,
		ViewModelContext viewModelContext)
			throws EMFFormsConverterException {
		if (value == null) {
			return;
		}
		final EAttribute eAttribute = EAttribute.class.cast(eStructuralFeature);
		if (eAttribute == null) {
			throw new EMFFormsConverterException("The provided eAttribute is null."); //$NON-NLS-1$
		}
		final EDataType attributeType = eAttribute.getEAttributeType();
		if (attributeType == null) {
			throw new EMFFormsConverterException("The attributeType of the provided eAttribute is null."); //$NON-NLS-1$
		}

		if (isBoolean(attributeType.getInstanceClass())) {
			cell.setCellValue(Boolean.class.cast(value));
		} else if (isByte(attributeType.getInstanceClass()) ||
			isShort(attributeType.getInstanceClass()) ||
			isInteger(attributeType.getInstanceClass()) ||
			isLong(attributeType.getInstanceClass())) {
			cell.setCellValue(Number.class.cast(value).doubleValue());
		} else if (isFloat(attributeType.getInstanceClass()) ||
			isDouble(attributeType.getInstanceClass())) {
			writeFloatDouble(cell, value, viewModelContext, eAttribute);
		} else if (isBigInteger(attributeType.getInstanceClass())) {
			writeBigInteger(cell, value, viewModelContext);
		} else if (isBigDecimal(attributeType.getInstanceClass())) {
			writeBigDecimal(cell, value, viewModelContext, eAttribute);
		} else if (isDate(attributeType.getInstanceClass())) {
			cell.setCellValue(DateUtil.getExcelDate(Date.class.cast(value)));
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.DATE));
		} else if (isXMLDate(attributeType.getInstanceClass())) {
			final XMLGregorianCalendar xmlDate = XMLGregorianCalendar.class.cast(value);
			cell.setCellValue(
				DateUtil.getExcelDate(xmlDate.toGregorianCalendar(TimeZone.getTimeZone("UTC"), null, xmlDate), false)); //$NON-NLS-1$
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.DATE));
		} else {
			cell.setCellValue(EcoreUtil.convertToString(attributeType, value));
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.TEXT));
		}
	}

	private void writeFloatDouble(Cell cell, Object value, ViewModelContext viewModelContext,
		final EAttribute eAttribute) {
		cell.setCellValue(Number.class.cast(value).doubleValue());
		final String format = NumberFormatHelper.getNumberFormat(eAttribute);
		if (format != null) {
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(format));
		}
	}

	private void writeBigDecimal(Cell cell, Object value, ViewModelContext viewModelContext, EAttribute eAttribute) {
		final BigDecimal bigDecimal = BigDecimal.class.cast(value);
		if (Double.isInfinite(bigDecimal.doubleValue())
			|| bigDecimal.precision() > DOUBLE_PRECISION) {
			cell.setCellValue(bigDecimal.toString());
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.TEXT));
		} else {
			cell.setCellValue(bigDecimal.doubleValue());
			final String format = NumberFormatHelper.getNumberFormat(eAttribute);
			if (format != null) {
				cell.setCellStyle((CellStyle) viewModelContext.getContextValue(format));
			}
		}
	}

	private void writeBigInteger(Cell cell, Object value, ViewModelContext viewModelContext) {
		final BigInteger bigInteger = BigInteger.class.cast(value);
		if (bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0
			|| bigInteger.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
			cell.setCellValue(bigInteger.toString());
			cell.setCellStyle((CellStyle) viewModelContext.getContextValue(EMFFormsCellStyleConstants.TEXT));
		} else {
			cell.setCellValue(bigInteger.doubleValue());
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverter#getCellValue(org.apache.poi.ss.usermodel.Cell,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public Object getCellValue(Cell cell, EStructuralFeature eStructuralFeature) throws EMFFormsConverterException {
		final EAttribute eAttribute = EAttribute.class.cast(eStructuralFeature);
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			return null;
		}
		if (eAttribute == null) {
			return null;
		}
		final EDataType attributeType = eAttribute.getEAttributeType();
		if (attributeType == null) {
			return null;
		}
		try {
			return readCellValue(cell, attributeType);
		} catch (final IllegalStateException e) {
			throw new EMFFormsConverterException(
				String.format("Cell value of column %1$s in row %2$s on sheet %3$s must be a string.", //$NON-NLS-1$
					cell.getColumnIndex() + 1, cell.getRowIndex() + 1, cell.getSheet().getSheetName()),
				e);
		} catch (final NumberFormatException e) {
			throw new EMFFormsConverterException(
				String.format("Cell value of column %1$s in row %2$s on sheet %3$s is not a valid number.", //$NON-NLS-1$
					cell.getColumnIndex() + 1, cell.getRowIndex() + 1, cell.getSheet().getSheetName()),
				e);
		}
	}

	private Object readCellValue(Cell cell, final EDataType attributeType) {
		if (isByte(attributeType.getInstanceClass())) {
			return Double.valueOf(cell.getNumericCellValue()).byteValue();
		} else if (isShort(attributeType.getInstanceClass())) {
			return Double.valueOf(cell.getNumericCellValue()).shortValue();
		} else if (isInteger(attributeType.getInstanceClass())) {
			return Double.valueOf(cell.getNumericCellValue()).intValue();
		} else if (isLong(attributeType.getInstanceClass())) {
			return Double.valueOf(cell.getNumericCellValue()).longValue();
		} else if (isFloat(attributeType.getInstanceClass())) {
			return Double.valueOf(cell.getNumericCellValue()).floatValue();
		} else if (isDouble(attributeType.getInstanceClass())) {
			return cell.getNumericCellValue();
		} else if (isBigInteger(attributeType.getInstanceClass())) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return BigInteger.valueOf((long) cell.getNumericCellValue());
			}
			return new BigInteger(cell.getStringCellValue());
		} else if (isBigDecimal(attributeType.getInstanceClass())) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros();
			}
			final String value = cell.getStringCellValue();
			return new BigDecimal(value).stripTrailingZeros();
		} else if (isBoolean(attributeType.getInstanceClass())) {
			return cell.getBooleanCellValue();
		} else if (isDate(attributeType.getInstanceClass())) {
			return DateUtil.getJavaDate(cell.getNumericCellValue());
		} else if (isXMLDate(attributeType.getInstanceClass())) {
			final Calendar targetCal = DateUtil.getJavaCalendarUTC(cell.getNumericCellValue(), false);
			if (targetCal == null) {
				return null;
			}
			final XMLGregorianCalendar cal = new XMLCalendar(targetCal.getTime(), XMLCalendar.DATE);
			cal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			cal.setHour(DatatypeConstants.FIELD_UNDEFINED);
			cal.setMinute(DatatypeConstants.FIELD_UNDEFINED);
			cal.setSecond(DatatypeConstants.FIELD_UNDEFINED);
			cal.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			return cal;
		} else {
			return EcoreUtil.createFromString(attributeType, cell.getStringCellValue());
		}
	}

	private static boolean isXMLDate(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return XMLGregorianCalendar.class.isAssignableFrom(clazz);
	}

	private static boolean isDate(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Date.class.isAssignableFrom(clazz);
	}

	private static boolean isBigDecimal(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return BigDecimal.class.isAssignableFrom(clazz);
	}

	private static boolean isBigInteger(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return BigInteger.class.isAssignableFrom(clazz);
	}

	private static boolean isBoolean(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Boolean.TYPE == clazz || Boolean.class.isAssignableFrom(clazz);
	}

	private static boolean isDouble(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Double.TYPE == clazz || Double.class.isAssignableFrom(clazz);
	}

	private static boolean isFloat(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Float.TYPE == clazz || Float.class.isAssignableFrom(clazz);
	}

	private static boolean isLong(final Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Long.TYPE == clazz || Long.class.isAssignableFrom(clazz);
	}

	private static boolean isInteger(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Integer.TYPE == clazz || Integer.class.isAssignableFrom(clazz);
	}

	private static boolean isShort(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Short.TYPE == clazz || Short.class.isAssignableFrom(clazz);
	}

	private static boolean isByte(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}
		return Byte.TYPE == clazz || Byte.class.isAssignableFrom(clazz);
	}
}
