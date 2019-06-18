/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 546899
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.ui.validation;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * Helper class for creating the tree viewer used for the validation view.
 *
 * @author jfaltermeier
 *
 */
@Creatable
public final class ValidationTreeViewerFactory {

	private static final ValidationTreeViewerFactory BASIC = new ValidationTreeViewerFactory();

	@Inject
	private Provider<ValidationContentProvider> contentProviderProvider;

	@Inject
	private Provider<ValidationLabelProvider> labelProviderProvider;

	static {
		BASIC.contentProviderProvider = ValidationContentProvider::new;
		BASIC.labelProviderProvider = ValidationLabelProvider::new;
	}

	/**
	 * Initializes me.
	 */
	public ValidationTreeViewerFactory() {
		super();
	}

	/**
	 * Creates a {@link TreeViewer} which is able to display validation results from
	 * {@link org.eclipse.core.runtime.IStatus IStatus} objects.
	 *
	 * @param parent the parent of the viewer
	 * @return the tree viewer
	 */
	public TreeViewer create(Composite parent) {
		final Tree validationTree = new Tree(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		validationTree.setHeaderVisible(true);
		validationTree.setLinesVisible(true);
		final TreeViewer treeViewer = new TreeViewer(validationTree);

		final TreeColumn messageColumn = new TreeColumn(validationTree, SWT.LEFT);
		messageColumn.setAlignment(SWT.LEFT);
		messageColumn.setText(Messages.ValidationTreeViewerFactory_Description);
		messageColumn.setWidth(600);

		final TreeColumn objectColumn = new TreeColumn(validationTree, SWT.LEFT);
		objectColumn.setAlignment(SWT.LEFT);
		objectColumn.setText(Messages.ValidationTreeViewerFactory_Object);
		objectColumn.setWidth(200);

		final TreeColumn featureColumn = new TreeColumn(validationTree, SWT.LEFT);
		featureColumn.setAlignment(SWT.LEFT);
		featureColumn.setText(Messages.ValidationTreeViewerFactory_Feature);
		featureColumn.setWidth(200);

		treeViewer.setContentProvider(contentProviderProvider.get());
		treeViewer.setLabelProvider(labelProviderProvider.get());
		addDoubleClickListener(treeViewer);
		return treeViewer;
	}

	/**
	 * Creates a {@link TreeViewer} which is able to display validation results from
	 * {@link org.eclipse.core.runtime.IStatus IStatus} objects.
	 *
	 * @param parent the parent of the viewer
	 * @return the tree viewer
	 */
	public static TreeViewer createValidationViewer(Composite parent) {
		return BASIC.create(parent);
	}

	/**
	 * @param treeViewer
	 */
	private static void addDoubleClickListener(TreeViewer treeViewer) {
		IConfigurationElement[] doubleClickListener = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.ui.validation.doubleClickListener"); //$NON-NLS-1$
		for (final IConfigurationElement element : doubleClickListener) {
			doubleClickListener = null;
			try {
				final IDoubleClickListener dcl = (IDoubleClickListener) element.createExecutableExtension("class"); //$NON-NLS-1$
				treeViewer.addDoubleClickListener(dcl);
			} catch (final CoreException e) {
			}
		}
	}

}
