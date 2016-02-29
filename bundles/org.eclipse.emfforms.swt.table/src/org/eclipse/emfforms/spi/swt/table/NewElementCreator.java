/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.table;

/**
 *
 * Used to create new elements.
 *
 * @author Johannes Faltermeier
 *
 * @param <T> the type of the newly created element
 * @param <C> the type of the context
 */
public interface NewElementCreator<T, C> {

	/**
	 * Creates a new element.
	 *
	 * @param context a element indicating the context
	 * @return the new element
	 */
	T createNewElement(C context);
}
