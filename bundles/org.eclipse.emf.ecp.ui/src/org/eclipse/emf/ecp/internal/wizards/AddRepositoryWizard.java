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
import org.eclipse.emf.ecp.internal.wizards.page.AddRepositoryPage;
import org.eclipse.emf.ecp.ui.common.AddRepositoryComposite;

/**
 * @author Eugen Neufeld
 */
public class AddRepositoryWizard extends ECPWizard<AddRepositoryComposite> {

	/**
	 * . ({@inheritDoc})
	 */
	@Override
	public void addPages() {
		setWindowTitle(Messages.AddRepositoryWizard_Title_AddRepository);

		super.addPages();
		addPage(new AddRepositoryPage(Messages.AddRepositoryWizard_PageTitle_AddReposditory, getCompositeProvider()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

}
