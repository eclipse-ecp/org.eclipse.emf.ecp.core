/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.style.wrap.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTLabelWrapStyleProperty;
import org.eclipse.emf.ecp.view.template.style.wrap.model.VTWrapFactory;
import org.junit.Test;

public class VTLabelWrapStylePropertyImpl_Test {

	private static VTLabelWrapStyleProperty property(boolean isWrap) {
		final VTLabelWrapStyleProperty property = VTWrapFactory.eINSTANCE.createLabelWrapStyleProperty();
		property.setWrapLabel(isWrap);
		return property;
	}

	@Test
	public void equalStyle_null() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(true);

		/* act */
		final boolean equalStyles = underTest.equalStyles(null);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void equalStyle_differentProperty() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(true);

		/* act */
		final boolean equalStyles = underTest
			.equalStyles(VTLabelwidthFactory.eINSTANCE.createLabelWidthStyleProperty());

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void equalStyle_false1() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(true);

		/* act */
		final boolean equalStyles = underTest
			.equalStyles(property(false));

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void equalStyle_false2() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(false);

		/* act */
		final boolean equalStyles = underTest
			.equalStyles(property(true));

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void equalStyle_true1() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(true);

		/* act */
		final boolean equalStyles = underTest
			.equalStyles(property(true));

		/* assert */
		assertTrue(equalStyles);
	}

	@Test
	public void equalStyle_true2() {
		/* setup */
		final VTLabelWrapStyleProperty underTest = property(false);

		/* act */
		final boolean equalStyles = underTest
			.equalStyles(property(false));

		/* assert */
		assertTrue(equalStyles);
	}

}
