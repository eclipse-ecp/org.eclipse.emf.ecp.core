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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> for accumulating {@link EClass}es for instantiation to create a new
 * object in a reference.
 *
 * @since 1.16
 *
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 */
public interface EClassSelectionStrategy {
	/** An idempotent strategy (does not modify the selection). */
	EClassSelectionStrategy NULL = new EClassSelectionStrategy() {
		@Override
		public Collection<EClass> collectEClasses(EObject owner, EReference reference, Collection<EClass> eclasses) {
			return eclasses;
		}
	};

	/**
	 * Update a collection of {@link EClass}es that are eligible for creation of
	 * a new object in the reference.
	 *
	 * @param owner the proposed owner of a new reference
	 * @param reference the {@code owner}'s reference in which to add a new object
	 * @param eclasses a mutable collection of classes. Implementors may add and remove
	 *            elements in this collection as needed
	 *
	 * @return a mutable filtered collection of classes that can be passed into the
	 *         next strategy. A suggested pattern is to modify the {@code eclasses} in place
	 *         and return that collection
	 */
	Collection<EClass> collectEClasses(EObject owner, EReference reference, Collection<EClass> eclasses);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for {@code EClass} selection strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<EClassSelectionStrategy> {
		// Nothing to add to the superinterface
	}

}
