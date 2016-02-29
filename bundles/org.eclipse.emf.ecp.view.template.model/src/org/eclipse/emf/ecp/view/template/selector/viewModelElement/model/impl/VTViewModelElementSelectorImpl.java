/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementPackage;
import org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.VTViewModelElementSelector;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl#isSelectSubclasses
 * <em>Select Subclasses</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl#getClassType
 * <em>Class Type</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl#getAttribute
 * <em>Attribute</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.viewModelElement.model.impl.VTViewModelElementSelectorImpl#getAttributeValue
 * <em>Attribute Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTViewModelElementSelectorImpl extends MinimalEObjectImpl.Container implements VTViewModelElementSelector {
	/**
	 * The default value of the '{@link #isSelectSubclasses() <em>Select Subclasses</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSelectSubclasses()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SELECT_SUBCLASSES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSelectSubclasses() <em>Select Subclasses</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSelectSubclasses()
	 * @generated
	 * @ordered
	 */
	protected boolean selectSubclasses = SELECT_SUBCLASSES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClassType() <em>Class Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClassType()
	 * @generated
	 * @ordered
	 */
	protected EClass classType;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EAttribute attribute;

	/**
	 * The default value of the '{@link #getAttributeValue() <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object ATTRIBUTE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeValue() <em>Attribute Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected Object attributeValue = ATTRIBUTE_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTViewModelElementSelectorImpl() {
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
		return VTViewModelElementPackage.Literals.VIEW_MODEL_ELEMENT_SELECTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getClassType() {
		if (classType != null && classType.eIsProxy()) {
			final InternalEObject oldClassType = (InternalEObject) classType;
			classType = (EClass) eResolveProxy(oldClassType);
			if (classType != oldClassType) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE, oldClassType, classType));
				}
			}
		}
		return classType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EClass basicGetClassType() {
		return classType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setClassType(EClass newClassType) {
		final EClass oldClassType = classType;
		classType = newClassType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE, oldClassType, classType));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			final InternalEObject oldAttribute = (InternalEObject) attribute;
			attribute = (EAttribute) eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE, oldAttribute, attribute));
				}
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EAttribute basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAttribute(EAttribute newAttribute) {
		final EAttribute oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE, oldAttribute, attribute));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getAttributeValue() {
		return attributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAttributeValue(Object newAttributeValue) {
		final Object oldAttributeValue = attributeValue;
		attributeValue = newAttributeValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE, oldAttributeValue,
				attributeValue));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSelectSubclasses() {
		return selectSubclasses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSelectSubclasses(boolean newSelectSubclasses) {
		final boolean oldSelectSubclasses = selectSubclasses;
		selectSubclasses = newSelectSubclasses;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES, oldSelectSubclasses,
				selectSubclasses));
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
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES:
			return isSelectSubclasses();
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE:
			if (resolve) {
				return getClassType();
			}
			return basicGetClassType();
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE:
			if (resolve) {
				return getAttribute();
			}
			return basicGetAttribute();
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE:
			return getAttributeValue();
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
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES:
			setSelectSubclasses((Boolean) newValue);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE:
			setClassType((EClass) newValue);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE:
			setAttributeValue(newValue);
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
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES:
			setSelectSubclasses(SELECT_SUBCLASSES_EDEFAULT);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE:
			setClassType((EClass) null);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE:
			setAttribute((EAttribute) null);
			return;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE:
			setAttributeValue(ATTRIBUTE_VALUE_EDEFAULT);
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
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__SELECT_SUBCLASSES:
			return selectSubclasses != SELECT_SUBCLASSES_EDEFAULT;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__CLASS_TYPE:
			return classType != null;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE:
			return attribute != null;
		case VTViewModelElementPackage.VIEW_MODEL_ELEMENT_SELECTOR__ATTRIBUTE_VALUE:
			return ATTRIBUTE_VALUE_EDEFAULT == null ? attributeValue != null : !ATTRIBUTE_VALUE_EDEFAULT
				.equals(attributeValue);
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
		result.append(" (selectSubclasses: "); //$NON-NLS-1$
		result.append(selectSubclasses);
		result.append(", attributeValue: "); //$NON-NLS-1$
		result.append(attributeValue);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleSelector#isApplicable(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (classType == null) {
			return NOT_APPLICABLE;
		}
		if (selectSubclasses && !classType.isSuperTypeOf(vElement.eClass())) {
			return NOT_APPLICABLE;
		} else if (!selectSubclasses && !classType.equals(vElement.eClass())) {
			return NOT_APPLICABLE;
		}
		if (attribute != null) {
			final Object attrValue = vElement.eGet(attribute);
			if (attrValue == null) {
				return NOT_APPLICABLE;
			}
			if (!attrValue.equals(attributeValue)) {
				return NOT_APPLICABLE;
			}
			/*
			 * a view model element selector with an attribute should be regarded as more specific as a view model
			 * element selector without an attribute
			 */
			return 6;
		}

		return 5;
	}
} // VTViewModelElementSelectorImpl
