/*******************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.reference;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> for accumulating existing {@link EObject}s eligible for addition to a
 * reference in an owner.
 *
 * @since 1.16
 *
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 */
public interface EObjectSelectionStrategy {
	/** An idempotent strategy (does not modify the selection). */
	EObjectSelectionStrategy NULL = new EObjectSelectionStrategy() {
		@Override
		public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
			Collection<EObject> existingObjects) {
			return existingObjects;
		}
	};

	/**
	 * Update a collection of {@link EObject}s that are eligible for addition to
	 * a reference feature.
	 *
	 * @param owner the proposed owner of the references to existing objects
	 * @param reference the {@code owner}'s reference in which to add a objects
	 * @param existingObjects a mutable collection of eligible objects. Implementors may add and
	 *            remove elements in this collection as needed
	 *
	 * @return a mutable filtered collection of objects that can be passed into the
	 *         next strategy. A suggested pattern is to modify the {@code existingObjects} in place
	 *         and return that collection
	 */
	Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
		Collection<EObject> existingObjects);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for {@code EObject} selection strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<EObjectSelectionStrategy> {
		// Nothing to add to the superinterface
	}

}
