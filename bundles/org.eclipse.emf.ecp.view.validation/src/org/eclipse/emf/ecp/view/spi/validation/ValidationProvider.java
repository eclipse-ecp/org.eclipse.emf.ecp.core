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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 552715
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.validation;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emfforms.common.spi.validation.Validator;

/**
 * <p>
 * The ValidationService calls the providers after the validation with EMF.
 * By providing an own provider, one can extend the EMF validation by providing additional validation rules.
 * </p>
 * <p>
 * As of the 1.23 release, for validation that requires the current view model context, consider using a subclass of the
 * nested {@link ContextSensitive} class.
 * </p>
 *
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public interface ValidationProvider extends Validator {

	/**
	 * Initialize me in the view model {@code context} of the {@link ValidationService} to which I have been added.
	 * Note that I could be added to validation services in more than one context.
	 *
	 * @param context the context of the {@link ValidationService} to which I have been added
	 *
	 * @since 1.23
	 */
	default void setContext(ViewModelContext context) {
		// Nothing to do
	}

	/**
	 * Notify me that I have been removed from the {@link ValidationService} in the given {@code context}.
	 * Note that I may still be used in validation services in other contexts.
	 *
	 * @param context the context of the {@link ValidationService} from which I have been removed
	 *
	 * @since 1.23
	 */
	default void unsetContext(ViewModelContext context) {
		// Nothing to do
	}

	/**
	 * Validate an {@code object} in a view model {@code context}.
	 *
	 * @param context the view model context in which validation is occurring
	 * @param object the object to validate
	 * @return the results of validation of the {@code object}, or {@code null} if none
	 *
	 * @since 1.23
	 */
	default Iterable<? extends Diagnostic> validate(ViewModelContext context, EObject object) {
		return validate(object);
	}

	//
	// Nested types
	//

	/**
	 * A context-sensitive {@link ValidationProvider} that implements the
	 * {@link ValidationProvider#validate(ViewModelContext, EObject)}
	 * method to the exclusion of {@link Validator#validate(EObject)}.
	 *
	 * @since 1.23
	 */
	abstract class ContextSensitive implements ValidationProvider {

		/**
		 * Initializes me.
		 */
		public ContextSensitive() {
			super();
		}

		/**
		 * Un-implements the inherited method.
		 *
		 * @throws UnsupportedOperationException always
		 */
		@Override
		public final List<Diagnostic> validate(EObject eObject) {
			throw new UnsupportedOperationException("validate(EObject)"); //$NON-NLS-1$
		}

		@Override
		public abstract Iterable<? extends Diagnostic> validate(ViewModelContext context, EObject object);

	}

}
