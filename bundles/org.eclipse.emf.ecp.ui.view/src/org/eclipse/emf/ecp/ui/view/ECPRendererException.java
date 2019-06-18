/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view;

/**
 * Common super type of exceptions, which can occur during rendering a view.
 *
 * @author Jonas
 *
 */
public class ECPRendererException extends Exception {

	private static final long serialVersionUID = -2934792422909524779L;

	/**
	 * @param string the message
	 */
	public ECPRendererException(String string) {

		super(string);
	}

}
