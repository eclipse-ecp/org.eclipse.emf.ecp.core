/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.indexdmr.tooling;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;

/**
 * A Tester for the FeaturePathControl which is added as a child of a
 * {@link org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference VIndexDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
public class FeaturePathDMRReferenceTester implements
	ECPRendererTester {

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {

		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		if (control.getDomainModelReference() == null) {
			return NOT_APPLICABLE;
		}
		IValueProperty valueProperty;
		try {
			valueProperty = viewModelContext.getService(EMFFormsDatabinding.class)
				.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			viewModelContext.getService(ReportService.class).report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (VIndexdmrPackage.eINSTANCE.getIndexDomainModelReference_TargetDMR() == feature) {
			return 6;
		}

		return NOT_APPLICABLE;
	}
}
