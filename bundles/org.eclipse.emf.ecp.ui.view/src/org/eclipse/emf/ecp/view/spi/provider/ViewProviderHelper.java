/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * Util class for retrieving a {@link VView} based on an {@link EObject}.
 *
 * @author Eugen Neufeld
 * @since 1.2
 *
 */
public final class ViewProviderHelper {

	private ViewProviderHelper() {
	}

	/**
	 * This allows to retrieve a {@link VView} based on an {@link EObject}. This method reads all {@link IViewProvider
	 * IViewProviders} and searches for the best fitting. If none can be found, then null is returned.
	 *
	 * @param eObject the {@link EObject} to find a {@link VView} for
	 * @param properties the {@link VViewModelProperties properties}. May be <code>null</code>
	 * @return a view model for the given {@link EObject} or null if no suited provider could be found
	 * @since 1.7
	 */
	public static VView getView(EObject eObject, VViewModelProperties properties) {
		final Bundle bundle = FrameworkUtil.getBundle(ViewProviderHelper.class);
		if (bundle == null) {
			return null;
		}
		final BundleContext bundleContext = bundle.getBundleContext();
		if (bundleContext == null) {
			return null;
		}
		final ServiceReference<EMFFormsViewService> serviceReference = bundleContext
			.getServiceReference(EMFFormsViewService.class);
		if (serviceReference == null) {
			return null;
		}
		final EMFFormsViewService viewService = bundleContext.getService(serviceReference);
		final VView view = viewService.getView(eObject, properties);
		bundleContext.ungetService(serviceReference);
		return view;
	}
}
