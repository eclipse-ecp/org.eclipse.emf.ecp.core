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

import org.eclipse.emf.ecp.edit.spi.swt.util.ECPDialogExecutor;
import org.eclipse.jface.dialogs.Dialog;

/**
 * Interface to provide a wrapper for opening JFace dialogs.
 *
 * @author Eugen Neufeld
 *
 */
public interface DialogWrapper {

	/**
	 * The wrapper opens the provided JFace Dialog and returns the result via the provided callback.
	 *
	 * @param dialog the JFace {@link Dialog}
	 * @param callBack the {@link ECPDialogExecutor} called
	 */
	void openDialog(final Dialog dialog, final ECPDialogExecutor callBack);
}
