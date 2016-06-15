/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Domain Model Reference Segment</b></em>'.
 *
 * @since 2.0
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>
 *        {@link org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl#getDomainModelFeature
 *        <em>Domain Model Feature</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VFeatureDomainModelReferenceSegmentImpl extends EObjectImpl
	implements VFeatureDomainModelReferenceSegment {
	/**
	 * The default value of the '{@link #getDomainModelFeature() <em>Domain Model Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelFeature()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_MODEL_FEATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomainModelFeature() <em>Domain Model Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelFeature()
	 * @generated
	 * @ordered
	 */
	protected String domainModelFeature = DOMAIN_MODEL_FEATURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VFeatureDomainModelReferenceSegmentImpl() {
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
		return VViewPackage.Literals.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getDomainModelFeature() {
		return domainModelFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDomainModelFeature(String newDomainModelFeature) {
		final String oldDomainModelFeature = domainModelFeature;
		domainModelFeature = newDomainModelFeature;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE, oldDomainModelFeature,
				domainModelFeature));
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
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE:
			return getDomainModelFeature();
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
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE:
			setDomainModelFeature((String) newValue);
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
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE:
			setDomainModelFeature(DOMAIN_MODEL_FEATURE_EDEFAULT);
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
		case VViewPackage.FEATURE_DOMAIN_MODEL_REFERENCE_SEGMENT__DOMAIN_MODEL_FEATURE:
			return DOMAIN_MODEL_FEATURE_EDEFAULT == null ? domainModelFeature != null
				: !DOMAIN_MODEL_FEATURE_EDEFAULT.equals(domainModelFeature);
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
		result.append(" (domainModelFeature: "); //$NON-NLS-1$
		result.append(domainModelFeature);
		result.append(')');
		return result.toString();
	}

} // VFeatureDomainModelReferenceSegmentImpl
