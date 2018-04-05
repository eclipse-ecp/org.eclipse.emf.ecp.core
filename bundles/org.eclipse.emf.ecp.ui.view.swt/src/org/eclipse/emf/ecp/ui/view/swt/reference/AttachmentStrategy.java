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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Vendor;

/**
 * A {@link org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService}
 * <em>customization strategy</em> for attachment of new objects to the model.
 *
 * @since 1.16
 *
 * @see org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService DefaultReferenceService
 */
@SuppressWarnings("restriction")
public interface AttachmentStrategy {
	/**
	 * The default strategy. It looks for the first compatible containment reference
	 * in the owner and adds the object into it, otherwise recursively tries the same
	 * with the container. If no suitable owner is found up the containment chain,
	 * this strategy adds the new object to the resource.
	 */
	AttachmentStrategy DEFAULT = new AttachmentStrategy() {
		@Override
		public boolean addElementToModel(EObject owner, EReference reference, EObject object) {
			final EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);

			for (EObject container = owner; container != null; container = container.eContainer()) {
				for (final EReference ref : container.eClass().getEAllContainments()) {
					if (ref.getEType().isInstance(object)) {
						ECPControlHelper.addModelElementInReference(container, object, ref, domain);
						return true;
					}
				}
			}

			// This must be undoable
			final Resource resource = owner.eResource();
			final Command add = new AddCommand(domain, resource.getContents(), object);
			final boolean result = add.canExecute();
			if (result) {
				domain.getCommandStack().execute(add);
			}

			return result;
		}
	};

	/**
	 * Add a new {@code object} to the model.
	 *
	 * @param owner an existing object to which the given {@code object} was added in the {@code reference}
	 * @param reference the reference of the {@code owner} to which the {@code object} was added
	 * @param object the new object to be attached to the model
	 *
	 * @return {@code true} if the {@code object} was attached to the model by this
	 *         strategy; {@code false}, otherwise
	 */
	boolean addElementToModel(EObject owner, EReference reference, EObject object);

	//
	// Nested types
	//

	/**
	 * Specific Bazaar vendor interface for attachment strategies.
	 *
	 * @since 1.16
	 */
	public interface Provider extends Vendor<AttachmentStrategy> {
		// Nothing to add to the superinterface
	}
}
