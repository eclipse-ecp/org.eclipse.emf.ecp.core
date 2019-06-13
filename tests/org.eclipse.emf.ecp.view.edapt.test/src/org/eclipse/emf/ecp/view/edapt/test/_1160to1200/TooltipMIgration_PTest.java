/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test._1160to1200;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.categorization.model.VCategorizationElement;
import org.eclipse.emf.ecp.view.spi.model.VHasTooltip;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.section.model.VSectionedArea;

public class TooltipMIgration_PTest extends AbstractMigrationTest {

	@Override
	// BEGIN SUPRESS CATCH EXCEPTION
	protected void performTest() throws Exception {// END SUPRESS CATCH EXCEPTION
		assertFalse(getMigrator().checkMigration(getURI()));
		getMigrator().performMigration(getURI());
		final VView view = getMigratedView();
		assertTooltip("Group Tooltip", view.getChildren().get(0));
		assertTooltip("Composite Category Tooltip",
			VCategorizationElement.class.cast(view.getChildren().get(1)).getCategorizations().get(0));
		assertTooltip("Leaf Category Tooltip",
			VCategorizationElement.class.cast(view.getChildren().get(1)).getCategorizations().get(1));
		assertTooltip("Section Tooltip", VSectionedArea.class.cast(view.getChildren().get(2)).getRoot());
	}

	private static void assertTooltip(String expected, Object hasTooltip) {
		assertEquals(expected, VHasTooltip.class.cast(hasTooltip).getTooltip());
	}

	@Override
	protected String getPath() {
		return "1160/PlayerTooltip.view";
	}
}
