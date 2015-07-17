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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.editor.controls;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * An ISelectionStatusValidator allowing to get the {@link TreePath} of the current selection.
 *
 * @author Eugen Neufeld
 *
 */
public abstract class ECPSelectionStatusValidator implements ISelectionStatusValidator {
	private ECPViewEditorTreeSelectionDialog dialog;

	/**
	 * Sets the dialog.
	 *
	 * @param dialog the dialog
	 */
	public void setECPViewEditorTreeSelectionDialog(ECPViewEditorTreeSelectionDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * The {@link TreePath} of the current selection.
	 *
	 * @return the {@link TreePath}
	 */
	protected TreePath getTreePath() {
		return dialog.getTreePath();
	}
}
