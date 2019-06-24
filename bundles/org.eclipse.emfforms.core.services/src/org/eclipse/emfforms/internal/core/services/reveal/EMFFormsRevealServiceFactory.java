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
package org.eclipse.emfforms.internal.core.services.reveal;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealProvider;
import org.eclipse.emfforms.spi.core.services.reveal.EMFFormsRevealService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceFactory;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServicePolicy;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewServiceScope;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * Factory that creates the EMF Forms reveal service.
 *
 * @since 1.22
 */
@Component(name = "EMFFormsRevealServiceFactory")
public class EMFFormsRevealServiceFactory implements EMFFormsViewServiceFactory<EMFFormsRevealService> {

	private final Object sync = new Object();

	private final Collection<EMFFormsRevealProvider> providers = new HashSet<>();

	private final Map<EMFFormsViewContext, WeakReference<EMFFormsRevealService>> serviceInstances = new WeakHashMap<>();

	//
	// View service factory protocol
	//

	@Override
	public EMFFormsViewServiceScope getScope() {
		return EMFFormsViewServiceScope.GLOBAL;
	}

	@Override
	public EMFFormsViewServicePolicy getPolicy() {
		// We need to track all child contexts as they come and go for master/detail support
		return EMFFormsViewServicePolicy.IMMEDIATE;
	}

	@Override
	public double getPriority() {
		return 0.0;
	}

	@Override
	public Class<EMFFormsRevealService> getType() {
		return EMFFormsRevealService.class;
	}

	@Override
	public EMFFormsRevealService createService(EMFFormsViewContext context) {
		final EMFFormsRevealService result = new EMFFormsRevealServiceImpl(context);

		init(result, context);

		return result;
	}

	//
	// Service instance management
	//

	private void init(EMFFormsRevealService service, EMFFormsViewContext context) {
		synchronized (sync) {
			providers.forEach(service::addRevealProvider);
			serviceInstances.put(context, new WeakReference<>(service));
		}
	}

	/**
	 * Update service instances that are still valid.
	 *
	 * @param action the update action to apply to services
	 */
	private void updateServices(Consumer<? super EMFFormsRevealService> action) {
		synchronized (sync) {
			for (final Iterator<WeakReference<EMFFormsRevealService>> iter = serviceInstances.values().iterator(); iter
				.hasNext();) {
				final WeakReference<EMFFormsRevealService> next = iter.next();
				final EMFFormsRevealService service = next.get();

				if (service == null) {
					// It was reclaimed
					iter.remove();
				} else {
					action.accept(service);
				}
			}
		}
	}

	//
	// Service dependencies
	//

	/**
	 * Inject a reveal provider dependency.
	 *
	 * @param provider the reveal provider to add
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addRevealProvider(EMFFormsRevealProvider provider) {
		synchronized (sync) {
			providers.add(provider);
			updateServices(service -> service.addRevealProvider(provider));
		}
	}

	/**
	 * Uninject a reveal provider dependency.
	 *
	 * @param provider the reveal provider to remove
	 */
	public void removeRevealProvider(EMFFormsRevealProvider provider) {
		synchronized (sync) {
			updateServices(service -> service.removeRevealProvider(provider));
			providers.remove(provider);
		}
	}

}
