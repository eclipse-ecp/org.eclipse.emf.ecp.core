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

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Interface to influence the creation of the {@link TreeViewer} for the tree master detail.
 * 
 * @author Johannes Faltermeier
 *
 */
public interface TreeViewerBuilder {

	/**
	 * This method is called to create the {@link TreeViewer}.
	 *
	 * @param parent the {@link Composite parent composite}
	 * @return the viewer
	 */
	TreeViewer createTree(Composite parent);

}