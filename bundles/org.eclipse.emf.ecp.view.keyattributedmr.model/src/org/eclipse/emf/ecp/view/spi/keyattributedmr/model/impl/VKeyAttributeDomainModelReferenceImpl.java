/**
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 */
package org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyattributedmrPackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Key Attribute Domain Model Reference</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl#getKeyDMR
 * <em>Key DMR</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl#getKeyValue
 * <em>Key Value</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.keyattributedmr.model.impl.VKeyAttributeDomainModelReferenceImpl#getValueDMR
 * <em>Value DMR</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VKeyAttributeDomainModelReferenceImpl extends
	VFeaturePathDomainModelReferenceImpl implements
	VKeyAttributeDomainModelReference {
	/**
	 * The cached value of the '{@link #getKeyDMR() <em>Key DMR</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKeyDMR()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference keyDMR;
	/**
	 * The default value of the '{@link #getKeyValue() <em>Key Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKeyValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object KEY_VALUE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getKeyValue() <em>Key Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKeyValue()
	 * @generated
	 * @ordered
	 */
	protected Object keyValue = KEY_VALUE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getValueDMR() <em>Value DMR</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getValueDMR()
	 * @generated
	 * @ordered
	 */
	protected VDomainModelReference valueDMR;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VKeyAttributeDomainModelReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VKeyattributedmrPackage.Literals.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getKeyDMR()
	{
		return keyDMR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetKeyDMR(VDomainModelReference newKeyDMR, NotificationChain msgs)
	{
		final VDomainModelReference oldKeyDMR = keyDMR;
		keyDMR = newKeyDMR;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR, oldKeyDMR, newKeyDMR);
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
	public void setKeyDMR(VDomainModelReference newKeyDMR)
	{
		if (newKeyDMR != keyDMR)
		{
			NotificationChain msgs = null;
			if (keyDMR != null) {
				msgs = ((InternalEObject) keyDMR).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR, null, msgs);
			}
			if (newKeyDMR != null) {
				msgs = ((InternalEObject) newKeyDMR).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR, null, msgs);
			}
			msgs = basicSetKeyDMR(newKeyDMR, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR, newKeyDMR, newKeyDMR));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object getKeyValue()
	{
		return keyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setKeyValue(Object newKeyValue)
	{
		final Object oldKeyValue = keyValue;
		keyValue = newKeyValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE, oldKeyValue, keyValue));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public VDomainModelReference getValueDMR()
	{
		return valueDMR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetValueDMR(VDomainModelReference newValueDMR, NotificationChain msgs)
	{
		final VDomainModelReference oldValueDMR = valueDMR;
		valueDMR = newValueDMR;
		if (eNotificationRequired())
		{
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR, oldValueDMR, newValueDMR);
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
	public void setValueDMR(VDomainModelReference newValueDMR)
	{
		if (newValueDMR != valueDMR)
		{
			NotificationChain msgs = null;
			if (valueDMR != null) {
				msgs = ((InternalEObject) valueDMR).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR, null, msgs);
			}
			if (newValueDMR != null) {
				msgs = ((InternalEObject) newValueDMR).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR, null, msgs);
			}
			msgs = basicSetValueDMR(newValueDMR, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR, newValueDMR, newValueDMR));
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
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
			return basicSetKeyDMR(null, msgs);
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			return basicSetValueDMR(null, msgs);
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
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
			return getKeyDMR();
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE:
			return getKeyValue();
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			return getValueDMR();
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
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
			setKeyDMR((VDomainModelReference) newValue);
			return;
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE:
			setKeyValue(newValue);
			return;
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			setValueDMR((VDomainModelReference) newValue);
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
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
			setKeyDMR((VDomainModelReference) null);
			return;
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE:
			setKeyValue(KEY_VALUE_EDEFAULT);
			return;
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			setValueDMR((VDomainModelReference) null);
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
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_DMR:
			return keyDMR != null;
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__KEY_VALUE:
			return KEY_VALUE_EDEFAULT == null ? keyValue != null : !KEY_VALUE_EDEFAULT.equals(keyValue);
		case VKeyattributedmrPackage.KEY_ATTRIBUTE_DOMAIN_MODEL_REFERENCE__VALUE_DMR:
			return valueDMR != null;
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
		result.append(" (keyValue: "); //$NON-NLS-1$
		result.append(keyValue);
		result.append(')');
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(EObject eObject) {
		if (getKeyValue() == null) {
			return false;
		}
		final boolean init = super.init(eObject);
		final List<EObject> list = (List<EObject>) lastResolvedEObject.eGet(getDomainModelEFeature());
		EObject foundChild = null;
		if (list.size() == 0) {
			final EReference eReference = (EReference) getDomainModelEFeature();
			foundChild = EcoreUtil.create(eReference.getEReferenceType());
			list.add(foundChild);
			getKeyDMR().init(foundChild);
			final Setting keySetting = getKeyDMR().getIterator().next();
			Object keyValue = getKeyValue();
			if (EcorePackage.eINSTANCE.getEEnum().isInstance(keySetting.getEStructuralFeature().getEType())) {
				final EEnum eenum = EEnum.class.cast(keySetting.getEStructuralFeature().getEType());
				keyValue = eenum.getEEnumLiteralByLiteral((String) keyValue).getInstance();

			}
			keySetting.set(keyValue);
		}
		else {
			for (final EObject child : list) {
				getKeyDMR().init(child);
				final Setting setting = getKeyDMR().getIterator().next();
				if (EcorePackage.eINSTANCE.getEEnum().isInstance(setting.getEStructuralFeature().getEType())) {
					if (getKeyValue().equals(Enumerator.class.cast(setting.get(true)).getLiteral())) {
						foundChild = child;
						break;
					}

				}
				else if (getKeyValue().equals(setting.get(true))) {
					foundChild = child;
					break;
				}
			}
		}

		final boolean valueDMR = getValueDMR().init(foundChild);
		return init && valueDMR;
	}

	@Override
	public Iterator<Setting> getIterator() {
		if (getValueDMR() == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}
		return getValueDMR().getIterator();
	}

	@Override
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		if (getValueDMR() == null) {
			final Set<EStructuralFeature> features = Collections.emptySet();
			return features.iterator();
		}
		return getValueDMR().getEStructuralFeatureIterator();
	}

} // VKeyAttributeDomainModelReferenceImpl
