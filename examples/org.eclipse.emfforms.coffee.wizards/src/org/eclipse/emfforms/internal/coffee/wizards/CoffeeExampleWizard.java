/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.coffee.wizards;

import org.eclipse.emfforms.spi.example.wizards.EMFFormsExampleInstallerWizard;

/**
 * Wizard to install coffee model projects into the workspace.
 *
 * @author Lucas Koehler
 *
 */
public class CoffeeExampleWizard extends EMFFormsExampleInstallerWizard {

	@Override
	protected String getProjectPageTitle() {
		return CoffeeWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_title"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectPageDescription() {
		return CoffeeWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_description"); //$NON-NLS-1$
	}
}
