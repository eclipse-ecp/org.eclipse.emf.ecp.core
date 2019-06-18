/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.presentation;

import org.eclipse.emf.ecp.view.internal.editor.handler.SelectEClassWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page used for selecting a root EClass for a view model. In addition to the {@link SelectEClassWizardPage},
 * this page also shows a checkbox to configure whether the view model should be filled with the default layout.
 *
 * @author Lucas Koehler
 */
public class SelectEClassForViewWizardPage extends SelectEClassWizardPage {
	private Button generateViewModelChkBox;

	/**
	 * Create a new instance of {@link SelectEClassForViewWizardPage}.
	 */
	public SelectEClassForViewWizardPage() {
		setDescription(ViewEditorPlugin.getPlugin().getString("_UI_SelectEClassWizardPage_page_description")); //$NON-NLS-1$
	}

	/**
	 * Returns whether the option to generate the controls for the created view model is selected.
	 *
	 * @return <code>true</code> if the option is selected
	 */
	public boolean isGenerateViewModelOptionSelected() {
		return generateViewModelChkBox.getSelection();
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		final Composite container = (Composite) getControl();
		generateViewModelChkBox = new Button(container, SWT.CHECK);
		generateViewModelChkBox.setText("Fill view model with default layout"); //$NON-NLS-1$
		generateViewModelChkBox.setSelection(true);

		container.layout(true);
		container.pack();
	}
}
