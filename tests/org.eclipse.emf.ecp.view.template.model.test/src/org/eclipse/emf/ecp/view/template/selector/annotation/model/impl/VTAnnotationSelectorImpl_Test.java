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
package org.eclipse.emf.ecp.view.template.selector.annotation.model.impl;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.view.spi.model.VAttachment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.template.model.VTStyleSelector;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationFactory;
import org.eclipse.emf.ecp.view.template.selector.annotation.model.VTAnnotationSelector;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotation;
import org.eclipse.emf.emfforms.spi.view.annotation.model.VAnnotationFactory;
import org.junit.Before;
import org.junit.Test;

public class VTAnnotationSelectorImpl_Test {

	private static final String BAR = "BAR"; //$NON-NLS-1$
	private static final String FOO = "FOO"; //$NON-NLS-1$

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

	@Before
	public void before() {
		control = VViewFactory.eINSTANCE.createControl();
		final VAttachment dummyAttachment = VViewFactory.eINSTANCE.createDateTimeDisplayAttachment();
		control.getAttachments().add(dummyAttachment);
	}

	@Test
	public void noAnnotations() {
		/* setup */
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void nonMatchingAnnotation() {
		/* setup */
		createAnnotation(control, BAR, FOO);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void matchingAnnotation() {
		/* setup */
		createAnnotation(control, FOO, BAR);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void nullKeyExpectedNotFound() {
		/* setup */
		createAnnotation(control, BAR, FOO);
		final VTAnnotationSelector selector = selector(null, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void nullKeyExpectedFound() {
		/* setup */
		createAnnotation(control, null, BAR);
		final VTAnnotationSelector selector = selector(null, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void keyExpectedNullFound() {
		/* setup */
		createAnnotation(control, null, BAR);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void nullValueExpectedNotFound() {
		/* setup */
		createAnnotation(control, FOO, BAR);
		final VTAnnotationSelector selector = selector(FOO, null);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void nullValueExpectedFound() {
		/* setup */
		createAnnotation(control, FOO, null);
		final VTAnnotationSelector selector = selector(FOO, null);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

	@Test
	public void valueExpectedNullFound() {
		/* setup */
		createAnnotation(control, FOO, null);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void multipleMatchingKeys1() {
		/* first one wins */
		/* setup */
		createAnnotation(control, FOO, FOO);
		createAnnotation(control, FOO, BAR);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(VTStyleSelector.NOT_APPLICABLE, applicable);
	}

	@Test
	public void multipleMatchingKeys2() {
		/* first one wins */
		/* setup */
		createAnnotation(control, FOO, BAR);
		createAnnotation(control, FOO, FOO);
		final VTAnnotationSelector selector = selector(FOO, BAR);

		/* act */
		final Double applicable = selector.isApplicable(control, null);

		/* assert */
		assertEquals(new Double(15d), applicable);
	}

}
