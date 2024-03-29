/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.style.fontProperties.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesFactory;
import org.eclipse.emf.ecp.view.template.style.fontProperties.model.VTFontPropertiesStyleProperty;
import org.junit.Test;

/**
 * @author Alexandra Buzila
 *
 */
public class VTFontPropertiesStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTFontPropertiesStyleProperty fontPropertiesStyleProperty = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();
		final VTFontPropertiesStyleProperty fontPropertiesStyleProperty2 = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		assertTrue(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTFontPropertiesStyleProperty fontPropertiesStyleProperty = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();
		final VTFontPropertiesStyleProperty fontPropertiesStyleProperty2 = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		fontPropertiesStyleProperty.setBold(!fontPropertiesStyleProperty2.isBold());
		assertFalse(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		fontPropertiesStyleProperty.setItalic(!fontPropertiesStyleProperty2.isItalic());
		assertFalse(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		fontPropertiesStyleProperty.setColorHEX(fontPropertiesStyleProperty2.getColorHEX() + "111"); //$NON-NLS-1$
		assertFalse(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		fontPropertiesStyleProperty.setFontName(fontPropertiesStyleProperty2.getFontName() + "111"); //$NON-NLS-1$
		assertFalse(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));

		setEqualFields(fontPropertiesStyleProperty, fontPropertiesStyleProperty2);
		fontPropertiesStyleProperty.setHeight(fontPropertiesStyleProperty2.getHeight() + 1);
		assertFalse(fontPropertiesStyleProperty.equalStyles(fontPropertiesStyleProperty2));

	}

	@Test
	public void equalStyles_OtherNull() {
		final VTFontPropertiesStyleProperty fontPropertiesStyleProperty = VTFontPropertiesFactory.eINSTANCE
			.createFontPropertiesStyleProperty();

		assertFalse(fontPropertiesStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTFontPropertiesStyleProperty prop1, VTFontPropertiesStyleProperty prop2) {
		prop1.setBold(false);
		prop1.setColorHEX("aaa"); //$NON-NLS-1$
		prop1.setFontName("bbb"); //$NON-NLS-1$
		prop1.setHeight(12);
		prop1.setItalic(true);

		prop2.setBold(false);
		prop2.setColorHEX("aaa"); //$NON-NLS-1$
		prop2.setFontName("bbb"); //$NON-NLS-1$
		prop2.setHeight(12);
		prop2.setItalic(true);
	}
}
