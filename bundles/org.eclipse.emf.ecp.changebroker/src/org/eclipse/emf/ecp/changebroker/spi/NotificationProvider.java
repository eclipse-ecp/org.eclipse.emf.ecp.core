/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.spi;

/**
 * A notification provider is a source for {@link org.eclipse.emf.common.notify.Notification Notifications}. It
 * maintains a set of {@link NotificationReceiver NotificationReceivers} which will be notified when there is a new
 * Notification.
 *
 * @author Jonas
 * @author jfaltermeier
 *
 */
public interface NotificationProvider {

	/**
	 * Adds a new {@link NotificationReceiver}. Does nothing of receiver is already registered,
	 *
	 * @param receiver the receiver to add
	 */
	void addReceiver(NotificationReceiver receiver);

	/**
	 * Removes a {@link NotificationReceiver}. Does nothing if the receiver was
	 * never registered.
	 *
	 * @param receiver the receiver
	 */
	void removeReceiver(NotificationReceiver receiver);

}
