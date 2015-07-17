/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.ecp.view.mapping.test.example.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.mapping.test.example.Child;
import org.eclipse.emf.ecp.view.mapping.test.example.ExamplePackage;
import org.eclipse.emf.ecp.view.mapping.test.example.IntermediateTarget;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Child</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.mapping.test.example.impl.ChildImpl#getIntermediateTarget <em>Intermediate Target
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChildImpl extends AbstractChildImpl implements Child {
	/**
	 * The cached value of the '{@link #getIntermediateTarget() <em>Intermediate Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getIntermediateTarget()
	 * @generated
	 * @ordered
	 */
	protected IntermediateTarget intermediateTarget;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ChildImpl() {
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
		return ExamplePackage.Literals.CHILD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IntermediateTarget getIntermediateTarget() {
		return intermediateTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetIntermediateTarget(IntermediateTarget newIntermediateTarget, NotificationChain msgs) {
		final IntermediateTarget oldIntermediateTarget = intermediateTarget;
		intermediateTarget = newIntermediateTarget;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				ExamplePackage.CHILD__INTERMEDIATE_TARGET, oldIntermediateTarget, newIntermediateTarget);
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
	public void setIntermediateTarget(IntermediateTarget newIntermediateTarget) {
		if (newIntermediateTarget != intermediateTarget) {
			NotificationChain msgs = null;
			if (intermediateTarget != null) {
				msgs = ((InternalEObject) intermediateTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- ExamplePackage.CHILD__INTERMEDIATE_TARGET, null, msgs);
			}
			if (newIntermediateTarget != null) {
				msgs = ((InternalEObject) newIntermediateTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- ExamplePackage.CHILD__INTERMEDIATE_TARGET, null, msgs);
			}
			msgs = basicSetIntermediateTarget(newIntermediateTarget, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ExamplePackage.CHILD__INTERMEDIATE_TARGET,
				newIntermediateTarget, newIntermediateTarget));
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
		case ExamplePackage.CHILD__INTERMEDIATE_TARGET:
			return basicSetIntermediateTarget(null, msgs);
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
		case ExamplePackage.CHILD__INTERMEDIATE_TARGET:
			return getIntermediateTarget();
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
		case ExamplePackage.CHILD__INTERMEDIATE_TARGET:
			setIntermediateTarget((IntermediateTarget) newValue);
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
		case ExamplePackage.CHILD__INTERMEDIATE_TARGET:
			setIntermediateTarget((IntermediateTarget) null);
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
		case ExamplePackage.CHILD__INTERMEDIATE_TARGET:
			return intermediateTarget != null;
		}
		return super.eIsSet(featureID);
	}

} // ChildImpl
