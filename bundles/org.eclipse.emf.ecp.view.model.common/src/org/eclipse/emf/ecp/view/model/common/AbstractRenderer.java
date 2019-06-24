/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.common.report.ReportService;

/**
 * Common super class for renderer.
 *
 * @author Eugen Neufeld
 * @param <VELEMENT> the {@link VElement} this renderer is applicable for
 *
 */
public abstract class AbstractRenderer<VELEMENT extends VElement> {

	private final VELEMENT vElement;
	private final ViewModelContext viewModelContext;
	private boolean disposed;
	private final ReportService reportService;

	/**
	 * Default constructor.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 * @param reportService The {@link ReportService} to use
	 * @since 1.6
	 */
	public AbstractRenderer(final VELEMENT vElement, final ViewModelContext viewContext, ReportService reportService) {

		if (vElement == null) {
			throw new IllegalArgumentException("vElement must not be null"); //$NON-NLS-1$
		}
		if (viewContext == null) {
			throw new IllegalArgumentException("vContext must not be null"); //$NON-NLS-1$
		}
		if (reportService == null) {
			throw new IllegalArgumentException("reportService must not be null"); //$NON-NLS-1$
		}
		this.vElement = vElement;
		this.viewModelContext = viewContext;
		this.reportService = reportService;

		register(vElement);
	}

	/**
	 * The {@link ViewModelContext} to use.
	 *
	 * @return the {@link ViewModelContext}
	 */
	public final ViewModelContext getViewModelContext() {
		checkRenderer();
		return viewModelContext;
	}

	/**
	 * The {@link VElement} instance to use.
	 *
	 * @return the {@link VElement}
	 */
	public final VELEMENT getVElement() {
		checkRenderer();
		return vElement;
	}

	/**
	 * Disposes all resources used by the renderer.
	 * Don't forget to call super.dispose if overwriting this method.
	 */
	protected void dispose() {
		disposed = true;

		// JUnit tests often mock view-model elements, so there may not be an adapter list
		final EList<Adapter> adapters = vElement.eAdapters();
		if (adapters != null) {
			adapters.removeIf(a -> a.isAdapterForType(this));
		}
	}

	/**
	 * Checks whether the renderer is disposed and if so throws an {@link IllegalStateException}.
	 *
	 * @since 1.6
	 */
	protected void checkRenderer() {
		if (disposed) {
			throw new IllegalStateException("Renderer is disposed"); //$NON-NLS-1$
		}

	}

	/**
	 * The {@link SWTRendererFactory} instance to use.
	 *
	 * @return the {@link SWTRendererFactory}
	 * @since 1.6
	 */
	protected final ReportService getReportService() {
		checkRenderer();
		return reportService;
	}

	/**
	 * Associate me with a view model element as its renderer. Multiple view-model elements
	 * may be associated with one renderer.
	 *
	 * @param viewModelElement the view model element to register as rendered by me
	 *
	 * @since 1.22
	 */
	protected void register(VElement viewModelElement) {
		// JUnit tests often mock view-model elements, so there may not be an adapter list
		final EList<Adapter> adapters = viewModelElement.eAdapters();
		if (adapters != null) {
			viewModelElement.eAdapters().add(new RendererAdapter());
		}
	}

	/**
	 * Query the renderer that renders a given view model {@code element} in a particular
	 * view model {@code context}.
	 *
	 * @param element a view model element rendered in some {@code context}
	 * @param context the view model rendering {@code context}
	 * @return the renderer, or {@code null} if the {@code element} is not rendered in this {@code context}
	 *
	 * @since 1.22
	 */
	public static AbstractRenderer<? extends VElement> getRenderer(VElement element, ViewModelContext context) {
		// JUnit tests often mock view-model elements, so there may not be an adapter list
		final EList<Adapter> adapters = element.eAdapters();
		if (adapters == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		final AbstractRenderer<? extends VElement>.RendererAdapter adapter = (AbstractRenderer<? extends VElement>.RendererAdapter) EcoreUtil
			.getAdapter(adapters, new ContextKey(context));
		return adapter != null ? adapter.getRenderer() : null;
	}

	//
	// Nested types
	//

	/**
	 * Adapter that associates a view model element with the renderer that renders it.
	 *
	 * @since 1.22
	 */
	private final class RendererAdapter extends AdapterImpl {

		RendererAdapter() {
			super();
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == AbstractRenderer.class
				|| type == AbstractRenderer.this
				|| type instanceof Class<?> && ((Class<?>) type).isInstance(AbstractRenderer.this)
				|| type instanceof ContextKey && ((ContextKey) type).hasContext(getViewModelContext());
		}

		AbstractRenderer<? extends VElement> getRenderer() {
			return AbstractRenderer.this;
		}

	}

	/**
	 * A wrapper for the view model context used as a recognized type for the
	 * renderer adapter that cannot be confused with any other adapter that uses
	 * a view model context as its type.
	 *
	 * @since 1.22
	 */
	private static final class ContextKey {
		private final ViewModelContext context;

		ContextKey(ViewModelContext context) {
			super();

			this.context = context;
		}

		boolean hasContext(ViewModelContext context) {
			return isInContext(this.context, context);
		}

		/**
		 * Is a {@code context} either a child of or the same as a {@code parent} context?
		 *
		 * @param parent a possible parent context
		 * @param context a possible child context
		 * @return {@code true} if the {@code context} is or is, recursively, a child of the
		 *         {@code parent}; {@code false}, otherwise
		 */
		private boolean isInContext(ViewModelContext parent, ViewModelContext context) {
			return parent == context
				|| context.getParentContext() != null && isInContext(parent, context.getParentContext());
		}
	}

}
