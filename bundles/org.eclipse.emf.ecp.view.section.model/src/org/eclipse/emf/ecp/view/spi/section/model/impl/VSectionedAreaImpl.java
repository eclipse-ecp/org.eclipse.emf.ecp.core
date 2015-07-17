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
package org.eclipse.emf.ecp.view.spi.section.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl;
import org.eclipse.emf.ecp.view.spi.section.model.VSection;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionPackage;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sectioned Area</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.section.model.impl.VSectionedAreaImpl#getRoot <em>Root</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VSectionedAreaImpl extends VContainedElementImpl implements VSectionedArea
{
	/**
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected VSection root;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VSectionedAreaImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VSectionPackage.Literals.SECTIONED_AREA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VSection getRoot()
	{
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetRoot(VSection newRoot, NotificationChain msgs)
	{
		final VSection oldRoot = root;
		root = newRoot;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VSectionPackage.SECTIONED_AREA__ROOT, oldRoot, newRoot);
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
	public void setRoot(VSection newRoot)
	{
		if (newRoot != root)
		{
			NotificationChain msgs = null;
			if (root != null) {
				msgs = ((InternalEObject) root).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VSectionPackage.SECTIONED_AREA__ROOT, null, msgs);
			}
			if (newRoot != null) {
				msgs = ((InternalEObject) newRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VSectionPackage.SECTIONED_AREA__ROOT, null, msgs);
			}
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VSectionPackage.SECTIONED_AREA__ROOT, newRoot,
				newRoot));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
		case VSectionPackage.SECTIONED_AREA__ROOT:
			return basicSetRoot(null, msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VSectionPackage.SECTIONED_AREA__ROOT:
			return getRoot();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VSectionPackage.SECTIONED_AREA__ROOT:
			setRoot((VSection) newValue);
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VSectionPackage.SECTIONED_AREA__ROOT:
			setRoot((VSection) null);
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VSectionPackage.SECTIONED_AREA__ROOT:
			return root != null;
		}
		return super.eIsSet(featureID);
	}

} // VSectionedAreaImpl
