/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.util;

import java.io.File;

import org.eclipse.emf.ecp.internal.ui.PreferenceHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Component;

/**
 * {@link ECPFileDialogHelper} using the RWT implementation of the {@link FileDialog}.
 *
 * @author jfaltermeier
 *
 */
@Component
public class ECPFileDialogHelperImpl implements ECPFileDialogHelper {

	private static final String IMPORT_MODEL_PATH = "org.eclipse.emf.emfstore.client.ui.importModelPath"; //$NON-NLS-1$

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.ui.util.ECPFileDialogHelper#getPathForImport(org.eclipse.swt.widgets.Shell)
	 */
	public String getPathForImport(Shell shell) {
		final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		final String fileName = dialog.open();
		if (fileName == null) {
			return null;
		}
		final File file = new File(fileName);
		PreferenceHelper.setPreference(IMPORT_MODEL_PATH, file.getParent());
		return file.getAbsolutePath();
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.internal.ui.util.ECPFileDialogHelper#getPathForExport(org.eclipse.swt.widgets.Shell,
	 *      java.lang.String)
	 */
	public String getPathForExport(Shell shell, String fileName) {
		final FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		final String filePath = dialog.open();
		return filePath;
	}

}
