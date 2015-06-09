/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomPackage;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.SettingPath;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.osgi.framework.Bundle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Model Reference</b></em>'.
 *
 * @since 1.3
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl#getChangeListener
 *        <em> Change Listener</em>}</li>
 *        <li>
 *        {@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl#getDomainModelReferences
 *        <em>Domain Model References</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl#getBundleName <em>
 *        Bundle Name</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl#getClassName <em>
 *        Class Name </em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.custom.model.impl.VCustomDomainModelReferenceImpl#isControlChecked
 *        <em> Control Checked</em>}</li>
 *        </ul>
 *        </p>
 *
 * @generated
 */
public class VCustomDomainModelReferenceImpl extends EObjectImpl implements VCustomDomainModelReference
{
	/**
	 * The cached value of the '{@link #getChangeListener() <em>Change Listener</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChangeListener()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainModelReferenceChangeListener> changeListener;

	/**
	 * The cached value of the '{@link #getDomainModelReferences() <em>Domain Model References</em>}' containment
	 * reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<VDomainModelReference> domainModelReferences;

	/**
	 * The default value of the '{@link #getBundleName() <em>Bundle Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBundleName()
	 * @generated
	 * @ordered
	 */
	protected static final String BUNDLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBundleName() <em>Bundle Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getBundleName()
	 * @generated
	 * @ordered
	 */
	protected String bundleName = BUNDLE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isControlChecked() <em>Control Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isControlChecked()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONTROL_CHECKED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isControlChecked() <em>Control Checked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isControlChecked()
	 * @generated
	 * @ordered
	 */
	protected boolean controlChecked = CONTROL_CHECKED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VCustomDomainModelReferenceImpl()
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
		return VCustomPackage.Literals.CUSTOM_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<DomainModelReferenceChangeListener> getChangeListener()
	{
		if (changeListener == null)
		{
			changeListener = new EDataTypeUniqueEList<DomainModelReferenceChangeListener>(
				DomainModelReferenceChangeListener.class, this,
				VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER);
		}
		return changeListener;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<VDomainModelReference> getDomainModelReferences()
	{
		if (domainModelReferences == null)
		{
			domainModelReferences = new EObjectContainmentEList<VDomainModelReference>(VDomainModelReference.class,
				this, VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES);
		}
		return domainModelReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getBundleName()
	{
		return bundleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setBundleName(String newBundleName)
	{
		final String oldBundleName = bundleName;
		bundleName = newBundleName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME, oldBundleName, bundleName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getClassName()
	{
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setClassName(String newClassName)
	{
		final String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME, oldClassName, className));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isControlChecked()
	{
		return controlChecked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setControlChecked(boolean newControlChecked)
	{
		final boolean oldControlChecked = controlChecked;
		controlChecked = newControlChecked;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED, oldControlChecked, controlChecked));
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
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
			return ((InternalEList<?>) getDomainModelReferences()).basicRemove(otherEnd, msgs);
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
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return getChangeListener();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
			return getDomainModelReferences();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME:
			return getBundleName();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME:
			return getClassName();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED:
			return isControlChecked();
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
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			getChangeListener().addAll((Collection<? extends DomainModelReferenceChangeListener>) newValue);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
			getDomainModelReferences().clear();
			getDomainModelReferences().addAll((Collection<? extends VDomainModelReference>) newValue);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME:
			setBundleName((String) newValue);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME:
			setClassName((String) newValue);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED:
			setControlChecked((Boolean) newValue);
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
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
			getDomainModelReferences().clear();
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME:
			setBundleName(BUNDLE_NAME_EDEFAULT);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME:
			setClassName(CLASS_NAME_EDEFAULT);
			return;
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED:
			setControlChecked(CONTROL_CHECKED_EDEFAULT);
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
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return changeListener != null && !changeListener.isEmpty();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCES:
			return domainModelReferences != null && !domainModelReferences.isEmpty();
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__BUNDLE_NAME:
			return BUNDLE_NAME_EDEFAULT == null ? bundleName != null : !BUNDLE_NAME_EDEFAULT.equals(bundleName);
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CLASS_NAME:
			return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
		case VCustomPackage.CUSTOM_DOMAIN_MODEL_REFERENCE__CONTROL_CHECKED:
			return controlChecked != CONTROL_CHECKED_EDEFAULT;
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
		result.append(" (changeListener: "); //$NON-NLS-1$
		result.append(changeListener);
		result.append(", bundleName: "); //$NON-NLS-1$
		result.append(bundleName);
		result.append(", className: "); //$NON-NLS-1$
		result.append(className);
		result.append(", controlChecked: "); //$NON-NLS-1$
		result.append(controlChecked);
		result.append(')');
		return result.toString();
	}

	private boolean resolve(EObject eObject) {
		if (getBundleName() == null || getClassName() == null) {
			return false;
		}
		final ECPHardcodedReferences customControl = loadObject(getBundleName(), getClassName());
		if (customControl == null) {
			return false;
		}
		if (!isControlChecked()) {
			// read stuff from control
			final Set<VDomainModelReference> controlReferences = new LinkedHashSet<VDomainModelReference>();
			controlReferences.addAll(customControl.getNeededDomainModelReferences());
			controlReferences.addAll(getDomainModelReferences());
			getDomainModelReferences().clear();
			getDomainModelReferences().addAll(controlReferences);
			setControlChecked(true);
		}
		// resolve references from control
		boolean result = true;
		for (final VDomainModelReference domainModelReference : getDomainModelReferences()) {
			result &= domainModelReference.init(eObject);
		}
		return result;
	}

	@Override
	public boolean init(EObject object) {
		return resolve(object);
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz) {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			new ClassNotFoundException(String.format(LocalizationServiceHelper.getString(
				VCustomDomainModelReferenceImpl.class, "BundleNotFound_ExceptionMessage"), clazz, bundleName)); //$NON-NLS-1$
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			if (!ECPHardcodedReferences.class.isAssignableFrom(loadClass)) {
				return null;
			}
			return ECPHardcodedReferences.class.cast(loadClass.newInstance());
		} catch (final ClassNotFoundException ex) {
			return null;
		} catch (final InstantiationException ex) {
			return null;
		} catch (final IllegalAccessException ex) {
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getIterator()
	 * @generated NOT
	 */
	@Override
	public Iterator<Setting> getIterator() {
		return new ExistingIteratorIterator<EStructuralFeature.Setting>() {
			@Override
			protected Iterator<Setting> getSubIterator(VDomainModelReference domainModelReference) {
				return domainModelReference.getIterator();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getEStructuralFeatureIterator()
	 * @generated NOT
	 */
	@Override
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		return new ExistingIteratorIterator<EStructuralFeature>() {
			@Override
			protected Iterator<EStructuralFeature> getSubIterator(VDomainModelReference domainModelReference) {
				return domainModelReference.getEStructuralFeatureIterator();
			}
		};
	}

	/**
	 * Private helper class to iterate over sub iterators.
	 *
	 * @author Eugen Neufeld
	 * @author jfaltermeier
	 *
	 * @param <T> the type to iterate over
	 */
	private abstract class ExistingIteratorIterator<T> implements Iterator<T> {

		private final Stack<Iterator<T>> subIterators = new Stack<Iterator<T>>();

		public ExistingIteratorIterator() {
			for (int i = getDomainModelReferences().size() - 1; i >= 0; i--) {
				final VDomainModelReference vDomainModelReference = getDomainModelReferences().get(i);
				final Iterator<T> subIterator = getSubIterator(vDomainModelReference);
				if (subIterator.hasNext()) {
					subIterators.push(subIterator);
				}
			}

		}

		@Override
		public boolean hasNext() {
			return !subIterators.isEmpty() && subIterators.peek().hasNext();
		}

		@Override
		public T next() {
			final T next = subIterators.peek().next();
			if (!subIterators.peek().hasNext()) {
				subIterators.pop();
			}
			return next;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		protected abstract Iterator<T> getSubIterator(VDomainModelReference domainModelReference);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getFullPathIterator()
	 */
	@Override
	public Iterator<SettingPath> getFullPathIterator() {
		return new ExistingIteratorIterator<SettingPath>() {
			@Override
			protected Iterator<SettingPath> getSubIterator(VDomainModelReference domainModelReference) {
				return domainModelReference.getFullPathIterator();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public void notifyChange(ModelChangeNotification notification) {
		for (final VDomainModelReference dmr : getDomainModelReferences()) {
			dmr.notifyChange(notification);
		}
	}

} // VCustomDomainModelReferenceImpl
