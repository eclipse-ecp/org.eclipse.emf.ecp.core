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

import org.eclipse.emf.ecp.core.ECPRepository;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.Messages;
import org.eclipse.emf.ecp.internal.ui.model.RepositoriesContentProvider;
import org.eclipse.emf.ecp.internal.ui.model.RepositoriesLabelProvider;
import org.eclipse.emf.ecp.internal.wizards.ShareWizard;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eugen Neufeld
 */
public class SelectRepositoryPage extends WizardPage {

	/**
	 * A WizardPage for selecting a repository.
	 *
	 * @param pageName the name of the wizard page, needed due to the {@link WizardPage} constructor
	 */
	public SelectRepositoryPage(String pageName) {
		super(pageName);
		setTitle(Messages.SelectRepositoryPage_PageTitle_SelectRepository);
		setDescription(Messages.SelectRepositoryPage_PageDescription_SelectRepository);
	}

	/** {@inheritDoc} */
	@Override
	public void createControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, true));

		final RepositoriesContentProvider contentProvider = new RepositoriesContentProvider(
			((ShareWizard) getWizard()).getProvider());
		final TableViewer viewer = new TableViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new RepositoriesLabelProvider(contentProvider));
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(ECPUtil.getECPRepositoryManager());
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				final ECPRepository ecpRepository = (ECPRepository) ((IStructuredSelection) event.getSelection())
					.getFirstElement();
				((ShareWizard) getWizard()).setSelectedRepository(ecpRepository);
				setPageComplete(true);
			}
		});

		setControl(container);
		setPageComplete(false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	 */
	@Override
	public boolean canFlipToNextPage() {
		return false;
	}

}
