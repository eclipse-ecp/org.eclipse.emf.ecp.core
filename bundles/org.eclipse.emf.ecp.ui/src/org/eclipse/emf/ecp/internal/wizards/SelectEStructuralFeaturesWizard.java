/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.wizards;

import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.ui.common.CheckedEStructuralFeatureComposite;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * A wizard to select EStructuralFeatures.
 * 
 * @author jfaltermeier
 * 
 */
public class SelectEStructuralFeaturesWizard extends ECPWizard<CheckedEStructuralFeatureComposite> {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		super.addPages();
		final WizardPage page = new WizardPage(Messages.SelectEStructuralFeaturesWizard_Title) {

			public void createControl(Composite parent) {
				final Composite composite = getCompositeProvider().createUI(parent);
				setPageComplete(true);
				setControl(composite);
			}
		};
		addPage(page);
		page.setTitle(Messages.SelectEStructuralFeaturesWizard_PageTitle);
		page.setDescription(Messages.SelectEStructuralFeaturesWizard_PageDescription);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

}
