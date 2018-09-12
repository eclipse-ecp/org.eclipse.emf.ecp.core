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
package org.eclipse.emf.ecp.view.template.style.mandatory.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTMandatoryStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTMandatoryStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTMandatoryStyleProperty mandatoryStyleProperty = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();
		final VTMandatoryStyleProperty mandatoryStyleProperty2 = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();

		setEqualFields(mandatoryStyleProperty, mandatoryStyleProperty2);
		assertTrue(mandatoryStyleProperty.equalStyles(mandatoryStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTMandatoryStyleProperty mandatoryStyleProperty = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();
		final VTMandatoryStyleProperty mandatoryStyleProperty2 = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();

		setEqualFields(mandatoryStyleProperty, mandatoryStyleProperty2);
		mandatoryStyleProperty2.setHighliteMandatoryFields(false);
		assertFalse(mandatoryStyleProperty.equalStyles(mandatoryStyleProperty2));

		setEqualFields(mandatoryStyleProperty, mandatoryStyleProperty2);
		mandatoryStyleProperty2.setMandatoryMarker("different"); //$NON-NLS-1$
		assertFalse(mandatoryStyleProperty.equalStyles(mandatoryStyleProperty2));

	}

	@Test
	public void equalStyles_OtherNull() {
		final VTMandatoryStyleProperty mandatoryStyleProperty = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();

		assertFalse(mandatoryStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTMandatoryStyleProperty prop1, VTMandatoryStyleProperty prop2) {
		prop1.setHighliteMandatoryFields(true);
		prop2.setHighliteMandatoryFields(true);

		prop1.setMandatoryMarker("x"); //$NON-NLS-1$
		prop2.setMandatoryMarker("x"); //$NON-NLS-1$
	}

}
