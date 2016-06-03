/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.table.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.spi.table.model.VWidthConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Width Configuration</b></em>'.
 *
 * @since 1.9
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VWidthConfigurationImpl#getColumnDomainReference
 *        <em>Column Domain Reference</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VWidthConfigurationImpl#getWeight <em>Weight</em>}
 *        </li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VWidthConfigurationImpl#getMinWidth
 *        <em>Min Width</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VWidthConfigurationImpl extends EObjectImpl implements VWidthConfiguration {
	/**
	 * The cached value of the '{@link #getColumnDomainReference() <em>Column Domain Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnDomainReference()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference columnDomainReference;

	/**
	 * The default value of the '{@link #getWeight() <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWeight()
	 * @generated
	 * @ordered
	 */
	protected static final int WEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWeight() <em>Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWeight()
	 * @generated
	 * @ordered
	 */
	protected int weight = WEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMinWidth() <em>Min Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMinWidth() <em>Min Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMinWidth()
	 * @generated
	 * @ordered
	 */
	protected int minWidth = MIN_WIDTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VWidthConfigurationImpl() {
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
		return VTablePackage.Literals.WIDTH_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getColumnDomainReference() {
		if (columnDomainReference != null && columnDomainReference.eIsProxy()) {
			final InternalEObject oldColumnDomainReference = (InternalEObject) columnDomainReference;
			columnDomainReference = (VDomainModelReference) eResolveProxy(oldColumnDomainReference);
			if (columnDomainReference != oldColumnDomainReference) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE, oldColumnDomainReference,
						columnDomainReference));
				}
			}
		}
		return columnDomainReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VDomainModelReference basicGetColumnDomainReference() {
		return columnDomainReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColumnDomainReference(VDomainModelReference newColumnDomainReference) {
		final VDomainModelReference oldColumnDomainReference = columnDomainReference;
		columnDomainReference = newColumnDomainReference;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE, oldColumnDomainReference,
				columnDomainReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getWeight() {
		return weight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWeight(int newWeight) {
		final int oldWeight = weight;
		weight = newWeight;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTablePackage.WIDTH_CONFIGURATION__WEIGHT, oldWeight,
				weight));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMinWidth(int newMinWidth) {
		final int oldMinWidth = minWidth;
		minWidth = newMinWidth;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VTablePackage.WIDTH_CONFIGURATION__MIN_WIDTH,
				oldMinWidth, minWidth));
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
		case VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE:
			if (resolve) {
				return getColumnDomainReference();
			}
			return basicGetColumnDomainReference();
		case VTablePackage.WIDTH_CONFIGURATION__WEIGHT:
			return getWeight();
		case VTablePackage.WIDTH_CONFIGURATION__MIN_WIDTH:
			return getMinWidth();
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
		case VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE:
			setColumnDomainReference((VDomainModelReference) newValue);
			return;
		case VTablePackage.WIDTH_CONFIGURATION__WEIGHT:
			setWeight((Integer) newValue);
			return;
		case VTablePackage.WIDTH_CONFIGURATION__MIN_WIDTH:
			setMinWidth((Integer) newValue);
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
		case VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE:
			setColumnDomainReference((VDomainModelReference) null);
			return;
		case VTablePackage.WIDTH_CONFIGURATION__WEIGHT:
			setWeight(WEIGHT_EDEFAULT);
			return;
		case VTablePackage.WIDTH_CONFIGURATION__MIN_WIDTH:
			setMinWidth(MIN_WIDTH_EDEFAULT);
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
		case VTablePackage.WIDTH_CONFIGURATION__COLUMN_DOMAIN_REFERENCE:
			return columnDomainReference != null;
		case VTablePackage.WIDTH_CONFIGURATION__WEIGHT:
			return weight != WEIGHT_EDEFAULT;
		case VTablePackage.WIDTH_CONFIGURATION__MIN_WIDTH:
			return minWidth != MIN_WIDTH_EDEFAULT;
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
		result.append(" (weight: "); //$NON-NLS-1$
		result.append(weight);
		result.append(", minWidth: "); //$NON-NLS-1$
		result.append(minWidth);
		result.append(')');
		return result.toString();
	}

} // VWidthConfigurationImpl
