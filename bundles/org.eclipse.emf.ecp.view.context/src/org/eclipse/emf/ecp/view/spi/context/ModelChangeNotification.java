/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * The Class ModelChangeNotification.
 * Such a notification is issued by the {@link ViewModelContext} when a change in the domain or the view model occurs.
 * 
 * @author Eugen Neufeld
 */
public class ModelChangeNotification {

	/** The notification. */
	private final Notification notification;

	/**
	 * Instantiates a new model change notification.
	 * 
	 * @param notification the {@link Notification}
	 */
	public ModelChangeNotification(Notification notification) {
		this.notification = notification;
	}

	/**
	 * Gets the structural feature.
	 * 
	 * @return the {@link EStructuralFeature} which value changed
	 */
	public EStructuralFeature getStructuralFeature() {
		return (EStructuralFeature) notification.getFeature();
	}

	/**
	 * Gets the notifier.
	 * 
	 * @return the notifier which value changed
	 */
	public EObject getNotifier() {
		return (EObject) notification.getNotifier();
	}

	/**
	 * Gets the raw notification.
	 * 
	 * @return the raw {@link Notification}
	 */
	public Notification getRawNotification() {
		return notification;
	}
}
