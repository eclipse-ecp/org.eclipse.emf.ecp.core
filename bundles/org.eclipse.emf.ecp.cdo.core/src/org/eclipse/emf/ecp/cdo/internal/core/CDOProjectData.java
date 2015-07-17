/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.cdo.internal.core;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.workspace.CDOWorkspace;
import org.eclipse.emf.cdo.workspace.CDOWorkspaceConfiguration;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.InternalProject;

/**
 * Represents the data of an ECP project.
 *
 * @author Eike Stepper
 */
public final class CDOProjectData {
	private final InternalProject project;

	private CDOWorkspace workspace;

	private CDOTransaction transaction;

	private CDOResource rootResource;

	/**
	 * Default constructor.
	 *
	 * @param project the project
	 */
	public CDOProjectData(InternalProject project) {
		this.project = project;
	}

	/**
	 * Returns the project.
	 *
	 * @return the project
	 */
	public InternalProject getProject() {
		return project;
	}

	/**
	 * Returns the {@link CDOWorkspace} of the project.
	 *
	 * @return the {@link CDOWorkspace}
	 */
	public synchronized CDOWorkspace getWorkspace() {
		if (workspace == null) {
			final CDOWorkspaceConfiguration config = createWorkspaceConfiguration();
			workspace = config.open();
		}

		return workspace;
	}

	/**
	 * Check out the {@link CDOWorkspace} of the project.
	 *
	 * @return the {@link CDOWorkspace}
	 */
	public synchronized CDOWorkspace checkoutWorkspace() {
		final CDOWorkspaceConfiguration config = createWorkspaceConfiguration();
		workspace = config.checkout();
		return workspace;
	}

	/**
	 * Get the a transaction fot the project.
	 *
	 * @return the {@link CDOTransaction}
	 */
	public synchronized CDOTransaction getTransaction() {
		if (transaction == null) {
			final ResourceSet resourceSet = project.getEditingDomain().getResourceSet();
			transaction = getWorkspace().openTransaction(resourceSet);
		}

		return transaction;
	}

	/**
	 * Get the root resource of the {@link CDOTransaction}, @see {@link CDOTransaction#getRootResource()}.
	 *
	 * @return the {@link CDOResource}
	 */
	public synchronized CDOResource getRootResource() {
		if (rootResource == null) {
			rootResource = getTransaction().getRootResource();
		}

		return rootResource;
	}

	/**
	 * Dispose all resources of the project data.
	 */
	public void dispose() {
		if (rootResource != null) {
			rootResource = null;
		}

		if (transaction != null) {
			transaction.close();
			transaction = null;
		}

		if (workspace != null) {
			workspace.close();
			workspace = null;
		}
	}

	private CDOWorkspaceConfiguration createWorkspaceConfiguration() {
		final CDOProvider provider = (CDOProvider) ECPUtil.getResolvedElement(project.getProvider());
		return provider.createWorkspaceConfiguration(project);
	}
}
