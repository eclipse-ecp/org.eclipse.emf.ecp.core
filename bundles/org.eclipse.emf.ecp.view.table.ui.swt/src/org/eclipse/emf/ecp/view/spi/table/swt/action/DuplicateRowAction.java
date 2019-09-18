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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * Action to duplicate a row within a table viewer.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public class DuplicateRowAction extends TableRendererAction {

	/**
	 * The ID of this action.
	 */
	public static final String ACTION_ID = PREFIX + "tablecontrol.duplicate_row"; //$NON-NLS-1$

	/**
	 * The default key binding of this action.
	 */
	public static final String DEFAULT_KEYBINDING = "M1+d"; //$NON-NLS-1$

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public DuplicateRowAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	@Override
	public String getId() {
		return ACTION_ID;
	}

	@Override
	public void execute() {
		final Setting setting = getActionContext().getSetting();
		final EObject eObject = setting.getEObject();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();

		@SuppressWarnings("unchecked")
		final List<EObject> toDuplicate = ((IStructuredSelection) getTableViewer().getSelection()).toList();

		final Collection<EObject> copies = copyElements(
			eObject, eStructuralFeature, getEditingDomainForContainment(), toDuplicate);

		getTableViewer().setSelection(new StructuredSelection(copies.iterator().next()), true);
	}

	private Collection<EObject> copyElements(EObject eObject, EStructuralFeature structuralFeature,
		EditingDomain editingDomain, Collection<EObject> elementsToCopy) {

		final Collection<EObject> copies = copy(elementsToCopy);
		final Command createCommand = AddCommand.create(editingDomain, eObject, structuralFeature, copies);
		if (createCommand.canExecute()) {
			if (editingDomain.getCommandStack() == null) {
				createCommand.execute();
			} else {
				editingDomain.getCommandStack().execute(createCommand);
			}
		}
		return copies;
	}

	/**
	 * Copies the collection of {@link EObject}.
	 *
	 * @param elementsToCopy The {@link Collection} to copy
	 * @return The copied {@link Collection}
	 */
	Collection<EObject> copy(Collection<EObject> elementsToCopy) {
		final Copier copier = new Copier();
		final Collection<EObject> copies = copier.copyAll(elementsToCopy);
		copier.copyReferences();
		copier.forEach(this::copyBidirectionalReferences);
		return copies;
	}

	private void copyBidirectionalReferences(final EObject original, final EObject copy) {
		final Map<EObject, Collection<Setting>> find = EcoreUtil.ExternalCrossReferencer.find(original);
		// find all bidirectional references and manually set them on the copy
		find.forEach((referenced, settings) -> {
			settings.forEach(setting -> {
				final EStructuralFeature feat = setting.getEStructuralFeature();
				final EReference opp = ((EReference) feat).getEOpposite();
				// if the opposite is a singleton, then we would change its reference, so ignore
				if (opp != null && opp.isMany() && setting.getEObject() == original) {
					copy.eSet(feat, setting.get(true));
				}
			});
		});
	}

	@Override
	public boolean canExecute() {
		if (isTableDisabled() || isUpperBoundReached() || isViewerSelectionInvalid()) {
			return false;
		}

		return true;
	}

}
