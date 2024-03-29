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
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.common;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.core.util.ECPModelContextProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.core.Activator;
import org.eclipse.emf.ecp.internal.ui.model.IECPLabelProvider;
import org.eclipse.emf.ecp.internal.ui.model.ModelContentProvider;
import org.eclipse.emf.ecp.internal.ui.model.ModelLabelProvider;
import org.eclipse.emf.ecp.internal.ui.model.RepositoriesContentProvider;
import org.eclipse.emf.ecp.internal.ui.model.RepositoriesLabelProvider;
import org.eclipse.emf.ecp.spi.common.ui.TreeViewerFactory;
import org.eclipse.emf.ecp.ui.common.dnd.ECPDropAdapter;
import org.eclipse.emf.ecp.ui.common.dnd.ModelExplorerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

/**
 * Utility class to create components for ECP.
 *
 * @author Eugen Neufeld
 *
 */
public final class ECPViewerFactory {

	private ECPViewerFactory() {
	}

	/**
	 * Create the ECP ModelExplorer View which is based on the {@link TreeViewer}.
	 *
	 * @param parent The {@link Composite} to create onto
	 * @param hasDnD Whether dnd should be enabled
	 * @param labelDecorator The {@link ILabelDecorator} to use on labels.
	 * @return The created {@link TreeViewer}
	 */
	public static TreeViewer createModelExplorerViewer(Composite parent, boolean hasDnD,
		ILabelDecorator labelDecorator) {
		final ModelContentProvider contentProvider = new ModelContentProvider();
		final TreeViewer viewer = TreeViewerFactory.createTreeViewer(parent, getLabelProvider(contentProvider),
			contentProvider,
			ECPUtil.getECPProjectManager(), labelDecorator, false);
		if (hasDnD) {
			final ECPDropAdapter dropAdapter = getDropAdapter(contentProvider, viewer);

			final int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
			final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
			viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));// new ECPDragAdapter(viewer)
			viewer.addDropSupport(dndOperations, transfers, dropAdapter);// ComposedDropAdapter
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					final Object[] elements = ((IStructuredSelection) event.getSelection()).toArray();
					if (elements == null || elements.length == 0) {
						return;
					}
					ECPProject project = null;
					if (elements[0] instanceof ECPProject) {
						final ECPContainer context = ECPUtil.getModelContext(contentProvider, elements);
						if (context != null && context instanceof ECPProject) {
							project = (ECPProject) context;
						}
					} else {
						final ECPModelContextProvider contextProvider = (ECPModelContextProvider) viewer
							.getContentProvider();
						project = (ECPProject) ECPUtil.getModelContext(contextProvider, elements[0]);
					}
					if (project != null) {
						dropAdapter.setEditingDomain(project.getEditingDomain());
					}
				}
			});
		}
		return viewer;
	}

	private static ECPDropAdapter getDropAdapter(ModelContentProvider contentProvider, TreeViewer viewer) {
		ECPDropAdapter dropAdapter = null;
		// read extensionpoint, if no defined take default
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
			"org.eclipse.emf.ecp.ui.dropAdapter"); //$NON-NLS-1$
		for (final IExtension extension : extensionPoint.getExtensions()) {
			final IConfigurationElement configurationElement = extension.getConfigurationElements()[0];
			try {
				dropAdapter = (ECPDropAdapter) configurationElement.createExecutableExtension("class"); //$NON-NLS-1$
				dropAdapter.setViewer(viewer);
				break;
			} catch (final CoreException ex) {
				Activator.log(ex);
			}
		}
		if (dropAdapter == null) {
			dropAdapter = new ModelExplorerDropAdapter(viewer);
		}
		return dropAdapter;
	}

	private static ILabelProvider getLabelProvider(ModelContentProvider contentProvider) {
		IECPLabelProvider labelProvider = null;
		// read extensionpoint, if no defined take default
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
			"org.eclipse.emf.ecp.ui.labelProvider"); //$NON-NLS-1$
		for (final IExtension extension : extensionPoint.getExtensions()) {
			final IConfigurationElement configurationElement = extension.getConfigurationElements()[0];
			try {
				labelProvider = (IECPLabelProvider) configurationElement.createExecutableExtension("class"); //$NON-NLS-1$
				labelProvider.setModelContextProvider(contentProvider);
				return labelProvider;
			} catch (final CoreException ex) {
				Activator.log(ex);
			}
		}

		labelProvider = new ModelLabelProvider(contentProvider);
		return labelProvider;
	}

	/**
	 * Create the ECP RepositoryExplorer View which is based on the {@link TreeViewer}.
	 * 
	 * @param parent The {@link Composite} to create onto
	 * @param labelDecorator The {@link ILabelDecorator} to use on labels
	 * @return The create {@link TreeViewer}
	 */
	public static TreeViewer createRepositoryExplorerViewer(Composite parent, ILabelDecorator labelDecorator) {
		final RepositoriesContentProvider contentProvider = new RepositoriesContentProvider();
		final TreeViewer viewer = TreeViewerFactory.createTreeViewer(parent, new RepositoriesLabelProvider(
			contentProvider),
			contentProvider,
			ECPUtil.getECPRepositoryManager(), labelDecorator, true);
		return viewer;
	}
}
