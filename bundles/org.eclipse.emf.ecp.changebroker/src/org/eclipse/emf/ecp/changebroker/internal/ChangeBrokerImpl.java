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
 * Johannes Faltermeier - - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.changebroker.internal;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.changebroker.spi.ChangeBroker;
import org.eclipse.emf.ecp.changebroker.spi.ChangeObserver;
import org.eclipse.emf.ecp.changebroker.spi.EMFObserver;
import org.eclipse.emf.ecp.changebroker.spi.NotificationProvider;
import org.eclipse.emf.ecp.changebroker.spi.NotificationReceiver;
import org.eclipse.emf.ecp.changebroker.spi.PostDeleteObserver;
import org.eclipse.emf.ecp.changebroker.spi.PreDeleteObserver;
import org.eclipse.emf.ecp.changebroker.spi.ReadOnlyChangeObserver;
import org.eclipse.emf.ecp.changebroker.spi.VetoableDeleteObserver;

/**
 *
 * Implementation of a {@link ChangeBroker}.
 * Registers itself as a {@link NotificationReceiver} at all {@link NotificationProvider providers}. Once it
 * gets notified it forwards the notification to all of the brokers notification receivers, as applicable.
 *
 * @author Jonas
 * @author jfaltermeier
 *
 */
public class ChangeBrokerImpl implements ChangeBroker, NotificationReceiver {

	private final Set<NotificationProvider> providers = new CopyOnWriteArraySet<NotificationProvider>();
	private boolean stopNotifyingEMFObservers;
	private final Set<Object> blocker = new CopyOnWriteArraySet<Object>();

	private final Set<Notification> runningNotifications = new LinkedHashSet<Notification>();

	private final NoStrategy noStrategy = new NoStrategy();
	private final EClassStrategy eClassStrategy = new EClassStrategy();
	private final ContainingEClassStrategy containingEClassStrategy = new ContainingEClassStrategy();
	private final FeatureStrategy featureStrategy = new FeatureStrategy();
	private final Strategy[] strategies;

	private final NoStrategy readOnlyNoStrategy = new NoStrategy();
	private final EClassStrategy readOnlyEClassStrategy = new EClassStrategy();
	private final ContainingEClassStrategy readOnlyContainingEClassStrategy = new ContainingEClassStrategy();
	private final FeatureStrategy readOnlyFeatureStrategy = new FeatureStrategy();
	private final Strategy[] readOnlyStrategies;

	private final Set<PreDeleteObserver> preDeleteObserver = new CopyOnWriteArraySet<PreDeleteObserver>();
	private final Set<PostDeleteObserver> postDeleteObserver = new CopyOnWriteArraySet<PostDeleteObserver>();
	private final Set<VetoableDeleteObserver> vetoableDeleteObserver = new CopyOnWriteArraySet<VetoableDeleteObserver>();

	/**
	 * Default constructor.
	 */
	public ChangeBrokerImpl() {
		strategies = new Strategy[] { noStrategy, eClassStrategy, containingEClassStrategy, featureStrategy };
		readOnlyStrategies = new Strategy[] { readOnlyNoStrategy, readOnlyEClassStrategy,
			readOnlyContainingEClassStrategy, readOnlyFeatureStrategy };
	}

	/**
	 * Adds a notification provider.
	 *
	 * @param provider the provider
	 */
	public void addNotificationProvider(NotificationProvider provider) {
		if (providers.add(provider)) {
			provider.addReceiver(this);
		}
	}

