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
package org.eclipse.emf.ecp.emfstore.core.internal;

import org.eclipse.emf.common.notify.Notification;

/**
 * Listener to get informed about changes on project contents by the {@link EMFStoreProvider}.
 *
 * @author jfaltermeier
 *
 */
public interface EMFStoreProviderChangeListener {

	/**
	 * Called when there is a new notification.
	 * 
	 * @param notification the new notification
	 */
	void onNewNotification(Notification notification);
}
