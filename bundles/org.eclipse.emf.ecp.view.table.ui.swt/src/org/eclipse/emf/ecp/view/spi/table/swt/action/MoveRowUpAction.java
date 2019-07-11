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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
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
public class MoveRowUpAction extends AbstractMoveRowAction {

	/**
	 * The ID of this action.
	 */
	public static final String ACTION_ID = PREFIX + "tablecontrol.move_row_up"; //$NON-NLS-1$

	/**
	 * The default key binding of this action.
	 */
	public static final String DEFAULT_KEYBINDING = "M1+M2+ARROW_UP"; //$NON-NLS-1$

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public MoveRowUpAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	@Override
	public String getId() {
		return ACTION_ID;
	}

	@Override
	public void execute() {
		final List<?> containments = getContainments();

		final List<?> moveUpList = Arrays.asList(
			((IStructuredSelection) getTableViewer().getSelection()).toArray());
		sortSelectionBasedOnIndex(moveUpList, containments);

		final EditingDomain editingDomain = getActionContext().getEditingDomain();
		final Setting setting = getActionContext().getSetting();
		final EObject eObject = setting.getEObject();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();

		final List<Command> commands = moveUpList.stream().map(moveUpObject -> {
			final int currentIndex = containments.indexOf(moveUpObject);
			if (currentIndex <= 0) {
				return null;
			}
			return new MoveCommand(
				editingDomain, eObject, eStructuralFeature, currentIndex, currentIndex - 1);
		}).filter(Objects::nonNull).collect(Collectors.toList());

		editingDomain.getCommandStack().execute(new CompoundCommand(commands));
		getTableViewer().refresh();
	}

	@Override
	public boolean canExecute() {
		final List<?> containments = getContainments();

		final List<?> moveUpList = Arrays.asList(
			((IStructuredSelection) getTableViewer().getSelection()).toArray());
		if (moveUpList.isEmpty()) {
			return false;
		}

		// The highest selected object must not already on top of the table
		sortSelectionBasedOnIndex(moveUpList, containments);
		return containments.indexOf(moveUpList.get(0)) > 0 && super.canExecute();
	}
}
