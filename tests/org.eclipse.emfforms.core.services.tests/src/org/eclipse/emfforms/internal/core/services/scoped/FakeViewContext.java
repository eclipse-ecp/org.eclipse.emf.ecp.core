/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * eugen - initial API and implementation
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import static java.util.Collections.singleton;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;

class FakeViewContext implements EMFFormsViewContext {

	private EObject domainObject;
	private final VElement viewModel;
	private final Set<EMFFormsContextListener> listeners = new LinkedHashSet<>();
	private final Set<RootDomainModelChangeListener> rootListeners = new LinkedHashSet<>();
	private final Set<ModelChangeListener> viewListeners = new LinkedHashSet<>();

	private VElement parentElement;
	private FakeViewContext parent;

	private boolean disposing;

	FakeViewContext(EObject domainObject, VElement viewModel) {
		this.domainObject = domainObject;
		this.viewModel = viewModel;
	}

	@Override
	public void unregisterRootDomainModelChangeListener(
		RootDomainModelChangeListener rootDomainModelChangeListener) {

		rootListeners.remove(rootDomainModelChangeListener);
	}

	@Override
	public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {
		listeners.remove(contextListener);
	}

	@Override
	public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {

	}

	@Override
	public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
		viewListeners.add(modelChangeListener);

		if (!disposing) {
			if (modelChangeListener instanceof ModelChangeAddRemoveListener) {
				final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) modelChangeListener;
				for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(viewModel)); iter.hasNext();) {
					addRemove.notifyAdd(iter.next());
				}
			}
		}
	}

	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		viewListeners.remove(modelChangeListener);

		if (!disposing) {
			if (modelChangeListener instanceof ModelChangeAddRemoveListener) {
				final ModelChangeAddRemoveListener addRemove = (ModelChangeAddRemoveListener) modelChangeListener;
				for (final Iterator<EObject> iter = EcoreUtil.getAllContents(singleton(viewModel)); iter.hasNext();) {
					addRemove.notifyRemove(iter.next());
				}
			}
		}
	}

	@Override
	public void registerRootDomainModelChangeListener(
		RootDomainModelChangeListener rootDomainModelChangeListener) {

		rootListeners.add(rootDomainModelChangeListener);
	}

	@Override
	public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {
		listeners.add(contextListener);
	}

	@Override
	public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {

	}

	@Override
	public VElement getViewModel() {
		return viewModel;
	}

	@Override
	public <T> T getService(Class<T> serviceType) {
		return null;
	}

	@Override
	public EObject getDomainModel() {
		return domainObject;
	}

	@Override
	public void changeDomainModel(EObject newDomainModel) {
		if (parent != null) {
			parent.removeChildContext(this, false);
		} else {
			dispose();
		}

		domainObject = newDomainModel;

		parent.addChildContext(parentElement, this);

		listeners.forEach(EMFFormsContextListener::contextInitialised);

		rootListeners.forEach(RootDomainModelChangeListener::notifyChange);
	}

	public void initialize() {
		listeners.forEach(EMFFormsContextListener::contextInitialised);
	}

	public void dispose() {
		dispose(true);
	}

	void dispose(boolean permanently) {
		final boolean oldDisposing = disposing;
		disposing = permanently;
		try {
			listeners.forEach(EMFFormsContextListener::contextDispose);
		} finally {
			disposing = oldDisposing;
		}
	}

	public void addChildContext(VElement vElement, FakeViewContext childContext) {
		childContext.parent = this;
		childContext.parentElement = vElement;

		for (final EMFFormsContextListener listener : listeners) {
			listener.childContextAdded(vElement, childContext);
			listener.contextInitialised();
		}
	}

	public void removeChildContext(FakeViewContext childContext) {
		childContext.dispose();

		for (final EMFFormsContextListener listener : listeners) {
			listener.childContextDisposed(childContext);
		}
	}

	void removeChildContext(FakeViewContext childContext, boolean permanently) {
		childContext.dispose(permanently);

		for (final EMFFormsContextListener listener : listeners) {
			listener.childContextDisposed(childContext);
		}
	}

}
