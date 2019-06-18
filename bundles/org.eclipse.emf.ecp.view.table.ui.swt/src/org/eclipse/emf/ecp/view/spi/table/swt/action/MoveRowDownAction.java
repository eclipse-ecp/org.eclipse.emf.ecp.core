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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Action to move a row upwards in a table viewer (requires the containment reference to be ordered).
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class MoveRowDownAction extends AbstractMoveRowAction {

	/**
	 * The ID of this action.
	 */
	public static final String ACTION_ID = PREFIX + "tablecontrol.move_row_down"; //$NON-NLS-1$

	/**
	 * The default key binding of this action.
	 */
	public static final String DEFAULT_KEYBINDING = "M1+M2+ARROW_DOWN"; //$NON-NLS-1$

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public MoveRowDownAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	@Override
	public String getId() {
		return ACTION_ID;
	}

	@Override
	public void execute() {
		final List<?> containments = getContainments();

		final List<?> moveDownList = Arrays.asList(
			((IStructuredSelection) getTableViewer().getSelection()).toArray());
		sortSelectionBasedOnIndex(moveDownList, containments);
		// need to reverse to avoid the moves interfering each other
		Collections.reverse(moveDownList);

		// shouldn't this be wrapped in a CompositeCommand?
		final int maxIndex = containments.size() - 1;
		final EditingDomain editingDomain = getActionContext().getEditingDomain();
		final Setting setting = getActionContext().getSetting();
		final EObject eObject = setting.getEObject();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
		for (final Object moveDownObject : moveDownList) {
			final int currentIndex = containments.indexOf(moveDownObject);
			if (currentIndex < 0 || currentIndex == maxIndex) {
				return;
			}
			editingDomain.getCommandStack()
				.execute(
					new MoveCommand(
						editingDomain, eObject, eStructuralFeature, currentIndex, currentIndex + 1));
		}
	}

	@Override
	public boolean canExecute() {
		final List<?> containments = getContainments();

		final List<?> moveDownList = Arrays.asList(
			((IStructuredSelection) getTableViewer().getSelection()).toArray());
		if (moveDownList.isEmpty()) {
			return false;
		}

		// The lowest selected object must not already be at the end of the table
		sortSelectionBasedOnIndex(moveDownList, containments);
		Collections.reverse(moveDownList);
		return containments.indexOf(moveDownList.get(0)) < containments.size() - 1 && super.canExecute();
	}
}
