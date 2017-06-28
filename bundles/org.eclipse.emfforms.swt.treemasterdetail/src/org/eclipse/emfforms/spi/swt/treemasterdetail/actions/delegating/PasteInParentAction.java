/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;

/**
 * A paste action that will switch to the parent element of the current selection. It will
 * create a {@link PasteFromClipboardCommand} either for the selected element or its parent.
 *
 * @since 1.13.0
 */
public class PasteInParentAction extends CommandActionHandler {
	private static final String PASTE_IN_PARENT = LocalizationServiceHelper.getString(PasteInParentAction.class,
		"PasteInParent");

	/**
	 * Default constructor.
	 *
	 * @param domain the {@link EditingDomain}
	 */
	public PasteInParentAction(EditingDomain domain) {
		super(domain, PASTE_IN_PARENT);
	}

	/**
	 * Constructor without providing an {@link EditingDomain}.
	 *
	 */
	public PasteInParentAction() {
		super(null, PASTE_IN_PARENT);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		if (selection.size() == 1) {
			final Object next = selection.iterator().next();
			if (next instanceof EObject) {
				final EObject eContainer = ((EObject) next).eContainer();
				if (eContainer != null) {
					return PasteFromClipboardCommand.create(domain, eContainer, null);
				}
			}
		}
		return UnexecutableCommand.INSTANCE;

	}

}
