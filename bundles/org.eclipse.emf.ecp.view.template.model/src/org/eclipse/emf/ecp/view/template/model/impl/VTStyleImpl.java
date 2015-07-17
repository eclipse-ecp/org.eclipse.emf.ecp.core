/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl#getSelector <em>Selector</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTStyleImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTStyleImpl extends MinimalEObjectImpl.Container implements VTStyle
{
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
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<VTStyleProperty> properties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTStyleImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VTTemplatePackage.Literals.STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTStyleSelector getSelector()
	{
		return selector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetSelector(VTStyleSelector newSelector, NotificationChain msgs)
	{
		final VTStyleSelector oldSelector = selector;
		selector = newSelector;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VTTemplatePackage.STYLE__SELECTOR, oldSelector, newSelector);
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
	public void setSelector(VTStyleSelector newSelector)
	{
		if (newSelector != selector)
		{
			NotificationChain msgs = null;
			if (selector != null) {
				msgs = ((InternalEObject) selector).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VTTemplatePackage.STYLE__SELECTOR, null, msgs);
			}
			if (newSelector != null) {
				msgs = ((InternalEObject) newSelector).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VTTemplatePackage.STYLE__SELECTOR, null, msgs);
			}
			msgs = basicSetSelector(newSelector, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTTemplatePackage.STYLE__SELECTOR, newSelector,
				newSelector));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VTStyleProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new EObjectContainmentEList<VTStyleProperty>(VTStyleProperty.class, this,
				VTTemplatePackage.STYLE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
		case VTTemplatePackage.STYLE__SELECTOR:
			return basicSetSelector(null, msgs);
		case VTTemplatePackage.STYLE__PROPERTIES:
			return ((InternalEList<?>) getProperties()).basicRemove(otherEnd, msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VTTemplatePackage.STYLE__SELECTOR:
			return getSelector();
		case VTTemplatePackage.STYLE__PROPERTIES:
			return getProperties();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VTTemplatePackage.STYLE__SELECTOR:
			setSelector((VTStyleSelector) newValue);
			return;
		case VTTemplatePackage.STYLE__PROPERTIES:
			getProperties().clear();
			getProperties().addAll((Collection<? extends VTStyleProperty>) newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VTTemplatePackage.STYLE__SELECTOR:
			setSelector((VTStyleSelector) null);
			return;
		case VTTemplatePackage.STYLE__PROPERTIES:
			getProperties().clear();
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VTTemplatePackage.STYLE__SELECTOR:
			return selector != null;
		case VTTemplatePackage.STYLE__PROPERTIES:
			return properties != null && !properties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VTStyleImpl
