/**
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.view.template.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The Activator.
 *
 * @author Eugen Neufeld
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext context;

	/**
	 * Method allowing to get the current instance.
	 *
	 * @return the current {@link BundleContext}.
	 */
	static BundleContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
