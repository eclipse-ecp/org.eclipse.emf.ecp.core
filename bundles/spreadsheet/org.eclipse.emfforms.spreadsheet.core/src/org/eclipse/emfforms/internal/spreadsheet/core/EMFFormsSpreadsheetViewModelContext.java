/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 527740
 ******************************************************************************/
package org.eclipse.emfforms.internal.spreadsheet.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.common.spi.UniqueSetting;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelServiceProvider;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.view.model.localization.LocalizationViewModelService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.RootDomainModelChangeListener;

/**
 * Spreadsheet specific implementation of the {@link ViewModelContext}.
 * This implementation doesn't do anything, it serves only as a container for a {@link VView} and the {@link EObject}.
 *
 * @author Eugen Neufeld
 * @noextend This class is not intended to be subclassed by clients.
 */
@SuppressWarnings("restriction")
public class EMFFormsSpreadsheetViewModelContext implements ViewModelContext {

	private final VView view;
	private final EObject domainModel;
	private final Map<String, Object> contextValues = new LinkedHashMap<String, Object>();
	private final ViewModelContext parentContext;
	private LocalizationViewModelService vms;

	/**
	 * Default Constructor.
	 *
	 * @param view The {@link VView}
	 * @param domainModel The {@link EObject}
	 */

	public EMFFormsSpreadsheetViewModelContext(VView view, EObject domainModel) {
		this(view, domainModel, null);
	}

	/**
	 * Default Constructor for child contexts.
	 *
	 * @param view The {@link VView}
	 * @param domainModel The {@link EObject}
	 * @param parentContext The parent {@link ViewModelContext}
	 */
	public EMFFormsSpreadsheetViewModelContext(VView view, EObject domainModel, ViewModelContext parentContext) {
		this.view = view;
		this.domainModel = domainModel;
		this.parentContext = parentContext;

		// we need this only on the root context
		if (parentContext == null) {
			vms = new LocalizationViewModelService();
			vms.instantiate(this);
		}
	}

	@Override
	public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	@Override
	public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	@Override
	public VElement getViewModel() {
		return view;
	}

	@Override
	public EObject getDomainModel() {
		return domainModel;
	}

	@Override
	public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	@Override
	public void dispose() {
		if (vms != null) {
			vms.dispose();
		}
		contextValues.clear();
	}

	@Override
	public <T> boolean hasService(Class<T> serviceType) {
		return false;
	}

	@Override
	public <T> T getService(Class<T> serviceType) {
		return null;
	}

	@Deprecated
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		return null;
	}

	@Deprecated
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		return null;
	}

	@Override
	public Object getContextValue(String key) {
		if (contextValues.containsKey(key)) {
			return contextValues.get(key);
		}
		if (parentContext != null) {
			return parentContext.getContextValue(key);
		}
		return null;
	}

	@Override
	public void putContextValue(String key, Object value) {
		contextValues.put(key, value);
	}

	@Deprecated
	@Override
	public ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
		ViewModelService... viewModelServices) {
		return new EMFFormsSpreadsheetViewModelContext(vView, eObject, this);
	}

	@Override
	public ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
		ViewModelServiceProvider viewModelServiceProvider) {

		return new EMFFormsSpreadsheetViewModelContext(vView, eObject, this);
	}

	@Override
	public void registerDisposeListener(ViewModelContextDisposeListener listener) {
		// intentionally left empty
	}

	@Override
	public void addContextUser(Object user) {
		// intentionally left empty
	}

	@Override
	public void removeContextUser(Object user) {
		// intentionally left empty
	}

	@Override
	public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {
	}

	@Override
	public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {
	}

	@Override
	public ViewModelContext getParentContext() {
		return parentContext;
	}

	@Override
	public VElement getParentVElement() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void changeDomainModel(EObject newDomainModel) {
		// Do not support feature
	}

	@Override
	public void registerRootDomainModelChangeListener(RootDomainModelChangeListener rootDomainModelChangeListener) {
		// Do not support feature
	}

	@Override
	public void unregisterRootDomainModelChangeListener(RootDomainModelChangeListener rootDomainModelChangeListener) {
		// Do not support feature
	}

	@Override
	public void pause() {
		// Do not support feature
	}

	@Override
	public void reactivate() {
		// Do not support feature
	}

}
