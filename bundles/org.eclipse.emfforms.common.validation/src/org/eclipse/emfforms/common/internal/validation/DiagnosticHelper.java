/*******************************************************************************
 * Copyright (c) 2017-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 * Christian W. Damus - bug 545686
 ******************************************************************************/
package org.eclipse.emfforms.common.internal.validation;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * Helper class for validation handling.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 */
public final class DiagnosticHelper {

	private DiagnosticHelper() {

	}

	/**
	 * Returns the first {@link EStructuralFeature} in the given list.
	 *
	 * @param data the list to search for a {@link EStructuralFeature}
	 * @return the found {@link EStructuralFeature}, null if no {@link EStructuralFeature} is found
	 */
	public static EStructuralFeature getEStructuralFeature(List<?> data) {
		// Exclude first object for cases when we validate an EStructuralFeature.
		if (data == null || data.size() < 2) {
			return null;
		}

		// The usual case is that the structural feature is the second object.
		final Object usual = data.get(1);
		if (usual instanceof EStructuralFeature) {
			return (EStructuralFeature) usual;
		}

		if (data.size() > 2) {
			for (final Object object : data.subList(2, data.size())) {
				if (EStructuralFeature.class.isInstance(object)) {
					return EStructuralFeature.class.cast(object);
				}
			}
		}

		return null;
	}

	/**
	 * Returns the first {@link InternalEObject} in the given list.
	 *
	 * @param data the list to search for a {@link InternalEObject}
	 * @return the found {@link InternalEObject}, null if no {@link InternalEObject} is found
	 */
	public static InternalEObject getFirstInternalEObject(List<?> data) {
		if (data == null || data.isEmpty()) {
			return null;
		}

		// The usual case is that the subject of the problem is the first object
		final Object usual = data.get(0);
		if (usual instanceof InternalEObject) {
			return (InternalEObject) usual;
		}

		if (data.size() > 1) {
			for (final Object object : data) {
				if (InternalEObject.class.isInstance(object)) {
					return InternalEObject.class.cast(object);
				}
			}
		}

		return null;
	}

	/**
	 * Verify if the given {@link Diagnostic} contains at least one {@link EStructuralFeature} or a
	 * {@link InternalEObject}.
	 *
	 * @param diagnostic the {@link Diagnostic} to check
	 * @return true if the {@link Diagnostic} contains at least one {@link EStructuralFeature} or
	 *         {@link InternalEObject}, false otherwise
	 */
	public static boolean checkDiagnosticData(Diagnostic diagnostic) {
		final List<?> data = diagnostic.getData();
		if (data.size() < 2) {
			return false;
		}
		if (getFirstInternalEObject(data) == null) {
			return false;
		}
		if (getEStructuralFeature(data) == null) {
			return false;
		}
		return true;
	}

}
