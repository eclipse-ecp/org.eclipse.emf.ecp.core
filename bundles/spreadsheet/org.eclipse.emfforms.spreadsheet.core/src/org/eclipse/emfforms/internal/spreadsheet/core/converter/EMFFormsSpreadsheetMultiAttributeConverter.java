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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
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

	@Override
	public String convertValueToString(Object fromObject, EObject domainObject, VDomainModelReference dmr) {
		final EStructuralFeature feature = EMFFormsSpreadsheetValueConverterHelper.getFeature(domainObject, dmr,
			databinding,
			reportService);
		final EAttribute eAttribute = EAttribute.class.cast(feature);
		final EDataType eDataType = eAttribute.getEAttributeType();
		final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
		final StringBuilder result = new StringBuilder();
		for (final Object value : (List<?>) fromObject) {
			if (result.length() != 0) {
				result.append(SEPARATOR);
			}
			result.append(eFactory.convertToString(eDataType, value));
		}
		return result.toString();
	}

	@Override
	public Object convertStringToValue(String string, EObject domainObject, VDomainModelReference dmr) {
		if (string == null || string.length() == 0) {
			return Collections.emptyList();
		}
		final EStructuralFeature feature = EMFFormsSpreadsheetValueConverterHelper.getFeature(domainObject, dmr,
			databinding,
			reportService);
		final EAttribute eAttribute = EAttribute.class.cast(feature);
		final List<Object> result = new ArrayList<Object>();
		final EDataType eDataType = eAttribute.getEAttributeType();
		final EFactory eFactory = eDataType.getEPackage().getEFactoryInstance();
		for (final String element : string.split(SEPARATOR)) {
			result.add(eFactory.createFromString(eDataType, element));
		}

		return result;
	}

}
