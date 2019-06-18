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
package org.eclipse.emf.ecp.view.spi.swt.selection;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * Default implementation of a master-detail selection provider.
 *
 * @since 1.21
 */
public class MasterDetailSelectionProvider implements IMasterDetailSelectionProvider, IPostSelectionProvider {
	private final ListenerList<ISelectionChangedListener> listeners = new ListenerList<>();
	private final ListenerList<ISelectionChangedListener> postListeners = new ListenerList<>();

	private final ISelectionChangedListener forwarder = event -> forward(listeners, event.getSelection());
	private final ISelectionChangedListener postForwarder = event -> forward(postListeners, event.getSelection());

	private final ISelectionProvider master;
	private ISelectionProvider detail;

	/**
	 * Initializes me with the {@code master} selection provider to which I delegate.
	 *
	 * @param master my master selection provider
	 */
	public MasterDetailSelectionProvider(ISelectionProvider master) {
		super();

		this.master = master;

		hookListeners(master);
	}

	/**
	 * Get the currently active selection provider.
	 *
	 * @return the active selection provider
	 */
	protected final ISelectionProvider getActiveSelectionProvider() {
		return detail != null ? detail : master;
	}

	@Override
	public ISelection getSelection() {
		return getActiveSelectionProvider().getSelection();
	}

	@Override
	public void setSelection(ISelection selection) {
		getActiveSelectionProvider().setSelection(selection);
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

	@Override
	public void setDetailSelectionProvider(ISelectionProvider selectionProvider) {
		if (selectionProvider != detail) {
			unhookListeners(getActiveSelectionProvider());

			detail = selectionProvider;

			hookListeners(getActiveSelectionProvider());

			pumpSelection();
		} // else nothing changed
	}

	private void hookListeners(ISelectionProvider delegate) {
		delegate.addSelectionChangedListener(forwarder);
		if (delegate instanceof IPostSelectionProvider) {
			((IPostSelectionProvider) delegate).addPostSelectionChangedListener(postForwarder);
		}
	}

	private void unhookListeners(ISelectionProvider delegate) {
		if (delegate instanceof IPostSelectionProvider) {
			((IPostSelectionProvider) delegate).removePostSelectionChangedListener(postForwarder);
		}
		delegate.removeSelectionChangedListener(forwarder);
	}

	/**
	 * Distribute my current selection to listeners.
	 */
	protected final void pumpSelection() {
		final ISelection selection = getSelection();
		forward(listeners, selection);
		forward(postListeners, selection);
	}

	private void forward(ListenerList<? extends ISelectionChangedListener> listeners, ISelection selection) {
		if (!listeners.isEmpty()) {
			final SelectionChangedEvent forward = new SelectionChangedEvent(this, selection);
			listeners.forEach(l -> l.selectionChanged(forward));
		}
	}

}
