/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.selector.hierarchy.model.impl;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationFactory;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchyFactory;
import org.eclipse.emf.ecp.view.template.selector.hierarchy.model.VTHierarchySelector;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotationFactory;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGrid;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridCell;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlGridRow;
import org.eclipse.emf.emfforms.spi.view.controlgrid.model.VControlgridFactory;
import org.junit.Before;
import org.junit.Test;

public class VTHierarchySelectorImpl_Test {

	private static final String FOO = "FOO"; //$NON-NLS-1$
	private static final String BAR = "BAR"; //$NON-NLS-1$

	private VView view;
	private VControlGrid layout;
	private VControl control;

	private static void createAnnotation(VElement parent, String key, String value) {
		final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
		annotation.setKey(key);
		annotation.setValue(value);
		parent.getAttachments().add(annotation);
	}

	private static VTHierarchySelector selector(VTStyleSelector selector) {
		final VTHierarchySelector hierarchySelector = VTHierarchyFactory.eINSTANCE.createHierarchySelector();
		hierarchySelector.setSelector(selector);
		return hierarchySelector;
	}

	private static VTAnnotationSelector selector(String key, String value) {
		final VTAnnotationSelector selector = VTAnnotationFactory.eINSTANCE
			.createAnnotationSelector();
		selector.setKey(key);
		selector.setValue(value);
		return selector;
	}

	@Before
	public void before() {
		view = VViewFactory.eINSTANCE.createView();

		layout = VControlgridFactory.eINSTANCE.createControlGrid();
		view.getChildren().add(layout);

		final VControlGridRow row = VControlgridFactory.eINSTANCE.createControlGridRow();
		layout.getRows().add(row);

		final VControlGridCell cell = VControlgridFactory.eINSTANCE.createControlGridCell();
		row.getCells().add(cell);

		control = VViewFactory.eINSTANCE.createControl();
		cell.setControl(control);
	}

	@Test
	public void selectorNull() {
		/* setup */
		final VTHierarchySelector selector = selector(null);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void applicable1() {
		/* setup */
		createAnnotation(control, FOO, BAR);
		final VTHierarchySelector selector = selector(selector(FOO, BAR));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void applicable2() {
		/* setup */
		createAnnotation(layout, FOO, BAR);
		final VTHierarchySelector selector = selector(selector(FOO, BAR));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void applicable3() {
		/* setup */
		createAnnotation(view, FOO, BAR);
		final VTHierarchySelector selector = selector(selector(FOO, BAR));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void notApplicable() {
		/* setup */
		final VTHierarchySelector selector = selector(selector(FOO, BAR));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

}
