/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * mat - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;

/**
 * Action to add a row to a table viewer.
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public abstract class AddRowAction extends TableRendererAction {

	/**
	 * The ID of this action.
	 */
	public static final String ACTION_ID = PREFIX + "tablecontrol.add_row"; //$NON-NLS-1$

	/**
	 * The default key binding of this action.
	 */
	public static final String DEFAULT_KEYBINDING = "M1+n"; //$NON-NLS-1$

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public AddRowAction(TableRendererViewerActionContext actionContext) {
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
		final EClass eClass = ((EReference) eStructuralFeature).getEReferenceType();

		addRowLegacy(eClass, eStructuralFeature, eObject);
	}

	/**
	 * Delegate method to legacy addRow() implementation.
	 *
	 * TODO:
	 * 1) deprecate addRow() within TableControlSWTRenderer
	 * 2) move addRow() from TableControlSWTRenderer into this action
	 * 3) remove this delegate method
	 *
	 * @param eObject the {@link EObject}
	 * @param eStructuralFeature the {@link EStructuralFeature}
	 * @param eClass the {@link EClass}
	 */
	public abstract void addRowLegacy(EClass eClass, EStructuralFeature eStructuralFeature, EObject eObject);

	@Override
	public boolean canExecute() {
		if (isTableDisabled() || getVTableControl().isAddRemoveDisabled() || isUpperBoundReached()) {
			return false;
		}
		return true;
	}

}