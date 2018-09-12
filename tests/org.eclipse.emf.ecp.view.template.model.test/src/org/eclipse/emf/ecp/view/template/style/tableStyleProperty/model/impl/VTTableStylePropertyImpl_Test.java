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
package org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory;
import org.junit.Test;

/**
 * Unit tests for {@link VTTableStylePropertyImpl}.
 * 
 * @author Lucas Koehler
 *
 */
public class VTTableStylePropertyImpl_Test {

	@Test
	public void equalStyles_otherEqual() {
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		final VTTableStyleProperty tableStyleProperty2 = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		assertTrue(tableStyleProperty.equalStyles(tableStyleProperty2));
	}

	@Test
	public void equalStyles_otherDifferent() {
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		final VTTableStyleProperty tableStyleProperty2 = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setEnableSorting(false);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.unsetMaximumHeight();
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setMaximumHeight(1);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.unsetMinimumHeight();
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setMinimumHeight(1);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setRenderMode(RenderMode.COMPACT_VERTICALLY);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setShowValidationSummaryTooltip(false);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.unsetVisibleLines();
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));

		setEqualFields(tableStyleProperty, tableStyleProperty2);
		tableStyleProperty2.setVisibleLines(1);
		assertFalse(tableStyleProperty.equalStyles(tableStyleProperty2));
	}

	@Test
	public void equalStyles_OtherNull() {
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();

		assertFalse(tableStyleProperty.equalStyles(null));
	}

	private void setEqualFields(VTTableStyleProperty prop1, VTTableStyleProperty prop2) {
		prop1.setEnableSorting(true);
		prop1.setEnableSorting(true);

		prop1.setMaximumHeight(100);
		prop2.setMaximumHeight(100);

		prop1.setMinimumHeight(5);
		prop2.setMinimumHeight(5);

		prop1.setRenderMode(RenderMode.DEFAULT);
		prop2.setRenderMode(RenderMode.DEFAULT);

		prop1.setShowValidationSummaryTooltip(true);
		prop2.setShowValidationSummaryTooltip(true);

		prop1.setVisibleLines(5);
		prop2.setVisibleLines(5);
	}

}
