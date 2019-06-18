/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Neil Mackenzie - initial implementation
 * Johannes Faltermeier - moved from ecp.core.rap
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.core.rap;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.emfstore.core.internal.ESWorkspaceProviderProvider;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 * Activator.
 *
 */
public class Activator extends Plugin {

	@Override
	public final void start(final BundleContext context) throws Exception {

		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 1000);
		context.registerService(ESWorkspaceProviderProvider.class,
			new ESWorkspaceProviderProviderFactory(), dictionary);
		super.start(context);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {

	}

}
