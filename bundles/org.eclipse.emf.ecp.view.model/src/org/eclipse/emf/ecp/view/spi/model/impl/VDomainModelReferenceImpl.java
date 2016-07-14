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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Model Reference</b></em>'.
 *
 * @since 2.0
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl#getChangeListener <em>Change
 *        Listener</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VDomainModelReferenceImpl#getSegments
 *        <em>Segments</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VDomainModelReferenceImpl extends EObjectImpl implements VDomainModelReference {
	/**
	 * The cached value of the '{@link #getChangeListener() <em>Change Listener</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getChangeListener()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainModelReferenceChangeListener> changeListener;
	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<VDomainModelReferenceSegment> segments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VDomainModelReferenceImpl() {
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
		return VViewPackage.Literals.DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<DomainModelReferenceChangeListener> getChangeListener() {
		if (changeListener == null) {
			changeListener = new EDataTypeUniqueEList<DomainModelReferenceChangeListener>(
				DomainModelReferenceChangeListener.class, this, VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER);
		}
		return changeListener;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<VDomainModelReferenceSegment> getSegments() {
		if (segments == null) {
			segments = new EObjectContainmentEList<VDomainModelReferenceSegment>(VDomainModelReferenceSegment.class,
				this, VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS);
		}
		return segments;
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
		case VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS:
			return ((InternalEList<?>) getSegments()).basicRemove(otherEnd, msgs);
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
		case VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return getChangeListener();
		case VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS:
			return getSegments();
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
		case VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			getChangeListener().addAll((Collection<? extends DomainModelReferenceChangeListener>) newValue);
			return;
		case VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS:
			getSegments().clear();
			getSegments().addAll((Collection<? extends VDomainModelReferenceSegment>) newValue);
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
		case VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			return;
		case VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS:
			getSegments().clear();
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
		case VViewPackage.DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return changeListener != null && !changeListener.isEmpty();
		case VViewPackage.DOMAIN_MODEL_REFERENCE__SEGMENTS:
			return segments != null && !segments.isEmpty();
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (changeListener: "); //$NON-NLS-1$
		result.append(changeListener);
		result.append(')');
		return result.toString();
	}
} // VDomainModelReferenceImpl
