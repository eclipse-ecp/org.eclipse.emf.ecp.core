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
package org.eclipse.emf.ecp.edit.spi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.context.ViewModelService;

/**
 * @author Eugen Neufeld
 * @since 1.2
 * 
 */
public interface ReferenceService extends ViewModelService {

	/**
	 * Adds a model element to the {@link EReference}.
	 * 
	 * @param eObject the {@link EObject} to add
	 * @param eReference the {@link EReference} to add the {@link EObject} to
	 */
	void addModelElement(EObject eObject, EReference eReference);

	/**
	 * Returns a new {@link EObject} that is suitable for the passed {@link EReference}.
	 * 
	 * @param eReference
	 *            the reference to create the element for
	 * @return the created EObject, this EObject is not contained in the tree
	 */
	EObject getNewElementFor(EReference eReference);

	/**
	 * Returns an existing {@link EObject} that is suitable for the passed {@link EReference}.
	 * 
	 * @param eReference the {@link EReference} to find an existing Element for
	 * @return an existing {@link EObject} that can be used in this {@link EReference}, the returned EObject must
	 *         already be in the containment tree
	 */
	EObject getExistingElementFor(EReference eReference);

	/**
	 * Opens an {@link EObject} in a new context.
	 * 
	 * @param eObject the {@link EObject} to open in a new context
	 */
	void openInNewContext(EObject eObject);
}
