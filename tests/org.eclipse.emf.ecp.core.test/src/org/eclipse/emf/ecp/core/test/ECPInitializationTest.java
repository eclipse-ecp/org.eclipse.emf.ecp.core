/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.core.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Eugen
 *
 */
@SuppressWarnings("restriction")
public class ECPInitializationTest {

	// This test runs in 8000 ms locally. On hudson this test needs probably 400000 ms -> 50 times slower !
	private static final int MAXIMAL_ALLOWED_DURATION = 150000;
	private ECPProject project;

	@Before
	public void before() {

		final BundleContext ctx = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
		final Bundle[] bundles = ctx.getBundles();

		for (final Bundle bundle : bundles) {
			if (bundle.getSymbolicName().contains("transaction")) {
				final int state = bundle.getState();
				System.out.println("DEBUG: Transactional plugin " + bundle.getSymbolicName() + " is in state " + state
					+ ".");
			}
		}

		try {
			project = ECPUtil.getECPProjectManager().createProject(
				ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME), "test");
		} catch (final ECPProjectWithNameExistsException e) {
			fail(e.getMessage());
		}
		System.out.println("DEBUG: There are " + ECPUtil.getECPProjectManager().getProjects().size() + " projects");
	}

	@Test
	public void createProjectAddElementTest() {
		final long startTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < 60000; i++) {

			project.getContents().add(EcoreFactory.eINSTANCE.createEClass());

			if (i % 1000 == 0) {
				if (System.currentTimeMillis() - startTimeMillis > MAXIMAL_ALLOWED_DURATION) {
					fail("Taking too long");
				}
				System.out
					.println("Added " + i + "Items, Time passed " + (System.currentTimeMillis() - startTimeMillis));
			}
		}
		assertTrue(System.currentTimeMillis() - startTimeMillis < MAXIMAL_ALLOWED_DURATION);
	}
}
