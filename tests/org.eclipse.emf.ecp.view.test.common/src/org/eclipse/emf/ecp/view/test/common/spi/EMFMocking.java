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

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.mockito.MockitoAnnotations;

/**
 * Utilities for injection of Mockito mocks with {@link EMock @EMock} fields
 * and for <em>ad hoc</em> configuration of mocks for EMF models.
 *
 * @since 1.22
 */
public final class EMFMocking {

	/**
	 * Not instantiable by clients.
	 */
	private EMFMocking() {
		super();
	}

	/**
	 * Inject mocks into an {@code object}.
	 *
	 * @param object an object to inject
	 */
	public static void initEMocks(Object object) {
		MockitoAnnotations.initMocks(object);

		for (Class<?> classs = object.getClass(); classs != Object.class; classs = classs.getSuperclass()) {
			try {
				initEMocks(object, classs);
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
				fail(String.format("Failed to inject mocks in %s: %s", object, e.getMessage()));
			}
		}
	}

	public static <T extends EObject> T eMock(Class<T> type) {
		return eMock(type, withESettings());
	}

	public static <T extends EObject> T eMock(Class<T> type, String name) {
		return eMock(type, withESettings().name(name));
	}

	public static <T extends EObject> T eMock(Class<T> type, EMockSettings settings) {
		return ((EMockSettingsImpl) settings).make(type);
	}

	public static EMockSettings withESettings() {
		return new EMockSettingsImpl();
	}

	public static <T extends EObject> T eSetContainer(T mock, EObject container) {
		when(mock.eContainer()).thenReturn(container);
		when(((InternalEObject) mock).eInternalContainer()).thenReturn((InternalEObject) container);
		return mock;
	}

	private static void initEMocks(Object testInstance, Class<?> testClass) throws IllegalAccessException {
		for (final Field next : testClass.getDeclaredFields()) {
			if (next.isAnnotationPresent(EMock.class)) {
				if (!EObject.class.isAssignableFrom(next.getType())) {
					throw new IllegalArgumentException("Cannot use @EMock annotation on field of non-EObject type"); //$NON-NLS-1$
				}

				inject(testInstance, next);
			}
		}
	}

	private static void inject(Object owner, Field field) throws IllegalAccessException {
		final EMock emock = field.getAnnotation(EMock.class);
		EMockSettings settings = withESettings();

		if (!emock.name().trim().isEmpty()) {
			settings = settings.name(emock.name().trim());
		} else {
			settings = settings.name(field.getName());
		}
		settings = settings.defaultAnswer(emock.answer().get());

		final EObject mock = eMock(field.getType().asSubclass(EObject.class), settings);

		final boolean wasAccessible = field.isAccessible();
		field.setAccessible(true);

		try {
			field.set(owner, mock);
		} finally {
			field.setAccessible(wasAccessible);
		}
	}

}
