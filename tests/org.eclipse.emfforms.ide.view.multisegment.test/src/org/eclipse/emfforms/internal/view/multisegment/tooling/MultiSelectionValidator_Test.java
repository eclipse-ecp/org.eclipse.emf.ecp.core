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
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link MultiSelectionValidator}.
 * 
 * @author Lucas Koehler
 *
 */
public class MultiSelectionValidator_Test {

	private MultiSelectionValidator validator;

	@Before
	public void setUp() {
		validator = new MultiSelectionValidator();
		assertNotNull(MultiSelectionValidator.ERROR_MESSAGE);
		assertFalse(MultiSelectionValidator.ERROR_MESSAGE.isEmpty());
	}

	@Test
	public void isValid_null() {
		final String result = validator.isValid(null);
		assertNotNull(result);
		assertEquals(MultiSelectionValidator.ERROR_MESSAGE, result);
	}

	@Test
	public void isValid_eAttribute() {
		final String result = validator.isValid(EcorePackage.Literals.ECLASS__ABSTRACT);
		assertNotNull(result);
		assertEquals(MultiSelectionValidator.ERROR_MESSAGE, result);
	}

	@Test
	public void isValid_singleReference() {
		final String result = validator.isValid(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		assertNotNull(result);
		assertEquals(MultiSelectionValidator.ERROR_MESSAGE, result);
	}

	@Test
	public void isValid_multiReference() {
		final String result = validator.isValid(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		assertNull(result);
	}
}
