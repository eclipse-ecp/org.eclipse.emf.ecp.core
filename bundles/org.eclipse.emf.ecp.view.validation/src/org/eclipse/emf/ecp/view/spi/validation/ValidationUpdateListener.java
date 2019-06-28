/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * An alternative validation listener that is most useful in applications that
 * {@linkplain ValidationServiceConstants#PROPAGATION_LIMIT_KEY throttle problem reporting},
 * to be notified of all problems found by validation, regardless of limits imposed by
 * the presentation in the editor.
 *
 * @since 1.22
 */
public interface ValidationUpdateListener {

	/**
	 * <p>
	 * Notifies the listener of updates to the model validation state.
	 * This is an incremental update: it provides validation status of settings
	 * in model objects, including results that explicitly indicate absence
	 * of problems (indicating that problems previously reported are resolved).
	 * Every diagnostic in the collection has at least two elements in the
	 * {@link Diagnostic#getData() data} list, of which the first two are:
	 * </p>
	 * <p>
	 * <ol>
	 * <li>the {@link EObject} that owns the feature that was validated</li>
	 * <li>the {@link EStructuralFeature} of the object that was validated</li>
	 * </ol>
	 * </p>
	 * <p>
	 * If any feature of any object that was previously reported as having
	 * problems no longer has problems, then an {@link Diagnostic#OK} diagnostic
	 * will be present for that setting. Otherwise, there may be one or more
	 * problem diagnostics for that setting. In any case, if the validation
	 * state of a setting is changed, then this collection contains the entire
	 * current validation state of that setting.
	 * </p>
	 *
	 * @param diagnostics the current validation problems in settings (features
	 *            of objects) that were validated
	 */
	void validationUpdated(Collection<Diagnostic> diagnostics);

	/**
	 * Register a {@code listener} with the given validation service, if it supports it.
	 *
	 * @param validationService a validation service
	 * @param listener the listener to register
	 * @return {@code true} if the {@code listener} was registered; {@code false}, otherwise
	 */
	static boolean register(ValidationService validationService, ValidationUpdateListener listener) {
		if (validationService instanceof IncrementalValidationService) {
			((IncrementalValidationService) validationService).registerValidationUpdateListener(listener);
			return true;
		}

		return false;
	}

	/**
	 * De-register a {@code listener} from the given validation service.
	 *
	 * @param validationService a validation service
	 * @param listener the listener to deregister
	 */
	static void deregister(ValidationService validationService, ValidationUpdateListener listener) {
		if (validationService instanceof IncrementalValidationService) {
			((IncrementalValidationService) validationService).deregisterValidationUpdateListener(listener);
		}
	}

}
