/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.util;

import org.eclipse.emf.ecore.EObject;

/**
 * This is needed in order to show the root element in the tree.
 *
 * @author Anas Chakfeh
 *
 */
public class RootObject {

	private final EObject modelElement;

	/**
	 * Default constructor.
	 * 
	 * @param modelElement the wrapped root object
	 */
	public RootObject(EObject modelElement) {
		this.modelElement = modelElement;
	}

	/**
	 * @return the root object
	 */
	public EObject getRoot() {
		return modelElement;
	}

}