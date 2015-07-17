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
package org.eclipse.emf.ecp.core.rap;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.util.observer.ECPObserverBus;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * This is the Activator for the ECP Core RAP plugin.
 *
 */
public class Activator extends Plugin {

	@Override
	public final void start(final BundleContext context) throws Exception {
		final Dictionary<String, Object> dictionary =
			new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 1000);
		context.registerService(ECPProjectManager.class,
			new ECPProjectManagerFactory(), dictionary);
		context.registerService(ECPProviderRegistry.class,
			new ECPProviderRegistryFactory(), dictionary);
		context.registerService(ECPRepositoryManager.class,
			new ECPRepositoryManagerFactory(), dictionary);
		context.registerService(ECPObserverBus.class,
			new ECPObserverBusFactory(), dictionary);
		super.start(context);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {

	}

}
