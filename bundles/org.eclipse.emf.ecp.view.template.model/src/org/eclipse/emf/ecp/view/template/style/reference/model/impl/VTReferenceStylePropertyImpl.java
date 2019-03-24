/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.style.reference.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.common.spi.EMFUtils;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferencePackage;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl#isShowCreateAndLinkButtonForCrossReferences
 * <em>Show Create And Link Button For Cross References</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.style.reference.model.impl.VTReferenceStylePropertyImpl#isShowLinkButtonForContainmentReferences
 * <em>Show Link Button For Containment References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VTReferenceStylePropertyImpl extends MinimalEObjectImpl.Container implements VTReferenceStyleProperty {
	/**
	 * The default value of the '{@link #isShowCreateAndLinkButtonForCrossReferences() <em>Show Create And Link Button
	 * For Cross References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowCreateAndLinkButtonForCrossReferences()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isShowCreateAndLinkButtonForCrossReferences() <em>Show Create And Link Button
	 * For Cross References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowCreateAndLinkButtonForCrossReferences()
	 * @generated
	 * @ordered
	 */
	protected boolean showCreateAndLinkButtonForCrossReferences = SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowLinkButtonForContainmentReferences() <em>Show Link Button For Containment
	 * References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowLinkButtonForContainmentReferences()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isShowLinkButtonForContainmentReferences() <em>Show Link Button For Containment
	 * References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isShowLinkButtonForContainmentReferences()
	 * @generated
	 * @ordered
	 */
	protected boolean showLinkButtonForContainmentReferences = SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTReferenceStylePropertyImpl() {
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
		return VTReferencePackage.Literals.REFERENCE_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isShowCreateAndLinkButtonForCrossReferences() {
		return showCreateAndLinkButtonForCrossReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setShowCreateAndLinkButtonForCrossReferences(boolean newShowCreateAndLinkButtonForCrossReferences) {
		final boolean oldShowCreateAndLinkButtonForCrossReferences = showCreateAndLinkButtonForCrossReferences;
		showCreateAndLinkButtonForCrossReferences = newShowCreateAndLinkButtonForCrossReferences;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES,
				oldShowCreateAndLinkButtonForCrossReferences, showCreateAndLinkButtonForCrossReferences));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isShowLinkButtonForContainmentReferences() {
		return showLinkButtonForContainmentReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setShowLinkButtonForContainmentReferences(boolean newShowLinkButtonForContainmentReferences) {
		final boolean oldShowLinkButtonForContainmentReferences = showLinkButtonForContainmentReferences;
		showLinkButtonForContainmentReferences = newShowLinkButtonForContainmentReferences;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES,
				oldShowLinkButtonForContainmentReferences, showLinkButtonForContainmentReferences));
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
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES:
			return isShowCreateAndLinkButtonForCrossReferences();
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES:
			return isShowLinkButtonForContainmentReferences();
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
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES:
			setShowCreateAndLinkButtonForCrossReferences((Boolean) newValue);
			return;
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES:
			setShowLinkButtonForContainmentReferences((Boolean) newValue);
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
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES:
			setShowCreateAndLinkButtonForCrossReferences(SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES_EDEFAULT);
			return;
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES:
			setShowLinkButtonForContainmentReferences(SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES_EDEFAULT);
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
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES:
			return showCreateAndLinkButtonForCrossReferences != SHOW_CREATE_AND_LINK_BUTTON_FOR_CROSS_REFERENCES_EDEFAULT;
		case VTReferencePackage.REFERENCE_STYLE_PROPERTY__SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES:
			return showLinkButtonForContainmentReferences != SHOW_LINK_BUTTON_FOR_CONTAINMENT_REFERENCES_EDEFAULT;
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

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (showCreateAndLinkButtonForCrossReferences: "); //$NON-NLS-1$
		result.append(showCreateAndLinkButtonForCrossReferences);
		result.append(", showLinkButtonForContainmentReferences: "); //$NON-NLS-1$
		result.append(showLinkButtonForContainmentReferences);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		return EMFUtils.filteredEquals(this, styleProperty);
	}

} // VTReferenceStylePropertyImpl
