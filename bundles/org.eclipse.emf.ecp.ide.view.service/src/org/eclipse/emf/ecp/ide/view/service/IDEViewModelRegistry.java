/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.view.service;

import java.io.IOException;

import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * The View Model registry handling the update of open view model editors.
 *
 * @author Eugen Neufeld
 *
 */
public interface IDEViewModelRegistry {

	/**
	 * Register an ECore with a VView.
	 *
	 * @param ecorePath the path to the ECore connected to the VView
	 * @param viewModel the VView
	 */
	void register(String ecorePath, VView viewModel);

	/**
	 * Unregister a VView from an ECore. eg when the root class changes.
	 *
	 * @param registeredEcorePath the path to the ECore connected to the VView
	 * @param viewModel the VView
	 */
	void unregister(String registeredEcorePath, VView viewModel);

	/**
	 * Register a view model editor with a view.
	 *
	 * @param viewModel the VView
	 * @param viewModelEditor the view model editor
	 * @throws IOException if a resource cannot be loaded
	 */
	void registerViewModelEditor(VView viewModel, ViewModelEditorCallback viewModelEditor) throws IOException;

	/**
	 * Unregister a view model editor, called when the view model editor closes.
	 *
	 * @param viewModel the VView
	 * @param viewModelEditor the view model editor
	 */
	void unregisterViewModelEditor(VView viewModel, ViewModelEditorCallback viewModelEditor);

	/**
	 * Register a view with its absolute path.
	 *
	 * @param view the VView
	 * @param viewPath the path to the view file
	 */
	void registerViewModel(VView view, String viewPath);
}
