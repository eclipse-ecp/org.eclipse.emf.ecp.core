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

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.exceptions.ECPProjectWithNameExistsException;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPProperties;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.core.InternalRepository;

/**
 * Wraps are CDO branch for ECP, so it can be checked out.
 * 
 * @author Eike Stepper
 */
public class CDOBranchWrapper implements ECPCheckoutSource {
	private final InternalRepository repository;

	private final String branchPath;

	/**
	 * Default constructor.
	 * 
	 * @param repository the repository
	 * @param branchPath the branch path
	 */
	public CDOBranchWrapper(InternalRepository repository, String branchPath) {
		this.repository = repository;
		this.branchPath = branchPath;
	}

	/** {@inheritDoc} */
	public ECPProvider getProvider() {
		return repository.getProvider();
	}

	/** {@inheritDoc} */
	public final InternalRepository getRepository() {
		return repository;
	}

	/**
	 * Returns the branch path of the wrapped CDO branch.
	 * 
	 * @return String of the path
	 */
	public final String getBranchPath() {
		return branchPath;
	}

	/**
	 * Return the name of the wrapped CDO branch.
	 * 
	 * @return the name
	 */
	public String getName() {
		int pos = branchPath.lastIndexOf('/');
		if (pos == -1) {
			return branchPath;
		}

		return branchPath.substring(pos + 1);
	}

	/** {@inheritDoc} */
	public String getDefaultCheckoutName() {
		return repository.getName() + "." + getName(); //$NON-NLS-1$
	}

	/** {@inheritDoc} */
	public void checkout(String projectName, ECPProperties projectProperties) throws ECPProjectWithNameExistsException {
		projectProperties.addProperty("branchPath", branchPath); //$NON-NLS-1$
		ECPUtil.getECPProjectManager().createProject(getRepository(), projectName, projectProperties);
	}

	@Override
	public String toString() {
		return getName();
	}
}
