/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.workspace.ui.rap.test;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.rap.sessionprovider.test.MockSessionProvider;
import org.eclipse.emf.ecp.core.rap.sessionprovider.test.MockSessionProvider.SessionProviderType;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.workspace.internal.core.WorkspaceProvider;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * @author neil 
 *  Test use of WorkspaceProvider with RAP and multiple sessions.
 */
@SuppressWarnings("restriction")
public class RAPWorkspace_PTest extends TestCase {

	/**
	 * Test that we can create and retrieve projects using the
	 *  workspace provider  when we have multiple sessions.
	 */
	@Test
	public final void testWorkspaceProviderService() throws ECPProjectWithNameExistsException {
		MockSessionProvider.getInstance();
		MockSessionProvider.
			setSessionProvider(SessionProviderType.SAME_SESSION_ID);
	
		final ECPProjectManager projectManager1 = 
					ECPUtil.getECPProjectManager();
		final ECPProvider provider1 = ECPUtil.getECPProviderRegistry()
			.getProvider(WorkspaceProvider.NAME);
	
		
		ECPProperties props1 = ECPUtil.createProperties();	
		props1.addProperty(WorkspaceProvider.PROP_ROOT_URI, 
								WorkspaceProvider.VIRTUAL_ROOT_URI);		
		final ECPProject project1 =
				projectManager1.
					createProject(provider1, "Test1",props1); //$NON-NLS-1$
		
		MockSessionProvider.
			setSessionProvider(SessionProviderType.DIFFERENT_SESSION_ID);
		
		final ECPProjectManager projectManager2 = ECPUtil.getECPProjectManager();		
		final ECPProvider provider2 =  ECPUtil.getECPProviderRegistry()
				.getProvider(WorkspaceProvider.NAME);
		ECPProperties props2 = ECPUtil.createProperties();	
		props2.addProperty
			(WorkspaceProvider.PROP_ROOT_URI,WorkspaceProvider.VIRTUAL_ROOT_URI);
		final ECPProject project2 = 
				projectManager2.
					createProject(provider2, "Test2",props2); //$NON-NLS-1$
	
		assertNotSame(projectManager1, projectManager2);
		
		final Collection<ECPProject> projects1 = projectManager1.getProjects();
		assertEquals(1, projects1.size());
		assertTrue(projects1.contains(project1));
		

		final Collection<ECPProject> projects2 = projectManager2.getProjects();
		assertEquals(1, projects2.size());
		assertTrue(projects2.contains(project2));
	
	}





}

