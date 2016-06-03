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
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.internal.swt.treemasterdetail.BaseLabelProviderWrapper;
import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeViewerCustomization;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.MasterDetailAction;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.CreateElementCallback;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * The TreeViewerSWTBuilder is initialized with a default behavior. It offers methods to customize certain
 * aspects of a {@link org.eclipse.jface.viewers.TreeViewer TreeViewer}.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public class TreeViewerSWTBuilder {

	private final EditingDomain editingDomain;
	private final Composite composite;
	private final Object input;
	private final DefaultTreeViewerCustomization behaviour;

	/**
	 * Default constructor.
	 *
	 * @param composite the parent composite
	 * @param input the input object
	 */
	/* package */ TreeViewerSWTBuilder(Composite composite, Object input) {
		this.composite = composite;
		this.input = input;
		editingDomain = getEditingDomain(input);
		behaviour = new DefaultTreeViewerCustomization();
	}

	/**
	 * Use this method to set a custom {@link org.eclipse.jface.viewers.IContentProvider IContentProvider} on the tree
	 * viewer. The default implementation will use
	 * an {@link org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider AdapterFactoryContentProvider}.
	 *
	 * @param contentProvider the desired behavior
	 * @return self
	 */
	public TreeViewerSWTBuilder customizeContentProvider(ContentProviderProvider contentProvider) {
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
	public TreeViewerSWTBuilder customizeContentProvider(final IContentProvider contentProvider) {
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
	 * Use this method to add a customized drag and drop behaviour to the tree or to remove drag and drop functionality.
	 * The default implementation supports {@link org.eclipse.swt.dnd.DND#DROP_COPY DND#DROP_COPY},
	 * {@link org.eclipse.swt.dnd.DND#DROP_MOVE DND#DROP_MOVE} and {@link org.eclipse.swt.dnd.DND#DROP_LINK
	 * DND#DROP_LINK} based
	 * on EMF Edit.
	 *
	 * @param dnd the desired behavior
	 * @return self
	 */
	public TreeViewerSWTBuilder customizeDragAndDrop(DNDProvider dnd) {
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
	public TreeViewerSWTBuilder customizeLabelProvider(LabelProviderProvider provider) {
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
	public TreeViewerSWTBuilder customizeLabelProvider(final IBaseLabelProvider provider) {
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
	public TreeViewerSWTBuilder customizeLabelDecorator(LabelDecoratorProvider provider) {
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
	public TreeViewerSWTBuilder customizeLabelDecorator(final ILabelDecorator decorator) {
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
	public TreeViewerSWTBuilder customizeMenu(MenuProvider menu) {
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
	public TreeViewerSWTBuilder customizeMenuItems(Collection<MasterDetailAction> rightClickActions) {
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
	public TreeViewerSWTBuilder customizeCildCreation(CreateElementCallback createElementCallback) {
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
	public TreeViewerSWTBuilder customizeDelete(DeleteActionBuilder deleteActionBuilder) {
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
	public TreeViewerSWTBuilder customizeInitialSelection(InitialSelectionProvider selection) {
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
	public TreeViewerSWTBuilder customizeTree(TreeViewerBuilder tree) {
		behaviour.setTree(tree);
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
	public TreeViewerSWTBuilder customizeViewerFilters(final ViewerFilter[] filters) {
		behaviour.setViewerFilters(new ViewerFilterProvider() {
			@Override
			public ViewerFilter[] getViewerFilters() {
				return filters;
			}
		});
		return this;
	}

	/**
	 * Call this method after all desired customizations have been passed to the builder. The will create a new
	 * {@link TreeMasterDetailComposite} with the desired customizations.
	 *
	 * @return the {@link TreeMasterDetailComposite}
	 */
	public TreeViewer create() {
		return create(behaviour, composite, editingDomain, input);
	}

	/**
	 * @param input the input
	 * @return the {@link EditingDomain}
	 */
	static EditingDomain getEditingDomain(Object input) {
		if (input instanceof Resource) {
			return AdapterFactoryEditingDomain.getEditingDomainFor(((Resource) input).getContents().get(0));
		} else if (input instanceof RootObject) {
			return AdapterFactoryEditingDomain.getEditingDomainFor(RootObject.class.cast(input).getRoot());
		} else {
			return AdapterFactoryEditingDomain.getEditingDomainFor(input);
		}
	}

	/**
	 * Creates a {@link TreeViewer}.
	 *
	 * @param behaviour the {@link TreeViewerCustomization}
	 * @param composite the parent {@link Composite}
	 * @param editingDomain the {@link EditingDomain}
	 * @param input the input
	 * @return the viewer
	 */
	static TreeViewer create(TreeViewerCustomization behaviour, Composite composite, EditingDomain editingDomain,
		Object input) {
		final TreeViewer treeViewer = behaviour.createTree(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(treeViewer.getControl());
		if (behaviour.hasDND()) {
			treeViewer.addDragSupport(behaviour.getDragOperations(), behaviour.getDragTransferTypes(),
				behaviour.getDragListener(treeViewer));
			treeViewer.addDropSupport(behaviour.getDropOperations(), behaviour.getDropTransferTypes(),
				behaviour.getDropListener(editingDomain, treeViewer));
		}
		treeViewer.setContentProvider(behaviour.getContentProvider());
		IBaseLabelProvider labelProvider = behaviour.getLabelProvider();
		final Optional<ILabelDecorator> labelDecorator = behaviour.getLabelDecorator(treeViewer);
		if (labelDecorator.isPresent()) {
			ILabelProvider labelProviderForDecorator;
			if (ILabelProvider.class.isInstance(labelProvider)) {
				labelProviderForDecorator = ILabelProvider.class.cast(labelProvider);
			} else {
				labelProviderForDecorator = new BaseLabelProviderWrapper(labelProvider);
			}
			labelProvider = new DecoratingLabelProvider(labelProviderForDecorator,
				labelDecorator.get());
		}
		treeViewer.setLabelProvider(labelProvider);
		treeViewer.setFilters(behaviour.getViewerFilters());
		treeViewer.getControl().setMenu(behaviour.getMenu(treeViewer, editingDomain));
		treeViewer.setInput(input);
		final EObject initialSelection = behaviour.getInitialSelection(input);
		if (initialSelection != null) {
			treeViewer.setSelection(new StructuredSelection(initialSelection), true);
		}
		return treeViewer;
	}

}
