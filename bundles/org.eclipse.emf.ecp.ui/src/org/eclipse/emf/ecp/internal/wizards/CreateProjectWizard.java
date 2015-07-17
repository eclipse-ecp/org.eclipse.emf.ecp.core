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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.spi.common.ui.ECPWizard;
import org.eclipse.emf.ecp.ui.common.CreateProjectComposite;
import org.eclipse.emf.ecp.ui.common.CreateProjectComposite.CreateProjectChangeListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class CreateProjectWizard extends ECPWizard<CreateProjectComposite> {

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
		final List<ECPProvider> providers = new ArrayList<ECPProvider>();
		for (final ECPProvider provider : ECPUtil.getECPProviderRegistry().getProviders()) {
			if (provider.hasCreateProjectWithoutRepositorySupport()) {
				providers.add(provider);
			}
		}
		final WizardPage wp = new WizardPage("CreateProject") //$NON-NLS-1$
		{

			@Override
			public void createControl(Composite parent) {
				final Composite composite = getCompositeProvider().createUI(parent);
				getCompositeProvider().setListener(new CreateProjectChangeListener() {

					@Override
					public void providerChanged(ECPProvider provider) {
					}

					@Override
					public void projectNameChanged(String projectName) {
						if (projectName != null && ECPUtil.getECPProjectManager().getProject(projectName) != null) {
							setErrorMessage("A project with name " + projectName + " already exists in the workspace.");
						} else {
							setErrorMessage(null);
						}
					}

					@Override
					public void completeStatusChanged(boolean status) {
						setPageComplete(status);

					}
				});
				setPageComplete(false);
				setControl(composite);
			}
		};
		addPage(wp);
		final String title = Messages.CreateProjectWizard_PageTitle_CreateProject;
		String message = Messages.CreateProjectWizard_PageMessage_SelectProviderAndSetName;
		if (providers.size() == 1) {
			message = Messages.CreateProjectWizard_PageMessage_SetProjectName;
		}
		wp.setTitle(title);
		wp.setImageDescriptor(Activator.getImageDescriptor("icons/checkout_project_wiz.png")); //$NON-NLS-1$
		wp.setMessage(message);
		setWindowTitle(title);
	}
}
