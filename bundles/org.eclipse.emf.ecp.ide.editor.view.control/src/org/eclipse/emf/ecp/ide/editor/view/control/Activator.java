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
package org.eclipse.emf.ecp.ide.editor.view.control;

import org.eclipse.emf.ecp.ide.view.service.IDEViewModelRegistry;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The Activator.
 *
 * @author Eugen Neufeld
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext context;

	/**
	 * The {@link BundleContext}.
	 *
	 * @return {@link BundleContext}
	 */
	static BundleContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	/**
	 * Return the {@link IDEViewModelRegistry}.
	 *
	 * @return the {@link IDEViewModelRegistry}
	 */
	public static IDEViewModelRegistry getViewModelRegistry() {
		final ServiceReference<IDEViewModelRegistry> serviceReference = context
			.getServiceReference(IDEViewModelRegistry.class);
		if (serviceReference == null) {
			return null;
		}
		return context.getService(serviceReference);
	}

}
