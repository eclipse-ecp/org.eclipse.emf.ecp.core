/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.viewproxy.resolver;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalFactory;
import org.eclipse.emf.ecp.view.spi.vertical.model.VVerticalLayout;
import org.eclipse.emf.ecp.view.spi.viewproxy.model.VViewProxy;

/**
 * {@link ViewModelService} which replaces all {@link VViewProxy} occurrences in the view model with its resolved
 * contents.
 *
 * @author jfaltermeier
 *
 */
public class ProxyResolverViewService implements ViewModelService {

	/**
	 * Id for proxy view models.
	 */
	public static final String VIEW_MODEL_ID = "proxyId"; //$NON-NLS-1$

	private ViewModelContext context;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		final VView viewModel = (VView) context.getViewModel();
		findProxiesAndResolve(viewModel);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		context = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		return -10;
	}

	/**
	 * Finds and resolves view proxies in the given view.
	 *
	 * @param view the view
	 * @return whether resolvement was successful
	 */
	/* package */boolean findProxiesAndResolve(VView view) {
		final TreeIterator<EObject> iterator = view.eAllContents();
		while (iterator.hasNext()) {
			final EObject current = iterator.next();
			if (VViewProxy.class.isInstance(current)) {
				resolveProxy(VViewProxy.class.cast(current));
			}
		}
		return true;
	}

	private void resolveProxy(VViewProxy proxy) {
		final VView view = resolveView(proxy);
		final EStructuralFeature eContainingFeature = proxy.eContainingFeature();
		final EObject eContainer = proxy.eContainer();
		final Object objectContainer = eContainer.eGet(eContainingFeature);
		int index = -1;
		if (eContainingFeature.isMany()) {
			final List<?> objectList = (List<?>) objectContainer;
			index = objectList.indexOf(proxy);
		}
		EcoreUtil.remove(eContainer, eContainingFeature, proxy);
		if (view == null) {
			return;
		}
		if (!findProxiesAndResolve(view)) {
			return;
		}
		final VVerticalLayout layout = VVerticalFactory.eINSTANCE.createVerticalLayout();
		layout.getChildren().addAll(view.getChildren());
		if (eContainingFeature.isMany()) {
			// EMF API
			@SuppressWarnings("unchecked")
			final List<EObject> list = (List<EObject>) eContainer.eGet(eContainingFeature);
			list.add(index, layout);
		} else {
			eContainer.eSet(eContainingFeature, layout);
		}

		layout.getAttachments().addAll(proxy.getAttachments());
	}

	private VView resolveView(VViewProxy proxy) {
		final EObject eObject = context.getDomainModel();
		final VElement viewModel = context.getViewModel();
		final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(viewModel);
		final String id = proxy.getId() == null ? "" : proxy.getId(); //$NON-NLS-1$
		properties.addNonInheritableProperty(VIEW_MODEL_ID, id);
		final VView view = ViewProviderHelper.getView(eObject, properties);
		return view;
	}
}
