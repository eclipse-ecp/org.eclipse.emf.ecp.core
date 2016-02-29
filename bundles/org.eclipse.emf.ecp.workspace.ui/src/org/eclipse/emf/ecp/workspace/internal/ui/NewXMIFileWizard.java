/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.workspace.internal.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

/**
 * The NewXMIFileWizard allows to create a new XMI File in a project located in the workspace.
 *
 * @author Tobias Verhoeven
 */
public class NewXMIFileWizard extends Wizard {

	private NewXMIFileWizardPage newFileWizardPage;
	private IFile selectedFile;

	/**
	 * Instantiates a new new xmi file wizard.
	 */
	public NewXMIFileWizard() {
		setWindowTitle(Messages.NewXMIFileWizard_NewXMIFile);
		final ImageDescriptor desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfile_wiz.png");//$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		newFileWizardPage = new NewXMIFileWizardPage();
		addPage(newFileWizardPage);
	}

	@Override
	public boolean performFinish() {
		selectedFile = newFileWizardPage.createNewFile();
		if (selectedFile != null) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the URI of the newly created file.
	 *
	 * @return the URI or null.
	 */
	public URI getFileURI() {
		if (selectedFile == null) {
			return null;
		}
		return URI.createPlatformResourceURI(selectedFile.getFullPath().toString(), true);

	}
}
