/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.e4.emfstore;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.ManageUserGroupHelper;
import org.eclipse.emf.ecp.spi.core.InternalRepository;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to trigger the management of user groups.
 *
 * @see ManageUserGroupHelper#mangeUser(InternalRepository, Shell)
 * @author Eugen Neufeld
 */
public class ManageUserGroupHandler {
	/**
	 * Called by the framework when handler is triggered.
	 *
	 * @param shell The current {@link Shell}
	 * @param ecpRepository The currently selected {@link InternalRepository}
	 */
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional InternalRepository ecpRepository) {
		if (ecpRepository != null) {
			ManageUserGroupHelper.mangeUser(ecpRepository, shell);
		}
	}

}