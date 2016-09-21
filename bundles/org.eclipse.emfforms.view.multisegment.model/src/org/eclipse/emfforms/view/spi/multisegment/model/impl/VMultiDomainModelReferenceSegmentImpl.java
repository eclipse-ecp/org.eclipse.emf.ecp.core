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
package org.eclipse.emfforms.view.spi.multisegment.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeatureDomainModelReferenceSegmentImpl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Domain Model Reference Segment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.view.spi.multisegment.model.impl.VMultiDomainModelReferenceSegmentImpl#getChildDomainModelReferences
 * <em>Child Domain Model References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VMultiDomainModelReferenceSegmentImpl extends VFeatureDomainModelReferenceSegmentImpl
	implements VMultiDomainModelReferenceSegment {
	/**
	 * The cached value of the '{@link #getChildDomainModelReferences() <em>Child Domain Model References</em>}'
	 * containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildDomainModelReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<VDomainModelReference> childDomainModelReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VMultiDomainModelReferenceSegmentImpl() {
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
		return VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VDomainModelReference> getChildDomainModelReferences() {
		if (childDomainModelReferences == null) {
			childDomainModelReferences = new EObjectContainmentEList<VDomainModelReference>(VDomainModelReference.class,
				this, VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES);
		}
		return childDomainModelReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES:
			return ((InternalEList<?>) getChildDomainModelReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES:
			return getChildDomainModelReferences();
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
		case VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES:
			getChildDomainModelReferences().clear();
			getChildDomainModelReferences().addAll((Collection<? extends VDomainModelReference>) newValue);
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
		case VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES:
			getChildDomainModelReferences().clear();
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
		case VMultisegmentPackage.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES:
			return childDomainModelReferences != null && !childDomainModelReferences.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VMultiDomainModelReferenceSegmentImpl
