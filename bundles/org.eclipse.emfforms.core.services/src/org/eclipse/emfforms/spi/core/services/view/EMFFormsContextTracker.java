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
package org.eclipse.emfforms.spi.core.services.view;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.common.TriConsumer;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * A tracker of the comings and goings of contexts in a hierarchy of {@link EMFFormsViewContext}s.
 * This supplements the {@link EMFFormsContextListener} and {@link RootDomainModelChangeListener} APIs
 * with specificity in the call-backs of which context in a hierarchy the call-back pertains to.
 *
 * @since 1.22
 */
public class EMFFormsContextTracker {

	private final Map<EMFFormsViewContext, ContextListener> contextListeners = new WeakHashMap<>();
	private final Function<EMFFormsViewContext, ContextListener> listenerFactory = ContextListener::new;

	private final EMFFormsViewContext root;
	private final ReportService reportService;

	private boolean active;

	private Consumer<? super EMFFormsViewContext> initializedHandler;
	private Consumer<? super EMFFormsViewContext> disposedHandler;
	private TriConsumer<? super EMFFormsViewContext, ? super VElement, ? super EMFFormsViewContext> addedHandler;
	private TriConsumer<? super EMFFormsViewContext, ? super VElement, ? super EMFFormsViewContext> removedHandler;
	private Consumer<? super EMFFormsViewContext> domainModelChangedHandler;

	/**
	 * Initializes me with the root context to track.
	 *
	 * @param context the root context
	 */
	public EMFFormsContextTracker(EMFFormsViewContext context) {
		super();

		root = context;
		reportService = context.getService(ReportService.class);
	}

	/**
	 * Query whether a {@code context} is the root of this tracker.
	 *
	 * @param context a context
	 * @return whether it is the root of the context tree being tracked
	 */
	public boolean isRoot(EMFFormsViewContext context) {
		return context == root;
	}

	/**
	 * Add a call-back to handle the initialization of a new context, including the
	 * {@linkplain #isRoot(EMFFormsViewContext) root context}.
	 *
	 * @param handler the call-back. It is invoked with the context that was initialized
	 * @return myself, for convenience of call chaining
	 */
	public EMFFormsContextTracker onContextInitialized(Consumer<? super EMFFormsViewContext> handler) {
		initializedHandler = handler;
		return this;
	}

	/**
	 * Add a call-back to handle the disposal of a context, including the {@linkplain #isRoot(EMFFormsViewContext) root
	 * context}.
	 *
	 * @param handler the call-back. It is invoked with the context that was disposed
	 * @return myself, for convenience of call chaining
	 */
	public EMFFormsContextTracker onContextDisposed(Consumer<? super EMFFormsViewContext> handler) {
		disposedHandler = handler;
		return this;
	}

	/**
	 * Add a call-back to handle the replacement of a context's domain model, including the
	 * {@linkplain #isRoot(EMFFormsViewContext) root context}.
	 *
	 * @param handler the call-back. It is invoked with the context that had its domain-model
	 *            replaced
	 * @return myself, for convenience of call chaining
	 */
	public EMFFormsContextTracker onDomainModelChanged(Consumer<? super EMFFormsViewContext> handler) {
		domainModelChangedHandler = handler;
		return this;
	}

	/**
	 * Add a call-back to handle the addition of a new child context to a parent context.
	 *
	 * @param handler the call-back. It is invoked with the parent context, the parent element,
	 *            and the child context that was added
	 * @return myself, for convenience of call chaining
	 */
	public EMFFormsContextTracker onChildContextAdded(
		TriConsumer<? super EMFFormsViewContext, ? super VElement, ? super EMFFormsViewContext> handler) {
		addedHandler = handler;
		return this;
	}

	/**
	 * Add a call-back to handle the removal of a child context from a parent context.
	 *
	 * @param handler the call-back. It is invoked with the parent context, the parent element with
	 *            which the child context was associated, and the child context that was removed
	 * @return myself, for convenience of call chaining
	 */
	public EMFFormsContextTracker onChildContextRemoved(
		TriConsumer<? super EMFFormsViewContext, ? super VElement, ? super EMFFormsViewContext> handler) {
		removedHandler = handler;
		return this;
	}

	/**
	 * Start tracking my {@linkplain #isRoot(EMFFormsViewContext) root context}.
	 */
	public void open() {
		if (!active) {
			active = true;
			handleChildContextAdded(null, null, root);
		}
	}

	/**
	 * Stop tracking contexts.
	 */
	public void close() {
		if (active) {
			active = false;

			try {
				contextListeners.values().forEach(ContextListener::dispose);
			} finally {
				contextListeners.clear();
			}
		}
	}

	private void handleChildContextAdded(EMFFormsViewContext parentContext, VElement parentElement,
		EMFFormsViewContext childContext) {

		final ContextListener listener = getListener(childContext);
		listener.addedToParent(true);
		listener.setParentElement(parentElement);
	}

	private ContextListener getListener(EMFFormsViewContext context) {
		return contextListeners.computeIfAbsent(context, listenerFactory);
	}

