/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Action to remove a row from a table viewer.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public abstract class RemoveRowAction extends TableRendererAction {

	/**
	 * The ID of this action.
	 */
	public static final String ACTION_ID = PREFIX + "tablecontrol.remove_row"; //$NON-NLS-1$

	/**
	 * The default key binding of this action.
	 */
	public static final String DEFAULT_KEYBINDING = "M1+r"; //$NON-NLS-1$

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public RemoveRowAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	@Override
	public String getId() {
		return ACTION_ID;
	}

	@Override
	public void execute() {

		final ViewerActionContext<?> context = getActionContext();
		final IStructuredSelection selection = (IStructuredSelection) context.getViewer().getSelection();
		final List<EObject> itemsToRemove = new ArrayList<EObject>();
		final Iterator<?> iterator = selection.iterator();

		while (iterator.hasNext()) {
			itemsToRemove.add((EObject) iterator.next());
		}

		final Setting setting = getActionContext().getSetting();
		final EObject eObject = setting.getEObject();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();

		removeRowLegacy(itemsToRemove, eObject, eStructuralFeature);

		// TODO: select the last record before the removed row, see bug #536243
	}

	@Override
	public boolean canExecute() {
		if (isTableDisabled() || getVTableControl().isAddRemoveDisabled() || isLowerBoundReached()
			|| isViewerSelectionInvalid()) {
			return false;
		}
		return true;
	}

	/**
	 * Delegate method to legacy addRow() implementation.
	 *
	 * TODO:
	 * 1) deprecate deleteRowUserConfirmDialog(), deleteRows(), removeElements() within TableControlSWTRenderer
	 * 2) move deleteRowUserConfirmDialog(), deleteRows(), removeElements() from TableControlSWTRenderer
	 * into this action
	 * 3) remove this delegate method
	 *
	 * @param deletionList the elements to delete
	 * @param eObject the {@link EObject}
	 * @param eStructuralFeature the {@link EStructuralFeature}
	 */
	public abstract void removeRowLegacy(List<EObject> deletionList,
		EObject eObject, EStructuralFeature eStructuralFeature);

}
