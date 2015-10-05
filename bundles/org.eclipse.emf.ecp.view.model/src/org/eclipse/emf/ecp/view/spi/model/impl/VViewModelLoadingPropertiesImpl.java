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

import java.util.Map.Entry;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewModelLoadingProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model Properties</b></em>'.
 *
 * @since 1.7
 *        <!-- end-user-doc -->
 *        <p>
 *        The following features are implemented:
 *        </p>
 *        <ul>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl#getInheritableProperties
 *        <em>Inheritable Properties</em>}</li>
 *        <li>{@link org.eclipse.emf.ecp.view.spi.model.impl.VViewModelLoadingPropertiesImpl#getNonInheritableProperties
 *        <em>Non Inheritable Properties</em>}</li>
 *        </ul>
 *
 * @generated
 */
public class VViewModelLoadingPropertiesImpl extends EObjectImpl implements VViewModelLoadingProperties {
	/**
	 * The cached value of the '{@link #getInheritableProperties() <em>Inheritable Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getInheritableProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Object> inheritableProperties;

	/**
	 * The cached value of the '{@link #getNonInheritableProperties() <em>Non Inheritable Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getNonInheritableProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Object> nonInheritableProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected VViewModelLoadingPropertiesImpl() {
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
		return VViewPackage.Literals.VIEW_MODEL_LOADING_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMap<String, Object> getInheritableProperties() {
		if (inheritableProperties == null) {
			inheritableProperties = new EcoreEMap<String, Object>(VViewPackage.Literals.STRING_TO_OBJECT_MAP_ENTRY,
				VStringToObjectMapEntryImpl.class, this,
				VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES);
		}
		return inheritableProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMap<String, Object> getNonInheritableProperties() {
		if (nonInheritableProperties == null) {
			nonInheritableProperties = new EcoreEMap<String, Object>(VViewPackage.Literals.STRING_TO_OBJECT_MAP_ENTRY,
				VStringToObjectMapEntryImpl.class, this,
				VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES);
		}
		return nonInheritableProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES:
			return ((InternalEList<?>) getInheritableProperties()).basicRemove(otherEnd, msgs);
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES:
			return ((InternalEList<?>) getNonInheritableProperties()).basicRemove(otherEnd, msgs);
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES:
			if (coreType) {
				return getInheritableProperties();
			}
			return getInheritableProperties().map();

		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES:
			if (coreType) {
				return getNonInheritableProperties();
			}
			return getNonInheritableProperties().map();

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
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES:
			((EStructuralFeature.Setting) getInheritableProperties()).set(newValue);
			return;
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES:
			((EStructuralFeature.Setting) getNonInheritableProperties()).set(newValue);
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
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES:
			getInheritableProperties().clear();
			return;
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES:
			getNonInheritableProperties().clear();
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
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__INHERITABLE_PROPERTIES:
			return inheritableProperties != null && !inheritableProperties.isEmpty();
		case VViewPackage.VIEW_MODEL_LOADING_PROPERTIES__NON_INHERITABLE_PROPERTIES:
			return nonInheritableProperties != null && !nonInheritableProperties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean containsKey(String key) {
		return getInheritableProperties().containsKey(key) || getNonInheritableProperties().containsKey(key);
	}

	@Override
	public Object get(String key) {
		if (getInheritableProperties().containsKey(key)) {
			return getInheritableProperties().get(key);
		}
		if (getNonInheritableProperties().containsKey(key)) {
			return getNonInheritableProperties().get(key);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.model.VViewModelProperties#inherit()
	 */
	@Override
	public VViewModelProperties inherit() {
		final VViewModelLoadingProperties properties = VViewFactory.eINSTANCE.createViewModelLoadingProperties();
		for (final Entry<String, Object> entry : getInheritableProperties().entrySet()) {
			properties.getInheritableProperties().put(entry.getKey(), entry.getValue());
		}
		return properties;
	}

	@Override
	public Object addInheritableProperty(String key, Object value) {
		if (getNonInheritableProperties().containsKey(key)) {
			final Object oldValue = getNonInheritableProperties().get(key);
			getNonInheritableProperties().remove(key);
			getInheritableProperties().put(key, value);
			return oldValue;
		}
		return getInheritableProperties().put(key, value);
	}

	@Override
	public Object addNonInheritableProperty(String key, Object value) {
		if (getInheritableProperties().containsKey(key)) {
			final Object oldValue = getInheritableProperties().get(key);
			getInheritableProperties().remove(key);
			getNonInheritableProperties().put(key, value);
			return oldValue;
		}
		return getNonInheritableProperties().put(key, value);
	}

} // VViewModelPropertiesImpl
