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

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> for adding an object to a reference of another.
 *
 * @since 1.16
 *
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 */
@SuppressWarnings("restriction")
public interface ReferenceStrategy {
	/**
	 * The default strategy. Just executes a simple command to add the reference.
	 */
	ReferenceStrategy DEFAULT = new ReferenceStrategy() {
		@Override
		public boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects) {
			final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);

			// The ECPControlHelper generic signature is wrong. It should be an upper bound,
			// but because this utility never actually adds to the set, this cast is safe
			@SuppressWarnings("unchecked")
			final Set<EObject> objectsToAdd = (Set<EObject>) objects;
			ECPControlHelper.addModelElementsInReference(owner, objectsToAdd, reference, domain);

			return true;
		}
	};

	/**
	 * Add a set of new {@code objects} to a {@code reference}.
	 *
	 * @param owner an existing object to which the given {@code objects} are to be added in the {@code reference}
	 * @param reference the reference of the {@code owner} to which the {@code objects} are to be added
	 * @param objects the new objects to be added to the {@code reference} of the {@code owner}
	 *
	 * @return {@code true} if the {@code objects} were added to the {@code reference} by this
	 *         strategy; {@code false}, otherwise
	 */
	boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for reference strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<ReferenceStrategy> {
		// Nothing to add to the superinterface
	}
}
