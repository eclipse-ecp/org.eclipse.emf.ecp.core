/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;

/**
 * @author jfaltermeier
 *
 */
public class FeatureStrategy implements Strategy {

	private final Map<EStructuralFeature, Set<EMFObserver>> registry = new LinkedHashMap<EStructuralFeature, Set<EMFObserver>>();
	private final Map<EMFObserver, Set<EStructuralFeature>> observerToKey = new LinkedHashMap<EMFObserver, Set<EStructuralFeature>>();

	/**
	 * Registers an observer.
	 * @param observer the observer
	 * @param feature the feature
	 */
	public void register(EMFObserver observer, EStructuralFeature feature) {
		if (!registry.containsKey(feature)) {
			registry.put(feature, new LinkedHashSet<EMFObserver>());
		}
		registry.get(feature).add(observer);

		if (!observerToKey.containsKey(observer)) {
			observerToKey.put(observer, new LinkedHashSet<EStructuralFeature>());
		}
		observerToKey.get(observer).add(feature);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getObservers(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public Set<EMFObserver> getObservers(Notification notification) {
		final Object feature = notification.getFeature();
		if (!registry.containsKey(feature)) {
			return Collections.emptySet();
		}
		return new LinkedHashSet<EMFObserver>(registry.get(feature));
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#deregister(EMFObserver)
	 */
	@Override
	public void deregister(EMFObserver observer) {
		final Set<EStructuralFeature> keys = observerToKey.remove(observer);
		if (keys == null) {
			return;
		}
		for (final EStructuralFeature feature : keys) {
			final Set<EMFObserver> set = registry.get(feature);
			set.remove(observer);
			if (set.isEmpty()) {
				registry.remove(feature);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getAllObservers()
	 */
	@Override
	public Set<EMFObserver> getAllObservers() {
		return new LinkedHashSet<EMFObserver>(observerToKey.keySet());
	}

}
