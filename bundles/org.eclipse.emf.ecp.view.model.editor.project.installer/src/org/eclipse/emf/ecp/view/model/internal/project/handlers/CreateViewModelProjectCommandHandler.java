/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.internal.project.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecp.view.model.internal.project.installer.NewPluginProjectWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Alexandra Buzila
 *
 */

/**
 *
 * Handler for creating a new project for a view model.
 *
 */
public class CreateViewModelProjectCommandHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get selected ecore
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final IFile selectedEcore = (IFile) selection.getFirstElement();

		// create and open the project creation wizard
		final NewPluginProjectWizard w = new NewPluginProjectWizard();
		w.setSelectedContainer(selectedEcore);
		w.setSelection(selection);
		w.setWorkbench(PlatformUI.getWorkbench());

		final WizardDialog dialog = new WizardDialog(HandlerUtil.getActiveShell(event), w);
		dialog.open();

		return null;
	}
}
