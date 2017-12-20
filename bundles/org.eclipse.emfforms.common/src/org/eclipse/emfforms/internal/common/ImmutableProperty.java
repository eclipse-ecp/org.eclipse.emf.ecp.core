/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.common;

import org.eclipse.emfforms.common.Property;

/**
 * Immutable implementation of a property.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.15
 *
 * @param <T> the value type
 */
public class ImmutableProperty<T> implements Property<T> {

	private final String name;
	private final T immutableValue;

	private void throwImmutableException() {
		throw new IllegalStateException("Property is immutable and cannot be modified during runtime"); //$NON-NLS-1$
	}

	/**
	 * The constructor.
	 *
	 * @param name the name of the property
	 * @param immutableValue the immutable value
	 */
	public ImmutableProperty(String name, T immutableValue) {
		this.name = name;
		this.immutableValue = immutableValue;
	}

	@Override
	public void setValue(T value) {
		throwImmutableException();
	}

	@Override
	public void addChangeListener(ChangeListener<T> listener) {
		// silently ignore this
	}

	@Override
	public void removeChangeListener(ChangeListener<T> listener) {
		// silently ignore this
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public T getValue() {
		return immutableValue;
	}

	@Override
	public T getDefault() {
		return immutableValue;
	}

	@Override
	public T resetToDefault() {
		return immutableValue;
	}

	@Override
	public void dispose() {
		// silently ignore this
	}

}
