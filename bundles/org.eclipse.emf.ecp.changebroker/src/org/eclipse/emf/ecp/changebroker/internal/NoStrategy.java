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
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;

/**
 * @author jfaltermeier
 *
 */
public class NoStrategy implements Strategy {

	private final Set<ChangeObserver> registry = new LinkedHashSet<ChangeObserver>();

	/**
	 * Registers an observer.
	 *
	 * @param observer the observer
	 */
	public void register(ChangeObserver observer) {
		registry.add(observer);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getObservers(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public Set<ChangeObserver> getObservers(Notification notification) {
		return new LinkedHashSet<ChangeObserver>(registry);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#deregister(ChangeObserver)
	 */
	@Override
	public void deregister(ChangeObserver observer) {
		registry.remove(observer);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.internal.Strategy#getAllObservers()
	 */
	@Override
	public Set<ChangeObserver> getAllObservers() {
		return new LinkedHashSet<ChangeObserver>(registry);
	}

}
