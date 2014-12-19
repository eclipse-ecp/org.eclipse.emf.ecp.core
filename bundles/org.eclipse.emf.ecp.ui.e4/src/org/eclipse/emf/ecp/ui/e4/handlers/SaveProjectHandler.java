/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;

public class SaveProjectHandler {
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Object object) {
		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(object);
		if (ecpProject != null) {
			ECPHandlerHelper.saveProject(ecpProject);
		}
	}

	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Object object) {
		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(object);
		if (ecpProject != null) {
			return ecpProject.hasDirtyContents();
		}
		return false;
	}

}