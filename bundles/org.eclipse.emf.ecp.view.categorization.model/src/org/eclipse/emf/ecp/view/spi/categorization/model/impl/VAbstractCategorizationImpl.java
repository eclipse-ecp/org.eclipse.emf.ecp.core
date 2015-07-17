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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.categorization.model.ECPAction;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAction;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Categorization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VAbstractCategorizationImpl#getActions <em>Actions
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class VAbstractCategorizationImpl extends VCategorizableElementImpl implements VAbstractCategorization
{
	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<VAction> actions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VAbstractCategorizationImpl()
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
		return VCategorizationPackage.Literals.ABSTRACT_CATEGORIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VAction> getActions()
	{
		if (actions == null)
		{
			actions = new EObjectContainmentEList<VAction>(VAction.class, this,
				VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS);
		}
		return actions;
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
		case VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS:
			return ((InternalEList<?>) getActions()).basicRemove(otherEnd, msgs);
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
		case VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS:
			return getActions();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS:
			getActions().clear();
			getActions().addAll((Collection<? extends VAction>) newValue);
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
		case VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS:
			getActions().clear();
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
		case VCategorizationPackage.ABSTRACT_CATEGORIZATION__ACTIONS:
			return actions != null && !actions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#getLabelObject()
	 */
	@Override
	public EObject getLabelObject() {
		return this;
	}

	private List<ECPAction> ecpActions;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#getECPActions()
	 */
	@Override
	public List<ECPAction> getECPActions() {
		return ecpActions;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizableElement#setECPActions(java.util.List)
	 */
	@Override
	public void setECPActions(List<ECPAction> actions) {
		ecpActions = actions;
	}

} // VAbstractCategorizationImpl
