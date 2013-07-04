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

import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPCheckoutSource;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.messages.Messages;
import org.eclipse.emf.ecp.ui.common.CheckoutProjectComposite;
import org.eclipse.emf.ecp.ui.common.CheckoutProjectComposite.CheckoutProjectChangeListener;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class CheckoutProjectWizard extends ECPWizard<CheckoutProjectComposite> {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

	@Override
	public void addPages() {
		WizardPage wp = new WizardPage("Checkout") //$NON-NLS-1$
		{

			public void createControl(Composite parent) {
				Composite composite = getCompositeProvider().createUI(parent);

				getCompositeProvider().setListener(new CheckoutProjectChangeListener() {
					public void projectNameChanged(String projectName) {
						validateName(projectName);
					}
				});

				// validate initial project name
				validateName(getCompositeProvider().getProjectName());
				setControl(composite);
			}

			private void validateName(String projectName) {
				if (ECPUtil.getECPProjectManager().getProject(projectName) != null) {
					setPageComplete(false);
					setErrorMessage("A project with name " + projectName + " already exists in the workspace.");
				} else {
					setErrorMessage(null);
					setPageComplete(true);
				}
			}
		};
		addPage(wp);

		wp.setTitle(Messages.CheckoutProjectWizard_PageTitle_CheckoutProject);
		wp.setImageDescriptor(Activator.getImageDescriptor("icons/checkout_project_wiz.png")); //$NON-NLS-1$

		ECPCheckoutSource checkoutSource = getCompositeProvider().getCheckoutSource();

		ECPRepository repository = checkoutSource.getRepository();
		if (checkoutSource == repository) {
			wp.setMessage(Messages.CheckoutProjectWizard_PageMessage_CheckoutRepositrory + repository.getLabel() + "."); //$NON-NLS-1$
		} else {
			wp.setMessage(Messages.CheckoutProjectWizard_PageMessage_CheckoutProject
				+ getCompositeProvider().getUiProvider().getText(checkoutSource)
				+ Messages.CheckoutProjectWizard_PageMessage_CheckoutFrom + repository.getLabel() + "."); //$NON-NLS-1$
		}
		setWindowTitle(Messages.CheckoutProjectWizard_Title_Checkout);
	}
}
