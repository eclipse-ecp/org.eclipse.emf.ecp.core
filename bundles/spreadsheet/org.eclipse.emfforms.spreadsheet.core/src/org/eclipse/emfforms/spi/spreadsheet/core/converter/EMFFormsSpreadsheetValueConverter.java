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
package org.eclipse.emfforms.spi.spreadsheet.core.converter;

import org.apache.poi.ss.usermodel.Cell;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;

/**
 * A value converter is used to convert a value to its string representation in the spreadsheer and vice versa.
 *
 * @author Johannes Faltermeier
 *
 */
public interface EMFFormsSpreadsheetValueConverter {

	/**
	 * Constant defining that this converter can not be used to convert the value.
	 */
	double NOT_APPLICABLE = Double.NaN;

	/**
	 * Returns whether this converter is applicable to convert the value for the given {@link VDomainModelReference
	 * domain model reference}. The {@link EMFFormsSpreadsheetValueConverter converter} with the highest priority will
	 * be used to convert this value. If this converter cannot convert the value this method should indicate this by
	 * returning {@link #NOT_APPLICABLE}.
	 *
	 * @param domainObject the domain object
	 * @param dmr the domain model reference for which the value is to be converted
	 * @return the priority or {@link #NOT_APPLICABLE}
	 */
	double isApplicable(EObject domainObject, VDomainModelReference dmr);

	/**
	 * Write the value to the {@link Cell} using the {@link EStructuralFeature} as meta information about the value.
	 *
	 * @param cell The Cell to write to
	 * @param value The value to write
	 * @param eStructuralFeature The {@link EStructuralFeature} describing the meta information of the value
	 * @param viewModelContext The {@link ViewModelContext} to use
	 * @throws EMFFormsConverterException Whenever the eStructuralFeature and the value don't match
	 */
	void setCellValue(Cell cell, Object value, EStructuralFeature eStructuralFeature, ViewModelContext viewModelContext)
		throws EMFFormsConverterException;

	/**
	 * Read the value of the {@link Cell} using the {@link EStructuralFeature} as meta information about the value.
	 *
	 * @param cell The Cell to read from
	 * @param eStructuralFeature The {@link EStructuralFeature} describing the meta information of the value
	 * @return The Object of de-serialized from the cell
	 * @throws EMFFormsConverterException Whenever the eStructuralFeature and the cell value don't match
	 */
	Object getCellValue(Cell cell, EStructuralFeature eStructuralFeature) throws EMFFormsConverterException;

}
