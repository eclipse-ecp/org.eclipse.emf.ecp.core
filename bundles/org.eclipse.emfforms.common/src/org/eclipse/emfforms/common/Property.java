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
package org.eclipse.emfforms.common;

/**
 * An observable value.
 *
 * @param <T>
 *            the type
 * @since 1.15
 */
public interface Property<T> {
	/**
	 * Set the new value.
	 *
	 * @param value
	 *            the value
	 */
	void setValue(T value);

	/**
	 * Get the name of the property.
	 *
	 * @return the value
	 */
	String getName();

	/**
	 * Get the current value.
	 *
	 * @return the value
	 */
	T getValue();

	/**
	 * Returns the default value.
	 *
	 * @return the value
	 */

	T getDefault();

	/**
	 * Resets the value to the initial value and returns the new value.
	 *
	 * @return the value
	 */
	T resetToDefault();

	/**
	 * Dispose the value.
	 */
	void dispose();

	/**
	 * Attach a listener.
	 *
	 * @param listener
	 *            the listener
	 */
	void addChangeListener(ChangeListener<T> listener);

	/**
	 * Remove the listener.
	 *
	 * @param listener
	 *            the listener
	 */
	void removeChangeListener(ChangeListener<T> listener);

	/**
	 * Listener to observe changes.
	 *
	 * @param <T>
	 *            the type
	 */
	// @FunctionalInterface
	interface ChangeListener<T> {
		/**
		 * Handle the changed value.
		 *
		 * @param property
		 *            the property
		 * @param oldValue
		 *            the old value
		 * @param newValue
		 *            the new value
		 */
		void valueChanged(Property<T> property, T oldValue, T newValue);
	}
}