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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;

/**
 * @author jfaltermeier
 *
 */
public class NoStrategy implements Strategy {

	private final Set<EMFObserver> registry = new LinkedHashSet<EMFObserver>();

	/**
	 * Registers an observer.
	 *
	 * @param observer the observer
	 */
	public void register(EMFObserver observer) {
		registry.add(observer);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getObservers(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public Set<EMFObserver> getObservers(Notification notification) {
		return new LinkedHashSet<EMFObserver>(registry);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#deregister(EMFObserver)
	 */
	@Override
	public void deregister(EMFObserver observer) {
		registry.remove(observer);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getAllObservers()
	 */
	@Override
	public Set<EMFObserver> getAllObservers() {
		return new LinkedHashSet<EMFObserver>(registry);
	}

}
