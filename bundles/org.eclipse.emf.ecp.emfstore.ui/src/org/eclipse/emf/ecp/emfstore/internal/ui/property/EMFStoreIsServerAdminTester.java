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
package org.eclipse.emf.ecp.emfstore.internal.ui.property;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProjectWrapper;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.internal.client.accesscontrol.AccessControlHelper;
import org.eclipse.emf.emfstore.internal.client.model.ServerInfo;
import org.eclipse.emf.emfstore.internal.client.model.Usersession;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESServerImpl;
import org.eclipse.emf.emfstore.internal.server.exceptions.AccessControlException;

/**
 * This tests whether a user is the serveradmin on a specific repository.
 *
 * @author Eugen Neufeld
 * @author Tobias Verhoeven
 */
public final class EMFStoreIsServerAdminTester extends PropertyTester {

	/** {@inheritDoc} */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		InternalRepository repository = null;

		if (receiver instanceof EMFStoreProjectWrapper) {
			final EMFStoreProjectWrapper wrapper = (EMFStoreProjectWrapper) receiver;
			repository = (InternalRepository) wrapper.getRepository();

		} else if (receiver instanceof InternalRepository) {
			repository = (InternalRepository) receiver;
		}
		if (repository == null) {
			return false;
		}

		final ServerInfo serverInfo = ((ESServerImpl) EMFStoreProvider.INSTANCE.getServerInfo(repository))
			.toInternalAPI();
		final Usersession usersession = serverInfo.getLastUsersession();
		boolean isAdmin = false;
		if (usersession != null && usersession.getACUser() != null) {
			// TODO EMFStore Constructor is missing
			final AccessControlHelper accessControlHelper = new AccessControlHelper(usersession);
			try {
				accessControlHelper.checkServerAdminAccess();
				isAdmin = true;
			} catch (final AccessControlException e) {
				// not an admin -> ignore
			}
		}
		return Boolean.valueOf(isAdmin).equals(expectedValue);
	}

}
