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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Constants for use with the {@link ValidationService}.
 *
 * @since 1.21
 */
public final class ValidationServiceConstants {
	/**
	 * <p>
	 * Key of a {@linkplain ViewModelContext#putContextValue(String, Object) context value}
	 * specifying a limit for the number of problems propagated up the view model hierarchy
	 * from any given control. Values are either a positive {@link Integer} for a propagation limit
	 * or {@code "*"} for unlimited propagation. The default is unlimited propagation, so the
	 * use cases for the {@code "*"} value should be few in number.
	 * </p>
	 * <p>
	 * If this value is to be injected into the context by a client application, it is
	 * recommended to do so via the factory APIs that accept an initial map of context values,
	 * for example,
	 * {@link ViewModelContextFactory#createViewModelContext(VElement, EObject, Map)}.
	 * For applications that are not in control of the editor's context lifecycle, an alternative
	 * approach is to set context values in the {@linkplain ViewModelService#instantiate(ViewModelContext)
	 * initialization} of a global {@link ViewModelService} registered at a priority
	 * less than the {@link ValidationService} priority (which by default is {@code 1.0}. This ensures that the service
	 * setting the context value is initialized before the validation service, so that the context value can be put
	 * before the validation service picks it up.
	 * </p>
	 *
	 * @see ViewModelContext#putContextValue(String, Object)
	 * @see ViewModelContextFactory#createViewModelContext(VElement, EObject, Map)
	 * @see #PROPAGATION_UNLIMITED_VALUE
	 */
	public static final String PROPAGATION_LIMIT_KEY = "org.eclipse.emf.ecp.view.validation.propagationLimit"; //$NON-NLS-1$

	/**
	 * Value of the {@linkplain #PROPAGATION_LIMIT_KEY propagation limit} in the context
	 * indicating unlimited propagation (which is the default in the absence of any value).
	 *
	 * @see #PROPAGATION_LIMIT_KEY
	 */
	public static final Object PROPAGATION_UNLIMITED_VALUE = "*"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private ValidationServiceConstants() {
		super();
	}

}
