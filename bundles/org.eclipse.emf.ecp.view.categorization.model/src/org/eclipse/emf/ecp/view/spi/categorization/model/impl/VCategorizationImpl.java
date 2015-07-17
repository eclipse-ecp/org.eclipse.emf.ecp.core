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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorization;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Categorization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.categorization.model.impl.VCategorizationImpl#getCategorizations <em>
 * Categorizations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VCategorizationImpl extends VAbstractCategorizationImpl implements VCategorization
{
	/**
	 * The cached value of the '{@link #getCategorizations() <em>Categorizations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCategorizations()
	 * @generated
	 * @ordered
	 */
	protected EList<VAbstractCategorization> categorizations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VCategorizationImpl()
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
		return VCategorizationPackage.Literals.CATEGORIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VAbstractCategorization> getCategorizations()
	{
		if (categorizations == null)
		{
			categorizations = new EObjectContainmentEList<VAbstractCategorization>(VAbstractCategorization.class, this,
				VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS);
		}
		return categorizations;
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
		case VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS:
			return ((InternalEList<?>) getCategorizations()).basicRemove(otherEnd, msgs);
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
		case VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS:
			return getCategorizations();
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
		case VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS:
			getCategorizations().clear();
			getCategorizations().addAll((Collection<? extends VAbstractCategorization>) newValue);
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
		case VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS:
			getCategorizations().clear();
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
		case VCategorizationPackage.CATEGORIZATION__CATEGORIZATIONS:
			return categorizations != null && !categorizations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VCategorizationImpl
