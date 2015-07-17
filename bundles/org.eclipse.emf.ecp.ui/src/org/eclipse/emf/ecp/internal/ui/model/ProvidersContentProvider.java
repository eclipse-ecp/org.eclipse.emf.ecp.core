/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.model;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.ECPProviderRegistry;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;

/**
 * @author Eike Stepper
 */
public class ProvidersContentProvider extends TreeContentProvider<ECPProviderRegistry> implements
	// ECPProviderRegistry.Listener
	ECPProvidersChangedObserver {
	private final boolean excludesProvidersThatCannotAddRepositories;

	public ProvidersContentProvider() {
		this(false);
	}

	public ProvidersContentProvider(boolean excludesProvidersThatCannotAddRepositories) {
		this.excludesProvidersThatCannotAddRepositories = excludesProvidersThatCannotAddRepositories;
	}

	public final boolean excludesProvidersThatCannotAddRepositories() {
		return excludesProvidersThatCannotAddRepositories;
	}

	@Override
	protected void fillChildren(Object parent, InternalChildrenList childrenList) {
		if (parent == ECPUtil.getECPProviderRegistry()) {
			final Collection<ECPProvider> providers = ECPUtil.getECPProviderRegistry().getProviders();
			if (!excludesProvidersThatCannotAddRepositories) {
				childrenList.addChildren(providers);
			} else {
				for (final ECPProvider provider : providers) {
					if (provider.hasCreateRepositorySupport()) {
						childrenList.addChild(provider);
					}
				}
			}
		}
	}

	@Override
	public void providersChanged(Collection<ECPProvider> oldProviders, Collection<ECPProvider> newProviders) {
		refreshViewer();
	}

	@Override
	protected void connectInput(ECPProviderRegistry input) {
		super.connectInput(input);
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	protected void disconnectInput(ECPProviderRegistry input) {
		ECPUtil.getECPObserverBus().unregister(this);
		super.disconnectInput(input);
	}
}
