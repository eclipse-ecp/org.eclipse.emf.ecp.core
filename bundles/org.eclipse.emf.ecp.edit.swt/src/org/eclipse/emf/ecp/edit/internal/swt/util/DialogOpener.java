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
 * 
 *******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecp.edit.internal.swt.Activator;
import org.eclipse.jface.dialogs.Dialog;

/**
 * @author Eugen Neufeld
 * 
 */
public final class DialogOpener {

	private DialogOpener() {

	}

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
