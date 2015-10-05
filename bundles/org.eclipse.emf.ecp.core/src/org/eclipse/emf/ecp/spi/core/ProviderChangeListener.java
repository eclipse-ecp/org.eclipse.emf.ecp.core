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
package org.eclipse.emf.ecp.spi.core;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

/**
 * Listener to get informed about changes on project contents by the {@link InternalProvider}.
 *
 * @author jfaltermeier
 * @since 1.7
 *
 */
public interface ProviderChangeListener {

	/**
	 * Called when there is a new change notification.
	 *
	 * @param notification the new notification
	 */
	void notify(Notification notification);

	/**
	 * @param objectToBeDeleted The deleted {@link EObject}
	 */
	void postDelete(EObject objectToBeDeleted);

	/**
	 * @param objectToBeDeleted The deleted {@link EObject}
	 */
	void preDelete(EObject objectToBeDeleted);

	/**
	 * @param objectToBeDeleted The deleted {@link EObject}
	 * @return if the object can be deleted
	 */
	boolean canDelete(EObject objectToBeDeleted);
}
