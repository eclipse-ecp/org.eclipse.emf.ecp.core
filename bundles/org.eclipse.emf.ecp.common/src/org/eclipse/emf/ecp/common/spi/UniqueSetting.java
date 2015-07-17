/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Edgar Mueller - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * <p>
 * Convenience class for managing settings.
 * </p>
 * <p>
 * Provides an equals method that returns {@code true} if the {@link EObject} and the
 * {@link org.eclipse.emf.ecore.EStructuralFeature} are the same.
 * </p>
 *
 * @author emueller
 * @since 1.5
 */
public class UniqueSetting {

	private final EObject eObject;
	private final EStructuralFeature structuralFeature;

	/**
	 * Convenience method for creating a setting.
	 *
	 * @param eObject
	 *            an {@link EObject}
	 * @param attribute
	 *            an EAttribute of the given {@code eObject}
	 * @return the constructed setting
	 */
	public static UniqueSetting createSetting(EObject eObject, EStructuralFeature attribute) {
		return new UniqueSetting(eObject, attribute);
	}

	/**
	 * Convenience method for creating a setting.
	 *
	 * @param setting the {@link Setting} to wrap
	 * @return the constructed setting
	 *
	 */
	public static UniqueSetting createSetting(Setting setting) {
		return new UniqueSetting(setting.getEObject(), setting.getEStructuralFeature());
	}

	/**
	 * Constructor.
	 *
	 * @param eObject
	 *            an {@link EObject}
	 * @param attribute
	 *            an EAttribute of the given {@code eObject}
	 */
	protected UniqueSetting(EObject eObject, EStructuralFeature attribute) {
		this.eObject = eObject;
		structuralFeature = attribute;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (structuralFeature == null ? 0 : structuralFeature.hashCode());
		result = prime * result + (eObject == null ? 0 : eObject.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UniqueSetting other = (UniqueSetting) obj;
		if (structuralFeature == null) {
			if (other.structuralFeature != null) {
				return false;
			}
		} else if (!structuralFeature.equals(other.structuralFeature)) {
			return false;
		}
		if (eObject == null) {
			if (other.eObject != null) {
				return false;
			}
		} else if (!eObject.equals(other.eObject)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the {@link EObject}.
	 *
	 * @return the {@link EObject}
	 */
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * Returns the EAttribute of the {@link EObject}.
	 *
	 * @return the EAttribute
	 */
	public EStructuralFeature getEStructuralFeature() {
		return structuralFeature;
	}

	/**
	 * Return the {@link Setting} wrapped in this {@link UniqueSetting}.
	 *
	 * @return the wrapped {@link Setting}
	 */
	public Setting getSetting() {
		return ((InternalEObject) eObject).eSetting(structuralFeature);
	}
}
