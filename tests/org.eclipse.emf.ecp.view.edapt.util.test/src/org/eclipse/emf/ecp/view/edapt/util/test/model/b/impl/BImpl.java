/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.A;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.B;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.C;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>B</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl.BImpl#getA <em>A</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl.BImpl#getC <em>C</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BImpl extends MinimalEObjectImpl.Container implements B {
	/**
	 * The cached value of the '{@link #getA() <em>A</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getA()
	 * @generated
	 * @ordered
	 */
	protected A a;

	/**
	 * The cached value of the '{@link #getC() <em>C</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getC()
	 * @generated
	 * @ordered
	 */
	protected C c;

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
		return BPackage.Literals.B;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public A getA() {
		if (a != null && a.eIsProxy()) {
			final InternalEObject oldA = (InternalEObject) a;
			a = (A) eResolveProxy(oldA);
			if (a != oldA) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPackage.B__A, oldA, a));
				}
			}
		}
		return a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public A basicGetA() {
		return a;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setA(A newA) {
		final A oldA = a;
		a = newA;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, BPackage.B__A, oldA, a));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public C getC() {
		if (c != null && c.eIsProxy()) {
			final InternalEObject oldC = (InternalEObject) c;
			c = (C) eResolveProxy(oldC);
			if (c != oldC) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPackage.B__C, oldC, c));
				}
			}
		}
		return c;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public C basicGetC() {
		return c;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setC(C newC) {
		final C oldC = c;
		c = newC;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, BPackage.B__C, oldC, c));
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
		case BPackage.B__A:
			if (resolve) {
				return getA();
			}
			return basicGetA();
		case BPackage.B__C:
			if (resolve) {
				return getC();
			}
			return basicGetC();
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
		case BPackage.B__A:
			setA((A) newValue);
			return;
		case BPackage.B__C:
			setC((C) newValue);
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
		case BPackage.B__A:
			setA((A) null);
			return;
		case BPackage.B__C:
			setC((C) null);
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
		case BPackage.B__A:
			return a != null;
		case BPackage.B__C:
			return c != null;
		}
		return super.eIsSet(featureID);
	}

} // BImpl
