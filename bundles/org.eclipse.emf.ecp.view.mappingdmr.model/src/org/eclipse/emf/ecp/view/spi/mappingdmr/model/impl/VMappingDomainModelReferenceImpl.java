/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Mapping Domain Model Reference</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl#getMappedClass
 * <em>Mapped Class</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.spi.mappingdmr.model.impl.VMappingDomainModelReferenceImpl#getDomainModelReference
 * <em>Domain Model Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VMappingDomainModelReferenceImpl extends
	VFeaturePathDomainModelReferenceImpl implements
	VMappingDomainModelReference {
	/**
	 * The cached value of the '{@link #getMappedClass() <em>Mapped Class</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getMappedClass()
	 * @generated
	 * @ordered
	 */
	protected EClass mappedClass;

	/**
	 * The cached value of the '{@link #getDomainModelReference()
	 * <em>Domain Model Reference</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDomainModelReference()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference domainModelReference;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VMappingDomainModelReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VMappingdmrPackage.Literals.MAPPING_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
					eNotify(new ENotificationImpl(
						this,
						Notification.RESOLVE,
						VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS,
						oldMappedClass, mappedClass));
				}
			}
		}
		return mappedClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EClass basicGetMappedClass() {
		return mappedClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMappedClass(EClass newMappedClass) {
		final EClass oldMappedClass = mappedClass;
		mappedClass = newMappedClass;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
				this,
				Notification.SET,
				VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS,
				oldMappedClass, mappedClass));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getDomainModelReference() {
		return domainModelReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDomainModelReference(
		VDomainModelReference newDomainModelReference,
		NotificationChain msgs) {
		final VDomainModelReference oldDomainModelReference = domainModelReference;
		domainModelReference = newDomainModelReference;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(
				this,
				Notification.SET,
				VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
				oldDomainModelReference, newDomainModelReference);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDomainModelReference(
		VDomainModelReference newDomainModelReference) {
		if (newDomainModelReference != domainModelReference) {
			NotificationChain msgs = null;
			if (domainModelReference != null) {
				msgs = ((InternalEObject) domainModelReference)
					.eInverseRemove(
						this,
						EOPPOSITE_FEATURE_BASE
							- VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
						null, msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference)
					.eInverseAdd(
						this,
						EOPPOSITE_FEATURE_BASE
							- VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
						null, msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(
				this,
				Notification.SET,
				VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE,
				newDomainModelReference, newDomainModelReference));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
		int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			return basicSetDomainModelReference(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS:
			if (resolve) {
				return getMappedClass();
			}
			return basicGetMappedClass();
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			return getDomainModelReference();
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS:
			setMappedClass((EClass) newValue);
			return;
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) newValue);
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS:
			setMappedClass((EClass) null);
			return;
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) null);
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
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__MAPPED_CLASS:
			return mappedClass != null;
		case VMappingdmrPackage.MAPPING_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		}
		return super.eIsSet(featureID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(EObject eObject) {
		final boolean init = super.init(eObject);
		final EMap<EClass, EObject> map = (EMap<EClass, EObject>) lastResolvedEObject.eGet(getDomainModelEFeature());
		if (!map.containsKey(getMappedClass())) {
			map.put(getMappedClass(), getMappedClass().getEPackage().getEFactoryInstance().create(getMappedClass()));
		}
		final EObject mappedEObject = map.get(getMappedClass());
		final boolean subDMRInit = getDomainModelReference().init(mappedEObject);
		return init && subDMRInit;
	}

	@Override
	public Iterator<Setting> getIterator() {
		if (getDomainModelReference() == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}
		return getDomainModelReference().getIterator();
	}

	@Override
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		if (getDomainModelReference() == null) {
			final Set<EStructuralFeature> features = Collections.emptySet();
			return features.iterator();
		}
		return getDomainModelReference().getEStructuralFeatureIterator();
	}

} // VMappingDomainModelReferenceImpl
