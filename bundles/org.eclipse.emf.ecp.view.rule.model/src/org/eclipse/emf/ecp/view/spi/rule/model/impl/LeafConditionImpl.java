/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich GmbH - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.rule.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.rule.model.Activator;
import org.eclipse.emf.ecp.view.spi.rule.model.CompareType;
import org.eclipse.emf.ecp.view.spi.rule.model.LeafCondition;
import org.eclipse.emf.ecp.view.spi.rule.model.NotApplicableForEvaluationException;
import org.eclipse.emf.ecp.view.spi.rule.model.RulePackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Leaf Condition</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl#getExpectedValue <em>Expected Value
 *        </em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl#getDomainModelReference <em>Domain
 *        Model Reference</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl#getValueDomainModelReference
 *        <em>Value Domain Model Reference</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.rule.model.impl.LeafConditionImpl#getCompareType <em>Compare
 *        Type</em>}</li>
 *        </ul>
 *        </p>
 *
 * @generated
 */
public class LeafConditionImpl extends ConditionImpl implements LeafCondition {
	/**
	 * The default value of the '{@link #getExpectedValue() <em>Expected Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getExpectedValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object EXPECTED_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpectedValue() <em>Expected Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getExpectedValue()
	 * @generated
	 * @ordered
	 */
	protected Object expectedValue = EXPECTED_VALUE_EDEFAULT;

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
	 * The cached value of the '{@link #getValueDomainModelReference() <em>Value Domain Model Reference</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @see #getValueDomainModelReference()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference valueDomainModelReference;

	/**
	 * The default value of the '{@link #getCompareType() <em>Compare Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 *
	 * @see #getCompareType()
	 * @generated
	 * @ordered
	 */
	protected static final CompareType COMPARE_TYPE_EDEFAULT = CompareType.EQUALS;

