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
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VFeature Path Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEFeature <em>
 * Domain Model EFeature</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEReferencePath
 * <em>Domain Model EReference Path</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VFeaturePathDomainModelReferenceImpl extends EObjectImpl implements
	VFeaturePathDomainModelReference
{
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
	protected VFeaturePathDomainModelReferenceImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EStructuralFeature getDomainModelEFeature()
	{
		if (domainModelEFeature != null && domainModelEFeature.eIsProxy())
		{
			final InternalEObject oldDomainModelEFeature = (InternalEObject) domainModelEFeature;
			domainModelEFeature = (EStructuralFeature) eResolveProxy(oldDomainModelEFeature);
			if (domainModelEFeature != oldDomainModelEFeature)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE,
						oldDomainModelEFeature, domainModelEFeature));
				}
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
	public EStructuralFeature basicGetDomainModelEFeature()
	{
		return domainModelEFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDomainModelEFeature(EStructuralFeature newDomainModelEFeature)
	{
		final EStructuralFeature oldDomainModelEFeature = domainModelEFeature;
		domainModelEFeature = newDomainModelEFeature;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE, oldDomainModelEFeature,
				domainModelEFeature));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EReference> getDomainModelEReferencePath()
	{
		if (domainModelEReferencePath == null)
		{
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
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			if (resolve) {
				return getDomainModelEFeature();
			}
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			return domainModelEFeature != null;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			return domainModelEReferencePath != null && !domainModelEReferencePath.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.emf.ecp.view.model.VDomainModelReference#resolve(org.eclipse.emf.ecore.EObject)
	// */
	// public boolean resolve(final EObject domainModel) {
	// final EStructuralFeature domainModelEFeatureValue = getDomainModelEFeature();
	// if (domainModel == null || domainModelEFeatureValue == null) {
	// return false;
	// }
	// EObject parent = domainModel;
	// for (final EReference eReference : getDomainModelEReferencePath()) {
	// if (eReference.isMany()) {
	// return false;
	// }
	// if (!eReference.eContainer().equals(parent.eClass())) {
	// return false;
	// }
	// EObject child = (EObject) parent.eGet(eReference);
	// if (child == null) {
	// child = EcoreUtil.create(eReference.getEReferenceType());
	// parent.eSet(eReference, child);
	// }
	// parent = child;
	// }
	// setDomainModel(parent);
	// setModelFeature(domainModelEFeatureValue);
	// return true;
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#resolve(org.eclipse.emf.ecore.EObject)
	 */
	public boolean resolve(EObject domainModel) {
		final EStructuralFeature domainModelEFeatureValue = getDomainModelEFeature();
		if (domainModel == null || domainModelEFeatureValue == null) {
			return false;
		}

		EObject currentResolvedEObject = domainModel;
		final ArrayList<EReference> currentLeftReferences = new ArrayList<EReference>(getDomainModelEReferencePath());
		for (final EReference eReference : getDomainModelEReferencePath()) {
			if (eReference.isMany()) {
				break;
			}
			if (!eReference.getEContainingClass().isInstance(currentResolvedEObject)) {
				return false;
			}
			EObject child = (EObject) currentResolvedEObject.eGet(eReference);
			if (child == null) {
				child = EcoreUtil.create(eReference.getEReferenceType());
				currentResolvedEObject.eSet(eReference, child);
			}
			currentResolvedEObject = child;
			currentLeftReferences.remove(eReference);
		}
		// FIXME this check is currently needed to ignore resolve tries with a wrong EObject
		// workaround block start
		// if (lastResolvedEObject != null && currentLeftReferences.isEmpty()
		// && !currentResolvedEObject.eClass().getEAllStructuralFeatures().contains(getDomainModelEFeature())) {
		// return false;
		// }
		// workaround block end
		lastResolvedEObject = currentResolvedEObject;
		leftReferences = currentLeftReferences;
		return true;
	}

	protected List<EReference> leftReferences;
	protected EObject lastResolvedEObject;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getIterator()
	 */
	public Iterator<Setting> getIterator() {
		if (lastResolvedEObject == null || leftReferences == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}

		return new DomainModelReferenceIterator(leftReferences, lastResolvedEObject, getDomainModelEFeature());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getEStructuralFeatureIterator()
	 */
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		return new Iterator<EStructuralFeature>() {

			private int counter = 1;

			public boolean hasNext() {
				return counter == 1;
			}

			public EStructuralFeature next() {
				if (counter != 1) {
					throw new NoSuchElementException(
						"There is only one EStructuralFeature in this VFeaturePathDomainModelReference.");
				}
				counter--;
				return getDomainModelEFeature();
			}

			public void remove() {
				// TODO Auto-generated method stub

			}
		};
	}

} // VFeaturePathDomainModelReferenceImpl
