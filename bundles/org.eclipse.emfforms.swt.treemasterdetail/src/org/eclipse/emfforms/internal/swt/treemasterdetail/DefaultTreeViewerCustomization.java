/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.treemasterdetail;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.model.common.edit.provider.CustomReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultDNDProvider;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultMenuProvider;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultTreeViewerBuilder;
import org.eclipse.emfforms.internal.swt.treemasterdetail.defaultprovider.DefaultViewerFilterProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ContentProviderProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DNDProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.DeleteActionBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.InitialSelectionProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.LabelProviderProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.MenuProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerBuilder;
import org.eclipse.emfforms.spi.swt.treemasterdetail.TreeViewerCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.ViewerFilterProvider;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

/**
 * Default implementation of the {@link TreeViewerCustomization}.
 *
 * @author Johannes Faltermeier
 *
 */
public class DefaultTreeViewerCustomization implements TreeViewerCustomization {

	private ViewerFilterProvider filters;
	private TreeViewerBuilder tree;
	private InitialSelectionProvider selection;
	private MenuProvider menu;
	private LabelProviderProvider labelProvider;
	private DNDProvider dnd;
	private ContentProviderProvider contentProvider;
	private ComposedAdapterFactory adapterFactory;
	private AdapterFactoryContentProvider adapterFactoryContentProvider;

	/**
	 * Default constructor.
	 */
	public DefaultTreeViewerCustomization() {
		filters = new DefaultViewerFilterProvider();
		tree = new DefaultTreeViewerBuilder();
		selection = new DefaultTreeMasterDetailSelectionProvider();
		menu = new DefaultMenuProvider();
		labelProvider = new DefaultLabelProviderProvider();
		dnd = new DefaultDNDProvider();
		contentProvider = new DefaultContentProviderProvider();
	}

