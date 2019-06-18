/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.template.model.VTMultiStyleSelectorContainer;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Style Selector Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTMultiStyleSelectorContainerImpl#getSelectors
 * <em>Selectors</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VTMultiStyleSelectorContainerImpl extends MinimalEObjectImpl.Container
	implements VTMultiStyleSelectorContainer {
	/**
	 * The cached value of the '{@link #getSelectors() <em>Selectors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSelectors()
	 * @generated
	 * @ordered
	 */
	protected EList<VTStyleSelector> selectors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTMultiStyleSelectorContainerImpl() {
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
		return VTTemplatePackage.Literals.MULTI_STYLE_SELECTOR_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VTStyleSelector> getSelectors() {
		if (selectors == null) {
			selectors = new EObjectContainmentEList<VTStyleSelector>(VTStyleSelector.class, this,
				VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS);
		}
		return selectors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS:
			return ((InternalEList<?>) getSelectors()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS:
			return getSelectors();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS:
			getSelectors().clear();
			getSelectors().addAll((Collection<? extends VTStyleSelector>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS:
			getSelectors().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VTTemplatePackage.MULTI_STYLE_SELECTOR_CONTAINER__SELECTORS:
			return selectors != null && !selectors.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VTMultiStyleSelectorContainerImpl
