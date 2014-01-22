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

import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * This Helper allows to retrieve the {@link EPartService}.
 * 
 * @author Eugen Neufeld
 * 
 */
public final class EPartServiceHelper {

	private static final String PARTSERVICE_FILTER = "(service.context.key=" + EPartService.class.getName() + ")";

	private EPartServiceHelper() {
	}

	/**
	 * Retrieves the part service.
	 * 
	 * @return the part service of the current window.
	 */
	public static EPartService getEPartService() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(EPartServiceHelper.class).getBundleContext();
		ServiceReference<?> service;
		try {
			service = bundleContext.getServiceReferences(
				IContextFunction.class.getName(), PARTSERVICE_FILTER)[0];
			// TODO a "bit" ugly
			return (EPartService) ((IContextFunction) bundleContext.getService(service))
				.compute(E4Workbench.getServiceContext(), null);
		} catch (final InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
