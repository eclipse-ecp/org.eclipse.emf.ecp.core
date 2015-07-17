/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Tobias Verhoeven - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.emfstore.localserver.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;
import org.osgi.framework.BundleContext;

/**
 * The Activator for this plugin.
 *
 * @author Eugen Neufeld
 *
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.emfstore.localserver"; //$NON-NLS-1$

	private static Activator plugin;

	/** {@inheritDoc} */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;
		LocalEmfStore.startIfNeeded();
	}

	/** {@inheritDoc} */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		EMFStoreController.getInstance().stop();
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
