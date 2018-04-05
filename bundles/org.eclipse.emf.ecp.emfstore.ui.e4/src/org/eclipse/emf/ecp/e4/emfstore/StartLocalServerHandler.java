/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.osgi.service.log.LogService;

@SuppressWarnings("restriction")
public class StartLocalServerHandler {
	@Execute
	public void execute(LogService logService) {
		StartLocalServerHelper.startLocalServer();
	}

	@CanExecute
	public boolean isEnabled() {
		return EMFStoreController.getInstance() == null;
	}

}