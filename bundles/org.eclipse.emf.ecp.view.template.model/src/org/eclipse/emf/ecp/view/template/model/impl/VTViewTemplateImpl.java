/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.template.model.VTControlValidationTemplate;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTTemplatePackage;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl#getControlValidationConfiguration <em>
 * Control Validation Configuration</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl#getStyles <em>Styles</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.template.model.impl.VTViewTemplateImpl#getReferencedEcores <em>Referenced Ecores
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTViewTemplateImpl extends MinimalEObjectImpl.Container implements VTViewTemplate
{
	/**
	 * The cached value of the '{@link #getControlValidationConfiguration() <em>Control Validation Configuration</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getControlValidationConfiguration()
	 * @generated
	 * @ordered
	 */
	protected VTControlValidationTemplate controlValidationConfiguration;

	/**
	 * The cached value of the '{@link #getStyles() <em>Styles</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getStyles()
	 * @generated
	 * @ordered
	 */
	protected EList<VTStyle> styles;

	/**
	 * The cached value of the '{@link #getReferencedEcores() <em>Referenced Ecores</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReferencedEcores()
	 * @generated
	 * @ordered
	 */
	protected EList<String> referencedEcores;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTViewTemplateImpl()
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
		return VTTemplatePackage.Literals.VIEW_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VTControlValidationTemplate getControlValidationConfiguration()
	{
		return controlValidationConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetControlValidationConfiguration(
		VTControlValidationTemplate newControlValidationConfiguration, NotificationChain msgs)
	{
		final VTControlValidationTemplate oldControlValidationConfiguration = controlValidationConfiguration;
		controlValidationConfiguration = newControlValidationConfiguration;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION, oldControlValidationConfiguration,
				newControlValidationConfiguration);
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
	public void setControlValidationConfiguration(VTControlValidationTemplate newControlValidationConfiguration)
	{
		if (newControlValidationConfiguration != controlValidationConfiguration)
		{
			NotificationChain msgs = null;
			if (controlValidationConfiguration != null) {
				msgs = ((InternalEObject) controlValidationConfiguration).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION, null, msgs);
			}
			if (newControlValidationConfiguration != null) {
				msgs = ((InternalEObject) newControlValidationConfiguration).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION, null, msgs);
			}
			msgs = basicSetControlValidationConfiguration(newControlValidationConfiguration, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION, newControlValidationConfiguration,
				newControlValidationConfiguration));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VTStyle> getStyles()
	{
		if (styles == null)
		{
			styles = new EObjectContainmentEList<VTStyle>(VTStyle.class, this, VTTemplatePackage.VIEW_TEMPLATE__STYLES);
		}
		return styles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getReferencedEcores()
	{
		if (referencedEcores == null)
		{
			referencedEcores = new EDataTypeUniqueEList<String>(String.class, this,
				VTTemplatePackage.VIEW_TEMPLATE__REFERENCED_ECORES);
		}
		return referencedEcores;
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
		case VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION:
			return basicSetControlValidationConfiguration(null, msgs);
		case VTTemplatePackage.VIEW_TEMPLATE__STYLES:
			return ((InternalEList<?>) getStyles()).basicRemove(otherEnd, msgs);
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
		case VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION:
			return getControlValidationConfiguration();
		case VTTemplatePackage.VIEW_TEMPLATE__STYLES:
			return getStyles();
		case VTTemplatePackage.VIEW_TEMPLATE__REFERENCED_ECORES:
			return getReferencedEcores();
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
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION:
			setControlValidationConfiguration((VTControlValidationTemplate) newValue);
			return;
		case VTTemplatePackage.VIEW_TEMPLATE__STYLES:
			getStyles().clear();
			getStyles().addAll((Collection<? extends VTStyle>) newValue);
			return;
		case VTTemplatePackage.VIEW_TEMPLATE__REFERENCED_ECORES:
			getReferencedEcores().clear();
			getReferencedEcores().addAll((Collection<? extends String>) newValue);
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
		case VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION:
			setControlValidationConfiguration((VTControlValidationTemplate) null);
			return;
		case VTTemplatePackage.VIEW_TEMPLATE__STYLES:
			getStyles().clear();
			return;
		case VTTemplatePackage.VIEW_TEMPLATE__REFERENCED_ECORES:
			getReferencedEcores().clear();
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
		case VTTemplatePackage.VIEW_TEMPLATE__CONTROL_VALIDATION_CONFIGURATION:
			return controlValidationConfiguration != null;
		case VTTemplatePackage.VIEW_TEMPLATE__STYLES:
			return styles != null && !styles.isEmpty();
		case VTTemplatePackage.VIEW_TEMPLATE__REFERENCED_ECORES:
			return referencedEcores != null && !referencedEcores.isEmpty();
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
		result.append(" (referencedEcores: "); //$NON-NLS-1$
		result.append(referencedEcores);
		result.append(')');
		return result.toString();
	}

} // VTViewTemplateImpl
