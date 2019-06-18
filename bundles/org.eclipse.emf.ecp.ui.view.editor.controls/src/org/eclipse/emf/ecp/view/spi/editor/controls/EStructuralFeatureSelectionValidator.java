/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.spi.editor.controls;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Used to check whether a selected {@link EStructuralFeature} is a valid selection in a wizard.
 * <p>
 * Implemented by clients to specify the validation criteria.
 *
 * @author Lucas Koehler
 * @since 1.20
 *
 */
@FunctionalInterface
public interface EStructuralFeatureSelectionValidator {

	/**
	 * Checks whether the given {@link EStructuralFeature} constitutes a valid selection.
	 *
	 * @param structuralFeature The selected {@link EStructuralFeature} to be validated
	 * @return The error message, or <code>null</code> if the selection is valid
	 */
	String isValid(EStructuralFeature structuralFeature);
}
