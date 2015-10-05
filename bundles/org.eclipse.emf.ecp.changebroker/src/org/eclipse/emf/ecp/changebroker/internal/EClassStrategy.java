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
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;

/**
 * @author jfaltermeier
 *
 */
public class EClassStrategy implements Strategy {

	private final Map<EClass, Set<ChangeObserver>> registry = new LinkedHashMap<EClass, Set<ChangeObserver>>();
	private final Map<ChangeObserver, Set<EClass>> observerToKey = new LinkedHashMap<ChangeObserver, Set<EClass>>();

	/**
	 * Registers an observer.
	 * @param observer the observer
	 * @param eClass the eclass
	 */
	public void register(ChangeObserver observer, EClass eClass) {
		if (!registry.containsKey(eClass)) {
			registry.put(eClass, new LinkedHashSet<ChangeObserver>());
		}
		registry.get(eClass).add(observer);

		if (!observerToKey.containsKey(observer)) {
			observerToKey.put(observer, new LinkedHashSet<EClass>());
		}
		observerToKey.get(observer).add(eClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getObservers(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public Set<ChangeObserver> getObservers(Notification notification) {
		final Set<ChangeObserver> observers = new LinkedHashSet<ChangeObserver>();
		final EClass eClass = getEClassFromNotification(notification);
		final Set<EClass> eClassesToCheck = new LinkedHashSet<EClass>(eClass.getESuperTypes());
		eClassesToCheck.add(eClass);
		for (final EClass eClassToCheck : eClassesToCheck) {
			if (!registry.containsKey(eClassToCheck)) {
				continue;
			}
			observers.addAll(registry.get(eClassToCheck));
		}
		return observers;

	}

	private EClass getEClassFromNotification(Notification notification) {
		return ((EObject) notification.getNotifier()).eClass();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#deregister(ChangeObserver)
	 */
	@Override
	public void deregister(ChangeObserver observer) {
		final Set<EClass> keys = observerToKey.remove(observer);
		if (keys == null) {
			return;
		}
		for (final EClass eClass : keys) {
			final Set<ChangeObserver> set = registry.get(eClass);
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
	public Set<ChangeObserver> getAllObservers() {
		return new LinkedHashSet<ChangeObserver>(observerToKey.keySet());
	}

}
