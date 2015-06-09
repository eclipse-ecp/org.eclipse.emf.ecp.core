/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.core.swt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecp.edit.spi.ECPControlFactory;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends Plugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.emf.ecp.view.core.swt"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * Logs exception.
	 *
	 * @param e
	 *            the {@link Exception} to log
	 */
	public static void logException(Exception e) {
		getDefault().getLog().log(
			new Status(IStatus.ERROR, Activator.getDefault().getBundle().getSymbolicName(), e
				.getMessage(), e));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private ServiceReference<ECPControlFactory> controlFactoryReference;

	/**
	 * Returns the {@link ECPControlFactory}.
	 *
	 * @return the {@link ECPControlFactory}
	 */
	public ECPControlFactory getECPControlFactory() {
		if (controlFactoryReference == null) {
			controlFactoryReference = plugin.getBundle().getBundleContext()
				.getServiceReference(ECPControlFactory.class);
		}
		return plugin.getBundle().getBundleContext().getService(controlFactoryReference);
	}

	/**
	 * Frees the {@link ECPControlFactory} from use, allowing the OSGi Bundle to be shutdown.
	 */
	public void ungetECPControlFactory() {
		if (controlFactoryReference == null) {
			return;
		}
		plugin.getBundle().getBundleContext().ungetService(controlFactoryReference);
		controlFactoryReference = null;
	}

	/**
	 * Finds and returns an image for the provided path.
	 *
	 * @param path the path to get the image from
	 * @return the image or null if nothing could be found
	 */
	public static Image getImage(String path) {

		final Image image = plugin.getImageRegistryService().getImage(plugin.getBundle(), path);

		plugin.getBundle().getBundleContext().ungetService(plugin.imageRegistryServiceReference);

		return image;
	}

	private ServiceReference<ImageRegistryService> imageRegistryServiceReference;

	private ImageRegistryService getImageRegistryService() {
		if (imageRegistryServiceReference == null) {
			imageRegistryServiceReference = getBundle().getBundleContext()
				.getServiceReference(ImageRegistryService.class);
		}
		return getBundle().getBundleContext().getService(imageRegistryServiceReference);
	}

	// private VTViewTemplateProvider viewTemplate;
	//
	// /**
	// * Returns the currentInstance of the {@link VTViewTemplateProvider}.
	// *
	// * @return the {@link ECPControlFactory}
	// */
	// public VTViewTemplateProvider getVTViewTemplateProvider() {
	// if (viewTemplate == null) {
	// final ServiceReference<VTViewTemplateProvider> viewTemplateReference = plugin.getBundle()
	// .getBundleContext()
	// .getServiceReference(VTViewTemplateProvider.class);
	// if (viewTemplateReference != null) {
	// viewTemplate = plugin.getBundle().getBundleContext().getService(viewTemplateReference);
	// }
	// }
	// return viewTemplate;
	// }
	//
	// /**
	// * Returns the {@link ReportService}.
	// *
	// * @return the {@link ReportService}
	// */
	// public ReportService getReportService() {
	// if (reportServiceReference == null) {
	// reportServiceReference = plugin.getBundle().getBundleContext()
	// .getServiceReference(ReportService.class);
	// }
	// return plugin.getBundle().getBundleContext().getService(reportServiceReference);
	// }

	// /**
	// * Returns the {@link EMFFormsDatabinding} service.
	// *
	// * @return The {@link EMFFormsDatabinding}
	// */
	// public EMFFormsDatabinding getEMFFormsDatabinding() {
	// final ServiceReference<EMFFormsDatabinding> serviceReference = plugin.getBundle().getBundleContext()
	// .getServiceReference(EMFFormsDatabinding.class);
	//
	// final EMFFormsDatabinding service = plugin.getBundle().getBundleContext()
	// .getService(serviceReference);
	// plugin.getBundle().getBundleContext().ungetService(serviceReference);
	//
	// return service;
	// }
	//
	// /**
	// * Returns the {@link EMFFormsLabelProvider} service.
	// *
	// * @return The {@link EMFFormsLabelProvider}
	// */
	// public EMFFormsLabelProvider getEMFFormsLabelProvider() {
	// final ServiceReference<EMFFormsLabelProvider> serviceReference = plugin.getBundle().getBundleContext()
	// .getServiceReference(EMFFormsLabelProvider.class);
	//
	// final EMFFormsLabelProvider service = plugin.getBundle().getBundleContext()
	// .getService(serviceReference);
	// plugin.getBundle().getBundleContext().ungetService(serviceReference);
	//
	// return service;
	// }
	//
	// /**
	// * Returns the {@link EMFFormsEditSupport} service.
	// *
	// * @return The {@link EMFFormsEditSupport}
	// */
	// public EMFFormsEditSupport getEMFFormsEditSupport() {
	// final ServiceReference<EMFFormsEditSupport> serviceReference = plugin.getBundle().getBundleContext()
	// .getServiceReference(EMFFormsEditSupport.class);
	//
	// final EMFFormsEditSupport service = plugin.getBundle().getBundleContext()
	// .getService(serviceReference);
	// plugin.getBundle().getBundleContext().ungetService(serviceReference);
	//
	// return service;
	// }
}
