/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.ui.e4.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.internal.ui.util.ECPImportHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to import an {@link EObject}.
 *
 * @author David
 *
 */
public class ImportHandler {

	/**
	 * Imports an {@link EObject} which will be selected via a dialog into the current project.
	 *
	 * @param shell {@link Shell} which should be used for the dialogs
	 * @param eObject The selected {@link EObject} which should be connected with the imported {@link EObject} or null
	 *            if an {@link ECPProject} was selected
	 * @param ecpProject The selected {@link ECPProject} which should be connected with the imported {@link EObject} or
	 *            null
	 *            if an {@link EObject} was selected
	 */
	@Execute
	public void execute(Shell shell, @Named(IServiceConstants.ACTIVE_SELECTION) @Optional EObject eObject,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject) {
		if (eObject != null) {
			ECPImportHandlerHelper.importElement(shell, eObject);
		}
		else if (ecpProject != null) {
			ECPImportHandlerHelper.importElement(shell, ecpProject);
		}
	}
}
