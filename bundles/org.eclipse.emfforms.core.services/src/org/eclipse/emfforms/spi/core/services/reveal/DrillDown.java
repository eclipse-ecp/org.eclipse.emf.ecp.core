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
package org.eclipse.emfforms.spi.core.services.reveal;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation on a method that computes the {@link Iterable Iterable&lt;? extends VElement&gt;}
 * children of a view-model element to drill down into for a delegated
 * {@linkplain RevealHelper#drillDown(Object, Object) drill-down} reveal step.
 *
 * @since 1.22
 * @see RevealHelper#drillDown(Object, Object)
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface DrillDown {
	// No attributes
}
