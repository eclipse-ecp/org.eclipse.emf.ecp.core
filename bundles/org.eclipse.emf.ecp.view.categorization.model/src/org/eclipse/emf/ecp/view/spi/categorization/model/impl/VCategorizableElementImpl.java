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
package org.eclipse.emf.ecp.view.spi.categorization.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.categorization.model.DerivedAttributeAdapter;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.model.VHasTooltip;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Categorizable Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl#getTooltip
 * <em>Tooltip</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizableElementImpl#getLabelObject <em>Label
 * Object</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class VCategorizableElementImpl extends VElementImpl implements VCategorizableElement {
	/**
	 * The default value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 * @since 1.13
	 */
	protected static final String TOOLTIP_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getTooltip() <em>Tooltip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTooltip()
	 * @generated
	 * @ordered
	 * @since 1.13
	 */
	protected String tooltip = TOOLTIP_EDEFAULT;
	private final DerivedAttributeAdapter labelAdapter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	protected VCategorizableElementImpl() {
		super();
		labelAdapter = new DerivedAttributeAdapter(this,
			VCategorizationPackage.Literals.CATEGORIZABLE_ELEMENT__LABEL_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->.
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VCategorizationPackage.Literals.CATEGORIZABLE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.13
	 */
	@Override
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.13
	 */
	@Override
	public void setTooltip(String newTooltip) {
		final String oldTooltip = tooltip;
		tooltip = newTooltip;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP,
				oldTooltip, tooltip));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject getLabelObject() {
		// TODO: implement this method to return the 'Label Object' reference
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->.
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP:
			return getTooltip();
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__LABEL_OBJECT:
			return getLabelObject();
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
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP:
			setTooltip((String) newValue);
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
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP:
			setTooltip(TOOLTIP_EDEFAULT);
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
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP:
			return TOOLTIP_EDEFAULT == null ? tooltip != null : !TOOLTIP_EDEFAULT.equals(tooltip);
		case VCategorizationPackage.CATEGORIZABLE_ELEMENT__LABEL_OBJECT:
			return getLabelObject() != null;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == VHasTooltip.class) {
			switch (derivedFeatureID) {
			case VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP:
				return VViewPackage.HAS_TOOLTIP__TOOLTIP;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == VHasTooltip.class) {
			switch (baseFeatureID) {
			case VViewPackage.HAS_TOOLTIP__TOOLTIP:
				return VCategorizationPackage.CATEGORIZABLE_ELEMENT__TOOLTIP;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (tooltip: "); //$NON-NLS-1$
		result.append(tooltip);
		result.append(')');
		return result.toString();
	}

	protected void addNavigatedDependencyToLabelAdapter(EStructuralFeature navigationFeature,
		EStructuralFeature dependantFeature) {
		labelAdapter.addNavigatedDependency(navigationFeature, dependantFeature);
	}

	protected void addLocalDependencyToLabelAdapter(EStructuralFeature localFeature) {
		labelAdapter.addLocalDependency(localFeature);
	}

} // VCategorizableElementImpl
