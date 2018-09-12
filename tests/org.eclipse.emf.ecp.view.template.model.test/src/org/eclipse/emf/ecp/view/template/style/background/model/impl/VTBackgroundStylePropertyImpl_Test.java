/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.style.background.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundFactory;
import org.eclipse.emf.ecp.view.template.style.background.model.VTBackgroundStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTBackgroundStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTBackgroundStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTBackgroundStyleProperty backgroundStyleProperty = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();
		final VTBackgroundStyleProperty backgroundStyleProperty2 = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();

		setEqualFields(backgroundStyleProperty, backgroundStyleProperty2);
		assertTrue(backgroundStyleProperty.equalStyles(backgroundStyleProperty2));
	}

	@Test
	public void equalStyles_ignoreColorStringCase() {
		final VTBackgroundStyleProperty backgroundStyleProperty = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();
		final VTBackgroundStyleProperty backgroundStyleProperty2 = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();

		setEqualFields(backgroundStyleProperty, backgroundStyleProperty2);
		backgroundStyleProperty2.setColor("#AAAAAA"); //$NON-NLS-1$
		assertTrue(backgroundStyleProperty.equalStyles(backgroundStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTBackgroundStyleProperty backgroundStyleProperty = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();
		final VTBackgroundStyleProperty backgroundStyleProperty2 = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();

		setEqualFields(backgroundStyleProperty, backgroundStyleProperty2);
		backgroundStyleProperty2.setColor("#bbbbbb"); //$NON-NLS-1$
		assertFalse(backgroundStyleProperty.equalStyles(backgroundStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTBackgroundStyleProperty backgroundStyleProperty = VTBackgroundFactory.eINSTANCE
			.createBackgroundStyleProperty();

		assertFalse(backgroundStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTBackgroundStyleProperty prop1, VTBackgroundStyleProperty prop2) {
		prop1.setColor("#aaaaaa"); //$NON-NLS-1$
		prop2.setColor("#aaaaaa"); //$NON-NLS-1$
	}

}
