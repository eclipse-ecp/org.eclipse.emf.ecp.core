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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;
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
		if (!EReference.class.isInstance(feature)) {
			return NOT_APPLICABLE;
		}
		if (!feature.isMany()) {
			return NOT_APPLICABLE;
		}
		return 0d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertValueToString(Object values, EObject domainObject, VDomainModelReference dmr) {
		if (values == null) {
			return ""; //$NON-NLS-1$
		}
		try {
			final StringBuilder result = new StringBuilder();
			for (final EObject value : (List<EObject>) values) {
				if (result.length() != 0) {
					result.append(SEPARATOR);
				}
				result.append(XMIStringConverterHelper.getSerializedEObject(value));
			}
			return result.toString();
		} catch (final IOException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Object convertStringToValue(String string, EObject domainObject, VDomainModelReference dmr) {
		if (string == null || string.length() == 0) {
			return Collections.emptyList();
		}
		final List<EObject> result = new ArrayList<EObject>();
		final String[] split = string.split(SEPARATOR);
		for (final String element : split) {
			result.add(XMIStringConverterHelper.deserializeObject(element.trim(), reportService));
		}

		return result;
	}

}
