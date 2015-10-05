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
package org.eclipse.emf.ecp.changebroker.provider.internal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.changebroker.spi.AbstractNotificationProvider;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.ProviderChangeListener;

/**
 * {@link org.eclipse.emf.ecp.changebroker.spi.NotificationProvider NotificationProvider} acting as a source for
 * notifications from EMFStore.
 *
 * @author jfaltermeier
 *
 */
public class ECPNotificationProvider extends AbstractNotificationProvider implements ProviderChangeListener {

	/**
	 * Binds the project manager.
	 *
	 * @param manager the ecp project manager
	 */
	public void bindManager(ECPProjectManager manager) {
		manager.getProjects();
		ECPUtil.getECPObserverBus().register(new ECPProvidersChangedObserver() {
			@Override
			public void providersChanged(Collection<ECPProvider> oldProviders, Collection<ECPProvider> newProviders) {
				for (final ECPProvider ecpProvider : newProviders) {
					addObserver((InternalProvider) ecpProvider);
				}
			}

		});
	}

	/**
	 * Binds the provider registry.
	 *
	 * @param registry the registry
	 */
	public void bindProviderRegistry(ECPProviderRegistry registry) {
		for (final ECPProvider ecpProvider : registry.getProviders()) {
			addObserver((InternalProvider) ecpProvider);
		}
	}

	private void addObserver(InternalProvider ecpProvider) {
		ecpProvider.registerChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.ProviderChangeListener#notify(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notify(Notification notification) {
		notifyAllReceivers(notification);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.ProviderChangeListener#postDelete(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void postDelete(EObject objectToBeDeleted) {
		notifyPostDelete(objectToBeDeleted);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.ProviderChangeListener#preDelete(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void preDelete(EObject objectToBeDeleted) {
		notifyPreDelete(objectToBeDeleted);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.spi.core.ProviderChangeListener#canDelete(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean canDelete(EObject objectToBeDeleted) {
		return notifyCanDelete(objectToBeDeleted);
	}

}
