/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.Collection;

import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeMasterDetailCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * The TreeMasterDetailSWTBuilder is initialized with a default behaviour. It offers methods to customize certain
 * aspects of the tree master detail.
 *
 * @author Johannes Faltermeier
 *
 */
public final class TreeMasterDetailSWTBuilder {

	private final Composite composite;
	private final int swtStyleBits;
	private final Object input;
	private final DefaultTreeMasterDetailCustomization behaviour;

	private int renderDelay;

	/**
	 * Default constructor.
	 *
	 * @param composite the parent composite
	 * @param swtStyleBits the style bits for the tree master detail composite
	 * @param input the input object
	 */
	/* package */ TreeMasterDetailSWTBuilder(Composite composite, int swtStyleBits, Object input) {
		this.composite = composite;
		this.swtStyleBits = swtStyleBits;
		this.input = input;
		behaviour = new DefaultTreeMasterDetailCustomization();
		renderDelay = 100;
	}

	/**
	 * Use this method to set a custom {@link org.eclipse.jface.viewers.IContentProvider IContentProvider} on the tree
	 * viewer. The default implementation will use
	 * an {@link org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider AdapterFactoryContentProvider}.
	 *
	 * @param contentProvider the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeContentProvider(ContentProviderProvider contentProvider) {
		behaviour.setContentProvider(contentProvider);
		return this;
	}

	/**
	 * Use this method to set a custom {@link org.eclipse.jface.viewers.IContentProvider IContentProvider} on the tree
	 * viewer. If the content provider requires more dispose code than calling {@link IContentProvider#dispose()} use
	 * {@link #customizeContentProvider(ContentProviderProvider)} instead. The default implementation will use
	 * an {@link org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider AdapterFactoryContentProvider}.
	 *
	 * @param contentProvider the content provider to add
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeContentProvider(final IContentProvider contentProvider) {
		behaviour.setContentProvider(new ContentProviderProvider() {

			@Override
			public void dispose() {
				contentProvider.dispose();
			}

			@Override
			public IContentProvider getContentProvider() {
				return contentProvider;
			}
		});
		return this;
	}

	/**
	 * Use this method to create the parent composite, which will contain the detail composite. The default
	 * implementation will create a {@link org.eclipse.swt.custom.ScrolledComposite ScrolledComposite} which allows
	 * vertical and horizontal scrolling.
	 *
	 * @param detailComposite the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeDetailComposite(DetailCompositeBuilder detailComposite) {
		behaviour.setDetailComposite(detailComposite);
		return this;
	}

	/**
	 * Use this method to add a customized drag and drop behaviour to the tree or to remove drag and drop functionality.
	 * The default implementation supports {@link org.eclipse.swt.dnd.DND#DROP_COPY DND#DROP_COPY},
	 * {@link org.eclipse.swt.dnd.DND#DROP_MOVE DND#DROP_MOVE} and {@link org.eclipse.swt.dnd.DND#DROP_LINK
	 * DND#DROP_LINK} based
	 * on EMF Edit.
	 *
	 * @param dnd the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeDragAndDrop(DNDProvider dnd) {
		behaviour.setDragAndDrop(dnd);
		return this;
	}

	/**
	 * Use this method a add a custom {@link org.eclipse.jface.viewers.IBaseLabelProvider IBaseLabelProvider} to the
	 * tree. The default implementation uses an
	 * {@link org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider AdapterFactoryLabelProvider}.
	 *
	 * @param provider the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeLabelProvider(LabelProviderProvider provider) {
		behaviour.setLabelProvider(provider);
		return this;
	}

	/**
	 * Use this method a add a custom {@link org.eclipse.jface.viewers.IBaseLabelProvider IBaseLabelProvider} to the
	 * tree. If the label provider requires more dispose code than a call to {@link IBaseLabelProvider#dispose()} use
	 * {@link #customizeLabelProvider(LabelProviderProvider)} instead. The default implementation uses an
	 * {@link org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider AdapterFactoryLabelProvider}.
	 *
	 * @param provider the label provider to add
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeLabelProvider(final IBaseLabelProvider provider) {
		behaviour.setLabelProvider(new LabelProviderProvider() {

			@Override
			public void dispose() {
				provider.dispose();
			}

			@Override
			public IBaseLabelProvider getLabelProvider() {
				return provider;
			}
		});
		return this;
	}

	/**
	 * Use this method to add a {@link ILabelDecorator} for decorating the labels of the label provider. The default
	 * implementation does not use a decorator.
	 *
	 * @param provider the {@link LabelDecoratorProvider} which will be used to create the decorator
	 * @return self
	 * @since 1.9
	 */
	public TreeMasterDetailSWTBuilder customizeLabelDecorator(LabelDecoratorProvider provider) {
		behaviour.setLabelDecorator(provider);
		return this;
	}

	/**
	 * Use this method to add a {@link ILabelDecorator} for decorating the labels of the label provider. The default
	 * implementation does not use a decorator.
	 *
	 * @param decorator the decorator instance to be used
	 * @return self
	 * @since 1.9
	 */
	public TreeMasterDetailSWTBuilder customizeLabelDecorator(final ILabelDecorator decorator) {
		behaviour.setLabelDecorator(new LabelDecoratorProvider() {
			@Override
			public Optional<ILabelDecorator> getLabelDecorator(TreeViewer viewer) {
				return Optional.of(decorator);
			}

			@Override
			public void dispose() {
				/* no op */
			}
		});
		return this;
	}

