/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleRepository;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Rule Repository</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleRepositoryImpl#getRuleEntries <em>Rule
 * Entries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VRuleRepositoryImpl extends EObjectImpl implements VRuleRepository {
	/**
	 * The cached value of the '{@link #getRuleEntries() <em>Rule Entries</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRuleEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<VRuleEntry> ruleEntries;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VRuleRepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VRulerepositoryPackage.Literals.RULE_REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VRuleEntry> getRuleEntries() {
		if (ruleEntries == null) {
			ruleEntries = new EObjectContainmentEList<VRuleEntry>(VRuleEntry.class, this,
				VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES);
		}
		return ruleEntries;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES:
			return ((InternalEList<?>) getRuleEntries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES:
			return getRuleEntries();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES:
			getRuleEntries().clear();
			getRuleEntries().addAll((Collection<? extends VRuleEntry>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES:
			getRuleEntries().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_REPOSITORY__RULE_ENTRIES:
			return ruleEntries != null && !ruleEntries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // VRuleRepositoryImpl
