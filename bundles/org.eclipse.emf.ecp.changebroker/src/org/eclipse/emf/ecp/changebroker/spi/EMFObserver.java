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
package org.eclipse.emf.ecp.changebroker.spi;

import org.eclipse.emf.common.notify.Notification;

/**
 * A EMF Observer has a handle notification method which receives a {@link org.eclipse.emf.common.notify.Notification
 * Notification} from the {@link ChangeBroker}. As opposed to the {@link ReadOnlyEMFObserver} it is allowed to make
 * changes on the EMF model and therefore to trigger further notifications.
 *
 * @author jfaltermeier
 *
 */
public interface EMFObserver {

	/**
	 * Called whenever a new {@link Notification} is provided to the {@link ChangeBroker}.
	 *
	 * @param notification the notification
	 */
	void handleNotification(Notification notification);
}
