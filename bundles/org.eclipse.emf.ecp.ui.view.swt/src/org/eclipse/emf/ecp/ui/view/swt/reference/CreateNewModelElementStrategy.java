/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.swt.reference;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfforms.bazaar.Vendor;
import org.eclipse.emfforms.common.Optional;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> to add one or more new elements to a reference of an owner.
 *
 * @author Lucas Koehler
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 * @since 1.17
 */
public interface CreateNewModelElementStrategy {

	/**
	 * Default strategy that creates a new model element based on the sub classes of the reference type. If
	 * there is more than one, a selection dialog is shown.
	 */
	CreateNewModelElementStrategy DEFAULT = new CreateNewModelElementStrategy() {

		@Override
		public Optional<EObject> createNewModelElement(EObject owner, EReference reference) {
			final EClass referenceType = reference.getEReferenceType();
			if (referenceType.isAbstract()) {
				return Optional.empty();
			}
			return Optional.of(EcoreUtil.create(referenceType));
		}
	};

	/**
	 * Create a new model element in the reference of the given owner.
	 *
	 * @param owner The {@link EObject} that contains the reference
	 * @param reference The reference for which a new model element should be created
	 * @return The created model element or <code>null</code> if none was created
	 */
	Optional<EObject> createNewModelElement(EObject owner, EReference reference);

	/**
	 * Specific Bazaar vendor interface for add new model elements strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<CreateNewModelElementStrategy> {
		// Nothing to add to the super interface
	}

}
