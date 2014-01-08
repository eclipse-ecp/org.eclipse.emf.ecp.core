/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.common;

import org.eclipse.jface.viewers.StructuredViewer;

/**
 * @author Eugen Neufeld
 * 
 * @param <T> The type of {@link StructuredViewer} to be shown in the {@link SelectionComposite}.
 * 
 */
public interface SelectionComposite<T extends StructuredViewer> extends CompositeProvider {

	/**
	 * Returns the {@link StructuredViewer}.
	 * 
	 * @return the viewer
	 */
	T getViewer();

	/**
	 * Returns the selected objects.
	 * 
	 * @return the selection
	 */
	Object[] getSelection();
}
