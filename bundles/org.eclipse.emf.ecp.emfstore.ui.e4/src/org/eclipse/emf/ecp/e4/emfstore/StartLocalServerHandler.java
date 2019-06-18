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

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.StartLocalServerHelper;
import org.eclipse.emf.emfstore.internal.server.EMFStoreController;

/**
 * Handler to start a local EMFStore Server.
 *
 * @see StartLocalServerHelper#startLocalServer()
 * @author Eugen Neufeld
 */
@SuppressWarnings("restriction")
public class StartLocalServerHandler {
	/**
	 * Called by the framework when handler is triggered.
	 */
	@Execute
	public void execute() {
		StartLocalServerHelper.startLocalServer();
	}

	/**
	 * Called by the framework to check whether handler is enabled.
	 * 
	 * @return true if enabled, false otherwise
	 */
	@CanExecute
	public boolean isEnabled() {
		return EMFStoreController.getInstance() == null;
	}

}