/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.swt.services;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;
import static org.eclipse.emf.ecore.util.EcoreUtil.getAllContents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeAddRemoveListener;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * A selection provider service that simply delegates the selection provider API
 * to the selection provider registered for the "top-most" element in the view model,
 * where "top-most" is the first element in a depth-first traversal.
 *
 * @since 1.20
 */
public class DefaultSelectionProviderService implements ECPSelectionProviderService {

	private final Map<VElement, ISelectionProvider> registeredProviders = new HashMap<VElement, ISelectionProvider>();

	private ViewModelContext context;
	private DelegatingSelectionProvider selectionProvider;
	private ModelChangeAddRemoveListener viewListener;

	/**
	 * Initializes me.
	 */
	public DefaultSelectionProviderService() {
		super();
	}

	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		selectionProvider = new DelegatingSelectionProvider();

		viewListener = createViewListener();
		context.registerViewChangeListener(viewListener);
	}

	@Override
	public void dispose() {
		registeredProviders.clear();
		selectionProvider = null;

		if (context != null) {
			context.unregisterViewChangeListener(viewListener);
			viewListener = null;
			context = null;
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public void registerSelectionProvider(VElement element, ISelectionProvider selectionProvider) {
		if (registeredProviders.put(requireNonNull(element), requireNonNull(selectionProvider)) != selectionProvider) {
			update();
		}
	}

	private void update() {
		ISelectionProvider delegate = null;

		// Find the "top-most" selection provider
		for (final Iterator<EObject> iter = getAllContents(singleton(context.getViewModel())); delegate == null
			&& iter.hasNext();) {
			delegate = registeredProviders.get(iter.next());
		}

		selectionProvider.setDelegate(delegate);
	}

	/**
	 * Create a listener that detects removal of elements from the view model
	 * and updates our selection-provider registration (and delegation) accordingly.
	 *
	 * @return the view model listener
	 */
	private ModelChangeAddRemoveListener createViewListener() {
		return new ModelChangeAddRemoveListener() {

			@Override
			public void notifyRemove(Notifier notifier) {
				if (registeredProviders.remove(notifier) != null) {
					update();
				}
			}

			@Override
			public void notifyAdd(Notifier notifier) {
				// Not interesting. We would need a registration call

			}

			@Override
			public void notifyChange(ModelChangeNotification notification) {
				// Not interesting
			}

		};
	}

	//
	// Nested types
	//

	/**
	 * A selection provider that delegates to the "top-most" registered provider.
	 */
	private static class DelegatingSelectionProvider implements ISelectionProvider {

		private ISelectionProvider delegate;

		private final ISelectionChangedListener delegateListener = this::selectionChanged;
		private final ListenerList<ISelectionChangedListener> listeners = new ListenerList<>();

		DelegatingSelectionProvider() {
			super();
		}

		@Override
		public ISelection getSelection() {
			return delegate == null ? StructuredSelection.EMPTY : delegate.getSelection();
		}

		@Override
		public void setSelection(ISelection selection) {
			if (delegate != null) {
				delegate.setSelection(selection);
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

		void setDelegate(ISelectionProvider delegate) {
			if (delegate == this.delegate) {
				return;
			}

			if (this.delegate != null) {
				this.delegate.removeSelectionChangedListener(delegateListener);
			}

			this.delegate = delegate;

			if (this.delegate != null) {
				this.delegate.addSelectionChangedListener(delegateListener);
				fireSelection(delegate.getSelection());
			}
		}

		void selectionChanged(SelectionChangedEvent event) {
			fireSelection(event.getSelection());
		}

		private void fireSelection(ISelection selection) {
			if (!listeners.isEmpty()) {
				final SelectionChangedEvent event = new SelectionChangedEvent(this, selection);
				listeners.forEach(l -> l.selectionChanged(event));
			}
		}

	}

}
