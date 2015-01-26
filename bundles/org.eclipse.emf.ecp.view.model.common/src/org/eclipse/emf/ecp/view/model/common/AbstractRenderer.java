/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * Common super class for renderer.
 *
 * @author Eugen Neufeld
 * @param <VELEMENT> the {@link VElement} this renderer is applicable for
 *
 */
public abstract class AbstractRenderer<VELEMENT extends VElement> {

	private VELEMENT vElement;
	private ViewModelContext viewModelContext;
	private boolean disposed;

	/**
	 * Initialize the control. This can only be called once.
	 *
	 * @param vElement the {@link VElement} to be rendered
	 * @param viewContext the {@link ViewModelContext} to use
	 */
	public void init(final VELEMENT vElement, final ViewModelContext viewContext) {
		checkRenderer();
		// TODO:Is it possible to call init twice?

		if (this.vElement != null) {
			throw new IllegalStateException("vElement must not be null"); //$NON-NLS-1$
		}
		if (vElement == null) {
			throw new IllegalArgumentException("vElement must not be null"); //$NON-NLS-1$
		}
		if (viewContext == null) {
			throw new IllegalArgumentException("vContext must not be null"); //$NON-NLS-1$
		}
		this.vElement = vElement;
		this.viewModelContext = viewContext;
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
	}

	/**
	 * Checks whether the renderer is disposed and if so throws an {@link IllegalStateException}.
	 */
	protected void checkRenderer() {
		if (disposed) {
			throw new IllegalStateException("Renderer is disposed"); //$NON-NLS-1$
		}

	}
}
