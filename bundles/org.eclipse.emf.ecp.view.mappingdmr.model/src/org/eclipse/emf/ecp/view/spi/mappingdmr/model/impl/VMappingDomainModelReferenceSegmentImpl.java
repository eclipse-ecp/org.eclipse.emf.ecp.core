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
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Domain Model Reference Segment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceSegmentImpl#getMappedClass
 * <em>Mapped Class</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VMappingDomainModelReferenceSegmentImpl extends VFeatureDomainModelReferenceSegmentImpl
	implements VMappingDomainModelReferenceSegment {
	/**
	 * The cached value of the '{@link #getMappedClass() <em>Mapped Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMappedClass()
	 * @generated
	 * @ordered
	 */
	protected EClass mappedClass;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VMappingDomainModelReferenceSegmentImpl() {
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
		return VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappedClass() {
		if (mappedClass != null && mappedClass.eIsProxy()) {
			final InternalEObject oldMappedClass = (InternalEObject) mappedClass;
			mappedClass = (EClass) eResolveProxy(oldMappedClass);
			if (mappedClass != oldMappedClass) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS, oldMappedClass,
						mappedClass));
				}
			}
		}
		return mappedClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EClass basicGetMappedClass() {
		return mappedClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMappedClass(EClass newMappedClass) {
		final EClass oldMappedClass = mappedClass;
		mappedClass = newMappedClass;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS, oldMappedClass, mappedClass));
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS:
			if (resolve) {
				return getMappedClass();
			}
			return basicGetMappedClass();
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS:
			setMappedClass((EClass) newValue);
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS:
			setMappedClass((EClass) null);
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE_SEGMENT__MAPPED_CLASS:
			return mappedClass != null;
		}
		return super.eIsSet(featureID);
	}

} // VMappingDomainModelReferenceSegmentImpl
