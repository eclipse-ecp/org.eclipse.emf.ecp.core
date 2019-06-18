/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.jface.dialogs.Dialog;

/**
 * @author Eugen Neufeld
 *
 */
public final class DialogOpener {

	private DialogOpener() {

	}

	/**
	 * The provided {@link Dialog} is opened and the result is returned via the provided {@link ECPDialogExecutor}.
	 * This method searches for a DialogWrapper which will wrap the code in order to allow opening JFace dialogs in RAP.
	 *
	 * @param dialog the JFace Dialog to open
	 * @param callBack the {@link ECPDialogExecutor} called to handle the result
	 */
	public static void openDialog(Dialog dialog, ECPDialogExecutor callBack) {
		DialogWrapper wrapper = null;
		final IConfigurationElement[] controls = Platform.getExtensionRegistry().getConfigurationElementsFor(
			"org.eclipse.emf.ecp.edit.swt.dialogWrapper"); //$NON-NLS-1$
		for (final IConfigurationElement e : controls) {
			try {
				wrapper = (DialogWrapper) e.createExecutableExtension("class"); //$NON-NLS-1$
				break;
			} catch (final CoreException e1) {
				Activator.logException(e1);
			}
		}
		if (wrapper == null) {
			callBack.handleResult(dialog.open());
			return;
		}
		wrapper.openDialog(dialog, callBack);
	}
}
