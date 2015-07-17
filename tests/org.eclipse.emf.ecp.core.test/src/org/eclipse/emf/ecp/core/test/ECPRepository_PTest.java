/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.core.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.internal.core.ECPRepositoryImpl;
import org.junit.Test;

public class ECPRepository_PTest extends AbstractTest {

	private final ECPRepositoryImpl repository = (ECPRepositoryImpl) getRepository();

	@Test
	public void testGetOpenProjects() {
		try {
			assertEquals(0, repository.getOpenProjects().length);
			final ECPProject project = getProjectManager().createProject(repository, "Project", getNewProperties());
			assertEquals(1, repository.getOpenProjects().length);
			final ECPProject project2 = getProjectManager().createProject(repository, "Project2", getNewProperties());
			assertEquals(2, repository.getOpenProjects().length);
			project2.close();
			assertEquals(1, repository.getOpenProjects().length);
			project.delete();
			project2.delete();
			assertEquals(0, repository.getOpenProjects().length);
		} catch (final ECPProjectWithNameExistsException e) {
		}
	}
}
