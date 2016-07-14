/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Path Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEFeature
 * <em>Domain Model EFeature</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEReferencePath
 * <em>Domain Model EReference Path</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VFeaturePathDomainModelReferenceImpl extends VDomainModelReferenceImpl
	implements VFeaturePathDomainModelReference {
	/**
	 * The cached value of the '{@link #getDomainModelEFeature() <em>Domain Model EFeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDomainModelEFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature domainModelEFeature;

	/**
	 * The cached value of the '{@link #getDomainModelEReferencePath() <em>Domain Model EReference Path</em>}' reference
	 * list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDomainModelEReferencePath()
	 * @generated
	 * @ordered
	 */
	protected EList<EReference> domainModelEReferencePath;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VFeaturePathDomainModelReferenceImpl() {
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
		return VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EStructuralFeature getDomainModelEFeature() {
		if (domainModelEFeature != null && domainModelEFeature.eIsProxy()) {
			InternalEObject oldDomainModelEFeature = (InternalEObject) domainModelEFeature;
			domainModelEFeature = (EStructuralFeature) eResolveProxy(oldDomainModelEFeature);
			if (domainModelEFeature != oldDomainModelEFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE, oldDomainModelEFeature,
						domainModelEFeature));
			}
		}
		return domainModelEFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EStructuralFeature basicGetDomainModelEFeature() {
		return domainModelEFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDomainModelEFeature(EStructuralFeature newDomainModelEFeature) {
		EStructuralFeature oldDomainModelEFeature = domainModelEFeature;
		domainModelEFeature = newDomainModelEFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE, oldDomainModelEFeature,
				domainModelEFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EReference> getDomainModelEReferencePath() {
		if (domainModelEReferencePath == null) {
			domainModelEReferencePath = new EObjectResolvingEList<EReference>(EReference.class, this,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH);
		}
		return domainModelEReferencePath;
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			if (resolve)
				return getDomainModelEFeature();
			return basicGetDomainModelEFeature();
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			return getDomainModelEReferencePath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			setDomainModelEFeature((EStructuralFeature) newValue);
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			getDomainModelEReferencePath().clear();
			getDomainModelEReferencePath().addAll((Collection<? extends EReference>) newValue);
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			setDomainModelEFeature((EStructuralFeature) null);
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			getDomainModelEReferencePath().clear();
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			return domainModelEFeature != null;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			return domainModelEReferencePath != null && !domainModelEReferencePath.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VFeaturePathDomainModelReferenceImpl
