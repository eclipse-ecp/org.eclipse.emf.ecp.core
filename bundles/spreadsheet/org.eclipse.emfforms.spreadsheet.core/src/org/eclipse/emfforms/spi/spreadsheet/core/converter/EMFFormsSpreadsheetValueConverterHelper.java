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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.spreadsheet.core.EMFFormsSpreadsheetReport;

/**
 * Helper for converters.
 *
 * @author jfaltermeier
 *
 */
public final class EMFFormsSpreadsheetValueConverterHelper {

	private EMFFormsSpreadsheetValueConverterHelper() {
		// util
	}

	/**
	 * Gets the feature from the domain model reference using the databinding service. In case of an error, the error
	 * will be logged and <code>null</code> will be returned.
	 *
	 * @param domain the domain object
	 * @param domainModelReference the domain model reference
	 * @param databinding the databinding service
	 * @param reportService the error report service
	 * @return the feature or <code>null</code>
	 */
	public static EStructuralFeature getFeature(EObject domain,
		VDomainModelReference domainModelReference, EMFFormsDatabindingEMF databinding, ReportService reportService) {
		try {
			return databinding.getSetting(domainModelReference, domain).getEStructuralFeature();
		} catch (final DatabindingFailedException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		} catch (final ClassCastException ex) {
			reportService.report(new EMFFormsSpreadsheetReport(ex, EMFFormsSpreadsheetReport.ERROR));
		}
		return null;
	}
}
