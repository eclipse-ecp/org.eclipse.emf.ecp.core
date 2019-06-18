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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.view.spi.editor.controls.EStructuralFeatureSelectionValidator;

/**
 * Validates that the selected {@link EStructuralFeature} is a multi {@link EReference}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSelectionValidator implements EStructuralFeatureSelectionValidator {

	/** The message for a failed validation. */
	static final String ERROR_MESSAGE = "A multi reference must be selected."; //$NON-NLS-1$

	@Override
	public String isValid(EStructuralFeature structuralFeature) {
		if (structuralFeature != null && EReference.class.isInstance(structuralFeature)
			&& structuralFeature.isMany()) {
			return null;
		}
		return ERROR_MESSAGE;
	}

}
