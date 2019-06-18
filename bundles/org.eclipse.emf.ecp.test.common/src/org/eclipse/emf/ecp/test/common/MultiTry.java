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
package org.eclipse.emf.ecp.test.common;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation used in conjunction with {@link MultiTryTestRule}. If a {@link MultiTryTestRule} is configured to not
 * apply to all tests of a test class, only tests annotated with this annotation allow multiple tries. Furthermore, this
 * annotation allows to increase the number of max tries. The number of max tries is the maximum of the rule's number
 * and the annotation's number.
 *
 * @author Lucas Koehler
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface MultiTry {
	/**
	 * The maximum tries. The effective number of maximum tries is the <strong>maximum</strong> of this value and the
	 * number configured in a {@link MultiTryTestRule} instance.
	 *
	 * @return The maximum number of retries
	 */
	int maxTries() default 1;
}
