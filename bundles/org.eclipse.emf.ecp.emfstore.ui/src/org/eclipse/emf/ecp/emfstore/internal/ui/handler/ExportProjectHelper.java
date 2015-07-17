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
 ******************************************************************************/
package org.eclipse.emf.ecp.emfstore.internal.ui.handler;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.emfstore.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.util.ECPExportHandlerHelper;
import org.eclipse.emf.ecp.internal.ui.util.ECPFileDialogHelper;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerExecutor;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.internal.client.importexport.impl.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * A helper class that can be used to export projects.
 *
 * @author Eugen Neufeld, David Soto Setzke
 *
 */
public final class ExportProjectHelper {

	private static final String FILE_EXTENSION = ExportImportDataUnits.ProjectSpace.getExtension();

	/**
	 * These filter names are used to filter which files are displayed.
	 */
	public static final String[] FILTER_NAMES = { "Model Files (*" + FILE_EXTENSION + ")" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * These filter extensions are used to filter which files are displayed.
	 */
	public static final String[] FILTER_EXTS = { "*" + FILE_EXTENSION }; //$NON-NLS-1$

	private static final String ECP_UI_PLUGIN_ID = "org.eclipse.emf.ecp.ui"; //$NON-NLS-1$

	private static final String FILE_DIALOG_HELPER_CLASS = "org.eclipse.emf.ecp.internal.ui.util.ECPFileDialogHelperImpl"; //$NON-NLS-1$

	private ExportProjectHelper() {
	}

	/**
	 * Exports a given project.
	 *
	 * @param internalProject The project that should be exported
	 * @param shell The active shell
	 */
	public static void exportProject(InternalProject internalProject, Shell shell) {
		final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(internalProject);

		final String filePath = getFilePathByFileDialog(shell, internalProject.getName());
		if (filePath == null) {
			return;
		}
		try {
			new ExportImportControllerExecutor(new File(filePath), new NullProgressMonitor())
				.execute(ExportImportControllerFactory.Export
					.getExportProjectSpaceController(((ESLocalProjectImpl) localProject).toInternalAPI()));

		} catch (final IOException ex) {
			Activator.log(ex);
		}
	}

	private static String getFilePathByFileDialog(Shell shell, String modelElementName) {
		final BundleContext bundleContext = FrameworkUtil.getBundle(ECPExportHandlerHelper.class).getBundleContext();
		final ServiceReference<ECPFileDialogHelper> serviceReference = bundleContext
			.getServiceReference(ECPFileDialogHelper.class);
		final ECPFileDialogHelper fileDialogHelper = bundleContext.getService(serviceReference);
		final String result = fileDialogHelper.getPathForExport(shell, modelElementName);
		bundleContext.ungetService(serviceReference);
		return result;
	}

}
