/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.actions.delegating;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.swt.treemasterdetail.actions.KeybindedMasterDetailAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * Abstract implementation of a {@link KeybindedMasterDetailAction} forwarding to a {@link BaseSelectionListenerAction}.
 *
 * @author Stefan Dirix
 * @since 1.8
 */
public abstract class DelegatingMasterDetailAction extends KeybindedMasterDetailAction {

	/**
	 * The {@link BaseSelectionListenerAction} to which this {@link KeybindedMasterDetailAction} forwards to.
	 */
	private final BaseSelectionListenerAction delegatedAction;

	/**
	 * Constructor.
	 *
	 * @param editingDomain The {@link EditingDomain} which is used by the {@link BaseSelectionListenerAction}.
	 */
	public DelegatingMasterDetailAction(EditingDomain editingDomain) {
		delegatedAction = createDelegatedAction(editingDomain);
		setLabel(delegatedAction.getText());
		setImagePath(getEMFImagePath());
	}

	/**
	 * The label for the {@link KeybindedMasterDetailAction}.
	 *
	 * @return
	 * 		The label for the {@link KeybindedMasterDetailAction}.
	 */
	protected String getEMFEditLabel() {
		return delegatedAction.getText();
	}

	/**
	 * The path to the image for the {@link BaseSelectionListenerAction}.
	 *
	 * @return
	 * 		The path to the local image for the {@link BaseSelectionListenerAction}.
	 */
	protected abstract String getEMFImagePath();

	/**
	 * Creates the {@link BaseSelectionListenerAction} to which this {@link KeybindedMasterDetailAction} forwards to.
	 *
	 * @param editingDomain
	 *            The {@link EditingDomain} which is used to create the {@link BaseSelectionListenerAction}.
	 *
	 * @return
	 * 		The {@link BaseSelectionListenerAction} to which this {@link KeybindedMasterDetailAction} forwards to.
	 */
	protected abstract BaseSelectionListenerAction createDelegatedAction(EditingDomain editingDomain);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) event.getTrigger();
		delegatedAction.selectionChanged(selection);
		if (delegatedAction.isEnabled()) {
			delegatedAction.run();
		}
		return null;
	}

	@Override
	public void execute(EObject object) {
		final IStructuredSelection selection = new StructuredSelection(object);
		delegatedAction.selectionChanged(selection);
		if (delegatedAction.isEnabled()) {
			delegatedAction.run();
		}
	}

	@Override
	public boolean shouldShow(EObject eObject) {
		final IStructuredSelection selection = new StructuredSelection(eObject);
		delegatedAction.selectionChanged(selection);
		return delegatedAction.isEnabled();
	}

	/**
	 * Returns the {@link BaseSelectionListenerAction} to which this {@link KeybindedMasterDetailAction} forwards to.
	 *
	 * @return
	 * 		The {@link BaseSelectionListenerAction} this {@link KeybindedMasterDetailAction} is forwarding to.
	 */
	public BaseSelectionListenerAction getDelegatedAction() {
		return delegatedAction;
	}

	@Override
	protected void executeOnKeyRelease(ISelection currentSelection) {
		if (currentSelection instanceof IStructuredSelection) {
			delegatedAction.selectionChanged(IStructuredSelection.class.cast(currentSelection));
		}
		delegatedAction.run();
	}

}
