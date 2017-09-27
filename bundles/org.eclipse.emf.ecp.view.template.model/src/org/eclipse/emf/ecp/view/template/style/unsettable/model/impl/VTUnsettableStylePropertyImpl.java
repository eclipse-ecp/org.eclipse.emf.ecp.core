/**
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.unsettable.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettablePackage;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl#getButtonAlignment
 * <em>Button Alignment</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.unsettable.model.impl.VTUnsettableStylePropertyImpl#getButtonPlacement
 * <em>Button Placement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTUnsettableStylePropertyImpl extends MinimalEObjectImpl.Container implements VTUnsettableStyleProperty {
	/**
	 * The default value of the '{@link #getButtonAlignment() <em>Button Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getButtonAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final ButtonAlignmentType BUTTON_ALIGNMENT_EDEFAULT = ButtonAlignmentType.RIGHT;

	/**
	 * The cached value of the '{@link #getButtonAlignment() <em>Button Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getButtonAlignment()
	 * @generated
	 * @ordered
	 */
	protected ButtonAlignmentType buttonAlignment = BUTTON_ALIGNMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getButtonPlacement() <em>Button Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getButtonPlacement()
	 * @generated
	 * @ordered
	 */
	protected static final ButtonPlacementType BUTTON_PLACEMENT_EDEFAULT = ButtonPlacementType.RIGHT_OF_LABEL;

	/**
	 * The cached value of the '{@link #getButtonPlacement() <em>Button Placement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getButtonPlacement()
	 * @generated
	 * @ordered
	 */
	protected ButtonPlacementType buttonPlacement = BUTTON_PLACEMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VTUnsettableStylePropertyImpl() {
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
		return VTUnsettablePackage.Literals.UNSETTABLE_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ButtonAlignmentType getButtonAlignment() {
		return buttonAlignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setButtonAlignment(ButtonAlignmentType newButtonAlignment) {
		final ButtonAlignmentType oldButtonAlignment = buttonAlignment;
		buttonAlignment = newButtonAlignment == null ? BUTTON_ALIGNMENT_EDEFAULT : newButtonAlignment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT, oldButtonAlignment, buttonAlignment));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ButtonPlacementType getButtonPlacement() {
		return buttonPlacement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setButtonPlacement(ButtonPlacementType newButtonPlacement) {
		final ButtonPlacementType oldButtonPlacement = buttonPlacement;
		buttonPlacement = newButtonPlacement == null ? BUTTON_PLACEMENT_EDEFAULT : newButtonPlacement;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT, oldButtonPlacement, buttonPlacement));
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
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT:
			return getButtonAlignment();
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT:
			return getButtonPlacement();
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
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT:
			setButtonAlignment((ButtonAlignmentType) newValue);
			return;
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT:
			setButtonPlacement((ButtonPlacementType) newValue);
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
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT:
			setButtonAlignment(BUTTON_ALIGNMENT_EDEFAULT);
			return;
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT:
			setButtonPlacement(BUTTON_PLACEMENT_EDEFAULT);
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
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_ALIGNMENT:
			return buttonAlignment != BUTTON_ALIGNMENT_EDEFAULT;
		case VTUnsettablePackage.UNSETTABLE_STYLE_PROPERTY__BUTTON_PLACEMENT:
			return buttonPlacement != BUTTON_PLACEMENT_EDEFAULT;
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
		result.append(" (buttonAlignment: "); //$NON-NLS-1$
		result.append(buttonAlignment);
		result.append(", buttonPlacement: "); //$NON-NLS-1$
		result.append(buttonPlacement);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty#equalStyles(org.eclipse.emf.ecp.view.template.model.VTStyleProperty)
	 */
	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (!(eClass() == styleProperty.eClass())) {
			return false;
		}
		final VTUnsettableStyleProperty unsettableStyleProperty = VTUnsettableStyleProperty.class.cast(styleProperty);
		return getButtonAlignment() == unsettableStyleProperty.getButtonAlignment();
	}

} // VTUnsettableStylePropertyImpl
