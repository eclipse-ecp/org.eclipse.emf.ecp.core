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
package org.eclipse.emf.ecp.view.spi.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Renderable</b></em>'.
 *
 * @since 1.2
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#getName <em>Name</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#getLabel <em>Label</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#isVisible <em>Visible</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#isEnabled <em>Enabled</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#isReadonly <em>Readonly</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#getDiagnostic <em>Diagnostic</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VElementImpl#getAttachments <em>Attachments</em>}</li>
 *        </ul>
 *        </p>
 *
 * @generated
 */
public abstract class VElementImpl extends EObjectImpl implements VElement
{
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
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDiagnostic() <em>Diagnostic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDiagnostic()
	 * @generated
	 * @ordered
	 */
	protected VDiagnostic diagnostic;

	/**
	 * The cached value of the '{@link #getAttachments() <em>Attachments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getAttachments()
	 * @generated
	 * @ordered
	 */
	protected EList<VAttachment> attachments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VElementImpl()
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
		return VViewPackage.Literals.ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName)
	{
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getLabel()
	{
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel)
	{
		final String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__LABEL, oldLabel, label));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setVisible(boolean newVisible)
	{
		final boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__VISIBLE, oldVisible, visible));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEnabled(boolean newEnabled)
	{
		final boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__ENABLED, oldEnabled, enabled));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isReadonly()
	{
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setReadonly(boolean newReadonly)
	{
		final boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__READONLY, oldReadonly, readonly));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDiagnostic getDiagnostic()
	{
		return diagnostic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetDiagnostic(VDiagnostic newDiagnostic, NotificationChain msgs)
	{
		final VDiagnostic oldDiagnostic = diagnostic;
		diagnostic = newDiagnostic;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VViewPackage.ELEMENT__DIAGNOSTIC, oldDiagnostic, newDiagnostic);
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
	public void setDiagnostic(VDiagnostic newDiagnostic)
	{
		if (newDiagnostic != diagnostic)
		{
			NotificationChain msgs = null;
			if (diagnostic != null) {
				msgs = ((InternalEObject) diagnostic).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VViewPackage.ELEMENT__DIAGNOSTIC, null, msgs);
			}
			if (newDiagnostic != null) {
				msgs = ((InternalEObject) newDiagnostic).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VViewPackage.ELEMENT__DIAGNOSTIC, null, msgs);
			}
			msgs = basicSetDiagnostic(newDiagnostic, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, VViewPackage.ELEMENT__DIAGNOSTIC, newDiagnostic,
				newDiagnostic));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VAttachment> getAttachments()
	{
		if (attachments == null)
		{
			attachments = new EObjectContainmentEList<VAttachment>(VAttachment.class, this,
				VViewPackage.ELEMENT__ATTACHMENTS);
		}
		return attachments;
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
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			return basicSetDiagnostic(null, msgs);
		case VViewPackage.ELEMENT__ATTACHMENTS:
			return ((InternalEList<?>) getAttachments()).basicRemove(otherEnd, msgs);
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
		case VViewPackage.ELEMENT__NAME:
			return getName();
		case VViewPackage.ELEMENT__LABEL:
			return getLabel();
		case VViewPackage.ELEMENT__VISIBLE:
			return isVisible();
		case VViewPackage.ELEMENT__ENABLED:
			return isEnabled();
		case VViewPackage.ELEMENT__READONLY:
			return isReadonly();
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			return getDiagnostic();
		case VViewPackage.ELEMENT__ATTACHMENTS:
			return getAttachments();
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
		case VViewPackage.ELEMENT__NAME:
			setName((String) newValue);
			return;
		case VViewPackage.ELEMENT__LABEL:
			setLabel((String) newValue);
			return;
		case VViewPackage.ELEMENT__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case VViewPackage.ELEMENT__ENABLED:
			setEnabled((Boolean) newValue);
			return;
		case VViewPackage.ELEMENT__READONLY:
			setReadonly((Boolean) newValue);
			return;
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			setDiagnostic((VDiagnostic) newValue);
			return;
		case VViewPackage.ELEMENT__ATTACHMENTS:
			getAttachments().clear();
			getAttachments().addAll((Collection<? extends VAttachment>) newValue);
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
		case VViewPackage.ELEMENT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case VViewPackage.ELEMENT__LABEL:
			setLabel(LABEL_EDEFAULT);
			return;
		case VViewPackage.ELEMENT__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case VViewPackage.ELEMENT__ENABLED:
			setEnabled(ENABLED_EDEFAULT);
			return;
		case VViewPackage.ELEMENT__READONLY:
			setReadonly(READONLY_EDEFAULT);
			return;
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			setDiagnostic((VDiagnostic) null);
			return;
		case VViewPackage.ELEMENT__ATTACHMENTS:
			getAttachments().clear();
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
		case VViewPackage.ELEMENT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case VViewPackage.ELEMENT__LABEL:
			return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
		case VViewPackage.ELEMENT__VISIBLE:
			return visible != VISIBLE_EDEFAULT;
		case VViewPackage.ELEMENT__ENABLED:
			return enabled != ENABLED_EDEFAULT;
		case VViewPackage.ELEMENT__READONLY:
			return readonly != READONLY_EDEFAULT;
		case VViewPackage.ELEMENT__DIAGNOSTIC:
			return diagnostic != null;
		case VViewPackage.ELEMENT__ATTACHMENTS:
			return attachments != null && !attachments.isEmpty();
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", visible: "); //$NON-NLS-1$
		result.append(visible);
		result.append(", enabled: "); //$NON-NLS-1$
		result.append(enabled);
		result.append(", readonly: "); //$NON-NLS-1$
		result.append(readonly);
		result.append(')');
		return result.toString();
	}

} // RenderableImpl
