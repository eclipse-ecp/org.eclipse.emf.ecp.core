/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 * Christian W. Damus - bug 547787
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import java.util.Collection;
import java.util.Collections;

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
	static {
		final Bundle bundle = FrameworkUtil.getBundle(ViewProviderHelper.class);
		if (bundle != null) {
			bundleContext = bundle.getBundleContext();
		}
	}
	private static BundleContext bundleContext;

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

	/**
	 * Retrieve a {@link VView} for a domain model {@code object} from the most confident of my
	 * {@linkplain EMFFormsViewService#addProvider(IViewProvider) registered view providers}.
	 *
	 * @param object the domain model object for which a view is to be requested
	 * @param properties the {@link VViewModelProperties properties} for providing the view, that
	 *            may or may not include matching filters
	 * @param requiredKeys a subset (possibly empty) of the keys in the {@code properties} that
	 *            must be matched by any view model that I would provide. If any of these keys does not match
	 *            a view model, then that view model must not be provided. Otherwise, it may just be less
	 *            preferred than some other view model that does match
	 *
	 * @return a view model for the given domain model {@code object} or {@code null} if no
	 *         suitable provider could be found to provide one
	 *
	 * @since 1.22
	 */
	public static VView getView(EObject object, VViewModelProperties properties, Collection<String> requiredKeys) {
		VView result = null;

		if (bundleContext == null) {
			return result;
		}

		final ServiceReference<EMFFormsFilteredViewService> serviceReference = bundleContext
			.getServiceReference(EMFFormsFilteredViewService.class);
		if (serviceReference == null) {
			return result;
		}

		final EMFFormsFilteredViewService viewService = bundleContext.getService(serviceReference);
		try {
			if (requiredKeys == null) {
				requiredKeys = Collections.emptySet();
			}
			result = viewService.getView(object, properties, requiredKeys);
		} finally {
			bundleContext.ungetService(serviceReference);
		}

		return result;
	}

}
