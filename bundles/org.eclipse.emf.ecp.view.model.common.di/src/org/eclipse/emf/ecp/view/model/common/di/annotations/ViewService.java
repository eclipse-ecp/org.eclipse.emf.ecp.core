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
package org.eclipse.emf.ecp.view.model.common.di.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;

/**
 * Annotation indicating an injectable dependency of some view-model service type. This
 * may actually be a {@link ViewModelService} or some other kind of service provided
 * by an {@link EMFFormsViewServiceFactory}. In any case, injection of services from the
 * view context does depend on the Eclipse context containing an {@link EMFFormsViewContext}.
 *
 * @since 1.22
 */
@Documented
@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface ViewService {
	// No attributes
}
