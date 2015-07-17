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
package org.eclipse.emf.ecp.changebroker.emfstore.internal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.changebroker.spi.AbstractNotificationProvider;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProviderChangeListener;

/**
 * {@link org.eclipse.emf.ecp.changebroker.spi.NotificationProvider NotificationProvider} acting as a source for
 * notifications from EMFStore.
 *
 * @author jfaltermeier
 *
 */
public class EMFStoreNotificationProvider extends AbstractNotificationProvider {

	private static final String EMFSTORE_PROVIDER = "org.eclipse.emf.ecp.emfstore.provider"; //$NON-NLS-1$

	/**
	 * Binds the project manager.
	 *
	 * @param manager the ecp project manager
	 */
	public void bindManager(ECPProjectManager manager) {
		manager.getProjects();
		if (EMFStoreProvider.INSTANCE == null) {
			ECPUtil.getECPObserverBus().register(new ECPProvidersChangedObserver() {
				@Override
				public void providersChanged(Collection<ECPProvider> oldProviders, Collection<ECPProvider> newProviders) {
					for (final ECPProvider ecpProvider : newProviders) {
						if (EMFSTORE_PROVIDER.equals(ecpProvider.getName())) {
							ECPUtil.getResolvedElement(ecpProvider);
							addObserver();
							ECPUtil.getECPObserverBus().unregister(this);
						}
					}
				}

			});
		} else {
			addObserver();
		}
	}

	private void addObserver() {
		EMFStoreProvider.INSTANCE.registerChangeListener(new EMFStoreProviderChangeListener() {
			@Override
			public void onNewNotification(Notification notification) {
				notifyAllReceivers(notification);
			}
		});
	}

}