	/**
	 * Gives access to the composed adapter factory.
	 *
	 * @return the adapter factory
	 */
	protected ComposedAdapterFactory getComposedAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new CustomReflectiveItemProviderAdapterFactory(),
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		}
		return adapterFactory;
	}

	/**
	 * Returns the {@link AdapterFactoryContentProvider}.
	 *
	 * @return the content provider
	 */
	protected AdapterFactoryContentProvider getAdapterFactoryContentProvider() {
		if (adapterFactoryContentProvider == null) {
			final ComposedAdapterFactory adapterFactory = getComposedAdapterFactory();
			adapterFactoryContentProvider = new AdapterFactoryContentProvider(
				adapterFactory) {

				@Override
				public Object[] getElements(Object object) {
					if (RootObject.class.isInstance(object)) {
						return new Object[] { RootObject.class.cast(object).getRoot() };
					}
					return super.getElements(object);
				}

				@Override
				public boolean hasChildren(Object object) {
					return getChildren(object).length > 0;
				}
			};
		}
		return adapterFactoryContentProvider;
	}

	@Override
	public TreeViewer createTree(Composite parent) {
		return tree.createTree(parent);
	}

	@Override
	public boolean hasDND() {
		return dnd.hasDND();
	}

	@Override
	public int getDragOperations() {
		return dnd.getDragOperations();
	}

	@Override
	public Transfer[] getDragTransferTypes() {
		return dnd.getDragTransferTypes();
	}

	@Override
	public DragSourceListener getDragListener(TreeViewer treeViewer) {
		return dnd.getDragListener(treeViewer);
	}

	@Override
	public int getDropOperations() {
		return dnd.getDropOperations();
	}

	@Override
	public Transfer[] getDropTransferTypes() {
		return dnd.getDropTransferTypes();
	}

	@Override
	public DropTargetListener getDropListener(EditingDomain editingDomain, TreeViewer treeViewer) {
		return dnd.getDropListener(editingDomain, treeViewer);
	}

	@Override
	public IContentProvider getContentProvider() {
		return contentProvider.getContentProvider();
	}

	@Override
	public IBaseLabelProvider getLabelProvider() {
		return labelProvider.getLabelProvider();
	}

	@Override
	public ViewerFilter[] getViewerFilters() {
		return filters.getViewerFilters();
	}

	@Override
	public EObject getInitialSelection(Object input) {
		return selection.getInitialSelection(input);
	}

	@Override
	public Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain) {
		return menu.getMenu(treeViewer, editingDomain);
	}

	@Override
	public void dispose() {
		contentProvider.dispose();
		labelProvider.dispose();
		if (adapterFactoryContentProvider != null) {
			adapterFactoryContentProvider.dispose();
		}
		if (adapterFactory != null) {
			adapterFactory.dispose();
		}
	}

	/**
	 * Sets the content provider provider.
	 *
	 * @param contentProvider the content provider
	 */
	public void setContentProvider(ContentProviderProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * Sets the d&d support.
	 *
	 * @param dnd the dnd
	 */
	public void setDragAndDrop(DNDProvider dnd) {
		this.dnd = dnd;
	}

	/**
	 * Sets the label provider provider.
	 *
	 * @param labelProvider the provider
	 */
	public void setLabelProvider(LabelProviderProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * Sets the menu provider.
	 *
	 * @param menu the provider
	 */
	public void setMenu(MenuProvider menu) {
		this.menu = menu;
	}

	/**
	 * Sets the right click actions of the menu.
	 *
	 * @param rightClickActions the actions
	 */
	public void customizeMenu(Collection<MasterDetailAction> rightClickActions) {
		if (!DefaultMenuProvider.class.isInstance(menu)) {
			menu = new DefaultMenuProvider();
		}
		final DefaultMenuProvider defaultMenuProvider = DefaultMenuProvider.class.cast(menu);
		defaultMenuProvider.setRightClickAction(rightClickActions);
	}

	/**
	 * Sets the {@link CreateElementCallback}.
	 *
	 * @param createElementCallback the callback
	 */
	public void customizeMenu(CreateElementCallback createElementCallback) {
		if (!DefaultMenuProvider.class.isInstance(menu)) {
			menu = new DefaultMenuProvider();
		}
		final DefaultMenuProvider defaultMenuProvider = DefaultMenuProvider.class.cast(menu);
		defaultMenuProvider.setCreateElementCallback(createElementCallback);
	}

	/**
	 * Sets the delete action.
	 *
	 * @param deleteActionBuilder the builder
	 */
	public void customizeMenu(DeleteActionBuilder deleteActionBuilder) {
		if (!DefaultMenuProvider.class.isInstance(menu)) {
			menu = new DefaultMenuProvider();
		}
		final DefaultMenuProvider defaultMenuProvider = DefaultMenuProvider.class.cast(menu);
		defaultMenuProvider.setDeleteAction(deleteActionBuilder);
	}

	/**
	 * Sets the initial selection provider.
	 *
	 * @param selection the provider
	 */
	public void setInitialSelection(InitialSelectionProvider selection) {
		this.selection = selection;
	}

	/**
	 * Sets the tree builder.
	 *
	 * @param tree the tree builder
	 */
	public void setTree(TreeViewerBuilder tree) {
		this.tree = tree;
	}

	/**
	 * Sets the viewer filter provider.
	 *
	 * @param filters the provider
	 */
	public void setViewerFilters(ViewerFilterProvider filters) {
		this.filters = filters;
	}

	/**
	 * Default {@link ContentProviderProvider}.
	 *
	 * @author jfaltermeier
	 *
	 */
	private final class DefaultContentProviderProvider implements ContentProviderProvider {
		@Override
		public IContentProvider getContentProvider() {
			return getAdapterFactoryContentProvider();
		}

		@Override
		public void dispose() {
			/* disposed by build behaviour */
		}
	}

	/**
	 * Default {@link LabelProviderProvider}.
	 *
	 * @author jfaltermeier
	 *
	 */
	private final class DefaultLabelProviderProvider implements LabelProviderProvider {
		private AdapterFactoryLabelProvider provider;

		@Override
		public IBaseLabelProvider getLabelProvider() {
			final ComposedAdapterFactory adapterFactory = getComposedAdapterFactory();
			provider = new AdapterFactoryLabelProvider(adapterFactory);
			return provider;
		}

		@Override
		public void dispose() {
			/* adapter factory will be disposed by build behaviour */
			provider.dispose();
		}
	}

	/**
	 * Default {@link InitialSelectionProvider}.
	 *
	 * @author jfaltermeier
	 *
	 */
	private final class DefaultTreeMasterDetailSelectionProvider implements InitialSelectionProvider {
		@Override
		public EObject getInitialSelection(Object input) {
			final AdapterFactoryContentProvider contentProvider = getAdapterFactoryContentProvider();
			if (input instanceof EObject) {
				return (EObject) input;
			}
			for (final Object child : contentProvider.getChildren(input)) {
				final EObject childSelector = getInitialSelection(child);
				if (childSelector != null) {
					return childSelector;
				}
			}
			return null;
		}
	}

}
