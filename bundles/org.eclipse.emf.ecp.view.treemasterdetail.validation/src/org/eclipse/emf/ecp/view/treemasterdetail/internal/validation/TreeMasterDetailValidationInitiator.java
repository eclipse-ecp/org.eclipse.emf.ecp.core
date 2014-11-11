/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.internal.validation;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.RootObject;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSelectionManipulatorHelper;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

@SuppressWarnings("restriction")
public class TreeMasterDetailValidationInitiator implements
	ViewModelService {
	/**
	 * The detail key passed to the view model context.
	 */
	public static final String DETAIL_KEY = "detail"; //$NON-NLS-1$

	/**
	 * Context key for the root.
	 */
	public static final String ROOT_KEY = "root"; //$NON-NLS-1$

	private ComposedAdapterFactory adapterFactory;

	private AdapterFactoryContentProvider adapterFactoryContentProvider;

	public TreeMasterDetailValidationInitiator() {
		adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new CustomReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });

		adapterFactoryContentProvider = new AdapterFactoryContentProvider(
			adapterFactory) {
			@Override
			public Object[] getElements(Object object) {
				return new Object[] { ((RootObject) object).getRoot() };
			}
		};

	}

	@Override
	public void instantiate(ViewModelContext context) {

		final EObject viewRoot = context.getViewModel();
		final TreeIterator<EObject> eAllContents = viewRoot.eAllContents();
		while (eAllContents.hasNext()) {
			final EObject eObject = eAllContents.next();
			if (VTreeMasterDetail.class.isInstance(eObject)) {
				final VTreeMasterDetail treeMasterDetail = VTreeMasterDetail.class.cast(eObject);
				registerRootChildContext(treeMasterDetail, context);
				registerChildrenChildContext(treeMasterDetail, context, adapterFactoryContentProvider);
			}
		}

	}

	private void registerChildrenChildContext(VTreeMasterDetail treeMasterDetail, ViewModelContext viewModelContext,
		final AdapterFactoryContentProvider adapterFactoryContentProvider) {
		final Set<Object> children = getAllChildren(viewModelContext.getDomainModel(),
			adapterFactoryContentProvider);
		for (final Object object : children) {
			final Map<String, Object> context = new LinkedHashMap<String, Object>();
			context.put(DETAIL_KEY, true);
			final VView view = ViewProviderHelper.getView((EObject) object, context);

			viewModelContext.getChildContext((EObject) object, treeMasterDetail, view);
		}
	}

	private void registerRootChildContext(VTreeMasterDetail treeMasterDetail, ViewModelContext viewModelContext) {
		final Map<String, Object> context = new LinkedHashMap<String, Object>();
		context.put(DETAIL_KEY, true);
		context.put(ROOT_KEY, true);
		final Object manipulateSelection = manipulateSelection(viewModelContext.getDomainModel());
		VView view = treeMasterDetail.getDetailView();
		if (view == null || view.getChildren().isEmpty()) {
			view = ViewProviderHelper.getView((EObject) manipulateSelection, context);
		}
		viewModelContext.getChildContext((EObject) manipulateSelection, treeMasterDetail, view);
	}

	private Set<Object> getAllChildren(Object parent, AdapterFactoryContentProvider adapterFactoryContentProvider) {
		final Set<Object> allChildren = new LinkedHashSet<Object>();
		final Object[] children = adapterFactoryContentProvider.getChildren(parent);
		for (final Object object : children) {
			final Object manipulatedSelection = manipulateSelection(object);
			if (!EObject.class.isInstance(manipulatedSelection)) {
				continue;
			}
			allChildren.add(manipulatedSelection);
			allChildren.addAll(getAllChildren(object, adapterFactoryContentProvider));
		}
		return allChildren;
	}

	private Object manipulateSelection(Object object) {
		return TreeMasterDetailSelectionManipulatorHelper.manipulateSelection(object);
	}

	@Override
	public void dispose() {
		adapterFactoryContentProvider.dispose();
		adapterFactory.dispose();
	}

	@Override
	public int getPriority() {
		return 3;
	}

}
