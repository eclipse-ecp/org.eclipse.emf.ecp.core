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
package org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementFactory;
import org.eclipse.emf.ecp.view.template.style.textControlEnablement.model.VTTextControlEnablementStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTTextControlEnablementStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTTextControlEnablementStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTTextControlEnablementStyleProperty property = VTTextControlEnablementFactory.eINSTANCE
			.createTextControlEnablementStyleProperty();
		final VTTextControlEnablementStyleProperty property2 = VTTextControlEnablementFactory.eINSTANCE
			.createTextControlEnablementStyleProperty();

		setEqualFields(property, property2);
		assertTrue(property.equalStyles(property2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTTextControlEnablementStyleProperty property = VTTextControlEnablementFactory.eINSTANCE
			.createTextControlEnablementStyleProperty();
		final VTTextControlEnablementStyleProperty property2 = VTTextControlEnablementFactory.eINSTANCE
			.createTextControlEnablementStyleProperty();

		setEqualFields(property, property2);
		property2.setRenderDisableAsEditable(false);
		assertFalse(property.equalStyles(property2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		assertFalse(tableValidationStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTTextControlEnablementStyleProperty prop1,
		VTTextControlEnablementStyleProperty prop2) {

		prop1.setRenderDisableAsEditable(true);
		prop2.setRenderDisableAsEditable(true);
	}

}
