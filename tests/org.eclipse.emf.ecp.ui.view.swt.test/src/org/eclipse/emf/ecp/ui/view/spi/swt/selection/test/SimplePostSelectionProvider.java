/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.spi.swt.selection.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * A simple selection-provider to drive tests that need a selection provider.
 */
class SimplePostSelectionProvider implements IPostSelectionProvider {

	private Object selection;

	private final List<ISelectionChangedListener> listeners = new ArrayList<ISelectionChangedListener>(3);
	private final List<ISelectionChangedListener> postListeners = new ArrayList<ISelectionChangedListener>(3);

	/**
	 * Initializes me.
	 */
	SimplePostSelectionProvider() {
		super();
	}

	@Override
	public ISelection getSelection() {
		return selection == null ? StructuredSelection.EMPTY : new StructuredSelection(selection);
	}

	@Override
	public void setSelection(ISelection selection) {
		Object newSelection = null;

		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			newSelection = ((IStructuredSelection) selection).getFirstElement();
		}

		if (!Objects.equals(newSelection, this.selection)) {
			select(newSelection);
		}
	}

	void select(Object selection) {
		select(selection, true, true);
	}

	void select(Object selection, boolean notify, boolean notifyPost) {
		this.selection = selection;

		if (notify) {
			fireSelection();
		}
		if (notifyPost) {
			firePostSelection();
		}
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		postListeners.add(listener);
	}

	@Override
	public void removePostSelectionChangedListener(ISelectionChangedListener listener) {
		postListeners.remove(listener);
	}

	private void fireSelection() {
		final SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		listeners.forEach(l -> l.selectionChanged(event));
	}

	private void firePostSelection() {
		final SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		postListeners.forEach(l -> l.selectionChanged(event));
	}

}
