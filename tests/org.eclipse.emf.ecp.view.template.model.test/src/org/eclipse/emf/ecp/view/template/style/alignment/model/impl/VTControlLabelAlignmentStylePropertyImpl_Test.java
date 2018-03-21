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
package org.eclipse.emf.ecp.view.template.style.alignment.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.template.style.alignment.model.AlignmentType;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTAlignmentFactory;
import org.eclipse.emf.ecp.view.template.style.alignment.model.VTControlLabelAlignmentStyleProperty;
import org.junit.Test;

public class VTControlLabelAlignmentStylePropertyImpl_Test {

	@Test
	public void testOtherNull() {
		/* setup */
		final VTControlLabelAlignmentStyleProperty mine = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();

		/* act */
		final boolean equalStyles = mine.equalStyles(null);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testOtherDifferent() {
		/* setup */
		final VTControlLabelAlignmentStyleProperty mine = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		mine.setType(AlignmentType.LEFT);

		final VTControlLabelAlignmentStyleProperty other = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		other.setType(AlignmentType.RIGHT);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertFalse(equalStyles);
	}

	@Test
	public void testOtherSame() {
		/* setup */
		final VTControlLabelAlignmentStyleProperty mine = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		mine.setType(AlignmentType.LEFT);

		final VTControlLabelAlignmentStyleProperty other = VTAlignmentFactory.eINSTANCE
			.createControlLabelAlignmentStyleProperty();
		other.setType(AlignmentType.LEFT);

		/* act */
		final boolean equalStyles = mine.equalStyles(other);

		/* assert */
		assertTrue(equalStyles);
	}

}
