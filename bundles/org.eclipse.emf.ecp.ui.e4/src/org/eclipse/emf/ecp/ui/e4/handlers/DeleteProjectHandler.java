/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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

package org.eclipse.emf.ecp.ui.e4.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPContainer;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to delete selected projects.
 *
 * @author Jonas
 *
 */
public class DeleteProjectHandler {
	/**
	 * Deletes a project or a list of projects, based on the selection.
	 *
	 * @param shell
	 *            the current shell to display a confimation dialog.
	 * @param ecpProject
	 *            an {@link ECPProject}, if only one is selected or null.
	 * @param ecpProjects
	 *            a List of {@link ECPProject}s, is several projects are
	 *            selected or null
	 */
	@Execute
	public void execute(
		Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final ECPProject ecpProject,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional final List<ECPProject> ecpProjects) {
		final List<ECPContainer> toBeDeleted = new ArrayList<ECPContainer>();
		if (ecpProject != null) {
			toBeDeleted.add(ecpProject);
		}
		if (ecpProjects != null) {
			toBeDeleted.addAll(ecpProjects);
		}
		if (!toBeDeleted.isEmpty()) {
			ECPHandlerHelper.deleteHandlerHelper(
				toBeDeleted, shell);
		}
	}

	/**
	 * Checks if a single project or a list of projects are selected.
	 *
	 * @param ecpProject
	 *            an {@link ECPProject}, if only one is selected or null.
	 * @param ecpProjects
	 *            a List of {@link ECPProject}s, is several projects are
	 *            selected or null
	 *
	 * @return true, if either a single project or a list of projects are selected
	 */
	@CanExecute
	public boolean canExecute(
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<ECPProject> ecpProjects) {
		return ecpProject != null || ecpProjects != null;
	}
}
