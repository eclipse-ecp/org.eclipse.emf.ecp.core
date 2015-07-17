/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl#getRootEClass <em>Root EClass</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewImpl#getEcorePath <em>Ecore Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.2
 */
public class VViewImpl extends VElementImpl implements VView {
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
	protected EList<VContainedElement> children;

	/**
	 * The default value of the '{@link #getEcorePath() <em>Ecore Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEcorePath()
	 * @generated
	 * @ordered
	 * @since 1.3
	 */
	protected static final String ECORE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEcorePath() <em>Ecore Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEcorePath()
	 * @generated
	 * @ordered
	 * @since 1.3
	 */
	protected String ecorePath = ECORE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewImpl() {
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
		return VViewPackage.Literals.VIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRootEClass() {
		if (rootEClass != null && rootEClass.eIsProxy())
		{
			final InternalEObject oldRootEClass = (InternalEObject) rootEClass;
			rootEClass = (EClass) eResolveProxy(oldRootEClass);
			if (rootEClass != oldRootEClass)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VViewPackage.VIEW__ROOT_ECLASS,
						oldRootEClass, rootEClass));
				}
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
	@Override
	public void setRootEClass(EClass newRootEClass) {
		final EClass oldRootEClass = rootEClass;
		rootEClass = newRootEClass;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.VIEW__ROOT_ECLASS, oldRootEClass,
				rootEClass));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VContainedElement> getChildren() {
		if (children == null)
		{
			children = new EObjectContainmentEList<VContainedElement>(VContainedElement.class, this,
				VViewPackage.VIEW__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.3
	 */
	@Override
	public String getEcorePath()
	{
		return ecorePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.3
	 */
	@Override
	public void setEcorePath(String newEcorePath)
	{
		final String oldEcorePath = ecorePath;
		ecorePath = newEcorePath;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.VIEW__ECORE_PATH, oldEcorePath,
				ecorePath));
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
		switch (featureID)
		{
		case VViewPackage.VIEW__CHILDREN:
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
		case VViewPackage.VIEW__ROOT_ECLASS:
			if (resolve) {
				return getRootEClass();
			}
			return basicGetRootEClass();
		case VViewPackage.VIEW__CHILDREN:
			return getChildren();
		case VViewPackage.VIEW__ECORE_PATH:
			return getEcorePath();
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
		case VViewPackage.VIEW__ROOT_ECLASS:
			setRootEClass((EClass) newValue);
			return;
		case VViewPackage.VIEW__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends VContainedElement>) newValue);
			return;
		case VViewPackage.VIEW__ECORE_PATH:
			setEcorePath((String) newValue);
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
		case VViewPackage.VIEW__ROOT_ECLASS:
			setRootEClass((EClass) null);
			return;
		case VViewPackage.VIEW__CHILDREN:
			getChildren().clear();
			return;
		case VViewPackage.VIEW__ECORE_PATH:
			setEcorePath(ECORE_PATH_EDEFAULT);
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
		case VViewPackage.VIEW__ROOT_ECLASS:
			return rootEClass != null;
		case VViewPackage.VIEW__CHILDREN:
			return children != null && !children.isEmpty();
		case VViewPackage.VIEW__ECORE_PATH:
			return ECORE_PATH_EDEFAULT == null ? ecorePath != null : !ECORE_PATH_EDEFAULT.equals(ecorePath);
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
	public String toString()
	{
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (ecorePath: "); //$NON-NLS-1$
		result.append(ecorePath);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VView#setAllContentsReadOnly()
	 */
	@Override
	public void setAllContentsReadOnly() {
		final TreeIterator<EObject> contents = super.eAllContents();
		setReadonly(true);
		while (contents.hasNext()) {
			final EObject object = contents.next();
			if (object instanceof VElement) {
				final VElement next = (VElement) object;
				next.setReadonly(true);
			}
		}

	}

} // ViewImpl
