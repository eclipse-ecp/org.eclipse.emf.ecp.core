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
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.spi.ui.util.ECPHandlerHelper;
import org.eclipse.swt.widgets.Shell;

/**
 * Handler to filter the selection of EPackages.
 *
 * @author David
 *
 */
public class FilterPackagesHandler {

	/**
	 * Opens a dialog for adjusting the packages filter.
	 *
	 * @param ecpProject The {@link ECPProject} to which the filter should be applied
	 * @param shell The {@link Shell} which should be used for the dialogs
	 */
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) @Optional ECPProject ecpProject, Shell shell) {
		ECPHandlerHelper.filterProjectPackages(ecpProject, shell);
	}
}
