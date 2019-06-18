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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.selector.bool.model.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationFactory;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector;
import org.eclipse.emf.ecp.view.template.selector.bool.model.VTAndSelector;
import org.eclipse.emf.ecp.view.template.selector.bool.model.VTBoolFactory;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotationFactory;
import org.junit.Before;
import org.junit.Test;

public class VTAndSelectorImpl_Test {

	private static final String FOO = "FOO"; //$NON-NLS-1$
	private static final String BAR = "BAR"; //$NON-NLS-1$

	private VControl control;

	private static void createAnnotation(VElement parent, String key, String value) {
		final VAnnotation annotation = VAnnotationFactory.eINSTANCE.createAnnotation();
		annotation.setKey(key);
		annotation.setValue(value);
		parent.getAttachments().add(annotation);
	}

	private static VTAnnotationSelector selector(String key, String value) {
		final VTAnnotationSelector selector = VTAnnotationFactory.eINSTANCE
			.createAnnotationSelector();
		selector.setKey(key);
		selector.setValue(value);
		return selector;
	}

	private static VTAndSelector selector(VTStyleSelector... selectors) {
		final VTAndSelector andSelector = VTBoolFactory.eINSTANCE.createAndSelector();
		andSelector.getSelectors().addAll(Arrays.asList(selectors));
		return andSelector;
	}

	@Before
	public void before() {
		control = VViewFactory.eINSTANCE.createControl();
	}

	@Test
	public void noSelectors() {
		/* setup */
		final VTAndSelector selector = selector();

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void notAllMatching() {
		/* setup */
		createAnnotation(control, FOO, BAR);
		final VTAndSelector selector = selector(
			selector(FOO, BAR),
			selector(BAR, FOO));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void allMatching() {
		/* setup */
		createAnnotation(control, FOO, BAR);
		createAnnotation(control, BAR, FOO);
		final VTAndSelector selector = selector(
			selector(FOO, BAR),
			selector(BAR, FOO));

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(30d), applicable);
	}

}
