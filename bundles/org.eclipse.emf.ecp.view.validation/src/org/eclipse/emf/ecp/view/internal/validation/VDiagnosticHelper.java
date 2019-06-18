/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.validation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDiagnostic;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;

/**
 * This class compares to {@link VDiagnostic} elements for equality.
 *
 * @author Eugen Neufeld
 *
 */
public final class VDiagnosticHelper {

	private VDiagnosticHelper() {

	}

	/**
	 * Compares two {@link VDiagnostic VDiagnostics} with each other.
	 *
	 * @param vDiagnostic1 the first {@link VDiagnostic} to compare
	 * @param vDiagnostic2 the second {@link VDiagnostic} to compare
	 * @return true if both {@link VDiagnostic VDiagnostics} are equal
	 */
	public static boolean isEqual(VDiagnostic vDiagnostic1, VDiagnostic vDiagnostic2) {

		if (vDiagnostic1 == null && vDiagnostic2 == null) {
			return true;
		}
		if (vDiagnostic1 == null) {
			return false;
		}
		if (vDiagnostic2 == null) {
			return false;
		}

		final boolean arePropertiesEqual = arePropertiesEqual(vDiagnostic1, vDiagnostic2);
		if (!arePropertiesEqual) {
			return false;
		}

		for (int i = 0; i < vDiagnostic1.getDiagnostics().size(); i++) {
			final Diagnostic diagnostic1 = (Diagnostic) vDiagnostic1.getDiagnostics().get(i);
			final Diagnostic diagnostic2 = (Diagnostic) vDiagnostic2.getDiagnostics().get(i);
			if (!areUnderlyingDiagnosticsEqual(diagnostic1, diagnostic2)) {
				return false;
			}
		}

		return true;
	}

	private static boolean arePropertiesEqual(VDiagnostic vDiagnostic1, VDiagnostic vDiagnostic2) {
		if (vDiagnostic1.getHighestSeverity() != vDiagnostic2.getHighestSeverity()) {
			return false;
		}
		if (!vDiagnostic1.getMessage().equals(vDiagnostic2.getMessage())) {
			return false;
		}
		if (vDiagnostic1.getDiagnostics().size() != vDiagnostic2.getDiagnostics().size()) {
			return false;
		}
		return true;
	}

	/**
	 * @param vDiagnostic1
	 * @param vDiagnostic2
	 */
	private static boolean areUnderlyingDiagnosticsEqual(Diagnostic diagnostic1, Diagnostic diagnostic2) {

		// TODO: How can these cases ever apply? We already did check these since VDiagnostic#getHighestSeverity()
		// and VDiagnostic#getMessage() test the underlying diagnostic
		// FIXME: test order
		if (diagnostic1.getSeverity() != diagnostic2.getSeverity()) {
			return false;
		}

		if (diagnostic1.getData().size() != diagnostic2.getData().size()) {
			return false;
		}

		if (diagnostic1.getChildren().size() != diagnostic2.getChildren().size()) {
			return false;
		}

		for (int j = 0; j < diagnostic1.getData().size(); j++) {
			final Object data1 = diagnostic1.getData().get(j);
			final Object data2 = diagnostic2.getData().get(j);

			if (!data1.equals(data2)) {
				return false;
			}
		}

		for (int i = 0; i < diagnostic1.getChildren().size(); i++) {
			if (!areUnderlyingDiagnosticsEqual(diagnostic1.getChildren().get(i), diagnostic2.getChildren().get(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Analysis a {@link VDiagnostic} and merges all Diagnostic pointing to the same EObject.
	 *
	 * @param value the {@link VDiagnostic} that should be cleaned
	 * @return a cleaned {@link VDiagnostic}
	 */
	public static VDiagnostic clean(VDiagnostic value) {
		final VDiagnostic result = VViewFactory.eINSTANCE.createDiagnostic();
		final Map<EObject, Diagnostic> map = new LinkedHashMap<EObject, Diagnostic>();
		for (final Object object : value.getDiagnostics()) {
			final Diagnostic diagnostic = (Diagnostic) object;
			if (diagnostic.getData() == null || diagnostic.getData().size() == 0) {
				continue;
			}
			final EObject eObject = (EObject) diagnostic.getData().get(0);
			if (map.containsKey(eObject)) {
				((DiagnosticChain) map.get(eObject)).merge(diagnostic);
			} else {
				map.put(eObject, diagnostic);
			}
		}
		result.getDiagnostics().addAll(map.values());
		return result;
	}
}
