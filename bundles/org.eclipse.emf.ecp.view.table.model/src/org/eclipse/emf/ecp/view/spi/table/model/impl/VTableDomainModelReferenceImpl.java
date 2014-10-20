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
package org.eclipse.emf.ecp.view.spi.table.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.DomainModelReferenceChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Model Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl#getColumnDomainModelReferences
 * <em>Column Domain Model References</em>}</li>
 * <li>{@link org.eclipse.emf.ecp.view.spi.table.model.impl.VTableDomainModelReferenceImpl#getDomainModelReference <em>
 * Domain Model Reference</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VTableDomainModelReferenceImpl extends VFeaturePathDomainModelReferenceImpl implements
	VTableDomainModelReference
{
	/**
	 * The cached value of the '{@link #getColumnDomainModelReferences() <em>Column Domain Model References</em>}'
	 * containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getColumnDomainModelReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<VDomainModelReference> columnDomainModelReferences;

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

	private final List<VDomainModelReference> resolvedColumns = new ArrayList<VDomainModelReference>();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VTableDomainModelReferenceImpl()
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
		return VTablePackage.Literals.TABLE_DOMAIN_MODEL_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<VDomainModelReference> getColumnDomainModelReferences()
	{
		if (columnDomainModelReferences == null)
		{
			columnDomainModelReferences = new EObjectContainmentEList<VDomainModelReference>(
				VDomainModelReference.class, this,
				VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES);
		}
		return columnDomainModelReferences;
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
				VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE, oldDomainModelReference,
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
	public void setDomainModelReference(VDomainModelReference newDomainModelReference)
	{
		if (newDomainModelReference != domainModelReference)
		{
			NotificationChain msgs = null;
			if (domainModelReference != null) {
				msgs = ((InternalEObject) domainModelReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			if (newDomainModelReference != null) {
				msgs = ((InternalEObject) newDomainModelReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE, null, msgs);
			}
			msgs = basicSetDomainModelReference(newDomainModelReference, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		}
		else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
				VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE, newDomainModelReference,
				newDomainModelReference));
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
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			return ((InternalEList<?>) getColumnDomainModelReferences()).basicRemove(otherEnd, msgs);
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
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
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			return getColumnDomainModelReferences();
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			getColumnDomainModelReferences().clear();
			getColumnDomainModelReferences().addAll((Collection<? extends VDomainModelReference>) newValue);
			return;
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
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
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			getColumnDomainModelReferences().clear();
			return;
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
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
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			return columnDomainModelReferences != null && !columnDomainModelReferences.isEmpty();
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__DOMAIN_MODEL_REFERENCE:
			return domainModelReference != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#init(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean init(final EObject object) {
		final boolean init = super.init(object);
		eAdapters().add(new AdapterImpl() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification msg) {
				super.notifyChanged(msg);
				if (msg.getFeature() == VTablePackage.eINSTANCE
					.getTableDomainModelReference_ColumnDomainModelReferences()
					&& msg.getEventType() == Notification.ADD) {
					resolve(object, false);
				}
			}

		});
		return init;
	}

	@Override
	public Iterator<Setting> getIterator() {
		if (lastResolvedEObject == null || leftReferences == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}
		EStructuralFeature feature;
		if (getDomainModelReference() == null) {
			feature = getDomainModelEFeature();
		} else {
			feature = getDomainModelReference().getIterator().next().getEStructuralFeature();
		}

		final EStructuralFeature listFeature = feature;

		if (!EReference.class.isInstance(listFeature)) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}

		if (!lastResolvedEObject.eClass().getEAllStructuralFeatures().contains(listFeature)) {
			final List<Setting> settings = Collections.emptyList();
			return settings.iterator();
		}
		final int numElems = 1 + resolvedColumns.size();
		return new Iterator<EStructuralFeature.Setting>() {
			int returnedElements = 0;

			@Override
			public boolean hasNext() {

				return numElems > returnedElements;

			}

			@Override
			public Setting next() {
				Setting result = null;
				if (returnedElements == 0) {
					result = ((InternalEObject) lastResolvedEObject).eSetting(listFeature);
				}
				else {
					final Iterator<Setting> iterator = resolvedColumns.get(returnedElements - 1).getIterator();
					if (iterator.hasNext()) {
						result = iterator.next();
					} else {
						result = null;
					}
				}
				returnedElements++;
				return result;
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
	 * @see org.eclipse.emf.ecp.view.spi.model.impl.VFeaturePathDomainModelReferenceImpl#getEStructuralFeatureIterator()
	 */
	@Override
	public Iterator<EStructuralFeature> getEStructuralFeatureIterator() {
		EStructuralFeature feature;
		if (getDomainModelReference() == null) {
			feature = getDomainModelEFeature();
		} else {
			final Iterator<EStructuralFeature> iterator = getDomainModelReference().getEStructuralFeatureIterator();
			if (!iterator.hasNext()) {
				return Collections.<EStructuralFeature> emptyList().iterator();
			}
			feature = iterator.next();
		}
		final EStructuralFeature listFeature = feature;

		if (!EReference.class.isInstance(feature)) {
			final List<EStructuralFeature> features = Collections.emptyList();
			return features.iterator();
		}
		return new Iterator<EStructuralFeature>() {
			private int counter = 0;

			@Override
			public boolean hasNext() {
				return getColumnDomainModelReferences().size() + 1 > counter;

			}

			@Override
			public EStructuralFeature next() {
				EStructuralFeature result = null;
				if (0 == counter) {
					result = listFeature;
				}
				else {
					result = getColumnDomainModelReferences().get(counter - 1).getEStructuralFeatureIterator().next();

				}
				counter++;
				return result;
			}

			@Override
			public void remove() {
			}
		};

	}

	@Override
	protected boolean resolve(EObject domainModel, boolean createMissingChildren) {
		boolean result = false;
		EStructuralFeature feature;
		if (getDomainModelReference() == null) {
			result = super.resolve(domainModel, createMissingChildren);
			feature = getDomainModelEFeature();
		} else {
			result = getDomainModelReference().init(domainModel);
			final Setting dmrSetting = getDomainModelReference().getIterator().next();
			lastResolvedEObject = dmrSetting.getEObject();
			feature = dmrSetting.getEStructuralFeature();
			leftReferences = new ArrayList<EReference>();
		}
		resolvedColumns.clear();
		if (result == false) {
			return result;
		}

		if (lastResolvedEObject == null) {
			return result;
		}
		@SuppressWarnings("unchecked")
		final List<EObject> eObjects = (List<EObject>) lastResolvedEObject.eGet(feature);
		for (final EObject eObject : eObjects) {
			for (final VDomainModelReference columnReference : getColumnDomainModelReferences()) {
				final VDomainModelReference copy = EcoreUtil.copy(columnReference);
				copy.init(eObject);
				resolvedColumns.add(copy);
			}
		}
		return result;
	}

	@Override
	public void notifyChange(ModelChangeNotification notification) {
		if (getDomainModelEFeature() != null) {
			super.notifyChange(notification);
			return;
		}

		if (notification.getRawNotification().isTouch()) {
			return;
		}
		final Iterator<EStructuralFeature> structuralFeatureIterator = getDomainModelReference()
			.getEStructuralFeatureIterator();
		while (structuralFeatureIterator.hasNext()) {
			final EStructuralFeature feature = structuralFeatureIterator.next();
			if (feature.equals(notification.getStructuralFeature())) {
				cleanDiagnostic(feature.equals(notification.getStructuralFeature()), notification);
				resolve(rootEObject, false);
				for (final DomainModelReferenceChangeListener listener : getChangeListener()) {
					listener.notifyChange();
				}
			}
		}
	}
} // VTableDomainModelReferenceImpl
