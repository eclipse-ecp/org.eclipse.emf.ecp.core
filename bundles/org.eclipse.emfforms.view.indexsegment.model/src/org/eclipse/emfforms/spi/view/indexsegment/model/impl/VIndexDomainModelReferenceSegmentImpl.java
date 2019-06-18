/**
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.spi.view.indexsegment.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.view.indexsegment.model.VIndexsegmentPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Index
 * Domain Model Reference Segment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.view.indexsegment.model.impl.VIndexDomainModelReferenceSegmentImpl#getIndex
 * <em>Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VIndexDomainModelReferenceSegmentImpl extends VFeatureDomainModelReferenceSegmentImpl
	implements VIndexDomainModelReferenceSegment {
	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VIndexDomainModelReferenceSegmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VIndexsegmentPackage.Literals.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIndex(int newIndex) {
		final int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX, oldIndex, index));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX:
			return getIndex();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX:
			setIndex((Integer) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX:
			setIndex(INDEX_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VIndexsegmentPackage.INDEX_DOMAIN_MODEL_REFERENCE_SEGMENT__INDEX:
			return index != INDEX_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (index: "); //$NON-NLS-1$
		result.append(index);
		result.append(')');
		return result.toString();
	}

} // VIndexDomainModelReferenceSegmentImpl
