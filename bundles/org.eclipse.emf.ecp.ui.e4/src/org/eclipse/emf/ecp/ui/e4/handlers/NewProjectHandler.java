/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to create a new {@link org.eclipse.emf.ecp.core.ECPProject}.
 *
 * @author Jonas
 *
 */
public class NewProjectHandler {

	/**
	 * Creates a new project.
	 *
	 * @param shell shell to display a dialog
	 */
	@Execute
	public void execute(Shell shell) {
		ECPHandlerHelper.createProject(shell);
	}

}
