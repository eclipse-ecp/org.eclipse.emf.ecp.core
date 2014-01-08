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

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to create new model elements on the root level of {@link ECPProject}s.
 * 
 * @author Jonas
 * 
 */
public class NewModelElementHandler {
	/**
	 * Opens a dialog to select a new {@link org.eclipse.emf.ecore.EObject} to be created.
	 * 
	 * @param shell the Shell to display the dialog.
	 * @param ecpProject the selected {@link ECPProject}, parent of the newly created
	 *            {@link org.eclipse.emf.ecore.EObject}
	 */
	@Execute
	public void execute(Shell shell, @Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject) {
		if (ecpProject == null) {
			return;
		}
		ECPHandlerHelper.addModelElement(ecpProject, shell, false);
	}

	/**
	 * Checks if the current selection is an {@link ECPProject}.
	 * 
	 * @param ecpProject the current selection, if it is an {@link ECPProject} or null otherwise.
	 * @return if the current selection is an {@link ECPProject}
	 */
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject) {
		return ecpProject != null;
	}
}
