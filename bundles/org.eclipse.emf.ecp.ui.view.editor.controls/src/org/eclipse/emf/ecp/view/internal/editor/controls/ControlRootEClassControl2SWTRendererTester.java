/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * @author Alexandra Buzila
 *
 */
public class ControlRootEClassControl2SWTRendererTester implements ECPRendererTester {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int isApplicable(VElement vElement, ViewModelContext context) {
		if (context == null) {
			return NOT_APPLICABLE;
		}
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VControl control = (VControl) vElement;
		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getDefault().getEMFFormsDatabinding()
				.getValueProperty(control.getDomainModelReference(), context.getDomainModel().eClass());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		return isApplicable(feature);
	}

	/**
	 * Test if the structural feature and the corresponding EObject contain the correct data.
	 *
	 * @param feature The {@link EStructuralFeature} to check
	 * @return the priority of the control
	 */
	protected int isApplicable(EStructuralFeature feature) {
		if (VViewPackage.eINSTANCE.getView_RootEClass() != feature) {
			return NOT_APPLICABLE;
		}
		return 3;
	}

}
