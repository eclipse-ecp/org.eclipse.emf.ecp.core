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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <p>
 * A Change Broker maintains sets of {@link ChangeObserver observers}. Based on incoming notifications it forwards the
 * notification to the observers based on strategies.
 * </p>
 * <p>
 * There are two kinds of observers: regular {@link ChangeObserver EMFObservers} and {@link ReadOnlyChangeObserver
 * ReadOnlyEMFObservers}.
 * </p>
 * <p>
 * EMFObservers may change the EMF model and therefore trigger further notifications. To prevent circular updates
 * between EMFObservers, notifications that arrive while the
 * {@link ChangeObserver#handleNotification(org.eclipse.emf.common.notify.Notification) handleNotification} method is
 * called, will not be forwarded to non ReadOnlyEMFObservers.
 * </p>
 * <p>
 * ReadOnlyEMFObservers are not allowed to change the EMF model. They will receive all notifications at any time.
 * </p>
 *
 * @author Jonas
 * @author jfaltermeier
 *
 */
public interface ChangeBroker {

	/**
	 * Adds an {@link ChangeObserver} that will receive all notifications. Does nothing if already registered.
	 *
	 * @param observer the observer
	 * @since 1.7
	 */
	void subscribe(EMFObserver observer);

	/**
	 * Adds an {@link ChangeObserver} that will receive notifications of notifiers with the given EClass. Does nothing
	 * if
	 * already registered.
	 *
	 * @param observer the observer
	 * @param eClass the required EClass of the notifier
	 * @since 1.7
	 */
	void subscribeToEClass(ChangeObserver observer, EClass eClass);

	/**
	 * Adds an {@link ChangeObserver} that will receive notifications of notifiers with the given EClass or which are
	 * contained (either direct or indirect) in an EObject of the given EClass. Does nothing if
	 * already registered.
	 *
	 * @param observer the observer
	 * @param eClass the required EClass of the notifier or of one of its parents
	 */
	void subscribeToTree(ChangeObserver observer, EClass eClass);

	/**
	 * Adds an {@link ChangeObserver} that will receive notifications when the given EStructuralFeautre is effected.
	 * Does
	 * nothing if
	 * already registered.
	 *
	 * @param observer the observer
	 * @param feature the feature to receive changes for
	 * @since 1.7
	 */
	void subscribeToFeature(ChangeObserver observer, EStructuralFeature feature);

	/**
	 * Removes an observer. Does nothing if receiver is not registered.
	 *
	 * @param observer the receiver
	 * @since 1.7
	 */
	void unsubsribe(EMFObserver observer);

	/**
	 * Stops notifying all {@link ChangeObserver ChangeObserver}. {@link ReadOnlyChangeObserver ReadOnlyEMFObservers} will
	 * still
	 * be notified.
	 */
	void stopNotification();

	/**
	 * Notifying the {@link ChangeObserver ChangeObserver} is started again if {@link #stopNotification()} was called
	 * beforehand. Has no effect if the notification process has been {@link #stopNotification(Object) blocked} or if
	 * {@link #stopNotification()} wasn't called before.
	 */
	void continueNotification();

	/**
	 * Stops notifying all {@link ChangeObserver ChangeObserver}. {@link ReadOnlyChangeObserver ReadOnlyEMFObservers} will
	 * still
	 * be notified. The notifications will we blocked until {@link #continueNotification(Object)} has been called with
	 * <b>all</b> blocking elements. Using the same blocker multiple times has no effect.
	 *
	 * @param blocker the key object used to block all notifications
	 *
	 */
	void stopNotification(Object blocker);

	/**
	 * Removes a {@link #stopNotification(Object) blocker}. When <b>all</b> blockers have been removed the
	 * notification process will continue. This method calls {@link #continueNotification()} internally in all cases,
	 * even if the given blocker object was not used as a blocker beforehand.
	 *
	 * @param blocker the blocker object to remove.
	 */
	void continueNotification(Object blocker);

}
