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
package org.eclipse.emf.ecp.view.template.style.tableValidation.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTTableValidationStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTTableValidationStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();
		final VTTableValidationStyleProperty tableValidationStyleProperty2 = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		setEqualFields(tableValidationStyleProperty, tableValidationStyleProperty2);
		assertTrue(tableValidationStyleProperty.equalStyles(tableValidationStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();
		final VTTableValidationStyleProperty tableValidationStyleProperty2 = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		setEqualFields(tableValidationStyleProperty, tableValidationStyleProperty2);
		tableValidationStyleProperty2.setColumnName("different"); //$NON-NLS-1$
		assertFalse(tableValidationStyleProperty.equalStyles(tableValidationStyleProperty2));

		setEqualFields(tableValidationStyleProperty, tableValidationStyleProperty2);
		tableValidationStyleProperty2.setColumnWidth(5);
		assertFalse(tableValidationStyleProperty.equalStyles(tableValidationStyleProperty2));

		setEqualFields(tableValidationStyleProperty, tableValidationStyleProperty2);
		tableValidationStyleProperty2.setImagePath("different-icon.png"); //$NON-NLS-1$
		assertFalse(tableValidationStyleProperty.equalStyles(tableValidationStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		assertFalse(tableValidationStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTTableValidationStyleProperty prop1, VTTableValidationStyleProperty prop2) {
		prop1.setColumnName("name"); //$NON-NLS-1$
		prop2.setColumnName("name"); //$NON-NLS-1$

		prop1.setColumnWidth(10);
		prop2.setColumnWidth(10);

		prop1.setImagePath("icon.png"); //$NON-NLS-1$
		prop2.setImagePath("icon.png"); //$NON-NLS-1$
	}

}
