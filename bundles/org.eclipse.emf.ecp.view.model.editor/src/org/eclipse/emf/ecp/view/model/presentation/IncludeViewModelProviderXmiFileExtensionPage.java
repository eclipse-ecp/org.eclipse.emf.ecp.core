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
package org.eclipse.emf.ecp.view.model.presentation;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page allowing the selection of whether a contribution to the view model extension point should be made.
 */
public class IncludeViewModelProviderXmiFileExtensionPage extends WizardPage {

	private Composite container;
	private Button addExtensionChkBox;

	/**
	 * Creates a new wizard page with the given name.
	 *
	 * @param pageName the name of the page
	 */
	protected IncludeViewModelProviderXmiFileExtensionPage(String pageName) {
		super(pageName);
		setTitle(ViewEditorPlugin.INSTANCE
			.getString("_UI_IncludeViewModelProviderXmiFileExtensionPage_title")); //$NON-NLS-1$
		setDescription(ViewEditorPlugin.INSTANCE
			.getString("_UI_IncludeViewModelProviderXmiFileExtensionPage_description")); //$NON-NLS-1$
	}

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

	/**
	 * Returns <code>true</code> if the option to contribute the view model to the extension point is selected.
	 *
	 * @return whether the contribution to the extension point was selected
	 */
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
