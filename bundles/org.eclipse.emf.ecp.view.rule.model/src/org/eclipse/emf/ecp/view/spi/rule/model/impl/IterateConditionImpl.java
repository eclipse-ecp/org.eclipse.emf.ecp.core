/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.rule.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Activator;
import org.eclipse.emf.ecp.view.spi.rule.model.Condition;
import org.eclipse.emf.ecp.view.spi.rule.model.IterateCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.Quantifier;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterate Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl#getQuantifier <em>Quantifier</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl#isIfEmpty <em>If Empty</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl#getItemReference <em>Item
 * Reference</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IterateConditionImpl#getItemCondition <em>Item
 * Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IterateConditionImpl extends ConditionImpl implements IterateCondition {
	/**
	 * The default value of the '{@link #getQuantifier() <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getQuantifier()
	 * @generated
	 * @ordered
	 */
	protected static final Quantifier QUANTIFIER_EDEFAULT = Quantifier.ALL;

	/**
	 * The cached value of the '{@link #getQuantifier() <em>Quantifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getQuantifier()
	 * @generated
	 * @ordered
	 */
	protected Quantifier quantifier = QUANTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isIfEmpty() <em>If Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIfEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IF_EMPTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIfEmpty() <em>If Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isIfEmpty()
	 * @generated
	 * @ordered
	 */
	protected boolean ifEmpty = IF_EMPTY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItemReference() <em>Item Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getItemReference()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference itemReference;

	/**
	 * The cached value of the '{@link #getItemCondition() <em>Item Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getItemCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition itemCondition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IterateConditionImpl() {
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
		return RulePackage.Literals.ITERATE_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Quantifier getQuantifier() {
		return quantifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setQuantifier(Quantifier newQuantifier) {
		final Quantifier oldQuantifier = quantifier;
		quantifier = newQuantifier == null ? QUANTIFIER_EDEFAULT : newQuantifier;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ITERATE_CONDITION__QUANTIFIER,
				oldQuantifier, quantifier));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIfEmpty() {
		return ifEmpty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIfEmpty(boolean newIfEmpty) {
		final boolean oldIfEmpty = ifEmpty;
		ifEmpty = newIfEmpty;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ITERATE_CONDITION__IF_EMPTY, oldIfEmpty,
				ifEmpty));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getItemReference() {
		return itemReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetItemReference(VDomainModelReference newItemReference, NotificationChain msgs) {
		final VDomainModelReference oldItemReference = itemReference;
		itemReference = newItemReference;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				RulePackage.ITERATE_CONDITION__ITEM_REFERENCE, oldItemReference, newItemReference);
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
	public void setItemReference(VDomainModelReference newItemReference) {
		if (newItemReference != itemReference) {
			NotificationChain msgs = null;
			if (itemReference != null) {
				msgs = ((InternalEObject) itemReference).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.ITERATE_CONDITION__ITEM_REFERENCE, null, msgs);
			}
			if (newItemReference != null) {
				msgs = ((InternalEObject) newItemReference).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.ITERATE_CONDITION__ITEM_REFERENCE, null, msgs);
			}
			msgs = basicSetItemReference(newItemReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ITERATE_CONDITION__ITEM_REFERENCE,
				newItemReference, newItemReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Condition getItemCondition() {
		return itemCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetItemCondition(Condition newItemCondition, NotificationChain msgs) {
		final Condition oldItemCondition = itemCondition;
		itemCondition = newItemCondition;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				RulePackage.ITERATE_CONDITION__ITEM_CONDITION, oldItemCondition, newItemCondition);
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
	public void setItemCondition(Condition newItemCondition) {
		if (newItemCondition != itemCondition) {
			NotificationChain msgs = null;
			if (itemCondition != null) {
				msgs = ((InternalEObject) itemCondition).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.ITERATE_CONDITION__ITEM_CONDITION, null, msgs);
			}
			if (newItemCondition != null) {
				msgs = ((InternalEObject) newItemCondition).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.ITERATE_CONDITION__ITEM_CONDITION, null, msgs);
			}
			msgs = basicSetItemCondition(newItemCondition, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.ITERATE_CONDITION__ITEM_CONDITION,
				newItemCondition, newItemCondition));
		}
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
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
			return basicSetItemReference(null, msgs);
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
			return basicSetItemCondition(null, msgs);
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
		case RulePackage.ITERATE_CONDITION__QUANTIFIER:
			return getQuantifier();
		case RulePackage.ITERATE_CONDITION__IF_EMPTY:
			return isIfEmpty();
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
			return getItemReference();
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
			return getItemCondition();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case RulePackage.ITERATE_CONDITION__QUANTIFIER:
			setQuantifier((Quantifier) newValue);
			return;
		case RulePackage.ITERATE_CONDITION__IF_EMPTY:
			setIfEmpty((Boolean) newValue);
			return;
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
			setItemReference((VDomainModelReference) newValue);
			return;
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
			setItemCondition((Condition) newValue);
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
		case RulePackage.ITERATE_CONDITION__QUANTIFIER:
			setQuantifier(QUANTIFIER_EDEFAULT);
			return;
		case RulePackage.ITERATE_CONDITION__IF_EMPTY:
			setIfEmpty(IF_EMPTY_EDEFAULT);
			return;
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
			setItemReference((VDomainModelReference) null);
			return;
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
			setItemCondition((Condition) null);
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
		case RulePackage.ITERATE_CONDITION__QUANTIFIER:
			return quantifier != QUANTIFIER_EDEFAULT;
		case RulePackage.ITERATE_CONDITION__IF_EMPTY:
			return ifEmpty != IF_EMPTY_EDEFAULT;
		case RulePackage.ITERATE_CONDITION__ITEM_REFERENCE:
			return itemReference != null;
		case RulePackage.ITERATE_CONDITION__ITEM_CONDITION:
			return itemCondition != null;
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
		result.append(" (quantifier: "); //$NON-NLS-1$
		result.append(quantifier);
		result.append(", ifEmpty: "); //$NON-NLS-1$
		result.append(ifEmpty);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean evaluate(EObject domainModel) {
		final VDomainModelReference path = getItemReference();
		final Collection<? extends EObject> subjects = get(domainModel, path);
		return doEvaluate(subjects);
	}

	/**
	 * Evaluates me by iteration of the given {@code subjects}.
	 *
	 * @param subjects the objects over which to iterate my {@link #getItemCondition() condition}
	 * @return the result of the iteration
	 */
	protected boolean doEvaluate(Collection<? extends EObject> subjects) {
		boolean result = isIfEmpty();

		for (final EObject next : subjects) {
			result = getItemCondition().evaluate(next);
			if (!result && getQuantifier() == Quantifier.ALL) {
				break; // Short-circuit
			}
			if (result && getQuantifier() == Quantifier.ANY) {
				break; // Short-circuit
			}
		}

		return result;
	}

	/**
	 * Obtains the objects referenced by a domain-model reference, from the given
	 * {@code owner} object.
	 *
	 * @param owner the owner of the reference
	 * @param dmr the domain-model reference to follow
	 *
	 * @return the referenced objects
	 */
	List<? extends EObject> get(EObject owner, VDomainModelReference dmr) {
		try {
			@SuppressWarnings("unchecked")
			IObservableList<? extends EObject> result = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableList(dmr, owner);

			// We have to have EObjects, which means an EReference
			if (result == null || !(result.getElementType() instanceof EReference)) {
				result = Observables.emptyObservableList();
			}
			return result;
		} catch (final DatabindingFailedException e) {
			Activator.log(e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean evaluateChangedValues(EObject domainModel,
		Map<EStructuralFeature.Setting, Object> possibleNewValues) {

		// First, partition the proposed changes into
		// - changes to the set of objects that I iterate over (subjects)
		// - changes to the features of my subjects
		final List<Collection<? extends EObject>> proposedNewSubjects = new ArrayList<Collection<? extends EObject>>();
		final Map<EStructuralFeature.Setting, Object> proposedChangesToSubjects = new HashMap<EStructuralFeature.Setting, Object>();

		for (final Map.Entry<EStructuralFeature.Setting, Object> next : possibleNewValues.entrySet()) {
			final EStructuralFeature.Setting setting = next.getKey();
			final Object proposedValue = next.getValue();

			if (setting.getEObject() == domainModel) {
				// Changing the set of objects to iterate
				@SuppressWarnings("unchecked")
				final List<? extends EObject> newValues = (List<? extends EObject>) next.getValue();
				proposedNewSubjects.add(newValues);
			} else {
				// Change features of an existing subject
				proposedChangesToSubjects.put(setting, proposedValue);
			}
		}

		// Now, evaluate the two partitions. Within each, evaluate the disjuction
		// but between the partitions it's a conjuction
		boolean result = true;
		for (final Collection<? extends EObject> newValues : proposedNewSubjects) {
			result = doEvaluate(newValues);
			if (result) {
				break;
			}
		}
		if (result) {
			for (final Map.Entry<EStructuralFeature.Setting, Object> next : proposedChangesToSubjects.entrySet()) {
				final EStructuralFeature.Setting setting = next.getKey();
				final Object proposedValue = next.getValue();

				result = getItemCondition().evaluateChangedValues(setting.getEObject(),
					Collections.singletonMap(setting, proposedValue));

				if (!result && getQuantifier() == Quantifier.ALL) {
					break; // Short-circuit
				}
				if (result && getQuantifier() == Quantifier.ANY) {
					break; // Short-circuit
				}
			}
		}

		return result;
	}

} // IterateConditionImpl
