/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.datatemplate.tooling.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * Wizard for creating a new Data Template.
 * 
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class NewDataTemplateWizard extends BasicNewResourceWizard {

	private WizardNewFileCreationPage mainPage;

	@Override
	public void addPages() {
		super.addPages();
		mainPage = new WizardNewDataTemplateCreationPage("newFilePage1", getSelection());//$NON-NLS-1$
		mainPage.setTitle(Messages.NewDataTemplateWizard_title);
		mainPage.setDescription(Messages.NewDataTemplateWizard_description);
		addPage(mainPage);
	}

	@Override
	public boolean performFinish() {
		final IFile file = mainPage.createNewFile();
		if (file == null) {
			return false;
		}

		selectAndReveal(file);

		// Open editor on new file.
		final IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
		try {
			if (dw != null) {
				final IWorkbenchPage page = dw.getActivePage();
				if (page != null) {
					IDE.openEditor(page, file, true);
				}
			}
		} catch (final PartInitException e) {
			DialogUtil.openError(dw.getShell(), ResourceMessages.FileResource_errorMessage,
				e.getMessage(), e);
		}

		return true;
	}

}
