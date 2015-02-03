/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.E;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.F;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.FPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>F</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl.FImpl#getE <em>E</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FImpl extends MinimalEObjectImpl.Container implements F {
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
	protected FImpl() {
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
		return FPackage.Literals.F;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FPackage.F__E, oldE, e));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FPackage.F__E, oldE, e));
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
		case FPackage.F__E:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case FPackage.F__E:
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
		case FPackage.F__E:
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
		case FPackage.F__E:
			return e != null;
		}
		return super.eIsSet(featureID);
	}

} // FImpl
