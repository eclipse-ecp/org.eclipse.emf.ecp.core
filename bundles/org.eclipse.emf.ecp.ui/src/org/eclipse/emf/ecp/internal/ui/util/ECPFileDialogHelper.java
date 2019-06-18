/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.internal.ui.util;

import org.eclipse.swt.widgets.Shell;

/**
 * Interface defining methods for retrieving Filepaths from a {@link org.eclipse.swt.widgets.FileDialog FileDialog}.
 *
 * @author jfaltermeier
 *
 */
public interface ECPFileDialogHelper {

	/**
	 * Returns the absolute file path of the file selected by the user.
	 *
	 * @param shell The shell to open the {@link org.eclipse.swt.widgets.FileDialog FileDialog}
	 * @return the absolute filename or <code>null</code> if no file was selected
	 */
	String getPathForImport(Shell shell);

	/**
	 * Returns the file path to export a model element to.
	 *
	 * @param shell The shell to open the {@link org.eclipse.swt.widgets.FileDialog FileDialog}
	 * @param fileName a proposed filename for the export
	 * @return the file path
	 */
	String getPathForExport(Shell shell, String fileName);
}
