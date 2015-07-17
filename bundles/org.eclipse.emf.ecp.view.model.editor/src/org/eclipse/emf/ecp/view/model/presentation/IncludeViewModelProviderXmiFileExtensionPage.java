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
package org.eclipse.emf.ecp.view.model.presentation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Alexandra Buzila
 *
 */
public class IncludeViewModelProviderXmiFileExtensionPage extends WizardPage {

	private Composite container;
	private Button addExtensionChkBox;

	/**
	 * @param pageName
	 */
	protected IncludeViewModelProviderXmiFileExtensionPage(String pluginID) {
		super(pluginID);
		setTitle("Add Extension"); //$NON-NLS-1$
		setDescription("Register view model with EMFForms via extension."); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		container.setLayout(layout);
		// layout.numColumns = 3;

		addExtensionChkBox = new Button(container, SWT.CHECK);
		addExtensionChkBox.setText("Add Extension"); //$NON-NLS-1$
		addExtensionChkBox.setSelection(true);

		setControl(container);
		setPageComplete(false);
	}

	public boolean isContributeToExtensionOptionSelected() {
		return addExtensionChkBox.getSelection();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}

	}
}
