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
package org.eclipse.emf.ecp.view.template.style.reference.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryFactory;
import org.eclipse.emf.ecp.view.template.style.mandatory.model.VTMandatoryStyleProperty;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;
import org.junit.Test;

/**
 * Unit tests for {@link VTReferenceStylePropertyImpl}.
 *
 * @author Lucas Koehler
 *
 */
public class VTReferenceStylePropertyImpl_Test {

	@Test
	public void defaultValues() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		assertEquals(true, property.isShowCreateAndLinkButtonForCrossReferences());
	}

	@Test
	public void equalStyles_otherEqual() {
		final VTReferenceStyleProperty property1 = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		final VTReferenceStyleProperty property2 = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();

		assertTrue(property1.equalStyles(property2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTReferenceStyleProperty property1 = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		final VTReferenceStyleProperty property2 = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property2.setShowCreateAndLinkButtonForCrossReferences(false);

		assertFalse(property1.equalStyles(property2));

	}

	@Test
	public void equalStyles_OtherNull() {
		final VTMandatoryStyleProperty mandatoryStyleProperty = VTMandatoryFactory.eINSTANCE
			.createMandatoryStyleProperty();

		assertFalse(mandatoryStyleProperty.equalStyles(null));
	}
}
