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
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal;

import org.eclipse.emf.ecore.EObject;

/**
 * An wrapper for any EObject to allow displaying a tree which shows the EObject as a root element in the tree.
 *
 * @author Anas Chakfeh
 * @since 1.5
 *
 */
public class RootObject {

	private final EObject modelElement;

	/**
	 * @param modelElement The element to be shown in the tree as a root element.
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