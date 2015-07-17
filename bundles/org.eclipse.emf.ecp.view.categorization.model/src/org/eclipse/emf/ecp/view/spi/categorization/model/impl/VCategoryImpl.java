/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.categorization.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategory;
import org.eclipse.emf.ecp.view.spi.model.VContainedElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategoryImpl#getComposite <em>Composite</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VCategoryImpl extends VAbstractCategorizationImpl implements VCategory
{
	/**
	 * The cached value of the '{@link #getComposite() <em>Composite</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getComposite()
	 * @generated
	 * @ordered
	 */
	protected VContainedElement composite;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VCategoryImpl()
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
		return VCategorizationPackage.Literals.CATEGORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VContainedElement getComposite()
	{
		return composite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetComposite(VContainedElement newComposite, NotificationChain msgs)
	{
		final VContainedElement oldComposite = composite;
		composite = newComposite;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VCategorizationPackage.CATEGORY__COMPOSITE, oldComposite, newComposite);
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
	public void setComposite(VContainedElement newComposite)
	{
		if (newComposite != composite)
		{
			NotificationChain msgs = null;
			if (composite != null) {
				msgs = ((InternalEObject) composite).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VCategorizationPackage.CATEGORY__COMPOSITE, null, msgs);
			}
			if (newComposite != null) {
				msgs = ((InternalEObject) newComposite).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VCategorizationPackage.CATEGORY__COMPOSITE, null, msgs);
			}
			msgs = basicSetComposite(newComposite, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VCategorizationPackage.CATEGORY__COMPOSITE,
				newComposite, newComposite));
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
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			return basicSetComposite(null, msgs);
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
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			return getComposite();
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
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			setComposite((VContainedElement) newValue);
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
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			setComposite((VContainedElement) null);
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
		case VCategorizationPackage.CATEGORY__COMPOSITE:
			return composite != null;
		}
		return super.eIsSet(featureID);
	}

} // VCategoryImpl
