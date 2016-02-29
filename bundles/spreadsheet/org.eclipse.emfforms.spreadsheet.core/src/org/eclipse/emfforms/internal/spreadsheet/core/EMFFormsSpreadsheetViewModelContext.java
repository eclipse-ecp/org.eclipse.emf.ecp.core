/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
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
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.internal.view.model.localization.LocalizationViewModelService;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;

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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerDomainChangeListener(org.eclipse.emf.ecp.view.spi.model.ModelChangeListener)
	 */
	@Override
	public void registerDomainChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#unregisterDomainChangeListener(org.eclipse.emf.ecp.view.spi.model.ModelChangeListener)
	 */
	@Override
	public void unregisterDomainChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getViewModel()
	 */
	@Override
	public VElement getViewModel() {
		return view;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getDomainModel()
	 */
	@Override
	public EObject getDomainModel() {
		return domainModel;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerViewChangeListener(org.eclipse.emf.ecp.view.spi.model.ModelChangeListener)
	 */
	@Override
	public void registerViewChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#unregisterViewChangeListener(org.eclipse.emf.ecp.view.spi.model.ModelChangeListener)
	 */
	@Override
	public void unregisterViewChangeListener(ModelChangeListener modelChangeListener) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#dispose()
	 */
	@Override
	public void dispose() {
		if (vms != null) {
			vms.dispose();
		}
		contextValues.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#hasService(java.lang.Class)
	 */
	@Override
	public <T> boolean hasService(Class<T> serviceType) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getService(java.lang.Class)
	 */
	@Override
	public <T> T getService(Class<T> serviceType) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecore.EStructuralFeature.Setting)
	 * @deprecated
	 */
	@Deprecated
	@Override
	public Set<VControl> getControlsFor(Setting setting) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getControlsFor(org.eclipse.emf.ecp.common.spi.UniqueSetting)
	 * @deprecated
	 */
	@Deprecated
	@Override
	public Set<VElement> getControlsFor(UniqueSetting setting) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getContextValue(java.lang.String)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#putContextValue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void putContextValue(String key, Object value) {
		contextValues.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#getChildContext(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecp.view.spi.model.VElement, org.eclipse.emf.ecp.view.spi.model.VView,
	 *      org.eclipse.emf.ecp.view.spi.context.ViewModelService[])
	 */
	@Override
	public ViewModelContext getChildContext(EObject eObject, VElement parent, VView vView,
		ViewModelService... viewModelServices) {
		return new EMFFormsSpreadsheetViewModelContext(vView, eObject, this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#registerDisposeListener(org.eclipse.emf.ecp.view.spi.context.ViewModelContextDisposeListener)
	 */
	@Override
	public void registerDisposeListener(ViewModelContextDisposeListener listener) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#addContextUser(java.lang.Object)
	 */
	@Override
	public void addContextUser(Object user) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelContext#removeContextUser(java.lang.Object)
	 */
	@Override
	public void removeContextUser(Object user) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext#registerEMFFormsContextListener(org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener)
	 */
	@Override
	public void registerEMFFormsContextListener(EMFFormsContextListener contextListener) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext#unregisterEMFFormsContextListener(org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener)
	 */
	@Override
	public void unregisterEMFFormsContextListener(EMFFormsContextListener contextListener) {
	}

}
