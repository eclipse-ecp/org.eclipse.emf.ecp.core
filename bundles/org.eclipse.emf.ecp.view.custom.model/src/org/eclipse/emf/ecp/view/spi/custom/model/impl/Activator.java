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
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator handling the life cycle.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
@SuppressWarnings("deprecation")
public class Activator extends Plugin {

	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
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

	private ServiceReference<ECPControlFactory> controlFactoryReference;

	/**
	 * Returns the {@link ECPControlFactory}.
	 *
	 * @return the instance of the {@link ECPControlFactory}
	 */
	public ECPControlFactory getECPControlFactory() {
		if (controlFactoryReference == null) {
			controlFactoryReference = getBundle().getBundleContext()
				.getServiceReference(ECPControlFactory.class);
		}
		return getBundle().getBundleContext().getService(controlFactoryReference);
	}

	/**
	 * Allows to release the {@link ECPControlFactory} service.
	 */
	public void ungetECPControlFactory() {
		if (controlFactoryReference == null) {
			return;
		}
		getBundle().getBundleContext().ungetService(controlFactoryReference);
		controlFactoryReference = null;
	}

}
