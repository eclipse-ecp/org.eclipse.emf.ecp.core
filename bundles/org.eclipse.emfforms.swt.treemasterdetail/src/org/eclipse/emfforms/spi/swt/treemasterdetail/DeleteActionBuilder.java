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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * The {@link #createDeleteAction(IStructuredSelection, EditingDomain)} method is called everytime a context menu is
 * opened for the given selection.
 *
 * @author Johannes Faltermeier
 * @since 1.8
 *
 */
public interface DeleteActionBuilder {

	/**
	 * This method creates an action which gets added to the context menu of elements in the TreeMasterDetail. The
	 * action is supposed to delete the selected element.
	 *
	 * @param selection the tree viewer selection
	 * @param editingDomain the editing domain
	 * @return the action which will be added
	 */
	Action createDeleteAction(IStructuredSelection selection, EditingDomain editingDomain);

}
