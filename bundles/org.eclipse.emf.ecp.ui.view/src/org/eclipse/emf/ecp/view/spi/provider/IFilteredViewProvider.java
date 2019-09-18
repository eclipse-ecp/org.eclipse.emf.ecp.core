/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.spi.provider;

import static java.util.Collections.emptySet;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;

/**
 * A specialized <em>{@linkplain IViewProvider view provider}</em> that filters views
 * by properties in the <em>{@linkplain VViewModelProperties loading properties}</em>.
 * The mechanism by which matching of filters is performed is unspecified, but filters are
 * specified as key-value pairs in the {@code properties} parameter of each API method.
 *
 * @since 1.22
 */
public interface IFilteredViewProvider extends IViewProvider {

	/**
	 * Queries whether I {@linkplain #canProvideViewModel(EObject, VViewModelProperties, Collection) can provide}
	 * without any filter properties being required.
	 */
	@Override
	default double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
		return canProvideViewModel(eObject, properties, emptySet());
	}

	/**
	 * Queries the confidence with which I can provide a view model for the given domain model
	 * {@code object} where some keys in the {@code properties} are required to be matched in
	 * an implementation-specific way by the view models the I have access to.
	 *
	 * @param object the domain model object for which a view is to be requested
	 * @param properties the {@link VViewModelProperties properties} for providing the view, that
	 *            may or may not include matching filters
	 * @param requiredKeys a subset (possibly empty) of the keys in the {@code properties} that
	 *            must be matched by any view model that I would provide. If any of these keys does not match
	 *            a view model, then that view model must not be provided. Otherwise, it may just be less
	 *            preferred than some other view model that does match
	 *
	 * @return a <strong>positive</strong> double indicating how well I am suited to
	 *         provide a {@link VView} for the given {@code object}, or
	 *         {@link IViewProvider#NOT_APPLICABLE} if I cannot provide a view model
	 */
	double canProvideViewModel(EObject object, VViewModelProperties properties,
		Collection<String> requiredKeys);

	/**
	 * Obtains a {@linkplain #provideViewModel(EObject, VViewModelProperties, Collection) view model}
	 * without any filter properties being required.
	 */
	@Override
	default VView provideViewModel(EObject object, VViewModelProperties properties) {
		return provideViewModel(object, properties, emptySet());
	}

	/**
	 * Obtains the view model that I {@linkplain #canProvideViewModel(EObject, VViewModelProperties, Collection) can
	 * provide}
	 * for the given domain model {@code object}. This method is only called if a previous invocation of
	 * {@link #canProvideViewModel(EObject, VViewModelProperties, Collection)} with the same arguments
	 * returned the highest positive result amongst all available providers.
	 *
	 * @param object the domain model object for which a view is to be requested
	 * @param properties the {@link VViewModelProperties properties} for providing the view, that
	 *            may or may not include matching filters
	 * @param requiredKeys a subset (possibly empty) of the keys in the {@code properties} that
	 *            must be matched by any view model that I would provide. If any of these keys does not match
	 *            a view model, then that view model must not be provided. Otherwise, it may just be less
	 *            preferred than some other view model that does match
	 *
	 * @return the view model
	 */
	VView provideViewModel(EObject object, VViewModelProperties properties,
		Collection<String> requiredKeys);

}
