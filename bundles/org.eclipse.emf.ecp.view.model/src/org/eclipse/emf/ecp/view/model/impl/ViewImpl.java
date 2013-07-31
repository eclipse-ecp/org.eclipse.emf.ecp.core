/**
 */
package org.eclipse.emf.ecp.view.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.model.Composite;
import org.eclipse.emf.ecp.view.model.View;
import org.eclipse.emf.ecp.view.model.ViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.model.impl.ViewImpl#getRootEClass <em>Root EClass</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.model.impl.ViewImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ViewImpl extends CategorizationImpl implements View {
	/**
	 * The cached value of the '{@link #getRootEClass() <em>Root EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getRootEClass()
	 * @generated
	 * @ordered
	 */
	protected EClass rootEClass;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Composite> children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ViewImpl() {
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
		return ViewPackage.Literals.VIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRootEClass() {
		if (rootEClass != null && rootEClass.eIsProxy())
		{
			InternalEObject oldRootEClass = (InternalEObject) rootEClass;
			rootEClass = (EClass) eResolveProxy(oldRootEClass);
			if (rootEClass != oldRootEClass)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ViewPackage.VIEW__ROOT_ECLASS,
						oldRootEClass, rootEClass));
			}
		}
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass basicGetRootEClass() {
		return rootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setRootEClass(EClass newRootEClass) {
		EClass oldRootEClass = rootEClass;
		rootEClass = newRootEClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.VIEW__ROOT_ECLASS, oldRootEClass,
				rootEClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Composite> getChildren() {
		if (children == null)
		{
			children = new EObjectContainmentEList<Composite>(Composite.class, this, ViewPackage.VIEW__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
		case ViewPackage.VIEW__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
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
		switch (featureID)
		{
		case ViewPackage.VIEW__ROOT_ECLASS:
			if (resolve)
				return getRootEClass();
			return basicGetRootEClass();
		case ViewPackage.VIEW__CHILDREN:
			return getChildren();
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
		switch (featureID)
		{
		case ViewPackage.VIEW__ROOT_ECLASS:
			setRootEClass((EClass) newValue);
			return;
		case ViewPackage.VIEW__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends Composite>) newValue);
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
		switch (featureID)
		{
		case ViewPackage.VIEW__ROOT_ECLASS:
			setRootEClass((EClass) null);
			return;
		case ViewPackage.VIEW__CHILDREN:
			getChildren().clear();
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
		switch (featureID)
		{
		case ViewPackage.VIEW__ROOT_ECLASS:
			return rootEClass != null;
		case ViewPackage.VIEW__CHILDREN:
			return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ViewImpl
