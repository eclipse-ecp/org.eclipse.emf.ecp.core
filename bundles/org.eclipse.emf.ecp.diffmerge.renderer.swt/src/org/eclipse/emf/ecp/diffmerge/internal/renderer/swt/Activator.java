/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.diffmerge.internal.renderer.swt;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Activator for this plugin.
 *
 * @author Eugen Neufeld
 *
 */
public class Activator extends Plugin {

	private static Activator instance;
	private ServiceReference<ImageRegistryService> imageRegistryServiceReference;
	private ServiceReference<ReportService> reportServiceReference;

	// BEGIN SUPRESS CATCH EXCEPTION
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		instance = this;
		super.start(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
		super.stop(bundleContext);
	}

	// END SUPRESS CATCH EXCEPTION

	/**
	 * Finds and returns an image for the provided path.
	 *
	 * @param path the path to get the image from
	 * @return the image or null if nothing could be found
	 */
	public static Image getImage(String path) {

		final Image image = instance.getImageRegistryService().getImage(instance.getBundle(), path);

		instance.getBundle().getBundleContext().ungetService(instance.imageRegistryServiceReference);

		return image;
	}

	private ImageRegistryService getImageRegistryService() {
		if (imageRegistryServiceReference == null) {
			imageRegistryServiceReference = getBundle().getBundleContext()
				.getServiceReference(ImageRegistryService.class);
		}
		return getBundle().getBundleContext().getService(imageRegistryServiceReference);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getInstance() {
		return instance;
	}

}
