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

import org.eclipse.emfforms.internal.swt.treemasterdetail.DefaultTreeViewerCustomization;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * This factory allows to create {@link TreeViewer TreeViewers}.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public final class TreeViewerSWTFactory {

	private TreeViewerSWTFactory() {
		// factory
	}

	/**
	 * Use this method if you want to customize any behavior of the {@link TreeViewer}. This will return
	 * a {@link TreeViewerSWTBuilder} which allows to customize certain aspects.
	 *
	 * @param composite the parent composite
	 * @param input the input object
	 * @return the builder
	 */
	public static TreeViewerSWTBuilder fillDefaults(Composite composite, Object input) {
		return new TreeViewerSWTBuilder(composite, input);
	}

	/**
	 * Creates a {@link TreeViewer} with the default behavior.
	 *
	 * @param parent the parent composite
	 * @param input the input object
	 * @return the tree viewer
	 */
	public static TreeViewer createTreeViewer(Composite parent, Object input) {
		return TreeViewerSWTBuilder.create(new DefaultTreeViewerCustomization(), parent,
			TreeViewerSWTBuilder.getEditingDomain(input), input);
	}

	/**
	 * Creates a {@link TreeViewer} with a customized behavior. Please note that there is also the
	 * {@link #fillDefaults(Composite, Object)} method which allows to customize single aspects of the default
	 * behavior without having to provider a full implementation of {@link TreeViewerCustomization}.
	 *
	 * @param parent the parent composite
	 * @param input the input object
	 * @param buildBehaviour the custom behavior
	 * @return the tree viewer
	 */
	public static TreeViewer createTreeViewer(Composite parent, Object input,
		TreeViewerCustomization buildBehaviour) {
		return TreeViewerSWTBuilder.create(buildBehaviour, parent, TreeViewerSWTBuilder.getEditingDomain(input), input);
	}

}
