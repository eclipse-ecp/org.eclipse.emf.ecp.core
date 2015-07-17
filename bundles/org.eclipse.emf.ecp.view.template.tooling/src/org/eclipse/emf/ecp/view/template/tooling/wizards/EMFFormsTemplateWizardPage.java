/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (template).
 */

public class EMFFormsTemplateWizardPage extends WizardPage {
	private Text containerText;

	private Text fileText;

	private final ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 *
	 * @param selection the current {@link ISelection}
	 */
	public EMFFormsTemplateWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.EMFFormsTemplateWizardPage_title);
		setDescription(Messages.EMFFormsTemplateWizardPage_description);
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages.EMFFormsTemplateWizardPage_containerSelection);

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		final Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.EMFFormsTemplateWizardPage_browseContainer);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.EMFFormsTemplateWizardPage_fileSelection);

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && !selection.isEmpty()
			&& selection instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1) {
				return;
			}
			final Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer) {
					container = (IContainer) obj;
				} else {
					container = ((IResource) obj).getParent();
				}
				containerText.setText(container.getFullPath().toString());
			}
		}
		fileText.setText("new_file.template"); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(
			getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
			Messages.EMFFormsTemplateWizardPage_browseFile);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		final IResource container = ResourcesPlugin.getWorkspace().getRoot()
			.findMember(new Path(getContainerName()));
		final String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus(Messages.EMFFormsTemplateWizardPage_errorNoContainer);
			return;
		}
		if (container == null
			|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus(Messages.EMFFormsTemplateWizardPage_errorContainerNotExists);
			return;
		}
		if (!container.isAccessible()) {
			updateStatus(Messages.EMFFormsTemplateWizardPage_errorProjectReadOnly);
			return;
		}
		if (fileName.length() == 0) {
			updateStatus(Messages.EMFFormsTemplateWizardPage_errorNoFilename);
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(Messages.EMFFormsTemplateWizardPage_errorInvalidFilename);
			return;
		}

		if (ResourcesPlugin.getWorkspace().getRoot().getFile(container.getFullPath().append(fileName)).exists()) {
			updateStatus(String.format(Messages.EMFFormsTemplateWizardPage_FileAlreadyExist, fileName));
			return;
		}
		final int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			final String ext = fileName.substring(dotLoc + 1);
			if (!ext.equalsIgnoreCase("template")) { //$NON-NLS-1$
				updateStatus(String.format(Messages.EMFFormsTemplateWizardPage_errorWrongFileExtension, "template")); //$NON-NLS-1$
				return;
			}
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * The container name.
	 *
	 * @return the name of the container
	 */
	public String getContainerName() {
		return containerText.getText();
	}

	/**
	 * The file name.
	 *
	 * @return the name of the file
	 */
	public String getFileName() {
		return fileText.getText();
	}
}