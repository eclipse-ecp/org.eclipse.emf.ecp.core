/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.B;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.D;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>D</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl.DImpl#getB <em>B</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DImpl extends MinimalEObjectImpl.Container implements D {
	/**
	 * The cached value of the '{@link #getB() <em>B</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected B b;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DImpl() {
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
		return DPackage.Literals.D;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public B getB() {
		if (b != null && b.eIsProxy()) {
			final InternalEObject oldB = (InternalEObject) b;
			b = (B) eResolveProxy(oldB);
			if (b != oldB) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DPackage.D__B, oldB, b));
				}
			}
		}
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public B basicGetB() {
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setB(B newB) {
		final B oldB = b;
		b = newB;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DPackage.D__B, oldB, b));
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
		case DPackage.D__B:
			if (resolve) {
				return getB();
			}
			return basicGetB();
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
		case DPackage.D__B:
			setB((B) newValue);
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
		case DPackage.D__B:
			setB((B) null);
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
		case DPackage.D__B:
			return b != null;
		}
		return super.eIsSet(featureID);
	}

} // DImpl
