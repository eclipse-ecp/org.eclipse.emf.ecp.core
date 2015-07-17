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

/**
 * @author Eugen
 *
 */
public class SaveProjectHandler {

	/**
	 * Saves the current {@link ECPProject}.
	 *
	 * @param object an object adaptable to an {@link ECPProject}
	 */
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Object object) {
		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(object);
		if (ecpProject != null) {
			ECPHandlerHelper.saveProject(ecpProject);
		}
	}

	/**
	 * Checks whether the current selection is adaptable to an {@link ECPProject} and if so, whether this is dirty.
	 *
	 * @param object an object adaptable to an {@link ECPProject}
	 * @return true if the object is adaptable to an {@link ECPProject} and dirty ot false otherwise.
	 */
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional Object object) {
		final ECPProject ecpProject = ECPUtil.getECPProjectManager().getProject(object);
		if (ecpProject != null) {
			return ecpProject.hasDirtyContents();
		}
		return false;
	}

}