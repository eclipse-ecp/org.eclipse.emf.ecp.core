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
package org.eclipse.emf.ecp.ui.e4.util;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * This Helper allows to retrieve the {@link EPartService}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class EPartServiceHelper {

	private EPartServiceHelper() {
	}

	/**
	 * Retrieves the part service.
	 * 
	 * @return the part service of the current window.
	 */
	public static EPartService getEPartService() {
		final MApplication currentApplication = E4Workbench.getServiceContext().get(IWorkbench.class).getApplication();
		final IEclipseContext selectedWindowContext = currentApplication.getSelectedElement().getContext();
		return selectedWindowContext.get(EPartService.class);
	}
}
