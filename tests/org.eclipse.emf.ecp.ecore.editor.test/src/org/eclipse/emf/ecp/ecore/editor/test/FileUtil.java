/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * David Soto Setzke - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ecore.editor.test;

import java.io.File;

/**
 * File utility class.
 */
public final class FileUtil {

	private FileUtil() {

	}

	/**
	 * Deletes the given file.
	 *
	 * @param fileToBeDeleted
	 *            the file to be deleted
	 * @return {@code true}, if the file was successfully deleted, {@code false} otherwise
	 */
	public static boolean delete(File fileToBeDeleted) {
		if (fileToBeDeleted.isDirectory()) {
			if (fileToBeDeleted.list().length == 0) {
				return fileToBeDeleted.delete();
			}
			for (final String temp : fileToBeDeleted.list()) {
				final File fileDelete = new File(fileToBeDeleted, temp);
				delete(fileDelete);
			}
			if (fileToBeDeleted.list().length == 0) {
				fileToBeDeleted.delete();
			}
			return true;
		}
		return fileToBeDeleted.delete();
	}
}
