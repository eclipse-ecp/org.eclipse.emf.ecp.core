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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelectorContainer;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Selector Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTStyleSelectorContainerImpl#getSelector
 * <em>Selector</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VTStyleSelectorContainerImpl extends MinimalEObjectImpl.Container
	implements VTStyleSelectorContainer {
	/**
	 * The cached value of the '{@link #getSelector() <em>Selector</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSelector()
	 * @generated
	 * @ordered
	 */
	protected VTStyleSelector selector;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTStyleSelectorContainerImpl() {
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
		return VTTemplatePackage.Literals.STYLE_SELECTOR_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTStyleSelector getSelector() {
		return selector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetSelector(VTStyleSelector newSelector, NotificationChain msgs) {
		final VTStyleSelector oldSelector = selector;
		selector = newSelector;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR, oldSelector, newSelector);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSelector(VTStyleSelector newSelector) {
		if (newSelector != selector) {
			NotificationChain msgs = null;
			if (selector != null) {
				msgs = ((InternalEObject) selector).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR, null, msgs);
			}
			if (newSelector != null) {
				msgs = ((InternalEObject) newSelector).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR, null, msgs);
			}
			msgs = basicSetSelector(newSelector, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR,
				newSelector, newSelector));
		}
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
		case VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR:
			return basicSetSelector(null, msgs);
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
		case VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR:
			return getSelector();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR:
			setSelector((VTStyleSelector) newValue);
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
		case VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR:
			setSelector((VTStyleSelector) null);
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
		case VTTemplatePackage.STYLE_SELECTOR_CONTAINER__SELECTOR:
			return selector != null;
		}
		return super.eIsSet(featureID);
	}

} // VTStyleSelectorContainerImpl
