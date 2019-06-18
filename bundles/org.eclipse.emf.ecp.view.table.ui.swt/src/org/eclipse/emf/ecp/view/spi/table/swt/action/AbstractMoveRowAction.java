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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.swt.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emfforms.spi.swt.table.action.ViewerActionContext;

/**
 * Abstract base type for actions which move rows inside a table.
 *
 * @author Lucas Koehler
 * @since 1.18
 *
 */
public abstract class AbstractMoveRowAction extends TableRendererAction {

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public AbstractMoveRowAction(TableRendererViewerActionContext actionContext) {
		super(actionContext);
	}

	/**
	 * Sorting helper for a table viewer selection.
	 *
	 * @param selection the selection to sort
	 * @param list the index list
	 */
	public static void sortSelectionBasedOnIndex(List<?> selection, final List<?> list) {
		Collections.sort(
			selection,
			new Comparator<Object>() {
				@Override
				public int compare(Object left, Object right) {
					return list.indexOf(left) - list.indexOf(right);
				}
			});
	}

	@Override
	public boolean canExecute() {
		if (isTableDisabled() || !isOrdered()
			|| getVTableControl().isMoveUpDownDisabled()
			|| getNumberOfVisibleRows() <= 1) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the list containing all objects of the table viewer.
	 *
	 * @return the containments list
	 */
	protected List<?> getContainments() {
		final Setting setting = getActionContext().getSetting();
		final EObject eObject = setting.getEObject();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();

		// TODO: will containments work in combination with viewer filters?
		final List<?> containments = (List<?>) eObject.eGet(eStructuralFeature, true);
		return containments;
	}
}
