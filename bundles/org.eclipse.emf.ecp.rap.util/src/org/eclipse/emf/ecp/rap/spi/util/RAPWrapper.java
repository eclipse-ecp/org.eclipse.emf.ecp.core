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
 *******************************************************************************/
package org.eclipse.emf.ecp.rap.spi.util;

import org.eclipse.emf.ecp.edit.internal.swt.util.DialogWrapper;
import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * The RAP Wrapper for JFace Dialog class.
 *
 * @author Eugen Neufeld
 *
 */
@SuppressWarnings("restriction")
public class RAPWrapper implements DialogWrapper {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.internal.swt.util.DialogWrapper#openDialog(org.eclipse.jface.dialogs.Dialog,
	 *      org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor)
	 * @since 1.5
	 */
	@Override
	public void openDialog(final Dialog dialog, final ECPDialogExecutor callBack) {
		dialog.setBlockOnOpen(false);
		dialog.open();
		dialog.getShell().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent event) {
				callBack.handleResult(dialog.getReturnCode());
			}
		});
	}

}
