/*******************************************************************************
 * Copyright (c) 2011-2012 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui;

import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author Eugen Neufeld
 *
 */
public abstract class ECPViewerFilter extends ViewerFilter {

	private String searchTerm;

	/**
	 * Sets the search term for this name filter.
	 *
	 * @param searchTerm
	 *            the search term
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	/**
	 *
	 * @return he search term for this name filter.
	 */
	protected String getSearchTerm() {
		return searchTerm;
	}

}
