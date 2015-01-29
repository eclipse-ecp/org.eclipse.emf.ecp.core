/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.common.spi.asserts;

/**
 * Class for checking the validity of a provided object.
 *
 * @param <T> the type of the object the <code>Assert</code> class verifies.
 * @author Alexandra Buzila
 *
 */
public final class Assert<T> {

	private final T object;

	private Assert(T object) {
		this.object = object;
	}

	/**
	 * @param object the object that will be checked
	 * @param <T> the type of the object the <code>Assert</code> class verifies.
	 * @return a new instance of the <code>Assert</code> class.
	 * */
	public static <T> Assert<T> create(T object) {
		return new Assert<T>(object);
	}

	/**
	 * Checks if the object is null, in which case an {@link IllegalArgumentException} is thrown.
	 *
	 * @return the <code>Assert</code> instance, when no exception is thrown.
	 */
	public Assert<T> notNull() {
		if (object == null) {
			throw new IllegalArgumentException("Object must not be null."); //$NON-NLS-1$
		}
		return this;
	}

	/**
	 * Checks if the object has a different type than the Class provided as parameter, in which case an
	 * {@link IllegalArgumentException} is thrown.
	 *
	 * @param clazz the <code>Class</code> against which the type of the object is checked.
	 * @return the <code>Assert</code> instance, when no exception is thrown.
	 */
	public Assert<?> ofClass(Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Class must not be null."); //$NON-NLS-1$
		}
		if (!clazz.isInstance(object)) {
			final String message = String.format(
				"%1$s is not of type %2$s.", object == null ? object : object.getClass().getName(), clazz.getName()); //$NON-NLS-1$
			throw new IllegalArgumentException(message);
		}
		return this;
	}

	/**
	 *
	 * Usage example: <br>
	 * <code>Assert.create(Object object).notNull().ofClass(Class clazz).check()</code>.
	 *
	 * @return the object for which the <code>Assert</code> is created.
	 *
	 *
	 */
	public T check() {
		return object;
	}

}
