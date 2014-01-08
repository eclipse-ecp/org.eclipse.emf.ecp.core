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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.core.ECPProjectManager;
import org.eclipse.emf.ecp.core.util.ECPUtil;
import org.eclipse.emf.ecp.internal.ui.util.ECPHandlerHelper;

/**
 * Handler to delete selected {@link EObject}.
 * 
 * @author Jonas
 * 
 */
public class DeleteModelElementHandler {

	/**
	 * Deletes a single {@link EObject} or a list of {@link EObject}.
	 * 
	 * @param eObject the eobject to be deleted or null, if several eobjects are to be deleted.
	 * @param eObjects A List of EObjects to be deleted or null, if only one {@link EObject} should be deleted.
	 */
	@Execute
	public void execute(
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional EObject eObject,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EObject> eObjects) {
		final ECPProjectManager ecpProjectManager = ECPUtil.getECPProjectManager();
		final List<Object> toBeDeleted = new ArrayList<Object>();
		if (eObject != null) {
			toBeDeleted.add(eObject);
		}
		if (eObjects != null) {
			toBeDeleted.addAll(eObjects);
		}
		if (!toBeDeleted.isEmpty()) {
			ECPHandlerHelper.deleteModelElement(
				ecpProjectManager.getProject(eObject),
				toBeDeleted);
		}
	}

	/**
	 * Checks if one EObject or a list of EObjects are selected.
	 * 
	 * @param eObject the selected eobject or null, if several or none eobjects are selected.
	 * @param eObjects the list of selected eobjects or null, if one or none eobjects are selected.
	 * @return true if one EObject or a list of EObjects are selected.
	 */
	@CanExecute
	public boolean canExecute(
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional EObject eObject,
		@Named(IServiceConstants.ACTIVE_SELECTION) @Optional List<EObject> eObjects) {
		return eObject != null || eObjects != null;
	}
}
