/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.emfstore.core.internal;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESRemoteProject;
import org.eclipse.emf.emfstore.server.exceptions.ESException;

/**
 * This is the EMFStore implementation of a {@link ECPCheckoutSource}.
 * 
 * @author Eugen Neufeld
 * 
 */
public class EMFStoreProjectWrapper implements ECPCheckoutSource {

	private final InternalRepository repository;

	private final ESRemoteProject remoteProject;

	/**
	 * The Constructor fro creating an {@link EMFStoreProjectWrapper}.
	 * 
	 * @param repository the repository for this CheckoutSource
	 * @param remoteProject the remote project to be checked out
	 */
	public EMFStoreProjectWrapper(InternalRepository repository, ESRemoteProject remoteProject) {
		this.repository = repository;
		this.remoteProject = remoteProject;
	}

	/** {@inheritDoc} **/
	public ECPRepository getRepository() {
		return repository;
	}

	/** {@inheritDoc} **/
	public ECPProvider getProvider() {
		return repository.getProvider();
	}

	/** {@inheritDoc} **/
	public String getDefaultCheckoutName() {
		return remoteProject.getProjectName();
	}

	/** {@inheritDoc} **/
	public void checkout(String projectName, ECPProperties projectProperties) throws ECPProjectWithNameExistsException {
		try {
			remoteProject.checkout(projectName, new NullProgressMonitor());
		} catch (final ESException ex) {
			Activator.log(ex);
		}
	}

	/**
	 * This return the {@link EMFStoreCheckoutData} used in this wrapper.
	 * 
	 * @return the {@link EMFStoreCheckoutData} used
	 */
	public ESRemoteProject getCheckoutData() {
		return remoteProject;
	}

}
