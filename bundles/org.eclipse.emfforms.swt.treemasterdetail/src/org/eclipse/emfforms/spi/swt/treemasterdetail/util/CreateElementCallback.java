/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.swt.treemasterdetail.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * This interface allows intercepting the TreeMasterDetailSWTRenderer's create function.
 * The callback can modify the newly created element as well as cancel the action.
 */
public interface CreateElementCallback {

	/**
	 * Gets called <b>before</b> the new object has been added to containment tree. Users may make changes to the
	 * newObject inside this method only. Changing the passed parent or reachable classes in any way might cause
	 * unwanted side effects. The future parent is passed to this method for read only reasons in order to perform all
	 * necessary init tasks.
	 *
	 * @param parent the future parent
	 * @param reference the parent reference
	 * @param newObject the newly created EObject, which should be initialized.
	 * @since 1.9
	 */
	void initElement(EObject parent, EReference reference, EObject newObject);

	/**
	 * Gets called after the new Element has been added to the containment tree. This method may be used to allow the
	 * user to modify the newly created element or to allow the user canceling the add.
	 *
	 * @param newElement The new element
	 * @return <code>true</code> if the element should be kept in the containment tree, <code>false</code> if it should
	 *         be removed again
	 */
	boolean beforeCreateElement(Object newElement);

	/**
	 * Gets called after a new element was added to the domain model.
	 * 
	 * @param newElement the new Element.
	 * @since 1.9
	 */
	void afterCreateElement(Object newElement);
}
