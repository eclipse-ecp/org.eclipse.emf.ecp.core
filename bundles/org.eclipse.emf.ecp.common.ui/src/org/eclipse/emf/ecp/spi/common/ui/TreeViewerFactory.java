/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.spi.common.ui;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public final class TreeViewerFactory {
	private TreeViewerFactory() {
	}

	public static TreeViewer createTreeViewer(Composite parent, ILabelProvider labelProvider,
		ITreeContentProvider contentProvider, Object input, ILabelDecorator labelDecorator, boolean sort) {
		final TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		createTreeViewer(labelProvider, contentProvider, input, labelDecorator, viewer, sort);
		return viewer;
	}

	public static TreeViewer createCheckedTreeViewer(Composite parent, ILabelProvider labelProvider,
		ITreeContentProvider contentProvider, Object input, ILabelDecorator labelDecorator, boolean sort) {
		final CheckboxTreeViewer viewer = new CheckboxTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
			| SWT.V_SCROLL);
		createTreeViewer(labelProvider, contentProvider, input, labelDecorator, viewer, sort);
		return viewer;
	}

	/**
	 * @param labelProvider
	 * @param contentProvider
	 * @param input
	 * @param labelDecorator
	 * @param viewer
	 */
	private static void createTreeViewer(ILabelProvider labelProvider, ITreeContentProvider contentProvider,
		Object input, ILabelDecorator labelDecorator, TreeViewer viewer, boolean sort) {
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		if (sort) {
			viewer.setSorter(new ViewerSorter());
		}
		viewer.setInput(input);

		if (labelDecorator != null) {
			if (!(labelProvider instanceof DecoratingLabelProvider)) {
				viewer.setLabelProvider(new DecoratingLabelProvider(labelProvider, labelDecorator));
			}
		}
	}
}
