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
package org.eclipse.emf.ecp.view.index.test.example.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.index.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.index.test.example.Intermediate;
import org.eclipse.emf.ecp.view.index.test.example.Root;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.index.test.example.impl.RootImpl#getIntermediate <em>Intermediate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RootImpl extends MinimalEObjectImpl.Container implements Root {
	/**
	 * The cached value of the '{@link #getIntermediate() <em>Intermediate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getIntermediate()
	 * @generated
	 * @ordered
	 */
	protected Intermediate intermediate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RootImpl() {
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
		return ExamplePackage.Literals.ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Intermediate getIntermediate() {
		return intermediate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetIntermediate(Intermediate newIntermediate, NotificationChain msgs) {
		final Intermediate oldIntermediate = intermediate;
		intermediate = newIntermediate;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ExamplePackage.ROOT__INTERMEDIATE, oldIntermediate, newIntermediate);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIntermediate(Intermediate newIntermediate) {
		if (newIntermediate != intermediate) {
			NotificationChain msgs = null;
			if (intermediate != null) {
				msgs = ((InternalEObject) intermediate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- ExamplePackage.ROOT__INTERMEDIATE, null, msgs);
			}
			if (newIntermediate != null) {
				msgs = ((InternalEObject) newIntermediate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- ExamplePackage.ROOT__INTERMEDIATE, null, msgs);
			}
			msgs = basicSetIntermediate(newIntermediate, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExamplePackage.ROOT__INTERMEDIATE, newIntermediate,
				newIntermediate));
		}
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
		case ExamplePackage.ROOT__INTERMEDIATE:
			return basicSetIntermediate(null, msgs);
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
		case ExamplePackage.ROOT__INTERMEDIATE:
			return getIntermediate();
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
		case ExamplePackage.ROOT__INTERMEDIATE:
			setIntermediate((Intermediate) newValue);
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
		case ExamplePackage.ROOT__INTERMEDIATE:
			setIntermediate((Intermediate) null);
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
		case ExamplePackage.ROOT__INTERMEDIATE:
			return intermediate != null;
		}
		return super.eIsSet(featureID);
	}

} // RootImpl
