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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;

/**
 * @author jfaltermeier
 *
 */
public class ContainingEClassStrategy implements Strategy {
	private final Map<EClass, Set<EMFObserver>> registry = new LinkedHashMap<EClass, Set<EMFObserver>>();
	private final Map<EMFObserver, Set<EClass>> observerToKey = new LinkedHashMap<EMFObserver, Set<EClass>>();

	/**
	 * Registers an observer.
	 *
	 * @param eClass the containing eclass
	 * @param observer the observer
	 */
	void register(EClass eClass, EMFObserver observer) {
		if (!registry.containsKey(eClass)) {
			registry.put(eClass, new LinkedHashSet<EMFObserver>());
		}
		registry.get(eClass).add(observer);

		if (!observerToKey.containsKey(observer)) {
			observerToKey.put(observer, new LinkedHashSet<EClass>());
		}
		observerToKey.get(observer).add(eClass);
	}

	@Override
	public Set<EMFObserver> getObservers(Notification notification) {
		final Set<EMFObserver> observers = new LinkedHashSet<EMFObserver>();
		final Set<EClass> allEClasses = getEClassFromNotification(notification);
		for (final EClass eClass : allEClasses) {
			final Set<EClass> eClassesToCheck = new LinkedHashSet<EClass>(eClass.getESuperTypes());
			eClassesToCheck.add(eClass);
			for (final EClass eClassToCheck : eClassesToCheck) {
				if (!registry.containsKey(eClassToCheck)) {
					continue;
				}
				observers.addAll(registry.get(eClassToCheck));
			}
		}
		return observers;
	}

	private Set<EClass> getEClassFromNotification(Notification notification) {
		final Set<EClass> allEClasses = new LinkedHashSet<EClass>();
		EObject notifier = (EObject) notification.getNotifier();
		while (notifier != null) {
			allEClasses.add(notifier.eClass());
			notifier = notifier.eContainer();
		}
		return allEClasses;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#deregister(org.eclipse.emf.ecp.changebroker.spi.EMFObserver)
	 */
	@Override
	public void deregister(EMFObserver observer) {
		final Set<EClass> keys = observerToKey.remove(observer);
		if (keys == null) {
			return;
		}
		for (final EClass eClass : keys) {
			final Set<EMFObserver> set = registry.get(eClass);
			set.remove(observer);
			if (set.isEmpty()) {
				registry.remove(eClass);
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
