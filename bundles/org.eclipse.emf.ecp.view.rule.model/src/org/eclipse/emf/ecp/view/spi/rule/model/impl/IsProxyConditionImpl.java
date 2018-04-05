/**
 * Copyright (c) 2018 Christian W. Damus and others.
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
import org.eclipse.emf.ecp.view.spi.rule.model.IsProxyCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Is Proxy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.IsProxyConditionImpl#getDomainModelReference <em>Domain Model
 * Reference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IsProxyConditionImpl extends ConditionImpl implements IsProxyCondition {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IsProxyConditionImpl() {
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
		return RulePackage.Literals.IS_PROXY_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getDomainModelReference() {
		return domainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDomainModelReference(VDomainModelReference newDomainModelReference,
		NotificationChain msgs) {
		final VDomainModelReference oldDomainModelReference = domainModelReference;
		domainModelReference = newDomainModelReference;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE, oldDomainModelReference,
				newDomainModelReference);
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
	public void setDomainModelReference(VDomainModelReference newDomainModelReference) {
		if (newDomainModelReference != domainModelReference) {
			NotificationChain msgs = null;
			if (domainModelReference != null) {
				msgs = ((InternalEObject) domainModelReference).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(
				new ENotificationImpl(this, Notification.SET, RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE,
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE:
			return basicSetDomainModelReference(null, msgs);
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
		case RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE:
			return getDomainModelReference();
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
		case RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) newValue);
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
		case RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) null);
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
		case RulePackage.IS_PROXY_CONDITION__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean evaluate(EObject domainModel) {
		final VDomainModelReference path = getDomainModelReference();
		final Collection<? extends EObject> subjects = get(domainModel, path);
		return doEvaluate(subjects);
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
		if (dmr == null) {
			// Condition on the owner object, itself
			return Collections.singletonList(owner);
		}

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

	/**
	 * Evaluates me by iteration of the given {@code subjects}.
	 *
	 * @param subjects the objects over which to iterate my {@link #getItemCondition() condition}
	 * @return the result of the iteration
	 */
	protected boolean doEvaluate(Collection<? extends EObject> subjects) {
		boolean result = false;

		for (final EObject next : subjects) {
			result = next != null && next.eIsProxy();
			if (result) {
				break; // Short-circuit
			}
		}

		return result;
	}

	@Override
	public boolean evaluateChangedValues(EObject domainModel,
		Map<EStructuralFeature.Setting, Object> possibleNewValues) {

		if (getDomainModelReference() == null) {
			// Condition on the domain object, itself
			return domainModel.eIsProxy();
		}

		// We are only interested in changes to the set of objects that I iterate over (subjects)
		final List<Collection<? extends EObject>> proposedNewSubjects = new ArrayList<Collection<? extends EObject>>();

		for (final Map.Entry<EStructuralFeature.Setting, Object> next : possibleNewValues.entrySet()) {
			final EStructuralFeature.Setting setting = next.getKey();

			if (setting.getEObject() == domainModel) {
				// Changing the set of objects to iterate
				@SuppressWarnings("unchecked")
				final List<? extends EObject> newValues = (List<? extends EObject>) next.getValue();
				proposedNewSubjects.add(newValues);
			} // else it's a change to features of an existing subject
		}

		boolean result = true;
		for (final Collection<? extends EObject> newValues : proposedNewSubjects) {
			result = doEvaluate(newValues);
			if (result) {
				break;
			}
		}

		return result;
	}

} // IsProxyConditionImpl
