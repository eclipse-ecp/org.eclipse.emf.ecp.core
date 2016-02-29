/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.editor.handlers.ShortcutHandler;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Display;

/**
 * {@link ShortcutHandler} for the EcoreEditor. Uses an accelerator when creating new elements.
 *
 * @author Johannes Faltermeier
 *
 */
public class EcoreShortcutHandler extends ShortcutHandler {

	@Override
	protected Dialog createNewElementDialog(EditingDomain editingDomain, ISelectionProvider selectionProvider,
		EObject selection, String title) {
		return new EcoreCreateNewChildDialog(Display.getCurrent().getActiveShell(), title, selection,
			selectionProvider);
	}

	@Override
	protected String getNewSiblingCmdName() {
		return "org.eclipse.emfforms.editor.ecore.new.sibling";
	}

	@Override
	protected String getNewChildCmdName() {
		return "org.eclipse.emfforms.editor.ecore.new";
	}

	@Override
	protected String getDeleteCmdName() {
		return "org.eclipse.emfforms.editor.ecore.delete";
	}
}
