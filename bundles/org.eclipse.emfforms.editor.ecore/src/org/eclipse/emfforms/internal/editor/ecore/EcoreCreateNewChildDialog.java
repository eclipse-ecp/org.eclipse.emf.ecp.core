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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.internal.editor.ecore.helpers.EcoreHelpers;
import org.eclipse.emfforms.internal.editor.ui.CreateNewChildDialog;
import org.eclipse.emfforms.spi.editor.InitializeChildCallback;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog which is shown when a new element is created in the ecore editor.
 *
 * @author Johannes Faltermeier
 *
 */
public class EcoreCreateNewChildDialog extends CreateNewChildDialog {

	/**
	 * Constructs a new {@link EcoreCreateNewChildDialog}.
	 * 
	 * @param parentShell the parent shell
	 * @param title the title of the dialog
	 * @param parent the parent EObject which will contain the new child
	 * @param selectionProvider a provider which gives access to the current selection
	 */
	public EcoreCreateNewChildDialog(Shell parentShell, String title, EObject parent,
		ISelectionProvider selectionProvider) {
		super(parentShell, title, parent, selectionProvider);
	}

	@Override
	protected List<Action> getNewChildActions(Collection<?> descriptors,
		final EditingDomain domain, final EObject eObject) {

		final List<Action> result = new ArrayList<Action>();

		for (final Object descriptor : descriptors) {

			final CommandParameter cp = (CommandParameter) descriptor;
			if (!CommandParameter.class.isInstance(descriptor)) {
				continue;
			}
			if (cp.getEReference() == null) {
				continue;
			}
			if (EcoreHelpers.isGenericFeature(cp.getFeature())) {
				// This ensures, that we won't show any generic features anymore
				continue;
			}
			if (!cp.getEReference().isMany()
				&& eObject.eIsSet(cp.getEStructuralFeature())) {
				continue;
			} else if (cp.getEReference().isMany()
				&& cp.getEReference().getUpperBound() != -1
				&& cp.getEReference().getUpperBound() <= ((List<?>) eObject
					.eGet(cp.getEReference())).size()) {
				continue;
			}

			result.add(new CreateChildActionWithAccelerator(eObject, domain, getSelectionProvider(), cp,
				new InitializeChildCallback()));
		}
		return result;
	}
}
