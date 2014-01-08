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
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.property;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.emf.emfstore.client.ESServer;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;

public class EmfStoreLocalServerAndNotRunningTester extends PropertyTester {

	private static final Set<String> ALLOWEDLOCALURIS = new HashSet<String>();
	static {
		ALLOWEDLOCALURIS.add("localhost");
		ALLOWEDLOCALURIS.add("127.0.0.1");
	}

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof ECPRepository && expectedValue instanceof Boolean) {
			final ECPRepository ecpRepository = (ECPRepository) receiver;
			EMFStoreProvider emfStoreProvider = (EMFStoreProvider) ECPUtil
				.getResolvedElement(ECPUtil.getECPProviderRegistry().getProvider(EMFStoreProvider.NAME));
			final ESServer serverInfo = emfStoreProvider.getServerInfo((InternalRepository) ecpRepository);
			if (ALLOWEDLOCALURIS.contains(serverInfo.getURL())) {
				if (EMFStoreController.getInstance() == null) {
					return Boolean.TRUE.equals(expectedValue);
				}
			}
		}
		return Boolean.FALSE.equals(expectedValue);
	}
}
