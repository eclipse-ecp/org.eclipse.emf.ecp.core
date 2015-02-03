/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.D;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.E;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EEPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.F;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>E</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl#getD <em>D</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EImpl#getF <em>F</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EImpl extends MinimalEObjectImpl.Container implements E {
	/**
	 * The cached value of the '{@link #getD() <em>D</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getD()
	 * @generated
	 * @ordered
	 */
	protected D d;

	/**
	 * The cached value of the '{@link #getF() <em>F</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getF()
	 * @generated
	 * @ordered
	 */
	protected F f;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EImpl() {
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
		return EEPackage.Literals.E;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public D getD() {
		if (d != null && d.eIsProxy()) {
			final InternalEObject oldD = (InternalEObject) d;
			d = (D) eResolveProxy(oldD);
			if (d != oldD) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EEPackage.E__D, oldD, d));
				}
			}
		}
		return d;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public D basicGetD() {
		return d;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setD(D newD) {
		final D oldD = d;
		d = newD;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, EEPackage.E__D, oldD, d));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public F getF() {
		if (f != null && f.eIsProxy()) {
			final InternalEObject oldF = (InternalEObject) f;
			f = (F) eResolveProxy(oldF);
			if (f != oldF) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EEPackage.E__F, oldF, f));
				}
			}
		}
		return f;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public F basicGetF() {
		return f;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setF(F newF) {
		final F oldF = f;
		f = newF;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, EEPackage.E__F, oldF, f));
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
		case EEPackage.E__D:
			if (resolve) {
				return getD();
			}
			return basicGetD();
		case EEPackage.E__F:
			if (resolve) {
				return getF();
			}
			return basicGetF();
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
		case EEPackage.E__D:
			setD((D) newValue);
			return;
		case EEPackage.E__F:
			setF((F) newValue);
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
		case EEPackage.E__D:
			setD((D) null);
			return;
		case EEPackage.E__F:
			setF((F) null);
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
		case EEPackage.E__D:
			return d != null;
		case EEPackage.E__F:
			return f != null;
		}
		return super.eIsSet(featureID);
	}

} // EImpl
