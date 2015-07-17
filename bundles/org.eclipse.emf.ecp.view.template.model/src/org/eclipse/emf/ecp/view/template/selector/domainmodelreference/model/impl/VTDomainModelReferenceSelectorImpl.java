/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.internal.view.template.model.Activator;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainmodelreferencePackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedReport;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Model Reference Selector</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.impl.VTDomainModelReferenceSelectorImpl#getDomainModelReference
 * <em>Domain Model Reference</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTDomainModelReferenceSelectorImpl extends MinimalEObjectImpl.Container implements
	VTDomainModelReferenceSelector
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTDomainModelReferenceSelectorImpl()
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
		return VTDomainmodelreferencePackage.Literals.DOMAIN_MODEL_REFERENCE_SELECTOR;
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
				VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE,
				oldDomainModelReference, newDomainModelReference);
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
				msgs = ((InternalEObject) domainModelReference)
					.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE, null,
						msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference)
					.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE, null,
						msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE,
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE:
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE:
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
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE:
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
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
		case VTDomainmodelreferencePackage.DOMAIN_MODEL_REFERENCE_SELECTOR__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public double isApplicable(VElement vElement, ViewModelContext viewModelContext) {
		if (!VControl.class.isInstance(vElement)) {
			return NOT_APPLICABLE;
		}
		final VDomainModelReference controlDomainModelReference = VControl.class.cast(vElement)
			.getDomainModelReference();
		if (controlDomainModelReference == null) {
			return NOT_APPLICABLE;
		}

		IObservableValue controlObservableValue;
		IObservableValue selectorObservableValue;
		try {
			controlObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(controlDomainModelReference, viewModelContext.getDomainModel());
			selectorObservableValue = Activator.getDefault().getEMFFormsDatabinding()
				.getObservableValue(getDomainModelReference(), viewModelContext.getDomainModel());
		} catch (final DatabindingFailedException ex) {
			Activator.getDefault().getReportService().report(new DatabindingFailedReport(ex));
			return NOT_APPLICABLE;
		}
		final EObject controlEObject = (EObject) ((IObserving) controlObservableValue).getObserved();
		final EStructuralFeature controlStructuralFeature = (EStructuralFeature) controlObservableValue.getValueType();
		final EObject selectorEObject = (EObject) ((IObserving) selectorObservableValue).getObserved();
		final EStructuralFeature selectorStructuralFeature = (EStructuralFeature) selectorObservableValue
			.getValueType();
		controlObservableValue.dispose();
		selectorObservableValue.dispose();

		final boolean equal = UniqueSetting.createSetting(selectorEObject, selectorStructuralFeature).equals(
			UniqueSetting.createSetting(controlEObject, controlStructuralFeature));
		if (!equal) {
			return NOT_APPLICABLE;
		}

		return 10;
	}

} // VTDomainModelReferenceSelectorImpl
