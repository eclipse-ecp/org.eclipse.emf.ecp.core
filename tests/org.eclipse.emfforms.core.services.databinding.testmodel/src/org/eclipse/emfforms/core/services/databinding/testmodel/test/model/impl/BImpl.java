/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 */
package org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.E;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl#getC <em>C</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl#getCList
 * <em>CList</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl#getEList
 * <em>EList</em>}</li>
 * <li>{@link org.eclipse.emfforms.core.services.databinding.testmodel.test.model.impl.BImpl#getE <em>E</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BImpl extends MinimalEObjectImpl.Container implements B {
	/**
	 * The cached value of the '{@link #getC() <em>C</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getC()
	 * @generated
	 * @ordered
	 */
	protected C c;

	/**
	 * The cached value of the '{@link #getCList() <em>CList</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCList()
	 * @generated
	 * @ordered
	 */
	protected EList<C> cList;

	/**
	 * The cached value of the '{@link #getEList() <em>EList</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEList()
	 * @generated
	 * @ordered
	 */
	protected EList<E> eList;

	/**
	 * The cached value of the '{@link #getE() <em>E</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getE()
	 * @generated
	 * @ordered
	 */
	protected E e;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected BImpl() {
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
		return TestPackage.Literals.B;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public C getC() {
		return c;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetC(C newC, NotificationChain msgs) {
		final C oldC = c;
		c = newC;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TestPackage.B__C, oldC,
				newC);
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
	public void setC(C newC) {
		if (newC != c) {
			NotificationChain msgs = null;
			if (c != null) {
				msgs = ((InternalEObject) c).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TestPackage.B__C, null,
					msgs);
			}
			if (newC != null) {
				msgs = ((InternalEObject) newC).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TestPackage.B__C, null,
					msgs);
			}
			msgs = basicSetC(newC, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.B__C, newC, newC));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<C> getCList() {
		if (cList == null) {
			cList = new EObjectResolvingEList<>(C.class, this, TestPackage.B__CLIST);
		}
		return cList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<E> getEList() {
		if (eList == null) {
			eList = new EObjectContainmentEList<>(E.class, this, TestPackage.B__ELIST);
		}
		return eList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public E getE() {
		if (e != null && e.eIsProxy()) {
			final InternalEObject oldE = (InternalEObject) e;
			e = (E) eResolveProxy(oldE);
			if (e != oldE) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TestPackage.B__E, oldE, e));
				}
			}
		}
		return e;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public E basicGetE() {
		return e;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setE(E newE) {
		final E oldE = e;
		e = newE;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TestPackage.B__E, oldE, e));
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
		case TestPackage.B__C:
			return basicSetC(null, msgs);
		case TestPackage.B__ELIST:
			return ((InternalEList<?>) getEList()).basicRemove(otherEnd, msgs);
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
		case TestPackage.B__C:
			return getC();
		case TestPackage.B__CLIST:
			return getCList();
		case TestPackage.B__ELIST:
			return getEList();
		case TestPackage.B__E:
			if (resolve) {
				return getE();
			}
			return basicGetE();
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
		case TestPackage.B__C:
			setC((C) newValue);
			return;
		case TestPackage.B__CLIST:
			getCList().clear();
			getCList().addAll((Collection<? extends C>) newValue);
			return;
		case TestPackage.B__ELIST:
			getEList().clear();
			getEList().addAll((Collection<? extends E>) newValue);
			return;
		case TestPackage.B__E:
			setE((E) newValue);
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
		case TestPackage.B__C:
			setC((C) null);
			return;
		case TestPackage.B__CLIST:
			getCList().clear();
			return;
		case TestPackage.B__ELIST:
			getEList().clear();
			return;
		case TestPackage.B__E:
			setE((E) null);
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
		case TestPackage.B__C:
			return c != null;
		case TestPackage.B__CLIST:
			return cList != null && !cList.isEmpty();
		case TestPackage.B__ELIST:
			return eList != null && !eList.isEmpty();
		case TestPackage.B__E:
			return e != null;
		}
		return super.eIsSet(featureID);
	}

} // BImpl
