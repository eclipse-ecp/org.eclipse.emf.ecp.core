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
package org.eclipse.emf.ecp.view.keyattributedmr.tooling;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * A Tester for the KeyValue Control which is added as a child of a
 * {@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference
 * VKeyAttributeDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
public class KeyValueControlTester implements
	ECPRendererTester {

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
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();

		if (VKeyattributedmrPackage.eINSTANCE.getKeyAttributeDomainModelReference_KeyValue() == feature) {
			return 6;
		}

		return NOT_APPLICABLE;
	}
}
