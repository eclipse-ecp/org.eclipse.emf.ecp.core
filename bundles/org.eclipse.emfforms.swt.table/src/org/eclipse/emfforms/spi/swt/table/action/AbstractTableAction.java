/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table.action;

import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Base class for table actions.
 *
 * @param <Viewer> the table viewer implementation to use
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 * @since 1.18
 *
 */
public abstract class AbstractTableAction<Viewer extends AbstractTableViewer> implements Action {

	private final ViewerActionContext<? extends Viewer> actionContext;

	/**
	 * The constructor.
	 *
	 * @param actionContext the {@link ViewerActionContext}
	 */
	public AbstractTableAction(ViewerActionContext<? extends Viewer> actionContext) {
		super();
		this.actionContext = actionContext;
	}

	/**
	 * Returns the {@link ViewerActionContext}.
	 *
	 * @return the context the {@link ViewerActionContext}
	 */
	protected ViewerActionContext<? extends Viewer> getActionContext() {
		return actionContext;
	}

	/**
	 * Returns the table viewer object.
	 *
	 * @return the table viewer
	 */
	protected AbstractTableViewer getTableViewer() {
		return actionContext.getViewer();
	}

	/**
	 * @return the editingDomain
	 */
	protected EditingDomain getEditingDomainForContainment() {
		return actionContext.getEditingDomain();
	}

	/**
	 * Returns whether the table is read-only.
	 *
	 * @return true when the table is read-only, false otherwise
	 */
	protected boolean isTableDisabled() {
		return false;
	}

	/**
	 * Returns true when the lower bound of the table setting has been reached.
	 *
	 * @return true when the lower bound of the table setting has been reached, false otherwise
	 */
	protected boolean isLowerBoundReached() {
		final Setting setting = actionContext.getSetting();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
		final EObject eObject = setting.getEObject();

		final List<?> containments = (List<?>) eObject.eGet(eStructuralFeature, true);
		return eStructuralFeature.getLowerBound() != 0
			&& containments.size() <= eStructuralFeature.getLowerBound();
	}

	/**
	 * Returns true when the upper bound of the table setting has been reached.
	 *
	 * @return true when the upper bound of the table setting has been reached, false otherwise
	 */
	protected boolean isUpperBoundReached() {
		final Setting setting = actionContext.getSetting();
		final EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
		final EObject eObject = setting.getEObject();

		final List<?> containments = (List<?>) eObject.eGet(eStructuralFeature, true);
		return eStructuralFeature.getUpperBound() != -1
			&& containments.size() >= eStructuralFeature.getUpperBound();
	}

	/**
	 * Returns true when the table feature is ordered.
	 *
	 * @return true when the table feature is ordered, false otherwise
	 */
	protected boolean isOrdered() {
		final ViewerActionContext<?> context = getActionContext();
		return context.getSetting().getEStructuralFeature().isOrdered();
	}

	/**
	 * Returns true when the table is read-only.
	 *
	 * @return true when the table is read-only, false otherwise
	 */
	protected boolean isViewerSelectionInvalid() {
		final IStructuredSelection selection = (IStructuredSelection) actionContext.getViewer().getSelection();
		return selection == null || selection.isEmpty() || selection.getFirstElement() == null;
	}

	/**
	 * Returns the number of visible viewer rows for the table control.
	 *
	 * @return the number of visible rows
	 */
	protected int getNumberOfVisibleRows() {
		// TODO: will this work in combination with viewer filters?
		return ((IObservableList<?>) actionContext.getViewer().getInput()).size();
		// return actionContext.getViewer().getTable().getItemCount();
	}

}
