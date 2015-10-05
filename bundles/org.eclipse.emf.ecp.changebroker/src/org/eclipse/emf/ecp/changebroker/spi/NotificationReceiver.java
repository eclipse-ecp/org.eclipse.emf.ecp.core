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
import org.eclipse.emf.ecore.EObject;

/**
 * A target for new notifications.
 *
 * @author jfaltermeier
 *
 */
public interface NotificationReceiver {

	/**
	 * Called whenever there is a new {@link Notification} meant for the receiver.
	 *
	 * @param notification the notification
	 */
	void notify(Notification notification);

	/**
	 * Called before an element gets deleted, after all {@link VetoableDeleteObserver} have returned true and therefore
	 * the delete operation is allowed.
	 *
	 * @param toBeDeleted The {@link EObject} to be deleted
	 * @since 1.7
	 */
	void notifyPreDelete(EObject toBeDeleted);

	/**
	 * Called after an element was deleted.
	 *
	 * @param toBeDeleted The {@link EObject} to be deleted
	 * @since 1.7
	 */
	void notifyPostDelete(EObject toBeDeleted);

	/**
	 * @param toBeDeleted The {@link EObject} to be deleted
	 * @return If all {@link VetoableDeleteObserver} return true and therefore allow the delete operation.
	 * @since 1.7
	 */
	boolean canDelete(EObject toBeDeleted);

}