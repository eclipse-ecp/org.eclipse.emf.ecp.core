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
package org.eclipse.emfforms.common;

import java.util.function.Function;

/**
 * Represents a function that accepts three arguments and produces a result.
 * This is the three-arity specialization of {@link Function}.
 *
 * <p>
 * This is a functional interface whose functional method is {@link #apply(Object, Object, Object)}.
 *
 * <p>
 * This interface was introduced because Java 8 only provides {@link java.util.function.Function Function} and
 * {@link java.util.function.BiFunction BiFunction}.
 *
 * @param <R> the type of the result of the function
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <V> the type of the third argument to the function
 *
 * @author Lucas Koehler
 * @since 1.20
 *
 */
@FunctionalInterface
public interface TriFunction<R, T, U, V> {

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param t the first function argument
	 * @param u the second function argument
	 * @param v the third function argument
	 * @return the function result
	 */
	R apply(T t, U u, V v);
}
