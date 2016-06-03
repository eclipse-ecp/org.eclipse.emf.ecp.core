/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * neilmack - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.core.internal;

import org.eclipse.emf.emfstore.internal.client.model.ESWorkspaceProviderImpl;

/**
 * @author neilmack
 *
 *         This class provides the relevant ESWorkspaceProvider
 */
public class ESWorkspaceProviderProviderImpl implements ESWorkspaceProviderProvider {

	private String token;

	/**
	 * Default constructor.
	 */
	public ESWorkspaceProviderProviderImpl() {

	}

	/**
	 * constructor. This constructor takes a token, the getESWorkspaceProviderInstance mothod
	 * of instances that have a token will try to recieve ESWorkspaceProviderImpl with the same token.
	 * This constructor is useful in applications where there may be more than one ESWorkspaceProviderInstance
	 * such as web based applications
	 *
	 * @param aToken a token to associate with the instance
	 */
	public ESWorkspaceProviderProviderImpl(String aToken) {

		token = aToken;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.emfstore.core.internal.ESWorkspaceProviderProvider#getESWorkspaceProviderInstance()
	 */
	@Override
	public ESWorkspaceProviderImpl getESWorkspaceProviderInstance() {
		// TODO Auto-generated method stub
		if (token == null) {
			return ESWorkspaceProviderImpl.getInstance();
		}
		return ESWorkspaceProviderImpl.getInstance(token);

	}

	/**
	 *
	 */
	public void removeESWorkspaceProviderInstance() {

		ESWorkspaceProviderImpl.removeInstance(token);

	}

}
