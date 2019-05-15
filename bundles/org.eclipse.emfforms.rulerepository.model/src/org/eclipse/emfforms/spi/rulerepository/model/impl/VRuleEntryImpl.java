/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emfforms.spi.rulerepository.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.rule.model.Rule;
import org.eclipse.emfforms.spi.rulerepository.model.MergeType;
import org.eclipse.emfforms.spi.rulerepository.model.VRuleEntry;
import org.eclipse.emfforms.spi.rulerepository.model.VRulerepositoryPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Rule Entry</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl#getRule <em>Rule</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl#getElements <em>Elements</em>}</li>
 * <li>{@link org.eclipse.emfforms.spi.rulerepository.model.impl.VRuleEntryImpl#getMergeType <em>Merge Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VRuleEntryImpl extends EObjectImpl implements VRuleEntry {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<VElement> elements;

	/**
	 * The default value of the '{@link #getMergeType() <em>Merge Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMergeType()
	 * @generated
	 * @ordered
	 */
	protected static final MergeType MERGE_TYPE_EDEFAULT = MergeType.OR;

	/**
	 * The cached value of the '{@link #getMergeType() <em>Merge Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMergeType()
	 * @generated
	 * @ordered
	 */
	protected MergeType mergeType = MERGE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VRuleEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VRulerepositoryPackage.Literals.RULE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, VRulerepositoryPackage.RULE_ENTRY__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Rule getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		final Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VRulerepositoryPackage.RULE_ENTRY__RULE, oldRule, newRule);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRule(Rule newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null) {
				msgs = ((InternalEObject) rule).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - VRulerepositoryPackage.RULE_ENTRY__RULE, null, msgs);
			}
			if (newRule != null) {
				msgs = ((InternalEObject) newRule).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - VRulerepositoryPackage.RULE_ENTRY__RULE, null, msgs);
			}
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VRulerepositoryPackage.RULE_ENTRY__RULE, newRule,
				newRule));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VElement> getElements() {
		if (elements == null) {
			elements = new EObjectResolvingEList<VElement>(VElement.class, this,
				VRulerepositoryPackage.RULE_ENTRY__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MergeType getMergeType() {
		return mergeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMergeType(MergeType newMergeType) {
		final MergeType oldMergeType = mergeType;
		mergeType = newMergeType == null ? MERGE_TYPE_EDEFAULT : newMergeType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VRulerepositoryPackage.RULE_ENTRY__MERGE_TYPE,
				oldMergeType, mergeType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VRulerepositoryPackage.RULE_ENTRY__RULE:
			return basicSetRule(null, msgs);
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
		case VRulerepositoryPackage.RULE_ENTRY__NAME:
			return getName();
		case VRulerepositoryPackage.RULE_ENTRY__RULE:
			return getRule();
		case VRulerepositoryPackage.RULE_ENTRY__ELEMENTS:
			return getElements();
		case VRulerepositoryPackage.RULE_ENTRY__MERGE_TYPE:
			return getMergeType();
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
		case VRulerepositoryPackage.RULE_ENTRY__NAME:
			setName((String) newValue);
			return;
		case VRulerepositoryPackage.RULE_ENTRY__RULE:
			setRule((Rule) newValue);
			return;
		case VRulerepositoryPackage.RULE_ENTRY__ELEMENTS:
			getElements().clear();
			getElements().addAll((Collection<? extends VElement>) newValue);
			return;
		case VRulerepositoryPackage.RULE_ENTRY__MERGE_TYPE:
			setMergeType((MergeType) newValue);
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
		case VRulerepositoryPackage.RULE_ENTRY__NAME:
			setName(NAME_EDEFAULT);
			return;
		case VRulerepositoryPackage.RULE_ENTRY__RULE:
			setRule((Rule) null);
			return;
		case VRulerepositoryPackage.RULE_ENTRY__ELEMENTS:
			getElements().clear();
			return;
		case VRulerepositoryPackage.RULE_ENTRY__MERGE_TYPE:
			setMergeType(MERGE_TYPE_EDEFAULT);
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
		case VRulerepositoryPackage.RULE_ENTRY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case VRulerepositoryPackage.RULE_ENTRY__RULE:
			return rule != null;
		case VRulerepositoryPackage.RULE_ENTRY__ELEMENTS:
			return elements != null && !elements.isEmpty();
		case VRulerepositoryPackage.RULE_ENTRY__MERGE_TYPE:
			return mergeType != MERGE_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", mergeType: "); //$NON-NLS-1$
		result.append(mergeType);
		result.append(')');
		return result.toString();
	}

} // VRuleEntryImpl
