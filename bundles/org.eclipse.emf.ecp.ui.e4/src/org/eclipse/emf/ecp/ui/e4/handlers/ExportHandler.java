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

import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.internal.ui.util.ECPExportHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to export an {@link EObject}.
 *
 * @author David
 *
 */
public class ExportHandler {

	/**
	 * Exports a single {@link EObject}.
	 *
	 * @param shell {@link Shell} to use for the dialogs
	 * @param eObject The {@link EObject} which should be exported
	 */
	@Execute
	public void execute(Shell shell, @Named(IServiceConstants.ACTIVE_SELECTION) EObject eObject) {
		final List<EObject> eObjects = new LinkedList<EObject>();
		eObjects.add(EcoreUtil.copy(eObject));
		ECPExportHandlerHelper.export(shell, eObjects);
	}
}
