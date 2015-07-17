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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementPackage;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Style Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl.VTTextControlEnablementStylePropertyImpl#isRenderDisableAsEditable
 * <em>Render Disable As Editable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VTTextControlEnablementStylePropertyImpl extends MinimalEObjectImpl.Container implements
	VTTextControlEnablementStyleProperty {
	/**
	 * The default value of the '{@link #isRenderDisableAsEditable() <em>Render Disable As Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isRenderDisableAsEditable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RENDER_DISABLE_AS_EDITABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRenderDisableAsEditable() <em>Render Disable As Editable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isRenderDisableAsEditable()
	 * @generated
	 * @ordered
	 */
	protected boolean renderDisableAsEditable = RENDER_DISABLE_AS_EDITABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VTTextControlEnablementStylePropertyImpl() {
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
		return VTTextControlEnablementPackage.Literals.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isRenderDisableAsEditable() {
		return renderDisableAsEditable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRenderDisableAsEditable(boolean newRenderDisableAsEditable) {
		final boolean oldRenderDisableAsEditable = renderDisableAsEditable;
		renderDisableAsEditable = newRenderDisableAsEditable;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE,
				oldRenderDisableAsEditable, renderDisableAsEditable));
		}
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
		case VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE:
			return isRenderDisableAsEditable();
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
		case VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE:
			setRenderDisableAsEditable((Boolean) newValue);
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
		case VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE:
			setRenderDisableAsEditable(RENDER_DISABLE_AS_EDITABLE_EDEFAULT);
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
		case VTTextControlEnablementPackage.TEXT_CONTROL_ENABLEMENT_STYLE_PROPERTY__RENDER_DISABLE_AS_EDITABLE:
			return renderDisableAsEditable != RENDER_DISABLE_AS_EDITABLE_EDEFAULT;
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
		result.append(" (RenderDisableAsEditable: "); //$NON-NLS-1$
		result.append(renderDisableAsEditable);
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
		if (!VTTextControlEnablementStyleProperty.class.isInstance(styleProperty)) {
			return false;
		}
		final VTTextControlEnablementStyleProperty property = VTTextControlEnablementStyleProperty.class
			.cast(styleProperty);
		if (isRenderDisableAsEditable() != property.isRenderDisableAsEditable()) {
			return false;
		}
		return true;
	}

} // VTTextControlEnablementStylePropertyImpl
