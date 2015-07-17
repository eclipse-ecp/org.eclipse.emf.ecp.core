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
package org.eclipse.emf.ecp.view.treemasterdetail.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetailPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tree Master Detail</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.treemasterdetail.model.impl.VTreeMasterDetailImpl#getDetailView <em>Detail View
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTreeMasterDetailImpl extends VContainedElementImpl implements VTreeMasterDetail
{
	/**
	 * The cached value of the '{@link #getDetailView() <em>Detail View</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDetailView()
	 * @generated
	 * @ordered
	 */
	protected VView detailView;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTreeMasterDetailImpl()
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
		return VTreeMasterDetailPackage.Literals.TREE_MASTER_DETAIL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VView getDetailView()
	{
		return detailView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDetailView(VView newDetailView, NotificationChain msgs)
	{
		final VView oldDetailView = detailView;
		detailView = newDetailView;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW, oldDetailView, newDetailView);
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
	public void setDetailView(VView newDetailView)
	{
		if (newDetailView != detailView)
		{
			NotificationChain msgs = null;
			if (detailView != null) {
				msgs = ((InternalEObject) detailView).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW, null, msgs);
			}
			if (newDetailView != null) {
				msgs = ((InternalEObject) newDetailView).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW, null, msgs);
			}
			msgs = basicSetDetailView(newDetailView, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW, newDetailView, newDetailView));
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW:
			return basicSetDetailView(null, msgs);
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW:
			return getDetailView();
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW:
			setDetailView((VView) newValue);
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW:
			setDetailView((VView) null);
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
		case VTreeMasterDetailPackage.TREE_MASTER_DETAIL__DETAIL_VIEW:
			return detailView != null;
		}
		return super.eIsSet(featureID);
	}

} // VTreeMasterDetailImpl
