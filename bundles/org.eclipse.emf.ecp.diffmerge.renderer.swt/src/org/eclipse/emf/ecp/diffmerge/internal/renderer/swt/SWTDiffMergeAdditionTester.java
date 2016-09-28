/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.renderer.swt;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.diffmerge.spi.context.DiffMergeModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.swt.ECPAdditionalRendererTester;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * The tester for {@link SWTDiffMergeAddition}.
 *
 * @author Eugen Neufeld
 *
 */
public class SWTDiffMergeAdditionTester implements ECPAdditionalRendererTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.swt.ECPAdditionalRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public boolean isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!DiffMergeModelContext.class.isInstance(viewModelContext)) {
			return false;
		}
		if (!VControl.class.isInstance(vElement)) {
			return false;
		}
		final VControl control = (VControl) vElement;
		@SuppressWarnings("rawtypes")
		IValueProperty valueProperty;
		try {
			valueProperty = Activator.getInstance().getEMFFormsDatabinding()
				.getValueProperty(control.getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getInstance().getReportService().report(new DatabindingFailedReport(ex));
			return false;
		}
		final EStructuralFeature feature = (EStructuralFeature) valueProperty.getValueType();
		if (!feature.isChangeable()) {
			return false;
		}
		if (!((DiffMergeModelContext) viewModelContext).hasDiff(control)) {
			return false;
		}
		return true;
	}

}
