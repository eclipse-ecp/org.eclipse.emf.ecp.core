/**
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 */
package org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.impl.VContainedElementImpl;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Control Grid</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.emfforms.spi.view.controlgrid.model.impl.VControlGridImpl#getRows <em>Rows</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VControlGridImpl extends VContainedElementImpl implements VControlGrid {
	/**
	 * The cached value of the '{@link #getRows() <em>Rows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRows()
	 * @generated
	 * @ordered
	 */
	protected EList<VControlGridRow> rows;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VControlGridImpl() {
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
		return VControlgridPackage.Literals.CONTROL_GRID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VControlGridRow> getRows() {
		if (rows == null) {
			rows = new EObjectContainmentEList<VControlGridRow>(VControlGridRow.class, this,
				VControlgridPackage.CONTROL_GRID__ROWS);
		}
		return rows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VControlgridPackage.CONTROL_GRID__ROWS:
			return ((InternalEList<?>) getRows()).basicRemove(otherEnd, msgs);
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
		switch (featureID) {
		case VControlgridPackage.CONTROL_GRID__ROWS:
			return getRows();
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
		switch (featureID) {
		case VControlgridPackage.CONTROL_GRID__ROWS:
			getRows().clear();
			getRows().addAll((Collection<? extends VControlGridRow>) newValue);
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
		switch (featureID) {
		case VControlgridPackage.CONTROL_GRID__ROWS:
			getRows().clear();
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
		switch (featureID) {
		case VControlgridPackage.CONTROL_GRID__ROWS:
			return rows != null && !rows.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VControlGridImpl
