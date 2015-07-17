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
package org.eclipse.emf.ecp.view.template.style.mandatory.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryPackage;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl#isHighliteMandatoryFields
 * <em>Highlite Mandatory Fields</em>}</li>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.mandatory.model.impl.VTMandatoryStylePropertyImpl#getMandatoryMarker
 * <em>Mandatory Marker</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTMandatoryStylePropertyImpl extends MinimalEObjectImpl.Container implements VTMandatoryStyleProperty
{
	/**
	 * The default value of the '{@link #isHighliteMandatoryFields() <em>Highlite Mandatory Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isHighliteMandatoryFields()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIGHLITE_MANDATORY_FIELDS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isHighliteMandatoryFields() <em>Highlite Mandatory Fields</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isHighliteMandatoryFields()
	 * @generated
	 * @ordered
	 */
	protected boolean highliteMandatoryFields = HIGHLITE_MANDATORY_FIELDS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMandatoryMarker() <em>Mandatory Marker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMandatoryMarker()
	 * @generated
	 * @ordered
	 */
	protected static final String MANDATORY_MARKER_EDEFAULT = "*"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getMandatoryMarker() <em>Mandatory Marker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getMandatoryMarker()
	 * @generated
	 * @ordered
	 */
	protected String mandatoryMarker = MANDATORY_MARKER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTMandatoryStylePropertyImpl()
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
		return VTMandatoryPackage.Literals.MANDATORY_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isHighliteMandatoryFields()
	{
		return highliteMandatoryFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setHighliteMandatoryFields(boolean newHighliteMandatoryFields)
	{
		final boolean oldHighliteMandatoryFields = highliteMandatoryFields;
		highliteMandatoryFields = newHighliteMandatoryFields;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS, oldHighliteMandatoryFields,
				highliteMandatoryFields));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getMandatoryMarker()
	{
		return mandatoryMarker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMandatoryMarker(String newMandatoryMarker)
	{
		final String oldMandatoryMarker = mandatoryMarker;
		mandatoryMarker = newMandatoryMarker;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER, oldMandatoryMarker, mandatoryMarker));
		}
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
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS:
			return isHighliteMandatoryFields();
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER:
			return getMandatoryMarker();
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
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS:
			setHighliteMandatoryFields((Boolean) newValue);
			return;
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER:
			setMandatoryMarker((String) newValue);
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
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS:
			setHighliteMandatoryFields(HIGHLITE_MANDATORY_FIELDS_EDEFAULT);
			return;
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER:
			setMandatoryMarker(MANDATORY_MARKER_EDEFAULT);
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
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__HIGHLITE_MANDATORY_FIELDS:
			return highliteMandatoryFields != HIGHLITE_MANDATORY_FIELDS_EDEFAULT;
		case VTMandatoryPackage.MANDATORY_STYLE_PROPERTY__MANDATORY_MARKER:
			return MANDATORY_MARKER_EDEFAULT == null ? mandatoryMarker != null : !MANDATORY_MARKER_EDEFAULT
				.equals(mandatoryMarker);
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
	public String toString()
	{
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuffer result = new StringBuffer(super.toString());
		result.append(" (highliteMandatoryFields: "); //$NON-NLS-1$
		result.append(highliteMandatoryFields);
		result.append(", mandatoryMarker: "); //$NON-NLS-1$
		result.append(mandatoryMarker);
		result.append(')');
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.template.model.VTStyleProperty#equalStyles(org.eclipse.emf.ecp.view.template.model.VTStyleProperty)
	 */
	@Override
	public boolean equalStyles(VTStyleProperty styleProperty) {
		if (VTMandatoryStyleProperty.class.equals(styleProperty.getClass())) {
			return false;
		}
		final VTMandatoryStyleProperty mandatoryStyleProperty = VTMandatoryStyleProperty.class.cast(styleProperty);
		return isHighliteMandatoryFields() == mandatoryStyleProperty.isHighliteMandatoryFields()
			&& getMandatoryMarker() == mandatoryStyleProperty.getMandatoryMarker();
	}

} // VTMandatoryStylePropertyImpl