	/**
	 * The cached value of the '{@link #getCompareType() <em>Compare Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 *
	 * @see #getCompareType()
	 * @generated
	 * @ordered
	 */
	protected CompareType compareType = COMPARE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected LeafConditionImpl() {
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
		return RulePackage.Literals.LEAF_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getExpectedValue() {
		return expectedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setExpectedValue(Object newExpectedValue) {
		final Object oldExpectedValue = expectedValue;
		expectedValue = newExpectedValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.LEAF_CONDITION__EXPECTED_VALUE,
				oldExpectedValue, expectedValue));
		}
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
				RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE, oldDomainModelReference, newDomainModelReference);
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
				msgs = ((InternalEObject) domainModelReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE,
				newDomainModelReference, newDomainModelReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VDomainModelReference getValueDomainModelReference() {
		return valueDomainModelReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueDomainModelReference(VDomainModelReference newValueDomainModelReference,
		NotificationChain msgs) {
		final VDomainModelReference oldValueDomainModelReference = valueDomainModelReference;
		valueDomainModelReference = newValueDomainModelReference;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE, oldValueDomainModelReference,
				newValueDomainModelReference);
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
	 *
	 * @since 1.5
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValueDomainModelReference(VDomainModelReference newValueDomainModelReference) {
		if (newValueDomainModelReference != valueDomainModelReference) {
			NotificationChain msgs = null;
			if (valueDomainModelReference != null) {
				msgs = ((InternalEObject) valueDomainModelReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			if (newValueDomainModelReference != null) {
				msgs = ((InternalEObject) newValueDomainModelReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			msgs = basicSetValueDomainModelReference(newValueDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE, newValueDomainModelReference,
				newValueDomainModelReference));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CompareType getCompareType() {
		return compareType;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.11
	 *        <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCompareType(CompareType newCompareType) {
		final CompareType oldCompareType = compareType;
		compareType = newCompareType == null ? COMPARE_TYPE_EDEFAULT : newCompareType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.LEAF_CONDITION__COMPARE_TYPE,
				oldCompareType, compareType));
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
		case RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE:
			return basicSetDomainModelReference(null, msgs);
		case RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE:
			return basicSetValueDomainModelReference(null, msgs);
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
		case RulePackage.LEAF_CONDITION__EXPECTED_VALUE:
			return getExpectedValue();
		case RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE:
			return getDomainModelReference();
		case RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE:
			return getValueDomainModelReference();
		case RulePackage.LEAF_CONDITION__COMPARE_TYPE:
			return getCompareType();
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
		case RulePackage.LEAF_CONDITION__EXPECTED_VALUE:
			setExpectedValue(newValue);
			return;
		case RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) newValue);
			return;
		case RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE:
			setValueDomainModelReference((VDomainModelReference) newValue);
			return;
		case RulePackage.LEAF_CONDITION__COMPARE_TYPE:
			setCompareType((CompareType) newValue);
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
		case RulePackage.LEAF_CONDITION__EXPECTED_VALUE:
			setExpectedValue(EXPECTED_VALUE_EDEFAULT);
			return;
		case RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE:
			setDomainModelReference((VDomainModelReference) null);
			return;
		case RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE:
			setValueDomainModelReference((VDomainModelReference) null);
			return;
		case RulePackage.LEAF_CONDITION__COMPARE_TYPE:
			setCompareType(COMPARE_TYPE_EDEFAULT);
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
		case RulePackage.LEAF_CONDITION__EXPECTED_VALUE:
			return EXPECTED_VALUE_EDEFAULT == null ? expectedValue != null : !EXPECTED_VALUE_EDEFAULT
				.equals(expectedValue);
		case RulePackage.LEAF_CONDITION__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		case RulePackage.LEAF_CONDITION__VALUE_DOMAIN_MODEL_REFERENCE:
			return valueDomainModelReference != null;
		case RulePackage.LEAF_CONDITION__COMPARE_TYPE:
			return compareType != COMPARE_TYPE_EDEFAULT;
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
		result.append(" (expectedValue: "); //$NON-NLS-1$
		result.append(expectedValue);
		result.append(", compareType: "); //$NON-NLS-1$
		result.append(compareType);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Condition#evaluate(org.eclipse.emf.ecore.EObject)
	 * @since 1.9
	 */
	@Override
	public boolean evaluate(EObject domainModel) {
		final LeafConditionSettingIterator settingIterator = new LeafConditionSettingIterator(this, domainModel, false);
		boolean result = false;
		final Object expectedValue = getExpectedValue();
		while (settingIterator.hasNext()) {
			try {
				final Setting setting = settingIterator.next();
				result |= doEvaluate(setting.getEObject(), setting.getEStructuralFeature(), expectedValue, false, null,
					getCompareType());
			} catch (final NotApplicableForEvaluationException e) {
				continue;
			}
		}
		settingIterator.dispose();

		return result;
	}

	private static boolean doEvaluate(EObject parent, EStructuralFeature feature, Object expectedValue,
		boolean useNewValue, Object newValue, CompareType compareType) throws NotApplicableForEvaluationException {

		final EClass attributeClass = feature.getEContainingClass();
		if (!attributeClass.isInstance(parent)) {
			throw new NotApplicableForEvaluationException();
		}
		Object value;
		if (!useNewValue) {
			value = parent.eGet(feature);
		} else {
			value = newValue;
		}
		boolean result;
		if (!feature.isMany()) {
			if (expectedValue == null) {
				result = value == null;
			} else if (EcorePackage.eINSTANCE.getEEnum().isInstance(feature.getEType())) {
				result = expectedValue.equals(Enumerator.class.cast(value).getLiteral());
			} else {
				result = expectedValue.equals(value);
			}

		} else {
			// EMF API
			@SuppressWarnings("unchecked")
			final List<Object> objects = (List<Object>) value;
			result = objects.contains(expectedValue);
		}

		if (compareType == CompareType.EQUALS) {
			return result;
		} else if (compareType == CompareType.NOT_EQUALS) {
			return !result;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.rule.model.Condition#evaluateChangedValues(org.eclipse.emf.ecore.EObject,
	 *      java.util.Map)
	 * @since 1.9
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluateChangedValues(EObject domainModel, Map<Setting, Object> possibleNewValues) {
		boolean result = false;
		final Object expectedValue = getExpectedValue();

		for (final Setting setting : possibleNewValues.keySet()) {
			if (EReference.class.isInstance(setting.getEStructuralFeature())) {
				final List<EObject> newEObjects = new ArrayList<EObject>();
				if (setting.getEStructuralFeature().isMany()) {
					newEObjects.addAll((Collection<? extends EObject>) possibleNewValues.get(setting));
				} else {
					newEObjects.add((EObject) possibleNewValues.get(setting));
				}
				for (final EObject domain : newEObjects) {
					if (getValueDomainModelReference() == null) {
						continue;
					}
					IObservableValue observableValue;
					try {
						observableValue = Activator.getDefault().getEMFFormsDatabinding()
							.getObservableValue(getValueDomainModelReference(), domain);
					} catch (final DatabindingFailedException ex) {
						Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
						continue;
					}
					final EObject eObject = (EObject) ((IObserving) observableValue).getObserved();
					final EStructuralFeature structuralFeature = (EStructuralFeature) observableValue.getValueType();
					observableValue.dispose();

					try {
						result |= doEvaluate(eObject, structuralFeature, expectedValue, true,
							eObject.eGet(structuralFeature, true), getCompareType());
					} catch (final NotApplicableForEvaluationException ex) {
						continue;
					}
				}
			} else {
				try {
					result |= doEvaluate(setting.getEObject(), setting.getEStructuralFeature(), expectedValue, true,
						possibleNewValues.get(setting), getCompareType());
				} catch (final NotApplicableForEvaluationException e) {
					continue;
				}
			}
		}

		return result;
	}

} // LeafConditionImpl
