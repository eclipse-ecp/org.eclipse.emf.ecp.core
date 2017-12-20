/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation adapted from XGrid project
 ******************************************************************************/
package org.eclipse.emfforms.internal.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emfforms.common.Property;

/**
 * Simple implementation of a property.
 *
 * @param <T> the value type
 * @since 1.15
 */
public class SimpleProperty<T> implements Property<T> {

	private final String name;
	private final T defaultValue;
	private T value;

	private List<ChangeListener<T>> listenerList = new ArrayList<ChangeListener<T>>();
	private boolean disposed;

	private void checkDisposed() {
		if (disposed) {
			throw new IllegalStateException("Property is disposed"); //$NON-NLS-1$
		}
	}

	/**
	 * The constructor.
	 *
	 * @param name the name of the property
	 * @param defaultValue the default value
	 */
	public SimpleProperty(final String name, final T defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
	}

	@Override
	public void setValue(T value) {
		checkDisposed();
		final T oldValue = this.value;
		this.value = value;
		for (final ChangeListener<T> listener : new ArrayList<ChangeListener<T>>(listenerList)) {
			listener.valueChanged(this, oldValue, value);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public T getValue() {
		checkDisposed();
		return this.value;
	}

	@Override
	public T getDefault() {
		return this.defaultValue;
	}

	@Override
	public T resetToDefault() {
		setValue(defaultValue);
		return value;
	}

	@Override
	public void dispose() {
		if (listenerList != null) {
			listenerList.clear();
		}
		listenerList = null;
		disposed = true;
	}

	@Override
	public void addChangeListener(ChangeListener<T> listener) {
		checkDisposed();
		listenerList.add(listener);
	}

	@Override
	public void removeChangeListener(ChangeListener<T> listener) {
		checkDisposed();
		listenerList.remove(listener);
	}

}
