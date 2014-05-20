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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
		case VTablePackage.TABLE_DOMAIN_MODEL_REFERENCE__COLUMN_DOMAIN_MODEL_REFERENCES:
			return ((InternalEList<?>) getColumnDomainModelReferences()).basicRemove(otherEnd, msgs);
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
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Iterator<Setting> getIterator() {
		if (lastResolvedEObject == null || leftReferences == null) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}
		if (!EReference.class.isInstance(getDomainModelEFeature())) {
			final Set<Setting> settings = Collections.emptySet();
			return settings.iterator();
		}

		if (!lastResolvedEObject.eClass().getEAllStructuralFeatures().contains(getDomainModelEFeature())) {
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
					result = ((InternalEObject) lastResolvedEObject).eSetting(getDomainModelEFeature());
				}
				else {
					result = resolvedColumns.get(returnedElements - 1).getIterator().next();
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
		final EStructuralFeature structuralFeature = getDomainModelEFeature();

		if (!EReference.class.isInstance(structuralFeature)) {
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
					result = getDomainModelEFeature();
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
		final boolean result = super.resolve(domainModel, createMissingChildren);
		resolvedColumns.clear();
		if (lastResolvedEObject == null) {
			return result;
		}
		@SuppressWarnings("unchecked")
		final List<EObject> eObjects = (List<EObject>) lastResolvedEObject.eGet(getDomainModelEFeature());
		for (final EObject eObject : eObjects) {
			for (final VDomainModelReference columnReference : getColumnDomainModelReferences()) {
				final VDomainModelReference copy = EcoreUtil.copy(columnReference);
				copy.init(eObject);
				resolvedColumns.add(copy);
			}
		}
		return result;
	}
} // VTableDomainModelReferenceImpl
