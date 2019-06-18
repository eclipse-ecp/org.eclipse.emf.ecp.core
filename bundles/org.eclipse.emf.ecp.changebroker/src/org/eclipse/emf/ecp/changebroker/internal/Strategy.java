/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.internal;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;

/**
 * A Strategy is a registry/ruleset which allows to identify if a notification is relevant for an
 * {@link ChangeObserver}.
 *
 * @author jfaltermeier
 *
 */
public interface Strategy {

	/**
	 * Returns the {@link ChangeObserver observers} which should get notified.
	 *
	 * @param notification the notification
	 * @return the observers
	 */
	Set<ChangeObserver> getObservers(Notification notification);

	/**
	 * Returns all observers managed by this strategy.
	 *
	 * @return all observers
	 */
	Set<ChangeObserver> getAllObservers();

	/**
	 * Deregisters and observer.
	 *
	 * @param observer the observer
	 */
	void deregister(ChangeObserver observer);

}