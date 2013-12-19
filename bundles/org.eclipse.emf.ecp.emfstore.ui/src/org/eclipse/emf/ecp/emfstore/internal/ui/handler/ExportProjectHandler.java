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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecp.emfstore.core.internal.EMFStoreProvider;
import org.eclipse.emf.ecp.emfstore.internal.ui.Activator;
import org.eclipse.emf.ecp.internal.ui.PreferenceHelper;
import org.eclipse.emf.ecp.spi.core.InternalProject;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerExecutor;
import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportControllerFactory;
import org.eclipse.emf.emfstore.internal.client.importexport.impl.ExportImportDataUnits;
import org.eclipse.emf.emfstore.internal.client.model.impl.api.ESLocalProjectImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Eugen Neufeld
 * 
 */
public class ExportProjectHandler extends AbstractHandler {

	private static final String FILE_EXTENSION = ExportImportDataUnits.ProjectSpace.getExtension();

	/**
	 * These filter names are used to filter which files are displayed.
	 */
	public static final String[] FILTER_NAMES = { "Model Files (*" + FILE_EXTENSION + ")" };

	/**
	 * These filter extensions are used to filter which files are displayed.
	 */
	public static final String[] FILTER_EXTS = { "*" + FILE_EXTENSION };

	private static final String EXPORT_MODEL_PATH = "org.eclipse.emf.ecp.exportProjectModelPath";

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final InternalProject project = (InternalProject) ((IStructuredSelection) HandlerUtil
			.getActiveMenuSelection(event))
			.getFirstElement();
		final ESLocalProject localProject = EMFStoreProvider.INSTANCE.getProjectSpace(project);

		final String filePath = getFilePathByFileDialog(HandlerUtil.getActiveShell(event), project.getName());
		if (filePath == null) {
			return null;
		}
		try {
			new ExportImportControllerExecutor(new File(filePath), new NullProgressMonitor())
				.execute(ExportImportControllerFactory.Export
					.getExportProjectSpaceController(((ESLocalProjectImpl) localProject).toInternalAPI()));

		} catch (final IOException ex) {
			Activator.log(ex);
		}

		return null;
	}

	private String getFilePathByFileDialog(Shell shell, String modelElementName) {
		final FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
			SWT.SAVE);
		dialog.setFilterNames(FILTER_NAMES);
		dialog.setFilterExtensions(FILTER_EXTS);
		final String initialPath = PreferenceHelper.getPreference(EXPORT_MODEL_PATH, System.getProperty("user.home"));
		dialog.setFilterPath(initialPath);
		dialog.setOverwrite(true);

		try {
			// String initialFileName = projectSpace.getProjectName() + "@"
			// + projectSpace.getBaseVersion().getIdentifier() + ".ucp";
			final String initialFileName = "Project_" + modelElementName + FILE_EXTENSION;
			dialog.setFileName(initialFileName);

		} catch (final NullPointerException e) {
			// do nothing
		}

		final String filePath = dialog.open();
		PreferenceHelper.setPreference(EXPORT_MODEL_PATH, new File(filePath).getParent());
		return filePath;
	}

}
