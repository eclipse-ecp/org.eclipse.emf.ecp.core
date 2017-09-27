/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.scoped;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;

class FakeViewContext implements EMFFormsViewContext {

	private final EObject domainObject;
	private final VElement viewModel;
	private final Set<EMFFormsContextListener> listeners = new LinkedHashSet<EMFFormsContextListener>();

	FakeViewContext(EObject domainObject, VElement viewModel) {
		this.domainObject = domainObject;
		this.viewModel = viewModel;
	}

	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
	}

	@Override
	public void unregisterRootDomainModelChangeListener(
		RootDomainModelChangeListener rootDomainModelChangeListener) {

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

	}

	@Override
	public void registerRootDomainModelChangeListener(
		RootDomainModelChangeListener rootDomainModelChangeListener) {

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

	}

	public void addChildContext(VElement vElement, FakeViewContext childContext) {
		for (final EMFFormsContextListener listener : listeners) {
			listener.childContextAdded(vElement, childContext);
		}
	}
}