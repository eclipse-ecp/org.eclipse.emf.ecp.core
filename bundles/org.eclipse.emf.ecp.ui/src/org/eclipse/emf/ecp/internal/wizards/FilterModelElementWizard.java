/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * 
 *******************************************************************************/

package org.eclipse.emf.ecp.internal.wizards;

import org.eclipse.emf.ecp.internal.ui.messages.Messages;
import org.eclipse.emf.ecp.ui.common.CheckedModelClassComposite;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class FilterModelElementWizard extends ECPWizard<CheckedModelClassComposite> {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		WizardPage page = new WizardPage(Messages.FilterModelElementWizard_Title_FilterProject) {

			public void createControl(Composite parent) {
				Composite composite = getCompositeProvider().createUI(parent);

				setPageComplete(true);
				setControl(composite);
			}
		};
		addPage(page);
		page.setTitle(Messages.FilterModelElementWizard_PageTitle_FilterProject);
		page.setDescription(Messages.FilterModelElementWizard_PageDescription_FilterProject);
	}

}
