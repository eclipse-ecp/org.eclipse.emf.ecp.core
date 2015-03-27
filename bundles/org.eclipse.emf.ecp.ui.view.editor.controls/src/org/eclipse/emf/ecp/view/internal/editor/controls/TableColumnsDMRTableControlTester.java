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
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * @author Alexandra Buzila
 *
 */
public class TableColumnsDMRTableControlTester implements ECPRendererTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (viewModelContext == null) {
			return NOT_APPLICABLE;
		}
		if (!VControl.class.isInstance(vElement)) {
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
		return isApplicable((EStructuralFeature) valueProperty.getValueType());
	}

	/**
	 * Test if the feature contains the correct data.
	 *
	 * @param feature the {@link EStructuralFeature} to check
	 * @return the priority of the control
	 */
	protected int isApplicable(EStructuralFeature feature) {
		if (VTablePackage.eINSTANCE.getTableDomainModelReference_ColumnDomainModelReferences() != feature) {
			return NOT_APPLICABLE;
		}
		return 10;
	}

}
