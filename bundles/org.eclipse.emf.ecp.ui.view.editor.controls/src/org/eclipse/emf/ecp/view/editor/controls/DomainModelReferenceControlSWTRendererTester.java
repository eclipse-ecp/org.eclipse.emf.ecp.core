/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.editor.controls;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.model.common.ECPRendererTester;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.label.model.VLabelPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;

/**
 * @author Alexandra Buzila
 * 
 */
@SuppressWarnings("restriction")
public class DomainModelReferenceControlSWTRendererTester implements ECPRendererTester {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.model.common.ECPRendererTester#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */

	@Override
	public int isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final Setting setting = VControl.class.cast(vElement).getDomainModelReference().getIterator().next();
		return isApplicable(setting);
	}

	/**
	 * Test if setting contains the correct data.
	 * 
	 * @param setting the {@link Setting} to check
	 * @return the priority of the control
	 */
	protected int isApplicable(Setting setting) {
		if (setting == null) {
			return NOT_APPLICABLE;
		}
		if (VViewPackage.eINSTANCE.getControl_DomainModelReference() == setting.getEStructuralFeature()) {
			return 3;
		}
		if (VLabelPackage.eINSTANCE.getLabel_DomainModelReference() == setting.getEStructuralFeature()) {
			return 3;
		}
		if (VStackPackage.eINSTANCE.getStackLayout_DomainModelReference() == setting.getEStructuralFeature()) {
			return 3;
		}
		if (VTablePackage.eINSTANCE.getTableDomainModelReference_DomainModelReference() == setting
			.getEStructuralFeature()) {
			return 3;
		}
		return NOT_APPLICABLE;
	}

}
