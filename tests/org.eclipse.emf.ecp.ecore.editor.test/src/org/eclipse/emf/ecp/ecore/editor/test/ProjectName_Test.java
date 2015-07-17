/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ecore.editor.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.ecore.editor.util.ProjectHelper;
import org.junit.Before;
import org.junit.Test;

public class ProjectName_Test {

	private ProjectHelper projectHelper;

	@Before
	public void init() {
		projectHelper = new ProjectHelper("");
	}

	@Test
	public void testGetProjectName() {
		projectHelper.setProjectFullName("org.eclipse.ecp.emf.ecore.editor");
		final String string = projectHelper.getProjectName();
		assertEquals("editor", string);

	}

	@Test
	public void testGetProjectNameOnePart() {
		projectHelper.setProjectFullName("org");
		final String string = projectHelper.getProjectName();
		assertEquals("org", string);
	}

	@Test
	public void testGetNSPrefix() {
		projectHelper.setProjectFullName("org.eclipse.ecp.emf.ecore.editor");
		final String string = projectHelper.getNSPrefix();
		assertEquals("org.eclipse.ecp.emf.ecore", string);
	}

	@Test
	public void testGetNSURL() {
		projectHelper.setProjectFullName("org.eclipse.ecp.emf.ecore.editor");
		final String string = projectHelper.getNSURL();
		assertEquals("http://eclipse.org/ecp/emf/ecore/editor", string);
	}

}
