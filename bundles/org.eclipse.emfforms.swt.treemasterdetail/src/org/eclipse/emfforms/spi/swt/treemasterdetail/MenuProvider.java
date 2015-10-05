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

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Menu;

/**
 * Interface to influence the {@link Menu} which is added to the tree master detail.
 * 
 * @author Johannes Faltermeier
 *
 */
public interface MenuProvider {

	/**
	 * Returns the menu which will be added to the tree.
	 *
	 * @param treeViewer the treeviewer
	 * @param editingDomain the editing domain
	 * @return the menu
	 */
	Menu getMenu(TreeViewer treeViewer, EditingDomain editingDomain);

}