/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * The implementation of the EMFFormsScopedServicesFactory.
 *
 * @author Eugen Neufeld
 *
 */
@Component
public class EMFFormsViewServiceManagerImpl implements EMFFormsViewServiceManager {

	private final Map<Class<?>, EMFFormsViewServiceFactory<?>> localImmediateMap = new LinkedHashMap<Class<?>, EMFFormsViewServiceFactory<?>>();
	private final Map<Class<?>, EMFFormsViewServiceFactory<?>> localLazyMap = new LinkedHashMap<Class<?>, EMFFormsViewServiceFactory<?>>();
	private final Map<Class<?>, EMFFormsViewServiceFactory<?>> globalImmediateMap = new LinkedHashMap<Class<?>, EMFFormsViewServiceFactory<?>>();
	private final Map<Class<?>, EMFFormsViewServiceFactory<?>> globalLazyMap = new LinkedHashMap<Class<?>, EMFFormsViewServiceFactory<?>>();

	/**
	 * Called by OSGi whenever a new provider is available.
	 *
	 * @param provider The newly available provider
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected void addEMFFormsScopedServiceProvider(EMFFormsViewServiceFactory<?> provider) {
		final Map<Class<?>, EMFFormsViewServiceFactory<?>> serviceProviderMap = getServiceProviderMap(provider);
		double currentPrio = Double.NEGATIVE_INFINITY;

		if (serviceProviderMap.containsKey(provider.getType())) {
			currentPrio = serviceProviderMap.get(provider.getType()).getPriority();
		}

		if (currentPrio < provider.getPriority()) {
			serviceProviderMap.put(provider.getType(), provider);
		}
	}

	/**
	 * Called by OSGi whenever a provider is removed.
	 *
	 * @param provider The removed provider
	 */
	protected void removeEMFFormsScopedServiceProvider(EMFFormsViewServiceFactory<?> provider) {
		getServiceProviderMap(provider).remove(provider.getType());
	}

	private Map<Class<?>, EMFFormsViewServiceFactory<?>> getServiceProviderMap(
		EMFFormsViewServiceFactory<?> provider) {
		if (provider.getPolicy() == EMFFormsViewServicePolicy.IMMEDIATE
			&& provider.getScope() == EMFFormsViewServiceScope.GLOBAL) {
			return globalImmediateMap;
		} else if (provider.getPolicy() == EMFFormsViewServicePolicy.LAZY
			&& provider.getScope() == EMFFormsViewServiceScope.GLOBAL) {
			return globalLazyMap;
		} else if (provider.getPolicy() == EMFFormsViewServicePolicy.IMMEDIATE
			&& provider.getScope() == EMFFormsViewServiceScope.LOCAL) {
			return localImmediateMap;
		} else if (provider.getPolicy() == EMFFormsViewServicePolicy.LAZY
			&& provider.getScope() == EMFFormsViewServiceScope.LOCAL) {
			return localLazyMap;
		}
		throw new IllegalStateException(
			"This should never be reached as we check for all possible cases before.s Only when there is one more combination of policy and scope that we missed this can happen!"); //$NON-NLS-1$
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> getServiceOptional(Class<T> type,
		Map<Class<?>, EMFFormsViewServiceFactory<?>> classFactoryMap, EMFFormsViewContext emfFormsViewContext) {
		final EMFFormsViewServiceFactory<?> serviceProvider = classFactoryMap.get(type);
		if (serviceProvider != null) {
			return (Optional<T>) Optional.of(serviceProvider.createService(emfFormsViewContext));
		}
		return Optional.empty();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#createLocalImmediateService(java.lang.Class,EMFFormsViewContext)
	 */
	@Override
	public <T> Optional<T> createLocalImmediateService(Class<T> type, EMFFormsViewContext emfFormsViewContext) {
		return getServiceOptional(type, localImmediateMap, emfFormsViewContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#createLocalLazyService(java.lang.Class,EMFFormsViewContext)
	 */
	@Override
	public <T> Optional<T> createLocalLazyService(Class<T> type, EMFFormsViewContext emfFormsViewContext) {
		return getServiceOptional(type, localLazyMap, emfFormsViewContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#createGlobalImmediateService(java.lang.Class,EMFFormsViewContext)
	 */
	@Override
	public <T> Optional<T> createGlobalImmediateService(Class<T> type, EMFFormsViewContext emfFormsViewContext) {
		return getServiceOptional(type, globalImmediateMap, emfFormsViewContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#createGlobalLazyService(java.lang.Class,EMFFormsViewContext)
	 */
	@Override
	public <T> Optional<T> createGlobalLazyService(Class<T> type, EMFFormsViewContext emfFormsViewContext) {
		return getServiceOptional(type, globalLazyMap, emfFormsViewContext);
	}

	private Set<Class<?>> getAllTypes(Collection<EMFFormsViewServiceFactory<?>> factorySet) {
		final SortedSet<EMFFormsViewServiceFactory<?>> sortedFactories = new TreeSet<EMFFormsViewServiceFactory<?>>(
			new Comparator<EMFFormsViewServiceFactory<?>>() {

				@Override
				public int compare(EMFFormsViewServiceFactory<?> factory0, EMFFormsViewServiceFactory<?> factory1) {
					final int result = Double.compare(factory0.getPriority(), factory1.getPriority());
					if (result == 0) {
						return 1;
					}
					return result;
				}
			});
		for (final EMFFormsViewServiceFactory<?> factory : factorySet) {
			sortedFactories.add(factory);
		}
		final Set<Class<?>> result = new LinkedHashSet<Class<?>>();
		for (final EMFFormsViewServiceFactory<?> factory : sortedFactories) {
			result.add(factory.getType());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#getAllGlobalImmediateServiceTypes()
	 */
	@Override
	public Set<Class<?>> getAllGlobalImmediateServiceTypes() {
		// return globalImmediateMap.keySet();
		return getAllTypes(globalImmediateMap.values());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceManager#getAllLocalImmediateServiceTypes()
	 */
	@Override
	public Set<Class<?>> getAllLocalImmediateServiceTypes() {
		// return localImmediateMap.keySet();
		return getAllTypes(localImmediateMap.values());
	}

}
