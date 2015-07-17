/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.context;

/**
 * A Listener which gets notified when a {@link ViewModelContext} gets disposed.
 *
 * @author Eugen Neufeld
 * @since 1.5
 */
public interface ViewModelContextDisposeListener {

	/**
	 * This gets called whenever a {@link ViewModelContext} gets disposed.
	 *
	 * @param viewModelContext The disposed {@link ViewModelContext}.
	 */
	void contextDisposed(ViewModelContext viewModelContext);
}
