/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexandra Buzila - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecp.view.model.presentation.SelectEClassWizardPage;
import org.eclipse.emf.ecp.view.model.presentation.ViewModelWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * An {@link AbstractHandler} that creates a new wizard allowing the creation of new view model files.
 */
public class CreateViewModel extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get ecore
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		// TODO - check if the object can be cast and is an ecore
		final IFile selectedEcore = (IFile) selection.getFirstElement();

		final ViewModelWizard w = new ViewModelWizard();
		w.setSelection(selection);
		w.setSelectedContainer(selectedEcore);
		w.setWorkbench(PlatformUI.getWorkbench());

		final WizardDialog dialog = new ViewModelWizardDialog(HandlerUtil.getActiveShell(event), w);
		dialog.open();

		return null;
	}

	/** {@link WizardDialog} containing a {@link ViewModelWizard}. */
	private class ViewModelWizardDialog extends WizardDialog {

		private final ViewModelWizard wizard;

		/**
		 * Creates a new wizard dialog for the given wizard.
		 *
		 * @param parentShell the parent shell
		 * @param newWizard the wizard this dialog is working on
		 */
		ViewModelWizardDialog(Shell parentShell, ViewModelWizard newWizard) {
			super(parentShell, newWizard);
			wizard = newWizard;
		}

		@Override
		protected void backPressed() {
			if (SelectEClassWizardPage.class.isInstance(getCurrentPage())) {
				wizard.clearSelectedContainer();
			}
			super.backPressed();
		}
	}
}
