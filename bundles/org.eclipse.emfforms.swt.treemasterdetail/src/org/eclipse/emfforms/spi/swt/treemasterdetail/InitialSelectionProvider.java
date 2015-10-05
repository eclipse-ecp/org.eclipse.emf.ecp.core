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
package org.eclipse.emfforms.spi.swt.treemasterdetail;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface to influence the initial selection set on the tree master detail.
 * 
 * @author Johannes Faltermeier
 *
 */
public interface InitialSelectionProvider {

	/**
	 * Called to set the initial selection on the tree. This may be <code>null</code> if no specific default selection
	 * should be set.
	 *
	 * @param input the input
	 * @return the object to select or <code>null</code>
	 */
	EObject getInitialSelection(Object input);

}