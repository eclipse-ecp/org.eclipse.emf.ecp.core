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

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.emfstore.internal.ui.handler.ExportProjectHelper;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.swt.widgets.Shell;

public class ExportProjectHandler {
	@Execute
	public void execute(Shell shell,
		@Named(IServiceConstants.ACTIVE_SELECTION) ECPProject ecpProject) {
		if (ecpProject instanceof InternalProject) {
			ExportProjectHelper.exportProject((InternalProject) ecpProject, shell);
		}
	}
}