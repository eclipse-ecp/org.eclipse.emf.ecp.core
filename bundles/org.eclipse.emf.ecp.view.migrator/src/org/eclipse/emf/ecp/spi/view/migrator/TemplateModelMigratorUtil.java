/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.view.migrator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Lucas Koehler
 * @since 1.17
 *
 */
public final class TemplateModelMigratorUtil {

	private static TemplateModelWorkspaceMigrator templateModelWorkspaceMigrator;

	// Utility class should not be instantiated.
	private TemplateModelMigratorUtil() {
	}

	/**
	 * Returns a {@link TemplateModelWorkspaceMigrator} if any is registered. Otherwise, <code>null</code> is returned.
	 *
	 * @return The {@link TemplateModelWorkspaceMigrator}, or <code>null</code> if none is registered.
	 */
	public static TemplateModelWorkspaceMigrator getTemplateModelWorkspaceMigrator() {
		if (templateModelWorkspaceMigrator == null) {
			final Bundle bundle = FrameworkUtil.getBundle(TemplateModelMigratorUtil.class);
			final BundleContext bundleContext = bundle.getBundleContext();
			final ServiceReference<TemplateModelWorkspaceMigrator> serviceReference = bundleContext
				.getServiceReference(TemplateModelWorkspaceMigrator.class);
			if (serviceReference == null) {
				return null;
			}
			templateModelWorkspaceMigrator = bundleContext.getService(serviceReference);
		}
		return templateModelWorkspaceMigrator;
	}
}