	private void handleChildContextRemoved(EMFFormsViewContext parentContext, VElement parentElement,
		EMFFormsViewContext childContext) {
		final ContextListener listener = getListener(childContext);
		listener.addedToParent(false);
		listener.setParentElement(null);
	}

	private void notifyContextInitialized(EMFFormsViewContext context) {
		if (initializedHandler != null) {
			safeAccept(initializedHandler, context);
		}
	}

	private void notifyContextDisposed(EMFFormsViewContext context) {
		if (disposedHandler != null) {
			safeAccept(disposedHandler, context);
		}
	}

	private void notifyDomainModelChanged(EMFFormsViewContext context) {
		if (domainModelChangedHandler != null) {
			safeAccept(domainModelChangedHandler, context);
		}
	}

	private void notifyContextAdded(EMFFormsViewContext parent, VElement parentElement, EMFFormsViewContext child) {
		if (addedHandler != null) {
			safeAccept(addedHandler, parent, parentElement, child);
		}
	}

	private void notifyContextRemoved(EMFFormsViewContext parent, VElement parentElement, EMFFormsViewContext child) {
		if (removedHandler != null) {
			safeAccept(removedHandler, parent, parentElement, child);
		}
	}

	private <T> void safeAccept(Consumer<? super T> consumer, T t) {
		try {
			consumer.accept(t);
			// CHECKSTYLE.OFF: IllegalCatch
		} catch (final Exception e) {
			handleException(e);
			// CHECKSTYLE.ON: IllegalCatch
		} catch (final LinkageError e) {
			handleException(e);
		} catch (final AssertionError e) {
			handleException(e);
		}
	}

	private <T, U, V> void safeAccept(TriConsumer<? super T, ? super U, ? super V> consumer, T t, U u, V v) {
		try {
			consumer.accept(t, u, v);
			// CHECKSTYLE.OFF: IllegalCatch
		} catch (final Exception e) {
			handleException(e);
			// CHECKSTYLE.ON: IllegalCatch
		} catch (final LinkageError e) {
			handleException(e);
		} catch (final AssertionError e) {
			handleException(e);
		}
	}

	private void handleException(Throwable t) {
		reportService.report(new AbstractReport(t, "Unhandled exception in EMFFormsContextTracker call-back")); //$NON-NLS-1$
	}

	//
	// Nested types
	//

	/**
	 * Encapsulation of a context with a listener to its lifecycle events.
	 */
	private final class ContextListener implements EMFFormsContextListener {
		private final RootDomainModelChangeListener domainModelChangeListener = this::domainModelChanged;
		private final Reference<EMFFormsViewContext> context;
		private Reference<VElement> parentElement;
		private boolean addedToParent;

		ContextListener(EMFFormsViewContext context) {
			super();

			this.context = new WeakReference<>(context);
			context.registerEMFFormsContextListener(this);
			context.registerRootDomainModelChangeListener(domainModelChangeListener);
		}

		// Note that if we're invoking this method, it's because there are events to handle
		// involving the context, so the reference cannot have been cleared
		private Optional<EMFFormsViewContext> getContext() {
			return Optional.ofNullable(context.get());
		}

		void dispose() {
			getContext().ifPresent(ctx -> {
				ctx.unregisterRootDomainModelChangeListener(domainModelChangeListener);
				ctx.unregisterEMFFormsContextListener(this);
			});
		}

		@Override
		public void contextInitialised() {
			getContext().ifPresent(EMFFormsContextTracker.this::notifyContextInitialized);
		}

		@Override
		public void contextDispose() {
			getContext().ifPresent(EMFFormsContextTracker.this::notifyContextDisposed);
		}

		@Override
		public void childContextAdded(VElement parentElement, EMFFormsViewContext childContext) {
			// Check if we already processed the add
			if (getListener(childContext).isAddedToParent()) {
				return;
			}

			getContext().ifPresent(ctx -> {
				notifyContextAdded(ctx, parentElement, childContext);
				handleChildContextAdded(ctx, parentElement, childContext);
			});
		}

		@Override
		public void childContextDisposed(EMFFormsViewContext childContext) {
			// Check if we already processed the disposal
			if (!getListener(childContext).isAddedToParent()) {
				return;
			}

			getContext().ifPresent(ctx -> {
				final VElement parentElement = getListener(childContext).getParentElement();
				handleChildContextRemoved(ctx, parentElement, childContext);
				notifyContextRemoved(ctx, parentElement, childContext);
			});
		}

		void domainModelChanged() {
			getContext().ifPresent(EMFFormsContextTracker.this::notifyDomainModelChanged);
		}

		void addedToParent(boolean added) {
			addedToParent = added;
		}

		/**
		 * Is my context currently added to (attached to) its parent? Note that the root
		 * context is considered as implicitly added to its null parent.
		 *
		 * @return whether my context is currently known by its parent as a child of it
		 */
		boolean isAddedToParent() {
			return addedToParent;
		}

		VElement getParentElement() {
			return parentElement != null ? parentElement.get() : null;
		}

		void setParentElement(VElement parentElement) {
			this.parentElement = parentElement != null ? new WeakReference<>(parentElement) : null;
		}

	}

}
