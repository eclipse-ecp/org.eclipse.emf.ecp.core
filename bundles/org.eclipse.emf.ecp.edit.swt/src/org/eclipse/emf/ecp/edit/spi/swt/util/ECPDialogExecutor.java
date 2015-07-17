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
package org.eclipse.emf.ecp.edit.spi.swt.util;

import org.eclipse.emf.ecp.edit.internal.swt.util.DialogOpener;
import org.eclipse.jface.dialogs.Dialog;

/**
 * @author Eugen Neufeld
 * @since 1.5
 *
 */
public abstract class ECPDialogExecutor {

	private final Dialog dialog;

	/**
	 * Constructor.
	 *
	 * @param dialog the Dialog to show
	 */
	public ECPDialogExecutor(Dialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * Callback method which will get notified when the user presses a button leading to a close of the dialog. (e.g Ok
	 * or Cancel)
	 *
	 * @param codeResult the result code of the dialog. Constants are defined in {@link org.eclipse.jface.window.Window
	 *            Window}
	 */
	public abstract void handleResult(int codeResult);

	/**
	 * This is the method to run the {@link ECPDialogExecutor}.
	 */
	public void execute() {
		DialogOpener.openDialog(dialog, this);
	}
}
