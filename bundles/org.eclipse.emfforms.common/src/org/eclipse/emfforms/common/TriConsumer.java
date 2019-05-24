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
package org.eclipse.emfforms.common;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Protocol for an action that accepts three inputs. This is the ternary variant of {@link Consumer}
 * as {@link BiConsumer} is a binary variant of it.
 *
 * @param <T> the type of the first input
 * @param <U> the type of the second input
 * @param <V> the type of the third input
 *
 * @since 1.22
 *
 * @see TriFunction
 * @see BiConsumer
 * @see Consumer
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

	/**
	 * Accept the inputs and operate on them.
	 *
	 * @param t the first input
	 * @param u the second input
	 * @param v the third input
	 */
	void accept(T t, U u, V v);

	/**
	 * Obtain a composed {@code TriConsumer} that performs me followed by an
	 * {@code after} operation. If the {@code after} consumer throws, then
	 * that exception is propagated to the caller. If I throw, then likewise
	 * but also the {@code after} consumer will not be invoked.
	 *
	 * @param after a consumer to invoke after me. Must not be {@code null}
	 * @return the composition of myself with the {@code after} consumer
	 *
	 * @throws NullPointerException if {@code after} is {@code null}
	 */
	default TriConsumer<T, U, V> andThen(TriConsumer<? super T, ? super U, ? super V> after) {
		final TriConsumer<? super T, ? super U, ? super V> next = Objects.requireNonNull(after);

		return (t, u, v) -> {
			this.accept(t, u, v);
			next.accept(t, u, v);
		};
	}

}
