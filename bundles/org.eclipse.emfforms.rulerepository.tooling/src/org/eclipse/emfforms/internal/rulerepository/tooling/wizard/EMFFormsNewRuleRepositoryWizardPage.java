/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.rulerepository.tooling.wizard;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
 * OR with the extension that matches the expected one (rulerepository).
 */

public class EMFFormsNewRuleRepositoryWizardPage extends WizardPage {
	private Text containerText;

	private Text fileText;

	private final ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 *
	 * @param selection the current {@link ISelection}
	 */
	public EMFFormsNewRuleRepositoryWizardPage(ISelection selection) {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(Messages.EMFFormsRuleRepositoryWizardPage_title);
		setDescription(Messages.EMFFormsRuleRepositoryWizardPage_description);
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
		label.setText(Messages.EMFFormsRuleRepositoryWizardPage_containerSelection);

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
		button.setText(Messages.EMFFormsRuleRepositoryWizardPage_browseContainer);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages.EMFFormsRuleRepositoryWizardPage_fileSelection);

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
		fileText.setText("new_file.rulerepository"); //$NON-NLS-1$
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(
			getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
			Messages.EMFFormsRuleRepositoryWizardPage_browseFile);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Validates the container and file.
	 */

	private void dialogChanged() {
		if (getContainerName().length() == 0) {
			updateStatus(Messages.EMFFormsRuleRepositoryWizardPage_errorNoContainer);
			return;
		}

		final IResource container = ResourcesPlugin.getWorkspace().getRoot()
			.findMember(new Path(getContainerName()));

		final String message = getContainerErrorMessage(container);

		if (message != null) {
			updateStatus(message);
			return;
		}

		final String fileName = getFileName();

		if (fileName.length() == 0) {
			updateStatus(Messages.EMFFormsRuleRepositoryWizardPage_errorNoFilename);
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus(Messages.EMFFormsRuleRepositoryWizardPage_errorInvalidFilename);
			return;
		}

		if (ResourcesPlugin.getWorkspace().getRoot().getFile(container.getFullPath().append(fileName)).exists()) {
			updateStatus(String.format(Messages.EMFFormsRuleRepositoryWizardPage_FileAlreadyExist, fileName));
			return;
		}
		final int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			final String ext = fileName.substring(dotLoc + 1);
			if (!ext.equalsIgnoreCase("rulerepository")) { //$NON-NLS-1$
				updateStatus(
					String.format(Messages.EMFFormsRuleRepositoryWizardPage_errorWrongFileExtension, "rulerepository")); //$NON-NLS-1$
				return;
			}
		}
		updateStatus(null);
	}

	/**
	 * Return the error message for a selected {@link IResource} if
	 * 1. The container does not exists
	 * 2. If the container is project or folder
	 * 3. If the container is read-only
	 * Otherwise (no error) Null is returned.
	 *
	 * @param container The {@link IResource} to check
	 * @return the error message or null if the {@link IResource} is valid
	 */
	public static String getContainerErrorMessage(IResource container) {
		if (container == null
			|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			return Messages.EMFFormsRuleRepositoryWizardPage_errorContainerNotExists;
		}
		if (!container.isAccessible()) {
			return Messages.EMFFormsRuleRepositoryWizardPage_errorProjectReadOnly;
		}
		return null;
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