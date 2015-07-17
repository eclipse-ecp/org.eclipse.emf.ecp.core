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
import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.ECPRepositoryManager;
import org.eclipse.emf.ecp.core.util.ECPProviderAware;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPRepositoriesChangedObserver;
import org.eclipse.emf.ecp.core.util.observer.ECPRepositoryContentChangedObserver;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.ecp.spi.core.util.InternalChildrenList;

/**
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public class RepositoriesContentProvider extends ECPContentProvider<ECPRepositoryManager> implements
	// ECPRepositoryManager.Listener
	ECPRepositoriesChangedObserver, ECPRepositoryContentChangedObserver {
	private final ECPProvider allowedProvider;

	public RepositoriesContentProvider() {
		allowedProvider = null;
	}

	public RepositoriesContentProvider(ECPProvider allowedProvider) {
		this.allowedProvider = allowedProvider;
	}

	/** {@inheritDoc} */
	@Override
	public void repositoriesChanged(Collection<ECPRepository> oldRepositories, Collection<ECPRepository> newRepositories) {
		refreshViewer();
	}

	/** {@inheritDoc} */
	@Override
	public void contentChanged(ECPRepository repository, Collection<Object> objects) {
		// do always a full refresh
		refreshViewer(true, objects.toArray());
	}

	@Override
	protected void connectInput(ECPRepositoryManager input) {
		super.connectInput(input);
		ECPUtil.getECPObserverBus().register(this);
	}

	@Override
	protected void disconnectInput(ECPRepositoryManager input) {
		ECPUtil.getECPObserverBus().register(this);
		super.disconnectInput(input);
	}

	@Override
	protected boolean isSlow(Object parent) {
		if (parent instanceof ECPProviderAware) {
			final InternalProvider provider = (InternalProvider) ((ECPProviderAware) parent).getProvider();
			if (provider != null) {
				return provider.isSlow(parent);
			}
		}
		return super.isSlow(parent);
	}

	@Override
	protected void fillChildren(Object parent, InternalChildrenList childrenList) {
		if (parent == ECPUtil.getECPRepositoryManager()) {
			if (allowedProvider == null) {
				childrenList.addChildren(ECPUtil.getECPRepositoryManager().getRepositories());
			} else {
				for (final ECPRepository ecpRepository : ECPUtil.getECPRepositoryManager().getRepositories()) {
					if (allowedProvider.equals(ecpRepository.getProvider())) {
						childrenList.addChild(ecpRepository);
					}
				}
			}
		} else {
			super.fillChildren(parent, childrenList);
		}
	}
}
