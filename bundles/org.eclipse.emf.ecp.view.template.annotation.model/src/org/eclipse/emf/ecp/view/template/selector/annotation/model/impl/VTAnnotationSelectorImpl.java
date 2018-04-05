/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.annotation.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationPackage;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl#getKey
 * <em>Key</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.selector.annotation.model.impl.VTAnnotationSelectorImpl#getValue
 * <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTAnnotationSelectorImpl extends MinimalEObjectImpl.Container
	implements VTAnnotationSelector {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTAnnotationSelectorImpl() {
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
		return VTAnnotationPackage.Literals.ANNOTATION_SELECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		final String oldKey = key;
		key = newKey;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTAnnotationPackage.ANNOTATION_SELECTOR__KEY, oldKey,
				key));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		final String oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTAnnotationPackage.ANNOTATION_SELECTOR__VALUE,
				oldValue, value));
		}
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
		case VTAnnotationPackage.ANNOTATION_SELECTOR__KEY:
			return getKey();
		case VTAnnotationPackage.ANNOTATION_SELECTOR__VALUE:
			return getValue();
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
		case VTAnnotationPackage.ANNOTATION_SELECTOR__KEY:
			setKey((String) newValue);
			return;
		case VTAnnotationPackage.ANNOTATION_SELECTOR__VALUE:
			setValue((String) newValue);
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
		case VTAnnotationPackage.ANNOTATION_SELECTOR__KEY:
			setKey(KEY_EDEFAULT);
			return;
		case VTAnnotationPackage.ANNOTATION_SELECTOR__VALUE:
			setValue(VALUE_EDEFAULT);
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
		case VTAnnotationPackage.ANNOTATION_SELECTOR__KEY:
			return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
		case VTAnnotationPackage.ANNOTATION_SELECTOR__VALUE:
			return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (key: "); //$NON-NLS-1$
		result.append(key);
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		for (final VAttachment vAttachment : vElement.getAttachments()) {
			if (!VAnnotation.class.isInstance(vAttachment)) {
				continue;
			}
			final VAnnotation annotation = VAnnotation.class.cast(vAttachment);
			if (getKey() == null) {
				if (annotation.getKey() != null) {
					continue;
				}
			} else {
				if (!getKey().equals(annotation.getKey())) {
					continue;
				}
			}
			/* matching keys */
			if (getValue() == null) {
				if (annotation.getValue() != null) {
					return NOT_APPLICABLE;
				}
			} else {
				if (!getValue().equals(annotation.getValue())) {
					return NOT_APPLICABLE;
				}
			}
			/* matching key value */
			return 15;
		}
		return NOT_APPLICABLE;
	}

} // VTAnnotationInHierarchySelectorImpl
