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
package org.eclipse.emf.ecp.view.custom.internal.ui;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.ui.view.custom.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	// END SUPRESS CATCH EXCEPTION

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/** The control factory reference. */
	private static ServiceReference<ECPControlFactory> controlFactoryReference;

	/**
	 * Gets the {@link ECPControlFactory}.
	 * 
	 * @return the {@link ECPControlFactory}
	 */
	public static ECPControlFactory getECPControlFactory() {
		if (controlFactoryReference == null) {
			controlFactoryReference = getDefault().getBundle().getBundleContext()
				.getServiceReference(ECPControlFactory.class);
		}
		return getDefault().getBundle().getBundleContext().getService(controlFactoryReference);
	}

	/**
	 * Unget the {@link ECPControlFactory}.
	 */
	public static void ungetECPControlFactory() {
		if (controlFactoryReference == null) {
			return;
		}
		getDefault().getBundle().getBundleContext().ungetService(controlFactoryReference);
		controlFactoryReference = null;
	}
}
