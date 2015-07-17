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

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.junit.Before;
import org.junit.BeforeClass;

@SuppressWarnings("restriction")
public abstract class AbstractTest {
	private static ECPProjectManager projectManager;
	private static ECPProvider provider;
	private static ECPRepository repository;

	@BeforeClass
	public static void init() throws ECPProjectWithNameExistsException {
		provider = ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME);
		repository = ECPUtil.getECPRepositoryManager().addRepository(provider, "repositoryName", "repositoryLabel",
			"description", getNewProperties());
		projectManager = ECPUtil.getECPProjectManager();

	}

	@Before
	public void cleanup() throws ECPProjectWithNameExistsException {
		for (final ECPProject existingProject : projectManager.getProjects()) {
			existingProject.delete();
		}
	}

	public static ECPProjectManager getProjectManager() {
		return projectManager;
	}

	public static ECPProvider getProvider() {
		return provider;
	}

	public ECPRepository getRepository() {
		return repository;
	}

	public static ECPProperties getNewProperties() {
		final ECPProperties properties = ECPUtil.createProperties();
		properties.addProperty(EMFStoreProvider.PROP_REPOSITORY_URL, "localhost");
		properties.addProperty(EMFStoreProvider.PROP_PORT, "8080");
		properties.addProperty(EMFStoreProvider.PROP_CERTIFICATE,
			"emfstore test certificate (do not use in production!)");
		return properties;
	}
}
