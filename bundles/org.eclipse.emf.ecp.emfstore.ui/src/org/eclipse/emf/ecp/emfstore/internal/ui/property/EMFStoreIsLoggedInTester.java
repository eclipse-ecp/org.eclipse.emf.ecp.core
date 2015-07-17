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
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.client.ESUsersession;
import org.eclipse.emf.emfstore.internal.client.model.util.EMFStoreCommandWithResult;

/**
 * This tests whether a user is loggedIn to a specific repository.
 *
 * @author Eugen Neufeld
 */
public class EMFStoreIsLoggedInTester extends PropertyTester {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[],
	 *      java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, final Object expectedValue) {
		if (receiver instanceof ECPRepository && expectedValue instanceof Boolean) {
			final ECPRepository ecpRepository = (ECPRepository) receiver;
			final ESServer serverInfo = EMFStoreProvider.INSTANCE.getServerInfo((InternalRepository) ecpRepository);
			final EMFStoreCommandWithResult<Boolean> command = new EMFStoreCommandWithResult<Boolean>() {
				@Override
				protected Boolean doRun() {
					final ESUsersession usersession = serverInfo.getLastUsersession();
					final Boolean ret = new Boolean(usersession != null && usersession.isLoggedIn());
					return ret.equals(expectedValue);
				}
			};
			final Boolean result = command.run(false);
			return result;
		}
		return false;
	}

}
