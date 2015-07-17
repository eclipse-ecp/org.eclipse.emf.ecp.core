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

import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.internal.wizards.ShareWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class SelectOrCreateRepositoryPage extends WizardPage {

	/**
	 * A WizardPage allowing the user to choose whether to use an existing repository or to create a new one.
	 *
	 * @param pageName the name of the wizard page, needed due to the {@link WizardPage} constructor
	 */
	public SelectOrCreateRepositoryPage(String pageName) {
		super(pageName);
		setTitle(Messages.SelectOrCreateRepositoryPage_PageTitle_ExistingOrNewRepository);
		setDescription(Messages.SelectOrCreateRepositoryPage_PageDescription_ExistingOrNewRepository);
	}

	private boolean createNewRepository;

	/** {@inheritDoc} */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, true));
		final Button bCreateNewRepository = new Button(container, SWT.RADIO);
		bCreateNewRepository.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		bCreateNewRepository.setText(Messages.SelectOrCreateRepositoryPage_CreateNewRepository);
		bCreateNewRepository.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewRepository = true;
				((ShareWizard) getWizard()).setUseExistingRepository(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final Button bSelectRepository = new Button(container, SWT.RADIO);
		bSelectRepository.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		bSelectRepository.setText(Messages.SelectOrCreateRepositoryPage_SelectExistingRepository);
		bSelectRepository.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				createNewRepository = false;
				((ShareWizard) getWizard()).setUseExistingRepository(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		bSelectRepository.setSelection(true);

		setControl(container);
		setPageComplete(true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		if (createNewRepository) {
			return getWizard().getPage("AddRepository"); //$NON-NLS-1$
		}
		return getWizard().getPage("SelectRepository"); //$NON-NLS-1$

	}

}
