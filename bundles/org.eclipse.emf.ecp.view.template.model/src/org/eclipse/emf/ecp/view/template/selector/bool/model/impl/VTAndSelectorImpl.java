/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.bool.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.impl.VTMultiStyleSelectorContainerImpl;
import org.eclipse.emf.ecp.view.template.selector.bool.model.VTAndSelector;
import org.eclipse.emf.ecp.view.template.selector.bool.model.VTBoolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>And Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTAndSelectorImpl extends VTMultiStyleSelectorContainerImpl implements VTAndSelector {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTAndSelectorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VTBoolPackage.Literals.AND_SELECTOR;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (getSelectors().isEmpty()) {
			return NOT_APPLICABLE;
		}
		double prio = 0;
		for (final VTStyleSelector selector : getSelectors()) {
			final double applicable = selector.isApplicable(vElement, viewModelContext);
			if (applicable == NOT_APPLICABLE) {
				return NOT_APPLICABLE;
			}
			prio += applicable;
		}
		return prio;
	}

} // VTAndSelectorImpl
