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
package org.eclipse.emf.ecp.view.internal.editor.controls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TableColumnsDMRTableControl}.
 *
 * @author Lucas Koehler
 *
 */
public class TableColumnsDMRTableControl_Test {

	private TableColumnsDMRTableControl renderer;
	private DefaultRealm realm;

	@Before
	public void setUp() {
		realm = new DefaultRealm();
		// need to extend mocks when rendering is tested
		renderer = new TableColumnsDMRTableControl(mock(VControl.class), mock(ViewModelContext.class),
			mock(ReportService.class),
			mock(EMFFormsDatabinding.class), mock(EMFFormsLabelProvider.class), mock(VTViewTemplateProvider.class));
	}

	@After
	public void after() {
		realm.dispose();
	}

	@Test
	public void gridDescription() {
		final SWTGridDescription result = renderer.getGridDescription(mock(SWTGridDescription.class));

		assertEquals(3, result.getGrid().size());
		assertEquals(1, result.getRows());
		assertEquals(3, result.getColumns());
		final SWTGridCell labelCell = result.getGrid().get(0);
		final SWTGridCell validationCell = result.getGrid().get(1);
		final SWTGridCell controlCell = result.getGrid().get(2);

		assertFalse(labelCell.isHorizontalGrab());
		assertEquals(1, labelCell.getHorizontalSpan());

		assertFalse(validationCell.isHorizontalGrab());
		assertEquals(1, validationCell.getHorizontalSpan());

		assertTrue(controlCell.isHorizontalGrab());
		assertTrue(controlCell.isVerticalGrab());
		assertEquals(1, controlCell.getHorizontalSpan());
		assertTrue(controlCell.isHorizontalFill());
		assertTrue(controlCell.isVerticalFill());
	}

}
