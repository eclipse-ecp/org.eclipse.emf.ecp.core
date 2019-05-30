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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.provider;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.provider.IFilteredViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;

/**
 * Filtering view provider adapter. If any filter keys are required, then
 * it provides nothing. Otherwise, it delegates view provision.
 */
final class FilteredViewProviderAdapter implements IFilteredViewProvider {

	private static final Function<IViewProvider, IFilteredViewProvider> FACTORY = FilteredViewProviderAdapter::new;

	private static final Map<IViewProvider, IFilteredViewProvider> INSTANCES = new ConcurrentHashMap<>();

	private final IViewProvider delegate;

	/**
	 * Initializes me.
	 *
	 * @param delegate the view provider to which I delegate
	 */
	private FilteredViewProviderAdapter(IViewProvider delegate) {
		super();

		this.delegate = delegate;
	}

	@Override
	public double canProvideViewModel(EObject object, VViewModelProperties properties,
		Collection<String> requiredKeys) {

		return requiredKeys == null || requiredKeys.isEmpty()
			? delegate.canProvideViewModel(object, properties)
			: NOT_APPLICABLE;
	}

	@Override
	public VView provideViewModel(EObject object, VViewModelProperties properties, Collection<String> requiredKeys) {
		return requiredKeys == null || requiredKeys.isEmpty()
			? delegate.provideViewModel(object, properties)
			: null;
	}

	/**
	 * Obtain the canonical adapter for a view {@code provider}.
	 *
	 * @param provider a legacy view provider
	 * @return the filtering adapter
	 */
	static IFilteredViewProvider adapt(IViewProvider provider) {
		if (provider instanceof IFilteredViewProvider) {
			return (IFilteredViewProvider) provider;
		}
		return INSTANCES.computeIfAbsent(provider, FACTORY);
	}

	/**
	 * Forgets an {@code adapter} that is no longer needed.
	 *
	 * @param adapter an adapter to dispose
	 */
	static void dispose(IFilteredViewProvider adapter) {
		if (adapter instanceof FilteredViewProviderAdapter) {
			INSTANCES.remove(((FilteredViewProviderAdapter) adapter).delegate);
		}
	}

}
