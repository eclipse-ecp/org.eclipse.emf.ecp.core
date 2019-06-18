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
package org.eclipse.emfforms.internal.ide.view.mappingsegment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Utility functions for the mapping segment ide tests.
 *
 * @author Lucas Koehler
 */
final class TestUtil {
	private TestUtil() {
		// Utility class
	}

	/**
	 * @param valueEClass the reference type of the map's value reference
	 * @return a mocked {@link EReference} which describes a valid EMF Map
	 */
	static EReference mockMapReference(EClass valueEClass) {
		final EReference keyReference = mock(EReference.class);
		when(keyReference.getEReferenceType()).thenReturn(EcorePackage.Literals.ECLASS);
		when(keyReference.getName()).thenReturn("key"); //$NON-NLS-1$

		final EReference valueReference = mock(EReference.class);
		when(valueReference.getEReferenceType()).thenReturn(valueEClass);
		when(valueReference.getName()).thenReturn("value"); //$NON-NLS-1$

		final EClass mapReferenceType = mock(EClass.class);
		when(mapReferenceType.getInstanceClassName()).thenReturn("java.util.Map$Entry"); //$NON-NLS-1$
		when(mapReferenceType.getEStructuralFeature("key")).thenReturn(keyReference); //$NON-NLS-1$
		when(mapReferenceType.getEStructuralFeature("value")).thenReturn(valueReference); //$NON-NLS-1$
		when(mapReferenceType.getName()).thenReturn("EClassToMockMap"); //$NON-NLS-1$

		final EReference mapReference = mock(EReference.class);
		when(mapReference.isMany()).thenReturn(true);
		when(mapReference.getEReferenceType()).thenReturn(mapReferenceType);
		return mapReference;
	}

	/**
	 * @return a mocked {@link EReference} which describes a valid EMF Map
	 */
	static EReference mockMapReference() {
		return mockMapReference(mock(EClass.class));
	}
}
