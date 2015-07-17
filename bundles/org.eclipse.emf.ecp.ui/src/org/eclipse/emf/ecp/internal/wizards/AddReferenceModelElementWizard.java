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

import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.ECPWizard;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectModelElementCompositeImpl;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class AddReferenceModelElementWizard extends ECPWizard<SelectModelElementCompositeImpl> {
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
		final WizardPage page = new WizardPage("AddReferencePage") { //$NON-NLS-1$

			@Override
			public void createControl(Composite parent) {
				final Composite composite = getCompositeProvider().createUI(parent);

				setPageComplete(true);
				setControl(composite);
			}
		};
		addPage(page);
		page.setTitle(Messages.AddReferenceModelElementWizard_ReferenceToSet);
		page.setDescription(Messages.AddReferenceModelElementWizard_SelectEObjectToReference);
		setWindowTitle(Messages.AddReferenceModelElementWizard_AddReference);
	}

}
