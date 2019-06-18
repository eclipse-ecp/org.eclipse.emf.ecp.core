/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;

/**
 * @author Eugen
 *
 */
public class CloseProjectHandler {

	/**
	 * Closes the selected {@link ECPProject}.
	 *
	 * @param ecpProject the project to be closed
	 */
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject) {
		if (ecpProject != null) {
			ecpProject.close();
		}
	}
}
