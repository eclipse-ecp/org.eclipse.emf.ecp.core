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

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;

/**
 * A Strategy is a registry/ruleset which allows to identify if a notification is relevant for an {@link EMFObserver}.
 *
 * @author jfaltermeier
 *
 */
public interface Strategy {

	/**
	 * Returns the {@link EMFObserver observers} which should get notified.
	 *
	 * @param notification the notification
	 * @return the observers
	 */
	Set<EMFObserver> getObservers(Notification notification);

	/**
	 * Returns all observers managed by this strategy.
	 *
	 * @return all observers
	 */
	Set<EMFObserver> getAllObservers();

	/**
	 * Deregisters and observer.
	 *
	 * @param observer the observer
	 */
	void deregister(EMFObserver observer);

}