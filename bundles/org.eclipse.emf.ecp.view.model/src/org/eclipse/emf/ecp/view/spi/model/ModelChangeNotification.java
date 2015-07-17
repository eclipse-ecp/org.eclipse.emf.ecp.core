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
package org.eclipse.emf.ecp.view.spi.model;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * The Class ModelChangeNotification.
 * Such a notification is issued by the ViewModelContext when a change in the domain or the view model occurs.
 *
 * @author Eugen Neufeld
 * @since 1.3
 * @noextend This class is not intended to be subclassed by clients.
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

	/**
	 * Returns the collection of new EObjects.
	 *
	 * @return The collection of added EObjects, the collection might be empty but never null.
	 * @since 1.5
	 */
	@SuppressWarnings("unchecked")
	public Collection<EObject> getNewEObjects() {
		if (!EReference.class.isInstance(getStructuralFeature())) {
			return Collections.emptySet();
		}
		switch (getRawNotification().getEventType()) {
		case Notification.ADD:
			return Collections.singleton((EObject) getRawNotification().getNewValue());
		case Notification.ADD_MANY:
			return (Collection<EObject>) getRawNotification().getNewValue();
		default:
			return Collections.emptySet();
		}
	}
}
