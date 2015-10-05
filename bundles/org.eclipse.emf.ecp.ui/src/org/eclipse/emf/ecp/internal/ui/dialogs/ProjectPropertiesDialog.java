/********************************************************************************
 * Copyright (c) 2011 Eike Stepper (Berlin, Germany) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eike Stepper - initial API and implementation
 ********************************************************************************/
package org.eclipse.emf.ecp.internal.ui.dialogs;

import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Eike Stepper
 * @author Eugen Neufeld
 */
public class ProjectPropertiesDialog extends PropertiesDialog {
	private final ECPProject project;

	private Text nameText;

	private Text repositoryText;

	private Text providerText;

	public ProjectPropertiesDialog(Shell parentShell, boolean editable, ECPProject project) {
		super(parentShell, project.getName(), Messages.ProjectPropertiesDialog_DialogTitle_ProjectIs
			+ (project.isOpen() ? Messages.ProjectPropertiesDialog_DialogTitle_Open
				: Messages.ProjectPropertiesDialog_DialogTitle_Closed)
			+ ".", editable, project.getProperties()); //$NON-NLS-1$
		this.project = project;
	}

	public final ECPProject getProject() {
		return project;
	}

	public final Text getNameText() {
		return nameText;
	}

	public final Text getRepositoryText() {
		return repositoryText;
	}

	public final Text getProviderText() {
		return providerText;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ProjectPropertiesDialog_DialogTitle);
	}

	@Override
	protected void createSpecialProperties(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		repositoryText = addTextProperty(composite, Messages.ProjectPropertiesDialog_ProjectRepository,
			project.getRepository() != null ? project.getRepository().getLabel() : ""); //$NON-NLS-1$
		providerText = addTextProperty(composite, Messages.ProjectPropertiesDialog_ProjectProvider, project
			.getProvider().getLabel());
	}
}
