/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila- initial API and implementation
 ******************************************************************************/

package org.eclipse.emf.ecp.view.model.preview.common;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {
	/** The shared instance. **/
	private static Activator plugin;

	private static BundleContext context;

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.model.preview.common"; //$NON-NLS-1$

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		plugin = null;
	}

	/**
	 * Returns the Activator instance.
	 *
	 * @return the {@link Activator}
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
