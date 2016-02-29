/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Stefan Dirix - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.emf2web.exporter;

/**
 * A wrapper for the pure generated schemas.
 *
 * @author Stefan Dirix
 *
 */
public interface SchemaWrapper {
	/**
	 * Wraps the given string depending on the given type.
	 *
	 * @param toWrap
	 *            The content which shall be wrapped.
	 * @param type
	 *            The type which depicts how the wrap should look like.
	 * @return
	 * 		The wrapped {@code toWrap}.
	 */
	String wrap(String toWrap, String type);

	/**
	 * Depicts a proposal for the name of the wrapped content.
	 *
	 * @return
	 * 		A name for the wrapped content.
	 */
	String getName();

	/**
	 * Depicts a proposal for an file extension for the wrapped content.
	 *
	 * @return
	 * 		A file extension for the wrapped content.
	 */
	String getFileExtension();
}
