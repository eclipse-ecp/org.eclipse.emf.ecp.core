/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.databinding.mapping;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;

/**
 * A {@link MappedSetting} is a {@link Setting} whose {@link EStructuralFeature} is a map which has {@link EClass
 * EClasses} as its keys. Additionally, the setting contains an {@link EClass} that defines which value of the map is
 * the value of the setting.
 *
 * @author Lucas Koehler
 *
 */
public class MappedSetting implements Setting {

	private final EObject eObject;
	private final EStructuralFeature eStructuralFeature;
	private final EClass mappedEClass;
	private final EMap<EClass, Object> map;

	/**
	 * Creates a new {@link MappedSetting}.
	 *
	 * @param eObject The {@link EObject} holding a map
	 * @param eStructuralFeature The specific feature holding a map
	 * @param mappedEClass The EClass that defines which map entry's value is the value of this {@link Setting}.
	 * @throws IllegalMapTypeException If the structural feature does not describe a valid map.
	 */
	@SuppressWarnings("unchecked")
	public MappedSetting(EObject eObject, EStructuralFeature eStructuralFeature, EClass mappedEClass)
		throws IllegalMapTypeException {
		checkMapType(eStructuralFeature);

		this.eObject = eObject;
		this.eStructuralFeature = eStructuralFeature;
		this.mappedEClass = mappedEClass;
		map = (EMap<EClass, Object>) eObject.eGet(eStructuralFeature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEObject()
	 */
	@Override
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#getEStructuralFeature()
	 */
	@Override
	public EStructuralFeature getEStructuralFeature() {
		return eStructuralFeature;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#get(boolean)
	 */
	@Override
	public Object get(boolean resolve) {
		// Always resolved => ignore the parameter
		return map.get(mappedEClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#set(java.lang.Object)
	 */
	@Override
	public void set(Object newValue) {
		map.put(mappedEClass, newValue);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#isSet()
	 */
	@Override
	public boolean isSet() {
		return map.get(mappedEClass) != null ? true : false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EStructuralFeature.Setting#unset()
	 */
	@Override
	public void unset() {
		map.removeKey(mappedEClass);
	}

	/**
	 * Checks whether the given structural feature references a proper map.
	 *
	 * @param structuralFeature The feature to check
	 * @throws IllegalMapTypeException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		checkStructuralFeature(structuralFeature);

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature("key"); //$NON-NLS-1$
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature("value"); //$NON-NLS-1$
		if (keyFeature == null || valueFeature == null) {
			throw new IllegalMapTypeException(
				"The given structural feature must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the given structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new IllegalMapTypeException(
				"The keys of the map referenced by the given structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
	}

	/**
	 * Checks basic required properties of the given {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to check
	 * @throws IllegalMapTypeException if something's wrong with the feature
	 */
	private void checkStructuralFeature(EStructuralFeature structuralFeature) throws IllegalMapTypeException {
		if (structuralFeature.getEType() == null) {
			throw new IllegalMapTypeException(
				"The EType of the given structural feature was null."); //$NON-NLS-1$
		}
		if (structuralFeature.getEType().getInstanceClassName() == null) {
			throw new IllegalMapTypeException(
				"The InstanceClassName of the given structural feature's EType was null."); //$NON-NLS-1$
		}
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new IllegalMapTypeException(
				"The given structural feature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new IllegalMapTypeException(
				"The given structural feature must reference a map."); //$NON-NLS-1$
		}
	}
}
