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

import org.eclipse.emf.ecp.view.template.internal.tooling.Messages;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This wizard page allows the user to decide whether a new template model should be created or if an existing one may
 * be resued.
 *
 * @author Johannes Faltermeier
 *
 */
public class EMFFormsChooseTemplateWizardPage extends WizardPage {

	private boolean useNewTemplate;

	/**
	 * Constructs this page.
	 */
	EMFFormsChooseTemplateWizardPage() {
		super("choosePage");//$NON-NLS-1$
		setTitle(Messages.EMFFormsChooseTemplateWizardPage_Title);
		setDescription(Messages.EMFFormsChooseTemplateWizardPage_Description);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(composite);

		final Button select = new Button(composite, SWT.RADIO);
		select.setText(Messages.EMFFormsChooseTemplateWizardPage_ExistingButton);
		select.setSelection(true);

		final Button newTemplate = new Button(composite, SWT.RADIO);
		newTemplate.setText(Messages.EMFFormsChooseTemplateWizardPage_NewButton);

		final SelectionAdapter selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				useNewTemplate = newTemplate == e.widget;
			}
		};

		select.addSelectionListener(selectionListener);
		newTemplate.addSelectionListener(selectionListener);

		setControl(composite);
		setPageComplete(true);
	}

	/**
	 * Whether to create a new template model.
	 * 
	 * @return <code>true</code> if a new one should be created, <code>false</code> if an existing template is reused
	 */
	public boolean createNewTemplate() {
		return useNewTemplate;
	}

}
