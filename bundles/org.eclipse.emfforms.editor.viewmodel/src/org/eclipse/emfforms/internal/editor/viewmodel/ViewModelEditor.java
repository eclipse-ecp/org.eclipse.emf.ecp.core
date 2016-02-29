/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.viewmodel;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.emfforms.spi.swt.treemasterdetail.util.RootObject;

/**
 * The Editor for ViewModels.
 *
 * @author Clemens Elflein
 */
public class ViewModelEditor extends GenericEditor {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.editor.GenericEditor#hasShortcuts()
	 */
	@Override
	protected boolean hasShortcuts() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.editor.GenericEditor#getEditorTitle()
	 */
	@Override
	protected String getEditorTitle() {
		return "View Model Editor";
	}

	/**
	 * Returns the currently edited View.
	 *
	 * @return The currently edited view
	 */
	public VView getView() {
		return (VView) getResourceSet().getResources().get(0).getAllContents().next();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emfforms.spi.editor.GenericEditor#modifyEditorInput(org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	@Override
	protected Object modifyEditorInput(ResourceSet resourceSet) {
		return new RootObject(getView());
	}
}
