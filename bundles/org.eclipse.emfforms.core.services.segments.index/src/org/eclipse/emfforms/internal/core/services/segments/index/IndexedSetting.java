/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.index;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;

/**
 * An {@link IndexedSetting} is a {@link Setting} whose {@link EStructuralFeature} is a multi reference or attribute
 * that is indexed. This means the value of the Setting is the Object at the {@link IndexedSetting IndexedSetting's}
 * index in the list referenced by the structural feature. In a "standard" Setting, the whole list would be the settings
 * value.
 *
 * @author Lucas Koehler
 *
 */
public class IndexedSetting implements Setting {

	private final EObject eObject;
	private final EStructuralFeature eStructuralFeature;
	private final int index;
	private final EList<Object> list;

	/**
	 * Creates a new {@link IndexedSetting}.
	 *
	 * @param eObject The {@link EObject} holding a list
	 * @param eStructuralFeature The specific feature holding the list
	 * @param index The index that defines which list item is the value of this {@link Setting}
	 */
	@SuppressWarnings("unchecked")
	public IndexedSetting(EObject eObject, EStructuralFeature eStructuralFeature, int index) {
		Assert.create(eObject).notNull();
		Assert.create(eStructuralFeature).notNull();

		if (!eStructuralFeature.isMany()) {
			throw new IllegalArgumentException(
				String.format("The given EStructuralFeature does not support multiple elements. The feature was %s", //$NON-NLS-1$
					eStructuralFeature));
		}

		this.eObject = eObject;
		this.eStructuralFeature = eStructuralFeature;
		this.index = index;
		list = (EList<Object>) eObject.eGet(eStructuralFeature);
	}

	@Override
	public EObject getEObject() {
		return eObject;
	}

	@Override
	public EStructuralFeature getEStructuralFeature() {
		return eStructuralFeature;
	}

	@Override
	public Object get(boolean resolve) {
		// Always resolved => ignore the parameter
		return list.get(index);
	}

	@Override
	public void set(Object newValue) {
		list.set(index, newValue);
	}

	@Override
	public boolean isSet() {
		return list.get(index) != null;
	}

	@Override
	public void unset() {
		throw new UnsupportedOperationException("It is not possible to unset a value in an IndexedSetting."); //$NON-NLS-1$
	}

	/**
	 * Returns the index of this {@link IndexedSetting}. The index defines the list index of this setting's value: The
	 * list is resolved from the holding {@link EObject} and the {@link EStructuralFeature} of this
	 * {@link IndexedSetting}.
	 *
	 * @return The list index
	 */
	public int getIndex() {
		return index;
	}
}
