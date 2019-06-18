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
package org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.model.impl.VTStyleSelectorContainerImpl;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyPackage;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VTHierarchySelectorImpl extends VTStyleSelectorContainerImpl implements VTHierarchySelector {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTHierarchySelectorImpl() {
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
		return VTHierarchyPackage.Literals.HIERARCHY_SELECTOR;
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (getSelector() == null) {
			return NOT_APPLICABLE;
		}
		EObject current = vElement;
		while (current != null) {
			/* check required because there may be non-VElements in the hierarchy as intermediate layout objects */
			if (VElement.class.isInstance(current)) {
				final double applicable = getSelector().isApplicable(VElement.class.cast(current), viewModelContext);
				if (applicable != NOT_APPLICABLE) {
					return applicable;
				}
			}
			current = current.eContainer();
		}
		return NOT_APPLICABLE;
	}

} // VTHierarchySelectorImpl
