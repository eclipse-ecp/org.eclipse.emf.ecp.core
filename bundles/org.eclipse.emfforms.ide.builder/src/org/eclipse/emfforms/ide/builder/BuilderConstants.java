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
package org.eclipse.emfforms.ide.builder;

/**
 * Constants, especially for names of injectable values in bazaar vendors, the
 * {@linkplain ValidationDelegateProvider validation-delegate} and
 * {@linkplain MarkerHelperProvider marker-helper} providers.
 */
public final class BuilderConstants {

	/**
	 * Name of the content-type ID of the file in the injection context (a string value).
	 */
	public static final String CONTENT_TYPE = "content-type"; //$NON-NLS-1$

	/**
	 * Not instantiable by clients.
	 */
	private BuilderConstants() {
		super();
	}

}
