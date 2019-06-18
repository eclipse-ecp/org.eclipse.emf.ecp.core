/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emfforms.common;

import java.util.NoSuchElementException;

/**
 * A container object that may or may not contain a value.
 *
 * @param <T> Type of the value
 * @author Eugen Neufeld
 * @since 1.8
 */
public final class Optional<T> {

	private static final Optional<?> EMPTY = new Optional<Object>();

	/**
	 * Returns an empty Optional instance. No value is present for this Optional.
	 *
	 * @param <T> Type of the non-existent value
	 * @return an empty Optional
	 */
	public static <T> Optional<T> empty() {
		@SuppressWarnings("unchecked")
		final Optional<T> t = (Optional<T>) EMPTY;
		return t;
	}

	/**
	 * Returns an Optional with the specified present non-null value.
	 *
	 * @param <T> the class of the value
	 * @param value the value to be present, which must be non-null
	 * @return an Optional with the value present
	 */
	public static <T> Optional<T> of(T value) {
		return new Optional<T>(value);
	}

	/**
	 * Returns an Optional describing the specified value, if non-null, otherwise returns an empty Optional.
	 *
	 * @param <T> the class of the value
	 * @param value the possibly-null value to describe
	 * @return an Optional with a present value if the specified value is non-null, otherwise an empty Optional
	 */
	public static <T> Optional<T> ofNullable(T value) {
		if (value == null) {
			return empty();
		}
		return of(value);
	}

	/**
	 * Creates a new Optional from the given java.util.Optional. If the java Optional is empty, an empty Optional is
	 * returned. Otherwise, the value is re-wrapped in this Optional.
	 *
	 * @param <T> the class of the value
	 * @param javaOptional The java.util.Optional to convert to an EMF Forms Optional
	 * @return An EMF Forms Optional equivalent to the given Java Optional
	 * @since 1.20
	 */
	public static <T> Optional<T> fromJavaOptional(java.util.Optional<T> javaOptional) {
		if (javaOptional.isPresent()) {
			return of(javaOptional.get());
		}
		return empty();
	}

	private final T value;

	private Optional() {
		value = null;
	}

	private Optional(T value) {
		if (value == null) {
			throw new NullPointerException("Value must not be null!"); //$NON-NLS-1$
		}
		this.value = value;
	}

	/**
	 * Return true if there is a value present, otherwise false.
	 *
	 * @return true if there is a value present, otherwise false
	 */
	public boolean isPresent() {
		return value != null;
	}

	/**
	 * If a value is present in this Optional, returns the value, otherwise throws NoSuchElementException.
	 *
	 * @return the non-null value held by this Optional
	 * @see #isPresent()
	 */
	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present!"); //$NON-NLS-1$
		}
		return value;
	}

	/**
	 * Returns the wrapped value, if present, otherwise {@code null}.
	 *
	 * @return the wrapped value, if present, otherwise {@code null}
	 *
	 * @since 1.13
	 */
	public Object orNull() {
		if (isPresent()) {
			return get();
		}
		return null;
	}
}
