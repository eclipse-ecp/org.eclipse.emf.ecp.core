/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.tooling.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

/**
 * Wizard page to select an existing template model file.
 *
 * @author Johannes Faltermeier
 *
 */
public class EMFFormsSelectTemplateWizardPage extends WizardPage {

	private Text templateText;

	/**
	 * Constructs the page.
	 */
	EMFFormsSelectTemplateWizardPage() {
		super("selectPage");//$NON-NLS-1$
		setTitle(Messages.EMFFormsSelectTemplateWizardPage_Title);
		setDescription(Messages.EMFFormsSelectTemplateWizardPage_Description);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(composite);

		final Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.EMFFormsSelectTemplateWizardPage_Label);

		templateText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(templateText);
		templateText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});

		final Button button = new Button(composite, SWT.PUSH);
		button.setText(Messages.EMFFormsSelectTemplateWizardPage_BrowseButton);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});

		validate();

		setControl(composite);
	}

	/**
	 * Returns the path to the template file.
	 *
	 * @return the path
	 */
	public String getTemplateName() {
		return templateText.getText();
	}

	private void handleBrowse() {
		final FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(templateText.getShell(),
			false, ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		dialog.setTitle(Messages.EMFFormsSelectTemplateWizardPage_BrowseDialogTitle);
		dialog.setInitialPattern("*.template", FilteredItemsSelectionDialog.CARET_BEGINNING); //$NON-NLS-1$
		if (Window.OK != dialog.open()) {
			return;
		}
		final Object firstResult = dialog.getFirstResult();
		if (!IFile.class.isInstance(firstResult)) {
			templateText.setText(""); //$NON-NLS-1$
			return;
		}
		templateText.setText(IFile.class.cast(firstResult).getFullPath().toString());
	}

	private void validate() {
		if (getTemplateName().length() == 0) {
			updateStatus(Messages.EMFFormsSelectTemplateWizardPage_ErrorNoTemplateName);
			return;
		}

		final IResource template = ResourcesPlugin.getWorkspace().getRoot()
			.findMember(new Path(getTemplateName()));

		if (template == null) {
			updateStatus(Messages.EMFFormsSelectTemplateWizardPage_ErrorTemplateNotExisting);
			return;
		}

		if (template.getType() != IResource.FILE) {
			updateStatus(Messages.EMFFormsSelectTemplateWizardPage_ErrorTemplateIsNoFile);
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}
