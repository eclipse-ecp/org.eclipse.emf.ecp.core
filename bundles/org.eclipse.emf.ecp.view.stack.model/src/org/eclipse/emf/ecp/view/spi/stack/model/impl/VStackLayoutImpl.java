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
package org.eclipse.emf.ecp.view.spi.stack.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackItem;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackLayout;
import org.eclipse.emf.ecp.view.spi.stack.model.VStackPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl#getDomainModelReference <em>Domain Model
 * Reference</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl#getStackItems <em>Stack Items</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.stack.model.impl.VStackLayoutImpl#getTopElement <em>Top Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VStackLayoutImpl extends VContainedElementImpl implements VStackLayout
{
	/**
	 * The cached value of the '{@link #getDomainModelReference() <em>Domain Model Reference</em>}' containment
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelReference()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference domainModelReference;

	/**
	 * The cached value of the '{@link #getStackItems() <em>Stack Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getStackItems()
	 * @generated
	 * @ordered
	 */
	protected EList<VStackItem> stackItems;

	/**
	 * The cached value of the '{@link #getTopElement() <em>Top Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getTopElement()
	 * @generated
	 * @ordered
	 */
	protected VStackItem topElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VStackLayoutImpl()
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
		return VStackPackage.Literals.STACK_LAYOUT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getDomainModelReference()
	{
		return domainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDomainModelReference(VDomainModelReference newDomainModelReference,
		NotificationChain msgs)
	{
		final VDomainModelReference oldDomainModelReference = domainModelReference;
		domainModelReference = newDomainModelReference;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE, oldDomainModelReference, newDomainModelReference);
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
	public void setDomainModelReference(VDomainModelReference newDomainModelReference)
	{
		if (newDomainModelReference != domainModelReference)
		{
			NotificationChain msgs = null;
			if (domainModelReference != null) {
				msgs = ((InternalEObject) domainModelReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE,
				newDomainModelReference, newDomainModelReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VStackItem> getStackItems()
	{
		if (stackItems == null)
		{
			stackItems = new EObjectContainmentEList<VStackItem>(VStackItem.class, this,
				VStackPackage.STACK_LAYOUT__STACK_ITEMS);
		}
		return stackItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VStackItem getTopElement()
	{
		if (topElement != null && topElement.eIsProxy())
		{
			final InternalEObject oldTopElement = (InternalEObject) topElement;
			topElement = (VStackItem) eResolveProxy(oldTopElement);
			if (topElement != oldTopElement)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VStackPackage.STACK_LAYOUT__TOP_ELEMENT,
						oldTopElement, topElement));
				}
			}
		}
		return topElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public VStackItem basicGetTopElement()
	{
		return topElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setTopElement(VStackItem newTopElement)
	{
		final VStackItem oldTopElement = topElement;
		topElement = newTopElement;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VStackPackage.STACK_LAYOUT__TOP_ELEMENT,
				oldTopElement, topElement));
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
		case VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE:
			return basicSetDomainModelReference(null, msgs);
		case VStackPackage.STACK_LAYOUT__STACK_ITEMS:
			return ((InternalEList<?>) getStackItems()).basicRemove(otherEnd, msgs);
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
		case VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE:
			return getDomainModelReference();
		case VStackPackage.STACK_LAYOUT__STACK_ITEMS:
			return getStackItems();
		case VStackPackage.STACK_LAYOUT__TOP_ELEMENT:
			if (resolve) {
				return getTopElement();
			}
			return basicGetTopElement();
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
		case VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) newValue);
			return;
		case VStackPackage.STACK_LAYOUT__STACK_ITEMS:
			getStackItems().clear();
			getStackItems().addAll((Collection<? extends VStackItem>) newValue);
			return;
		case VStackPackage.STACK_LAYOUT__TOP_ELEMENT:
			setTopElement((VStackItem) newValue);
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
		case VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) null);
			return;
		case VStackPackage.STACK_LAYOUT__STACK_ITEMS:
			getStackItems().clear();
			return;
		case VStackPackage.STACK_LAYOUT__TOP_ELEMENT:
			setTopElement((VStackItem) null);
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
		case VStackPackage.STACK_LAYOUT__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		case VStackPackage.STACK_LAYOUT__STACK_ITEMS:
			return stackItems != null && !stackItems.isEmpty();
		case VStackPackage.STACK_LAYOUT__TOP_ELEMENT:
			return topElement != null;
		}
		return super.eIsSet(featureID);
	}

} // VStackLayoutImpl
