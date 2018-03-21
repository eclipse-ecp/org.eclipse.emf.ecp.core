/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Clemens Elflein - initial API and implementation
 * Martin Fleck - bug 487101
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emfforms.internal.editor.ecore.referenceservices;

import java.util.Set;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceStrategy;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Create;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of reference editing strategy for specific use cases in Ecore models.
 *
 * @since 1.16
 */
// Ranking as was for EcoreReferenceService
@Component(name = "ecoreReferenceStrategyProvider", property = "service.ranking:Integer=3")
public class EcoreReferenceStrategyProvider extends ReferenceServiceCustomizationVendor<ReferenceStrategy>
	implements ReferenceStrategy.Provider {

	/**
	 * Initializes me.
	 */
	public EcoreReferenceStrategyProvider() {
		super();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return reference == EcorePackage.Literals.EREFERENCE__EOPPOSITE;
	}

	/**
	 * Create the reference strategy.
	 *
	 * @return the reference strategy
	 */
	@Create
	public ReferenceStrategy createReferenceStrategy() {
		return new Strategy();
	}

	//
	// Nested types
	//

	/**
	 * The reference strategy.
	 */
	private static class Strategy implements ReferenceStrategy {

		/**
		 * Initializes me.
		 */
		Strategy() {
			super();
		}

		@Override
		public boolean addElementsToReference(EObject owner, EReference reference, Set<? extends EObject> objects) {
			final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);

			if (reference == EcorePackage.Literals.EREFERENCE__EOPPOSITE) {
				// If we set the opposite and the current eReference does not have any
				// type set, we can also set the type of the current eReference.

				final EReference editReference = (EReference) owner;
				final EReference selectedReference = (EReference) onlyElement(objects);

				final CompoundCommand command = new CompoundCommand();

				// Set the opposite for the other reference as well
				command.append(SetCommand.create(editingDomain, selectedReference,
					EcorePackage.Literals.EREFERENCE__EOPPOSITE, editReference));

				if (editReference.getEReferenceType() == null && selectedReference.getEContainingClass() != null) {
					command
						.append(
							SetCommand.create(editingDomain, editReference, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE,
								selectedReference.getEContainingClass()));
				}
				if (selectedReference.getEReferenceType() == null && editReference.getEContainingClass() != null) {
					command
						.append(
							SetCommand.create(editingDomain, selectedReference,
								EcorePackage.Literals.ETYPED_ELEMENT__ETYPE,
								editReference.getEContainingClass()));

				}

				command
					.append(SetCommand.create(editingDomain, editReference, EcorePackage.Literals.EREFERENCE__EOPPOSITE,
						selectedReference));

				editingDomain.getCommandStack().execute(command);

				return true;
			}

			return false;
		}
	}

	private static <T> T onlyElement(Set<T> set) {
		return set.isEmpty() ? null : set.iterator().next();
	}
}
