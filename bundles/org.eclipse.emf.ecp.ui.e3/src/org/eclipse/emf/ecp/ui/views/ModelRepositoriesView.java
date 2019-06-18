/*******************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.views;

import java.util.Collection;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.core.util.observer.ECPProvidersChangedObserver;
import org.eclipse.emf.ecp.ui.common.ECPViewerFactory;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eike Stepper
 */
public class ModelRepositoriesView extends TreeView implements ECPProvidersChangedObserver // ECPProviderRegistry.Listener
{
	/**
	 * The view ID.
	 */
	public static final String ID = "org.eclipse.emf.ecp.ui.ModelRepositoriesView"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 */
	public ModelRepositoriesView() {
		super(ID);
	}

	@Override
	public void dispose() {
		ECPUtil.getECPObserverBus().unregister(this);
		super.dispose();
	}

	/** {@inheritDoc} */
	@Override
	public void providersChanged(Collection<ECPProvider> oldProviders, Collection<ECPProvider> newProviders) {
	}

	@Override
	protected TreeViewer createViewer(Composite parent) {
		final TreeViewer viewer = ECPViewerFactory.createRepositoryExplorerViewer(parent, createLabelDecorator());
		ECPUtil.getECPObserverBus().register(this);
		return viewer;
	}

	@Override
	protected void fillLocalToolBar(IToolBarManager manager) {
		super.fillLocalToolBar(manager);
	}
}
