/*******************************************************************************
 * Copyright (c) 2006-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * IBM - Initial API and implementation
 * Lucas Koehler- Initial API and implementation
 * Lucas Koehler - [1.18] Refactored to use a common EMF Forms installer wizard
 ******************************************************************************/
package org.eclipse.emf.ecp.makeithappen.internal.wizards;

import org.eclipse.emfforms.spi.example.wizards.EMFFormsExampleInstallerWizard;

/**
 * @author Lucas Koehler
 *
 */
public class MakeItHappenWizard extends EMFFormsExampleInstallerWizard {

	@Override
	protected String getProjectPageTitle() {
		return MakeItHappenWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_title"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectPageDescription() {
		return MakeItHappenWizardsPlugin.INSTANCE.getString("_UI_ProjectPage_description"); //$NON-NLS-1$
	}
}
