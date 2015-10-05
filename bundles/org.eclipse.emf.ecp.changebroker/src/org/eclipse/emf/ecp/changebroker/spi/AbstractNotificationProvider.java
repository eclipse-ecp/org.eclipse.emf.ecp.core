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

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

/**
 * Abstract implementation of a {@link NotificationProvider}.
 *
 * @author jfaltermeier
 *
 */
public abstract class AbstractNotificationProvider implements NotificationProvider {

	private final Set<NotificationReceiver> receivers = new CopyOnWriteArraySet<NotificationReceiver>();

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.NotificationProvider#addReceiver(org.eclipse.emf.ecp.changebroker.spi.NotificationReceiver)
	 */
	@Override
	public void addReceiver(NotificationReceiver receiver) {
		receivers.add(receiver);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.NotificationProvider#removeReceiver(org.eclipse.emf.ecp.changebroker.spi.NotificationReceiver)
	 */
	@Override
	public void removeReceiver(NotificationReceiver receiver) {
		receivers.remove(receiver);
	}

	/**
	 * Notifies all registered {@link org.eclipse.emf.ecp.changebroker.spi.ChangeObserver receivers}.
	 *
	 * @param notification the notification
	 */
	protected void notifyAllReceivers(Notification notification) {
		for (final NotificationReceiver receiver : receivers) {
			receiver.notify(notification);
		}
	}

	/**
	 *
	 * @param toBeDeleted The deleted {@link EObject}
	 *
	 * @since 1.7
	 */
	protected void notifyPreDelete(EObject toBeDeleted) {
		for (final NotificationReceiver receiver : receivers) {
			receiver.notifyPreDelete(toBeDeleted);
		}
	}

	/**
	 *
	 * @param toBeDeleted The deleted {@link EObject}
	 *
	 * @since 1.7
	 */
	protected void notifyPostDelete(EObject toBeDeleted) {
		for (final NotificationReceiver receiver : receivers) {
			receiver.notifyPostDelete(toBeDeleted);
		}
	}

	/**
	 * @param toBeDeleted The deleted {@link EObject}
	 * @return if the object can be deleted
	 * @since 1.7
	 */
	protected boolean notifyCanDelete(EObject toBeDeleted) {
		boolean canDelete = true;
		for (final NotificationReceiver receiver : receivers) {
			canDelete = receiver.canDelete(toBeDeleted);
			if (!canDelete) {
				break;
			}
		}
		return canDelete;
	}

}
