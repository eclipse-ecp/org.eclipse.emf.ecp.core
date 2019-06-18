/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.internal.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.util.ViewModelPropertiesHelper;
import org.eclipse.emf.ecp.view.spi.provider.ViewProviderHelper;
import org.eclipse.emf.ecp.view.treemasterdetail.model.VTreeMasterDetail;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.RootObject;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeMasterDetailSelectionManipulatorHelper;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener;
import org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext;

/**
 * The TreeMasterDatailValidationInitiator searches for {@link VTreeMasterDetail} VElements and registers the necessary
 * ViewModelContexts.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class TreeMasterDetailValidationInitiator implements
	GlobalViewModelService, EMFFormsContextListener {

	/**
	 * @author Eugen Neufeld
	 * @author Johannes Faltermeier
	 *
	 */
	private final class TreeMasterDetailValidationInitiatorDomainChangeListener implements ModelChangeListener {
		private final ViewModelContext context;

		/**
		 * @param context
		 */
		private TreeMasterDetailValidationInitiatorDomainChangeListener(ViewModelContext context) {
			this.context = context;
		}

		@Override
		public void notifyChange(ModelChangeNotification notification) {
			if (notification.getRawNotification().isTouch() || mapping.isEmpty()) {
				return;
			}
			for (final TreeContextMapping treeContextEntry : new LinkedHashSet<TreeContextMapping>(mapping.keySet())) {
				if (!mapping.get(treeContextEntry).contains(notification.getNotifier())) {
					return;
				}
				final Set<Object> children = getAllChildren(notification.getNotifier(), adapterFactoryContentProvider);
				final Set<Object> childrenTreeContextEntry = new HashSet<Object>(mapping.get(treeContextEntry));

				if (childrenTreeContextEntry.containsAll(children)) {
					if (isChildObjectRemoved(children, childrenTreeContextEntry,
						notification.getRawNotification().getEventType())) {

						// Calculate the removed child objects by calculating the diff between the objects referenced in
						// the mapping and the notifier and its actual children.
						childrenTreeContextEntry.removeAll(children);
						childrenTreeContextEntry.remove(notification.getNotifier());

						// For every removed child object, remove this listener and the TMD Validation Initiator as
						// context users from the child object's context. This allows that the child context is disposed
						// once its other listeners are removed, too.
						final Object oldValue = notification.getRawNotification().getOldValue();
						cleanupRemovedValues(oldValue, treeContextEntry);
					}
					return;
				}

				// Register this listener to every child context of any newly added child object.
				children.removeAll(childrenTreeContextEntry);
				for (final Object child : children) {
					mapping.get(treeContextEntry).add(child);
					final ViewModelContext childContext = getExistingOrNewChildContext(treeContextEntry, child);
					childContext.addContextUser(this);
				}
			}
		}

		/**
		 * Cleans up the removed values.
		 * For every removed child object, remove this listener and the TMD Validation Initiator as
		 * context users from the child object's context. This allows that the child context is disposed
		 * once its other listeners are removed, too.
		 *
		 * @param removedValueObject the removed value object.
		 * @param treeContextEntry the {@link TreeContextMapping} to retrieve the view model context to remove the
		 *            context users from.
		 */
		private void cleanupRemovedValues(Object removedValueObject, TreeContextMapping treeContextEntry) {
			if (removedValueObject != null) {
				final Set<Object> removedChildren = new HashSet<Object>();
				if (removedValueObject instanceof Collection) {
					for (final Object removedListObject : (Collection<?>) removedValueObject) {
						if (removedListObject instanceof EObject) {
							removedChildren.add(removedListObject);
						}
					}
				} else if (removedValueObject instanceof EObject) {
					removedChildren.add(removedValueObject);
				}
				for (final Object removedChild : removedChildren) {
					final ViewModelContext childContext = getExistingOrNewChildContext(treeContextEntry,
						removedChild);
					childContext.removeContextUser(this);
					childContext.removeContextUser(TreeMasterDetailValidationInitiator.this);
					mapping.get(treeContextEntry).remove(removedChild);
				}
			}
		}

		/**
		 * Checks whether a child object has been removed by looking at the notification type and comparing the actual
		 * children object to the children object known to the TreeMasterDetailValidationInitiator.
		 *
		 * @param children The actual children objects of the notifier
		 * @param childrenFromTreeContextEntry The children objects of the notifier known to the
		 *            TreeMasterDetailValidationInitiator
		 * @param notificationType The type of notification
		 * @return Whether a child object of the notifier was removed
		 */
		private boolean isChildObjectRemoved(Set<Object> children, Set<Object> childrenFromTreeContextEntry,
			int notificationType) {
			return (Notification.REMOVE == notificationType || Notification.REMOVE_MANY == notificationType)
				&& !children.containsAll(childrenFromTreeContextEntry);
		}

		private ViewModelContext getExistingOrNewChildContext(TreeContextMapping treeContextEntry, Object childObject) {
			final VElement viewModel = context.getViewModel();
			final VViewModelProperties properties = ViewModelPropertiesHelper
				.getInhertitedPropertiesOrEmpty(viewModel);
			properties.addNonInheritableProperty(DETAIL_KEY, true);
			final EObject eObject = (EObject) manipulateSelection(childObject);

			return treeContextEntry.context.getChildContext(eObject,
				treeContextEntry.control, ViewProviderHelper.getView(eObject, properties));
		}
	}

	/**
	 * A Mapping for TreeMasterDetails.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	private static class TreeContextMapping {
		private VTreeMasterDetail control;
		private ViewModelContext context;

		/**
		 * {@inheritDoc}
		 *
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (context == null ? 0 : context.hashCode());
			result = prime * result + (control == null ? 0 : control.hashCode());
			return result;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final TreeContextMapping other = (TreeContextMapping) obj;
			if (context == null) {
				if (other.context != null) {
					return false;
				}
			} else if (!context.equals(other.context)) {
				return false;
			}
			if (control == null) {
				if (other.control != null) {
					return false;
				}
			} else if (!control.equals(other.control)) {
				return false;
			}
			return true;
		}

	}

	private final Map<TreeContextMapping, Set<Object>> mapping = new LinkedHashMap<TreeContextMapping, Set<Object>>();
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
	private ViewModelContext context;

	/**
	 * Constructor of the Initiator.
	 */
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
	public void instantiate(final ViewModelContext context) {
		this.context = context;
		context.registerDomainChangeListener(new TreeMasterDetailValidationInitiatorDomainChangeListener(context));
		checkForTreeMasterDetail(context);
		context.registerEMFFormsContextListener(this);
	}

	private void checkForTreeMasterDetail(ViewModelContext context) {
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
			final VElement viewModel = viewModelContext.getViewModel();
			final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(viewModel);
			properties.addNonInheritableProperty(DETAIL_KEY, true);
			final TreeContextMapping entry = new TreeContextMapping();
			entry.context = viewModelContext;
			entry.control = treeMasterDetail;
			if (!mapping.containsKey(entry)) {
				mapping.put(entry, new LinkedHashSet<Object>());
			}
			mapping.get(entry).add(object);

			final EObject manipulateSelection = (EObject) manipulateSelection(object);
			final VView view = ViewProviderHelper.getView(manipulateSelection, properties);

			final ViewModelContext childContext = viewModelContext.getChildContext(manipulateSelection,
				treeMasterDetail, view);
			childContext.addContextUser(this);
		}
	}

	private void registerRootChildContext(VTreeMasterDetail treeMasterDetail, ViewModelContext viewModelContext) {
		final VElement viewModel = viewModelContext.getViewModel();
		final VViewModelProperties properties = ViewModelPropertiesHelper.getInhertitedPropertiesOrEmpty(viewModel);
		properties.addNonInheritableProperty(DETAIL_KEY, true);
		properties.addNonInheritableProperty(ROOT_KEY, true);
		final TreeContextMapping entry = new TreeContextMapping();
		entry.context = viewModelContext;
		entry.control = treeMasterDetail;
		if (!mapping.containsKey(entry)) {
			mapping.put(entry, new LinkedHashSet<Object>());
		}
		mapping.get(entry).add(viewModelContext.getDomainModel());

		final Object manipulateSelection = manipulateSelection(viewModelContext.getDomainModel());
		VView view = treeMasterDetail.getDetailView();
		if (view == null || view.getChildren().isEmpty()) {
			view = ViewProviderHelper.getView((EObject) manipulateSelection, properties);
		}
		final ViewModelContext childContext = viewModelContext.getChildContext((EObject) manipulateSelection,
			treeMasterDetail, view);
		childContext.addContextUser(this);
	}

	private Set<Object> getAllChildren(Object parent, AdapterFactoryContentProvider adapterFactoryContentProvider) {
		final Set<Object> allChildren = new LinkedHashSet<Object>();
		final Object[] children = adapterFactoryContentProvider.getChildren(parent);
		for (final Object object : children) {
			// final Object manipulatedSelection = manipulateSelection(object);
			if (!EObject.class.isInstance(object)) {
				continue;
			}
			allChildren.add(object);
			allChildren.addAll(getAllChildren(object, adapterFactoryContentProvider));
		}
		return allChildren;
	}

	private Object manipulateSelection(Object object) {
		return TreeMasterDetailSelectionManipulatorHelper.manipulateSelection(object);
	}

	@Override
	public void dispose() {
		context.unregisterEMFFormsContextListener(this);
		adapterFactoryContentProvider.dispose();
		adapterFactory.dispose();
	}

	@Override
	public int getPriority() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.GlobalViewModelService#childViewModelContextAdded(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void childViewModelContextAdded(ViewModelContext childContext) {
		checkForTreeMasterDetail(childContext);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextAdded(org.eclipse.emf.ecp.view.spi.model.VElement,
	 *      org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
		if (ViewModelContext.class.isInstance(childContext)) {
			checkForTreeMasterDetail(ViewModelContext.class.cast(childContext));
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#childContextDisposed(org.eclipse.emfforms.spi.core.services.view.EMFFormsViewContext)
	 */
	@Override
	public void childContextDisposed(EMFFormsViewContext childContext) {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextInitialised()
	 */
	@Override
	public void contextInitialised() {
		// intentionally left empty
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.view.EMFFormsContextListener#contextDispose()
	 */
	@Override
	public void contextDispose() {
		// intentionally left empty
	}

}
