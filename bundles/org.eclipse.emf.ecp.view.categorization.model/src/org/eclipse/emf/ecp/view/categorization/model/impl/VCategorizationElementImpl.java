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
package org.eclipse.emf.ecp.view.categorization.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.categorization.model.VAbstractCategorization;
import org.eclipse.emf.ecp.view.categorization.model.VCategorizableElement;
import org.eclipse.emf.ecp.view.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.categorization.model.VCategorizationPackage;
import org.eclipse.emf.ecp.view.model.impl.VContainedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.categorization.model.impl.VCategorizationElementImpl#getCategorizations <em>
 * Categorizations</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.categorization.model.impl.VCategorizationElementImpl#getCurrentSelection <em>
 * Current Selection</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VCategorizationElementImpl extends VContainedElementImpl implements VCategorizationElement
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
	 * The cached value of the '{@link #getCurrentSelection() <em>Current Selection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCurrentSelection()
	 * @generated
	 * @ordered
	 */
	protected VCategorizableElement currentSelection;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VCategorizationElementImpl()
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
		return VCategorizationPackage.Literals.CATEGORIZATION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<VAbstractCategorization> getCategorizations()
	{
		if (categorizations == null)
		{
			categorizations = new EObjectContainmentEList<VAbstractCategorization>(VAbstractCategorization.class, this,
				VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS);
		}
		return categorizations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VCategorizableElement getCurrentSelection()
	{
		if (currentSelection != null && currentSelection.eIsProxy())
		{
			InternalEObject oldCurrentSelection = (InternalEObject) currentSelection;
			currentSelection = (VCategorizableElement) eResolveProxy(oldCurrentSelection);
			if (currentSelection != oldCurrentSelection)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION, oldCurrentSelection,
						currentSelection));
			}
		}
		return currentSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VCategorizableElement basicGetCurrentSelection()
	{
		return currentSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCurrentSelection(VCategorizableElement newCurrentSelection)
	{
		VCategorizableElement oldCurrentSelection = currentSelection;
		currentSelection = newCurrentSelection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
				VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION, oldCurrentSelection, currentSelection));
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
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS:
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
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS:
			return getCategorizations();
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION:
			if (resolve)
				return getCurrentSelection();
			return basicGetCurrentSelection();
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
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS:
			getCategorizations().clear();
			getCategorizations().addAll((Collection<? extends VAbstractCategorization>) newValue);
			return;
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION:
			setCurrentSelection((VCategorizableElement) newValue);
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
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS:
			getCategorizations().clear();
			return;
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION:
			setCurrentSelection((VCategorizableElement) null);
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
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CATEGORIZATIONS:
			return categorizations != null && !categorizations.isEmpty();
		case VCategorizationPackage.CATEGORIZATION_ELEMENT__CURRENT_SELECTION:
			return currentSelection != null;
		}
		return super.eIsSet(featureID);
	}

} // VCategorizationElementImpl