	/**
	 * Use this method to customize the {@link org.eclipse.swt.widgets.Menu Menu} which is shown when an element in the
	 * tree is right-clicked. The
	 * default implementation will offer menu entries to create new elements based on EMF Edit and to delete elements.
	 *
	 * @param menu the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeMenu(MenuProvider menu) {
		behaviour.setMenu(menu);
		return this;
	}

	/**
	 * Use this method to customize the {@link org.eclipse.swt.widgets.Menu Menu} which is shown when an element in the
	 * tree is right-clicked. Use this method to add additional menu entries.
	 *
	 * @param rightClickActions the additional right click actions which will be shown in the context menu
	 * @return self
	 * @since 1.8
	 */
	public TreeMasterDetailSWTBuilder customizeMenuItems(Collection<MasterDetailAction> rightClickActions) {
		behaviour.customizeMenu(rightClickActions);
		return this;
	}

	/**
	 * Use this method to customize the {@link org.eclipse.swt.widgets.Menu Menu} which is shown when an element in the
	 * tree is right-clicked. Use this method to influence the way new children are created.
	 *
	 * @param createElementCallback a callback which gets notified when a new child is created. this allows to veto the
	 *            creation or to change the object to be added
	 * @return self
	 * @since 1.8
	 */
	public TreeMasterDetailSWTBuilder customizeCildCreation(CreateElementCallback createElementCallback) {
		behaviour.customizeMenu(createElementCallback);
		return this;
	}

	/**
	 * Use this method to customize the {@link org.eclipse.swt.widgets.Menu Menu} which is shown when an element in the
	 * tree is right-clicked. Use this method to change the way elements are deleted.
	 *
	 * @param deleteActionBuilder the delete action which will be added to the context menu
	 * @return self
	 * @since 1.8
	 */
	public TreeMasterDetailSWTBuilder customizeDelete(DeleteActionBuilder deleteActionBuilder) {
		behaviour.customizeMenu(deleteActionBuilder);
		return this;
	}

	/**
	 * Use this method to customize which element should be selected after the initial rendering. The default bahviour
	 * is to select the root node, if it is displayed in the tree.
	 *
	 * @param selection the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeInitialSelection(InitialSelectionProvider selection) {
		behaviour.setInitialSelection(selection);
		return this;
	}

	/**
	 * Use this method to create the {@link org.eclipse.jface.viewers.TreeViewer TreeViewer} which is part of the tree
	 * master detail. The default
	 * implementation creates a regular {@link org.eclipse.jface.viewers.TreeViewer TreeViewer} with an
	 * {@link org.eclipse.jface.viewers.TreeViewer#setAutoExpandLevel(int) expand
	 * level} of 3.
	 *
	 * @param tree the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeTree(TreeViewerBuilder tree) {
		behaviour.setTree(tree);
		return this;
	}

	/**
	 * Use this method to influence the width of the composite which hosts the
	 * {@link org.eclipse.jface.viewers.TreeViewer TreeViewer}. The default is 300px.
	 *
	 * @param width the desired width
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeInitialTreeWidth(final int width) {
		behaviour.setInitialTreeWidth(new TreeWidthProvider() {
			@Override
			public int getInitialTreeWidth() {
				return width;
			}
		});
		return this;
	}

	/**
	 * Use this method to add {@link org.eclipse.jface.viewers.ViewerFilter ViewerFilters} on the tree. The default
	 * implementation does not add
	 * filters.
	 *
	 * @param filters the filters to add
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeViewerFilters(final ViewerFilter[] filters) {
		behaviour.setViewerFilters(new ViewerFilterProvider() {
			@Override
			public ViewerFilter[] getViewerFilters() {
				return filters;
			}
		});
		return this;
	}

	/**
	 * Use this method to specify which {@link org.eclipse.emf.ecp.view.spi.context.ViewModelService ViewModelServices}
	 * will be added when the detail views
	 * are rendered. The default implementation does not add any services.
	 *
	 * @param viewServiceProvider the desired behavior
	 * @return self
	 */
	public TreeMasterDetailSWTBuilder customizeViewModelServices(ViewModelServiceProvider viewServiceProvider) {
		behaviour.setViewModelServices(viewServiceProvider);
		return this;
	}

	/**
	 * Use this method to specify the time between a detected selection change and updating the detail panel. The
	 * default is 100ms.
	 *
	 * @param updateDelay the new update delay in ms
	 * @return self
	 * @since 1.11
	 */
	public TreeMasterDetailSWTBuilder customizeUpdateDelay(int updateDelay) {
		renderDelay = updateDelay;
		return this;
	}

	/**
	 * Use this method to specify whether the tree and its details should be rendered as read-only. The default is
	 * false. Setting the tree master detail as read-only has the following implications:
	 * <ul>
	 * <li>Set all detail views as read-only</li>
	 * <li>Disable the tree's context menu</li>
	 * <li>Deactivate drag and drop in the tree</li>
	 * </ul>
	 * <strong>Note:</strong> Setting this to true voids all menu and DND customizations.
	 *
	 * @param readOnly <code>true</code> to make the tree master detail read-only.
	 * @return self
	 * @since 1.22
	 */
	public TreeMasterDetailSWTBuilder customizeReadOnly(boolean readOnly) {
		behaviour.setReadOnly(readOnly);
		return this;
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link TreeMasterDetailComposite} with the desired customizations.
	 *
	 * @return the {@link TreeMasterDetailComposite}
	 */
	public TreeMasterDetailComposite create() {
		return new TreeMasterDetailComposite(composite, swtStyleBits, input, behaviour, renderDelay);
	}

}
