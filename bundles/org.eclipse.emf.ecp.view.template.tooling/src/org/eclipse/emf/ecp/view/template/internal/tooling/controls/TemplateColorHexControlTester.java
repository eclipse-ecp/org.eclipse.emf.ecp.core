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
package org.eclipse.emf.ecp.view.template.internal.tooling.controls;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.internal.tooling.Activator;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesPackage;
import org.eclipse.emf.ecp.view.template.style.validation.model.VTValidationPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * The tester for the
 * {@link org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty#getImagePath()
 * VTTableValidationStyleProperty#getImagePath()}.
 *
 * @author Eugen Neufeld
 *
 */
public class TemplateColorHexControlTester implements ECPRendererTester {

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (viewModelContext == null) {
			return NOT_APPLICABLE;
		}
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VDomainModelReference dmr = VControl.class.cast(vElement).getDomainModelReference();
		if (dmr == null) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding()
				.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel().eClass());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (VTFontPropertiesPackage.eINSTANCE.getFontPropertiesStyleProperty_ColorHEX().equals(feature)) {
			return 5;
		}

		// validationstyle
		if (VTValidationPackage.eINSTANCE.getValidationStyleProperty_OkColorHEX().equals(feature)) {
			return 5;
		}
		if (VTValidationPackage.eINSTANCE.getValidationStyleProperty_InfoColorHEX().equals(feature)) {
			return 5;
		}
		if (VTValidationPackage.eINSTANCE.getValidationStyleProperty_WarningColorHEX().equals(feature)) {
			return 5;
		}
		if (VTValidationPackage.eINSTANCE.getValidationStyleProperty_ErrorColorHEX().equals(feature)) {
			return 5;
		}
		if (VTValidationPackage.eINSTANCE.getValidationStyleProperty_CancelColorHEX().equals(feature)) {
			return 5;
		}
		// template validation (Deprecated)
		if (VTTemplatePackage.eINSTANCE.getControlValidationTemplate_OkColorHEX().equals(feature)) {
			return 5;
		}
		if (VTTemplatePackage.eINSTANCE.getControlValidationTemplate_InfoColorHEX().equals(feature)) {
			return 5;
		}
		if (VTTemplatePackage.eINSTANCE.getControlValidationTemplate_WarningColorHEX().equals(feature)) {
			return 5;
		}
		if (VTTemplatePackage.eINSTANCE.getControlValidationTemplate_ErrorColorHEX().equals(feature)) {
			return 5;
		}
		if (VTTemplatePackage.eINSTANCE.getControlValidationTemplate_CancelColorHEX().equals(feature)) {
			return 5;
		}

		return NOT_APPLICABLE;
	}

}
