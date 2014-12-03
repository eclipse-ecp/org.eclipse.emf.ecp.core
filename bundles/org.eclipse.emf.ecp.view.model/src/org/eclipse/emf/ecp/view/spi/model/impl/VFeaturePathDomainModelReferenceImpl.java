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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.SettingPath;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model rootEObject '<em><b>VFeature Path Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getChangeListener <em>Change
 * Listener</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEFeature <em>
 * Domain Model EFeature</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getDomainModelEReferencePath
 * <em>Domain Model EReference Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VFeaturePathDomainModelReferenceImpl extends EObjectImpl implements
VFeaturePathDomainModelReference
{
	/**
	 * The cached value of the '{@link #getChangeListener() <em>Change Listener</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @see #getChangeListener()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainModelReferenceChangeListener> changeListener;
	/**
	 * The cached value of the '{@link #getDomainModelEFeature() <em>Domain Model EFeature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelEFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature domainModelEFeature;
	/**
	 * The cached value of the '{@link #getDomainModelEReferencePath() <em>Domain Model EReference Path</em>}' reference
	 * list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getDomainModelEReferencePath()
	 * @generated
	 * @ordered
	 */
	protected EList<EReference> domainModelEReferencePath;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VFeaturePathDomainModelReferenceImpl()
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
		return VViewPackage.Literals.FEATURE_PATH_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 *
	 * @since 1.3
	 *        <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DomainModelReferenceChangeListener> getChangeListener()
	{
		if (changeListener == null)
		{
			changeListener = new EDataTypeUniqueEList<DomainModelReferenceChangeListener>(
				DomainModelReferenceChangeListener.class, this,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER);
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
	public EStructuralFeature getDomainModelEFeature()
	{
		if (domainModelEFeature != null && domainModelEFeature.eIsProxy())
		{
			final InternalEObject oldDomainModelEFeature = (InternalEObject) domainModelEFeature;
			domainModelEFeature = (EStructuralFeature) eResolveProxy(oldDomainModelEFeature);
			if (domainModelEFeature != oldDomainModelEFeature)
			{
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE,
						oldDomainModelEFeature, domainModelEFeature));
				}
			}
		}
		return domainModelEFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EStructuralFeature basicGetDomainModelEFeature()
	{
		return domainModelEFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDomainModelEFeature(EStructuralFeature newDomainModelEFeature)
	{
		final EStructuralFeature oldDomainModelEFeature = domainModelEFeature;
		domainModelEFeature = newDomainModelEFeature;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE, oldDomainModelEFeature,
				domainModelEFeature));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<EReference> getDomainModelEReferencePath()
	{
		if (domainModelEReferencePath == null)
		{
			domainModelEReferencePath = new EObjectResolvingEList<EReference>(EReference.class, this,
				VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH);
		}
		return domainModelEReferencePath;
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return getChangeListener();
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			if (resolve) {
				return getDomainModelEFeature();
			}
			return basicGetDomainModelEFeature();
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			return getDomainModelEReferencePath();
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			getChangeListener().addAll((Collection<? extends DomainModelReferenceChangeListener>) newValue);
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			setDomainModelEFeature((EStructuralFeature) newValue);
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			getDomainModelEReferencePath().clear();
			getDomainModelEReferencePath().addAll((Collection<? extends EReference>) newValue);
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			getChangeListener().clear();
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			setDomainModelEFeature((EStructuralFeature) null);
			return;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			getDomainModelEReferencePath().clear();
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
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__CHANGE_LISTENER:
			return changeListener != null && !changeListener.isEmpty();
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EFEATURE:
			return domainModelEFeature != null;
		case VViewPackage.FEATURE_PATH_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_EREFERENCE_PATH:
			return domainModelEReferencePath != null && !domainModelEReferencePath.isEmpty();
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
		result.append(')');
		return result.toString();
	}

	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.emf.ecp.view.model.VDomainModelReference#resolve(org.eclipse.emf.ecore.EObject)
	// */
	// public boolean resolve(final EObject domainModel) {
	// final EStructuralFeature domainModelEFeatureValue = getDomainModelEFeature();
	// if (domainModel == null || domainModelEFeatureValue == null) {
	// return false;
	// }
	// EObject parent = domainModel;
	// for (final EReference eReference : getDomainModelEReferencePath()) {
	// if (eReference.isMany()) {
	// return false;
	// }
	// if (!eReference.eContainer().equals(parent.eClass())) {
	// return false;
	// }
	// EObject child = (EObject) parent.eGet(eReference);
	// if (child == null) {
	// child = EcoreUtil.create(eReference.getEReferenceType());
	// parent.eSet(eReference, child);
	// }
	// parent = child;
	// }
	// setDomainModel(parent);
	// setModelFeature(domainModelEFeatureValue);
	// return true;
	// }

	private final List<Setting> resolvedSetting = new ArrayList<Setting>();

	/**
	 * @since 1.3
	 */
	protected boolean resolve(EObject domainModel, boolean createMissingChildren) {
		final EStructuralFeature domainModelEFeatureValue = getDomainModelEFeature();
		if (domainModel == null || domainModelEFeatureValue == null) {
			return false;
		}

		EObject currentResolvedEObject = domainModel;
		final ArrayList<EReference> currentLeftReferences = new ArrayList<EReference>(getDomainModelEReferencePath());
		for (final EReference eReference : getDomainModelEReferencePath()) {
			if (!currentResolvedEObject.eClass().getEAllReferences().contains(eReference)) {
				return false;
			}
			resolvedSetting.add(InternalEObject.class.cast(currentResolvedEObject).eSetting(eReference));
			if (eReference.isMany()) {
				break;
			}
			if (!eReference.getEContainingClass().isInstance(currentResolvedEObject)) {
				return false;
			}
			EObject child = (EObject) currentResolvedEObject.eGet(eReference);
			if (createMissingChildren && child == null) {
				if (!eReference.getEReferenceType().isAbstract() && !eReference.getEReferenceType().isInterface()) {
					child = EcoreUtil.create(eReference.getEReferenceType());
				} else if (currentLeftReferences.size() == 1
					&& !domainModelEFeatureValue.getEContainingClass().isAbstract()
					&& !domainModelEFeatureValue.getEContainingClass().isInterface()) {
					child = EcoreUtil.create(domainModelEFeatureValue.getEContainingClass());
				}
				currentResolvedEObject.eSet(eReference, child);
			}
			if (child == null) {
				break;
			}
			currentResolvedEObject = child;
			currentLeftReferences.remove(eReference);
		}
		// FIXME this check is currently needed to ignore resolve tries with a wrong EObject
		// workaround block start
		// if (lastResolvedEObject != null && currentLeftReferences.isEmpty()
		// && !currentResolvedEObject.eClass().getEAllStructuralFeatures().contains(getDomainModelEFeature())) {
		// return false;
		// }
		// workaround block end
		lastResolvedEObject = currentResolvedEObject;
		leftReferences = currentLeftReferences;
		// resolvedSetting.add(InternalEObject.class.cast(lastResolvedEObject).eSetting(getDomainModelEFeature()));
		if (!leftReferences.isEmpty()) {
			return false;
		}
		return true;
	}

	protected List<EReference> leftReferences;
	protected EObject lastResolvedEObject;
	/**
	 * @since 1.5
	 */
	protected EObject rootEObject;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getIterator()
	 */
	@Override
	public Iterator<Setting> getIterator() {
		if (lastResolvedEObject == null || leftReferences == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}

		return new DomainModelReferenceIterator(leftReferences, lastResolvedEObject, getDomainModelEFeature());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getEStructuralFeatureIterator()
	 */
	@Override
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		return new Iterator<EStructuralFeature>() {

			private int counter = 1;

			@Override
			public boolean hasNext() {
				return counter == 1 && getDomainModelEFeature() != null;
			}

			@Override
			public EStructuralFeature next() {
				if (counter != 1) {
					throw new NoSuchElementException(
						"There is only one EStructuralFeature in this VFeaturePathDomainModelReference."); //$NON-NLS-1$
				}
				counter--;
				return getDomainModelEFeature();
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub

			}
		};
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#init(org.eclipse.emf.ecore.EObject)
	 * @since 1.3
	 */
	@Override
	public boolean init(final EObject object) {
		rootEObject = object;

		return resolve(object, true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VDomainModelReference#getFullPathIterator()
	 * @since 1.3
	 */
	@Override
	public Iterator<SettingPath> getFullPathIterator() {
		return new Iterator<SettingPath>() {

			int leftWaysToCheck = 1;

			@Override
			public boolean hasNext() {
				return leftWaysToCheck != 0;
			}

			@Override
			public SettingPath next() {
				leftWaysToCheck--;
				return new SettingPath() {

					@Override
					public Iterator<Setting> getPath() {
						return new Iterator<EStructuralFeature.Setting>() {

							private final Iterator<Setting> pathIterator = resolvedSetting.iterator();
							private final Iterator<Setting> childIterator = getIterator();

							@Override
							public void remove() {
								// TODO Auto-generated method stub

							}

							@Override
							public Setting next() {
								if (pathIterator.hasNext()) {
									return pathIterator.next();
								}
								if (childIterator != null && childIterator.hasNext()) {
									return childIterator.next();
								}
								return null;
							}

							@Override
							public boolean hasNext() {
								if (pathIterator.hasNext()) {
									return true;
								}
								// get the iterator once
								// if (childIterator == null) {
								// childIterator = getIterator();
								// }
								return childIterator.hasNext();
							}
						};
					}
				};
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub

			}
		};
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.ModelChangeListener#notifyChange(org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 * @since 1.3
	 */
	@Override
	public void notifyChange(ModelChangeNotification notification) {
		if (notification.getRawNotification().isTouch()) {
			return;
		}
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return;
		}
		if (getDomainModelEReferencePath().contains(notification.getStructuralFeature())
			||
			getDomainModelEFeature().equals(notification.getStructuralFeature())) { //

			cleanDiagnostic(getDomainModelEFeature().equals(notification.getStructuralFeature()), notification);

			resolve(rootEObject, false);
			final List<DomainModelReferenceChangeListener> copyOfChangeListeners = new ArrayList<DomainModelReferenceChangeListener>(
				getChangeListener());
			for (final DomainModelReferenceChangeListener listener : copyOfChangeListeners) {
				listener.notifyChange();
			}
		}
	}

	/**
	 * @param notification
	 * @since 1.5
	 */
	protected void cleanDiagnostic(boolean baseFeatureChanged, ModelChangeNotification notification) {
		if (!(eContainer() instanceof VControl)) {
			return;
		}
		final VControl vControl = (VControl) eContainer();
		if (vControl.getDiagnostic() == null) {
			return;
		}
		if (!baseFeatureChanged) {
			vControl.setDiagnostic(null);
		} else if (!notification.getStructuralFeature().isMany()) {
			vControl.setDiagnostic(null);
		} else if (Notification.REMOVE == notification.getRawNotification().getEventType()) {
			final EObject oldValue = (EObject) notification.getRawNotification().getOldValue();
			final Set<Diagnostic> toDelete = new LinkedHashSet<Diagnostic>();
			for (final Object diagnosticObject : vControl.getDiagnostic().getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) diagnosticObject;
				EObject diagnosticDataObject = (EObject) diagnostic.getData().get(0);
				while (diagnosticDataObject != oldValue && diagnosticDataObject != null) {
					diagnosticDataObject = diagnosticDataObject.eContainer();
				}
				if (diagnosticDataObject == oldValue) {
					toDelete.add(diagnostic);
				}
			}
			for (final Diagnostic diagnostic : toDelete) {
				vControl.getDiagnostic().getDiagnostics().remove(diagnostic);
			}
		} else if (Notification.REMOVE_MANY == notification.getRawNotification().getEventType()) {
			final Collection<?> oldValue = (Collection<?>) notification.getRawNotification().getOldValue();
			final Set<Diagnostic> toDelete = new LinkedHashSet<Diagnostic>();
			for (final Object diagnosticObject : vControl.getDiagnostic().getDiagnostics()) {
				final Diagnostic diagnostic = (Diagnostic) diagnosticObject;
				EObject diagnosticDataObject = (EObject) diagnostic.getData().get(0);
				while (!oldValue.contains(diagnosticDataObject) && diagnosticDataObject != null) {
					diagnosticDataObject = diagnosticDataObject.eContainer();
				}

				if (oldValue.contains(diagnosticDataObject)) {
					toDelete.add(diagnostic);
				}
			}
			for (final Diagnostic diagnostic : toDelete) {
				vControl.getDiagnostic().getDiagnostics().remove(diagnostic);
			}
		}
	}
} // VFeaturePathDomainModelReferenceImpl
