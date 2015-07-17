/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.internal.wizards.page;

import org.eclipse.emf.ecp.core.ECPProvider;
import org.eclipse.emf.ecp.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.ui.common.AddRepositoryComposite;
import org.eclipse.emf.ecp.ui.common.AddRepositoryComposite.AddRepositoryChangeListener;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class AddRepositoryPage extends WizardPage {

	/**
	 * A WizardPage for adding a repository.
	 *
	 * @param pageName the name of the wizard page, needed due to the {@link WizardPage} constructor
	 * @param addRepositoryComposite the {@link AddRepositoryComposite} to use in this page
	 */
	public AddRepositoryPage(String pageName, AddRepositoryComposite addRepositoryComposite) {
		super(pageName);
		this.addRepositoryComposite = addRepositoryComposite;
	}

	private final AddRepositoryComposite addRepositoryComposite;

	/** {@inheritDoc} */
	@Override
	public void createControl(Composite parent) {
		setPageComplete(false);
		setTitle(Messages.AddRepositoryPage_PageTitle_AddRepository);
		setImageDescriptor(Activator.getImageDescriptor("icons/checkout_project_wiz.png")); //$NON-NLS-1$
		setMessage(Messages.AddRepositoryPage_PageMessage_AddRepository);

		addRepositoryComposite.setListener(new AddRepositoryChangeListener() {

			@Override
			public void repositoryProviderChanged(ECPProvider provider) {
			}

			@Override
			public void repositoryNameChanged(String repositoryName) {
				if (repositoryName != null && repositoryName.length() != 0) {
					setPageComplete(true);
				} else {
					setPageComplete(false);
				}
			}

			@Override
			public void repositoryLabelChanged(String repositoryLabel) {
			}

			@Override
			public void repositoryDescriptionChanged(String repositoryDescription) {
			}
		});
		final Composite composite = addRepositoryComposite.createUI(parent);
		setControl(composite);
	}

}
