/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.style.labelwidth.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelWidthStyleProperty;
import org.eclipse.emf.ecp.view.template.style.labelwidth.model.VTLabelwidthFactory;
import org.junit.Test;

public class VTLabelWidthStylePropertyImpl_Test {

	private static VTLabelWidthStyleProperty createLabelWidthStyleProperty(
		boolean set,
		int width) {
		final VTLabelWidthStyleProperty property = VTLabelwidthFactory.eINSTANCE.createLabelWidthStyleProperty();
		property.setWidth(width);
		if (!set) {
			property.unsetWidth();
		}
		return property;
	}

	@Test
	public void testBothUnset() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(false, 10);
		final VTLabelWidthStyleProperty other = createLabelWidthStyleProperty(false, 20);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertTrue(equalStyles);
	}

	@Test
	public void testMineUnset() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(false, 10);
		final VTLabelWidthStyleProperty other = createLabelWidthStyleProperty(true, 10);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testOtherUnset() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(true, 10);
		final VTLabelWidthStyleProperty other = createLabelWidthStyleProperty(false, 10);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testOtherNull() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(true, 10);
		final VTLabelWidthStyleProperty other = null;

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testOtherDifferentStyle() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(true, 10);
		final VTStyleProperty other = VTAlignmentFactory.eINSTANCE.createAlignmentStyleProperty();

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testBothSetDifferent() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(true, 10);
		final VTLabelWidthStyleProperty other = createLabelWidthStyleProperty(true, 20);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testBothSetSame() {
		/* setup */
		final VTLabelWidthStyleProperty mine = createLabelWidthStyleProperty(true, 10);
		final VTLabelWidthStyleProperty other = createLabelWidthStyleProperty(true, 10);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertTrue(equalStyles);
	}

}
