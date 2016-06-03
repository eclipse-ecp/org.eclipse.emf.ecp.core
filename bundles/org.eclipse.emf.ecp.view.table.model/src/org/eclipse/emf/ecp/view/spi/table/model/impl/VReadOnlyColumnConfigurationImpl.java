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
package org.eclipse.emf.ecp.view.spi.table.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Read Only Column Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VReadOnlyColumnConfigurationImpl#getColumnDomainReferences
 * <em>Column Domain References</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VReadOnlyColumnConfigurationImpl extends EObjectImpl implements VReadOnlyColumnConfiguration {
	/**
	 * The cached value of the '{@link #getColumnDomainReferences() <em>Column Domain References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getColumnDomainReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<VDomainModelReference> columnDomainReferences;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VReadOnlyColumnConfigurationImpl() {
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
		return VTablePackage.Literals.READ_ONLY_COLUMN_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VDomainModelReference> getColumnDomainReferences() {
		if (columnDomainReferences == null) {
			columnDomainReferences = new EObjectResolvingEList<VDomainModelReference>(VDomainModelReference.class, this,
				VTablePackage.READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES);
		}
		return columnDomainReferences;
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
		case VTablePackage.READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES:
			return getColumnDomainReferences();
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
		case VTablePackage.READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES:
			getColumnDomainReferences().clear();
			getColumnDomainReferences().addAll((Collection<? extends VDomainModelReference>) newValue);
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
		case VTablePackage.READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES:
			getColumnDomainReferences().clear();
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
		case VTablePackage.READ_ONLY_COLUMN_CONFIGURATION__COLUMN_DOMAIN_REFERENCES:
			return columnDomainReferences != null && !columnDomainReferences.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VReadOnlyColumnConfigurationImpl
