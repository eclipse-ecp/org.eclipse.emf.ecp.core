/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.view;

import org.eclipse.emf.ecp.view.spi.model.VElement;

/**
 * A Listener for Context changes.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
public interface EMFFormsContextListener {

	/**
	 * Called when a child context is added.
	 *
	 * @param parentElement The {@link VElement} that is associated with the child context
	 * @param childContext The {@link EMFFormsViewContext} that was added
	 */
	void childContextAdded(VElement parentElement, EMFFormsViewContext childContext);

	/**
	 * Called when a child context is disposed.
	 *
	 * @param childContext The {@link EMFFormsViewContext} that was disposed
	 */
	void childContextDisposed(EMFFormsViewContext childContext);

	/**
	 * Called by the context when the initialization has finished.
	 */
	void contextInitialised();

	/**
	 * Called by the context when the dispose is running.
	 */
	void contextDispose();
}