	/**
	 * Removes a notification provider.
	 *
	 * @param provider the provider
	 */
	public void removeNotificationProvider(NotificationProvider provider) {
		if (providers.remove(provider)) {
			provider.removeReceiver(this);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeObserver#handleNotification(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notify(Notification notification) {
		final boolean includeEMFObservers = startNotify(notification);

		final Set<ChangeObserver> observers = new LinkedHashSet<ChangeObserver>();
		for (final Strategy strategy : readOnlyStrategies) {
			observers.addAll(strategy.getObservers(notification));
		}
		if (includeEMFObservers) {
			for (final Strategy strategy : strategies) {
				observers.addAll(strategy.getObservers(notification));
			}
		}
		for (final ChangeObserver observer : observers) {
			observer.handleNotification(notification);
		}

		endNotify(notification);
	}

	private synchronized boolean startNotify(Notification notification) {
		final boolean includeEMFObservers = !stopNotifyingEMFObservers && runningNotifications.isEmpty()
			&& blocker.isEmpty();
		runningNotifications.add(notification);
		return includeEMFObservers;
	}

	private synchronized void endNotify(Notification notification) {
		runningNotifications.remove(notification);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#subscribe(org.eclipse.emf.ecp.changebroker.spi.EMFObserver)
	 */
	@Override
	public void subscribe(EMFObserver observer) {
		if (ReadOnlyChangeObserver.class.isInstance(observer)) {
			readOnlyNoStrategy.register((ChangeObserver) observer);
		} else
			if (ChangeObserver.class.isInstance(observer)) {
			noStrategy.register((ChangeObserver) observer);
		}
		if (PreDeleteObserver.class.isInstance(observer)) {
			preDeleteObserver.add((PreDeleteObserver) observer);
		}
		if (PostDeleteObserver.class.isInstance(observer)) {
			postDeleteObserver.add((PostDeleteObserver) observer);
		}
		if (VetoableDeleteObserver.class.isInstance(observer)) {
			vetoableDeleteObserver.add((VetoableDeleteObserver) observer);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#subscribeToEClass(org.eclipse.emf.ecp.changebroker.spi.ChangeObserver,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public void subscribeToEClass(ChangeObserver observer, EClass eClass) {
		if (ReadOnlyChangeObserver.class.isInstance(observer)) {
			readOnlyEClassStrategy.register(observer, eClass);
		} else {
			eClassStrategy.register(observer, eClass);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#subscribeToTree(org.eclipse.emf.ecp.changebroker.spi.ChangeObserver,
	 *      org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public void subscribeToTree(ChangeObserver observer, EClass eClass) {
		if (ReadOnlyChangeObserver.class.isInstance(observer)) {
			readOnlyContainingEClassStrategy.register(observer, eClass);
		} else {
			containingEClassStrategy.register(observer, eClass);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#subscribeToFeature(org.eclipse.emf.ecp.changebroker.spi.ChangeObserver,
	 *      org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public void subscribeToFeature(ChangeObserver observer, EStructuralFeature feature) {
		if (ReadOnlyChangeObserver.class.isInstance(observer)) {
			readOnlyFeatureStrategy.register(observer, feature);
		} else {
			featureStrategy.register(observer, feature);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#unsubsribe(org.eclipse.emf.ecp.changebroker.spi.EMFObserver)
	 */
	@Override
	public void unsubsribe(EMFObserver receiver) {
		if (ChangeObserver.class.isInstance(receiver)) {
			for (final Strategy strategy : strategies) {
				strategy.deregister((ChangeObserver) receiver);
			}
		}
		if (PreDeleteObserver.class.isInstance(receiver)) {
			preDeleteObserver.remove(receiver);
		}
		if (PostDeleteObserver.class.isInstance(receiver)) {
			postDeleteObserver.remove(receiver);
		}
		if (VetoableDeleteObserver.class.isInstance(receiver)) {
			vetoableDeleteObserver.remove(receiver);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#stopNotification()
	 */
	@Override
	public void stopNotification() {
		stopNotifyingEMFObservers = true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#continueNotification()
	 */
	@Override
	public void continueNotification() {
		stopNotifyingEMFObservers = false;
	}

	/**
	 * For testing purposes.
	 *
	 * @return all registered providers
	 */
	public Set<NotificationProvider> getNotificationProviders() {
		return new LinkedHashSet<NotificationProvider>(providers);
	}

	/**
	 * For testing purposes.
	 *
	 * @return all registered observers
	 */
	public Set<ChangeObserver> getRegisteredObservers() {
		final LinkedHashSet<ChangeObserver> hashSet = new LinkedHashSet<ChangeObserver>();
		for (final Strategy strategy : strategies) {
			hashSet.addAll(strategy.getAllObservers());
		}
		for (final Strategy strategy : readOnlyStrategies) {
			hashSet.addAll(strategy.getAllObservers());
		}
		return hashSet;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#stopNotification(java.lang.Object)
	 */
	@Override
	public void stopNotification(Object blocker) {
		this.blocker.add(blocker);
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.changebroker.spi.ChangeBroker#continueNotification(java.lang.Object)
	 */
	@Override
	public void continueNotification(Object blocker) {
		this.blocker.remove(blocker);
		continueNotification();
	}

	/**
	 * @param toBeDeleted The {@link EObject} to be deleted
	 */
	@Override
	public void notifyPreDelete(EObject toBeDeleted) {
		for (final PreDeleteObserver observer : preDeleteObserver) {
			observer.preDelete(toBeDeleted);
		}

	}

	/**
	 * @param toBeDeleted The deleted {@link EObject}
	 */
	@Override
	public void notifyPostDelete(EObject toBeDeleted) {
		for (final PostDeleteObserver observer : postDeleteObserver) {
			observer.postDelete(toBeDeleted);
		}

	}

	/**
	 * @param toBeDeleted The {@link EObject} to be deleted
	 * @return if the element can be deleted meaning if no {@link VetoableDeleteObserver} has returned false
	 */
	@Override
	public boolean canDelete(EObject toBeDeleted) {
		boolean canDelete = true;
		for (final VetoableDeleteObserver observer : vetoableDeleteObserver) {
			canDelete = observer.canDelete(toBeDeleted);
			if (!canDelete) {
				break;
			}
		}
		return canDelete;
	}

}
