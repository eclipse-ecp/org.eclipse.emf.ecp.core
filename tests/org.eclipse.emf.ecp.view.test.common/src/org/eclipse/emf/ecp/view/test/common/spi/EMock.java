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
package org.eclipse.emf.ecp.view.test.common.spi;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.emf.ecore.EObject;
import org.mockito.Answers;
import org.mockito.Mock;

/**
 * Analogue of the {@link Mock} annotation that indicates a mock of {@link EObject} type
 * to be injected. It ensures that all of the interfaces expected by the EMF run-time are
 * provided, so therefore it does not have the {@link Mock#extraInterfaces()} attribute as
 * it would be unusual to mix in any other interfaces to an EMF model object.
 *
 * @since 1.22
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface EMock {

	String name() default "";

	Answers answer() default Answers.RETURNS_DEFAULTS;

}
