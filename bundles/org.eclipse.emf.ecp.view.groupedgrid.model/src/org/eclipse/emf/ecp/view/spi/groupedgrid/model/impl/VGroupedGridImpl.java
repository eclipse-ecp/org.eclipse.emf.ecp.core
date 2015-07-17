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
package org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroup;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGrid;
import org.eclipse.emf.ecp.view.spi.groupedgrid.model.VGroupedGridPackage;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Grouped Grid</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.groupedgrid.model.impl.VGroupedGridImpl#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.2
 */
public class VGroupedGridImpl extends VContainedElementImpl implements VGroupedGrid
{
	/**
	 * The cached value of the '{@link #getGroups() <em>Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<VGroup> groups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VGroupedGridImpl()
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
		return VGroupedGridPackage.Literals.GROUPED_GRID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VGroup> getGroups()
	{
		if (groups == null)
		{
			groups = new EObjectContainmentEList<VGroup>(VGroup.class, this, VGroupedGridPackage.GROUPED_GRID__GROUPS);
		}
		return groups;
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
		case VGroupedGridPackage.GROUPED_GRID__GROUPS:
			return ((InternalEList<?>) getGroups()).basicRemove(otherEnd, msgs);
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
		case VGroupedGridPackage.GROUPED_GRID__GROUPS:
			return getGroups();
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
		case VGroupedGridPackage.GROUPED_GRID__GROUPS:
			getGroups().clear();
			getGroups().addAll((Collection<? extends VGroup>) newValue);
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
		case VGroupedGridPackage.GROUPED_GRID__GROUPS:
			getGroups().clear();
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
		case VGroupedGridPackage.GROUPED_GRID__GROUPS:
			return groups != null && !groups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VGroupedGridImpl
