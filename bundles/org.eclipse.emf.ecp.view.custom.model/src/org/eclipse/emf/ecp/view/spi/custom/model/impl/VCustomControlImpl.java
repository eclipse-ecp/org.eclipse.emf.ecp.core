/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomControl;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VControlImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control</b></em>'.
 *
 * @since 1.3
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl#getBundleName <em>Bundle Name
 *        </em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomControlImpl#getClassName <em>Class Name</em>}
 *        </li>
 *        </ul>
 *        </p>
 *
 * @generated
 */
public class VCustomControlImpl extends VControlImpl implements VCustomControl
{
	/**
	 * The default value of the '{@link #getBundleName() <em>Bundle Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBundleName()
	 * @generated
	 * @ordered
	 */
	protected static final String BUNDLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBundleName() <em>Bundle Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBundleName()
	 * @generated
	 * @ordered
	 */
	protected String bundleName = BUNDLE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VCustomControlImpl()
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
		return VCustomPackage.Literals.CUSTOM_CONTROL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getBundleName()
	{
		return bundleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBundleName(String newBundleName)
	{
		final String oldBundleName = bundleName;
		bundleName = newBundleName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME,
				oldBundleName, bundleName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getClassName()
	{
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setClassName(String newClassName)
	{
		final String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VCustomPackage.CUSTOM_CONTROL__CLASS_NAME,
				oldClassName, className));
		}
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
		case VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME:
			return getBundleName();
		case VCustomPackage.CUSTOM_CONTROL__CLASS_NAME:
			return getClassName();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME:
			setBundleName((String) newValue);
			return;
		case VCustomPackage.CUSTOM_CONTROL__CLASS_NAME:
			setClassName((String) newValue);
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
		case VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME:
			setBundleName(BUNDLE_NAME_EDEFAULT);
			return;
		case VCustomPackage.CUSTOM_CONTROL__CLASS_NAME:
			setClassName(CLASS_NAME_EDEFAULT);
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
		case VCustomPackage.CUSTOM_CONTROL__BUNDLE_NAME:
			return BUNDLE_NAME_EDEFAULT == null ? bundleName != null : !BUNDLE_NAME_EDEFAULT.equals(bundleName);
		case VCustomPackage.CUSTOM_CONTROL__CLASS_NAME:
			return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (bundleName: "); //$NON-NLS-1$
		result.append(bundleName);
		result.append(", className: "); //$NON-NLS-1$
		result.append(className);
		result.append(')');
		return result.toString();
	}

} // VCustomControlImpl
