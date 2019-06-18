/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.template.style.unsettable.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationFactory;
import org.eclipse.emf.ecp.view.template.style.tableValidation.model.VTTableValidationStyleProperty;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonAlignmentType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.ButtonPlacementType;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableFactory;
import org.eclipse.emf.ecp.view.template.style.unsettable.model.VTUnsettableStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTUnsettableStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTUnsettableStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTUnsettableStyleProperty unsettableStyleProperty = VTUnsettableFactory.eINSTANCE
			.createUnsettableStyleProperty();
		final VTUnsettableStyleProperty unsettableStyleProperty2 = VTUnsettableFactory.eINSTANCE
			.createUnsettableStyleProperty();

		setEqualFields(unsettableStyleProperty, unsettableStyleProperty2);
		assertTrue(unsettableStyleProperty.equalStyles(unsettableStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTUnsettableStyleProperty unsettableStyleProperty = VTUnsettableFactory.eINSTANCE
			.createUnsettableStyleProperty();
		final VTUnsettableStyleProperty unsettableStyleProperty2 = VTUnsettableFactory.eINSTANCE
			.createUnsettableStyleProperty();

		setEqualFields(unsettableStyleProperty, unsettableStyleProperty2);
		unsettableStyleProperty2.setButtonAlignment(ButtonAlignmentType.RIGHT);
		assertFalse(unsettableStyleProperty.equalStyles(unsettableStyleProperty2));

		setEqualFields(unsettableStyleProperty, unsettableStyleProperty2);
		unsettableStyleProperty2.setButtonPlacement(ButtonPlacementType.RIGHT_OF_LABEL);
		assertFalse(unsettableStyleProperty.equalStyles(unsettableStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTableValidationStyleProperty tableValidationStyleProperty = VTTableValidationFactory.eINSTANCE
			.createTableValidationStyleProperty();

		assertFalse(tableValidationStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTUnsettableStyleProperty prop1, VTUnsettableStyleProperty prop2) {
		prop1.setButtonAlignment(ButtonAlignmentType.LEFT);
		prop2.setButtonAlignment(ButtonAlignmentType.LEFT);

		prop1.setButtonPlacement(ButtonPlacementType.LEFT_OF_LABEL);
		prop2.setButtonPlacement(ButtonPlacementType.LEFT_OF_LABEL);
	}

}
