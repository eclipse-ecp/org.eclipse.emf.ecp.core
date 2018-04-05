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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.ui.view.swt.reference.EObjectSelectionStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emfforms.bazaar.Create;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of existing object selection strategy for specific use cases in Ecore models,
 * such as selection of eligible references to be opposites of a reference.
 *
 * @since 1.16
 */
// Ranking as was for EcoreReferenceService
@Component(name = "ecoreEObjectSelectionStrategyProvider", property = "service.ranking:Integer=3")
public class EcoreEObjectSelectionStrategyProvider extends ReferenceServiceCustomizationVendor<EObjectSelectionStrategy>
	implements EObjectSelectionStrategy.Provider {

	/**
	 * Initializes me.
	 */
	public EcoreEObjectSelectionStrategyProvider() {
		super();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return owner instanceof EModelElement;
	}

	/**
	 * Create the selection strategy.
	 *
	 * @return the selection strategy
	 */
	@Create
	public EObjectSelectionStrategy createEObjectSelectionStrategy() {
		return new Strategy();
	}

	//
	// Nested types
	//

	/**
	 * The selection strategy.
	 */
	private static class Strategy implements EObjectSelectionStrategy {
		/**
		 * Initializes me.
		 */
		Strategy() {
			super();
		}

		@Override
		public Collection<EObject> collectExistingObjects(EObject owner, EReference reference,
			Collection<EObject> existingObjects) {
			final Collection<? extends EObject> available = getExistingElementsFor(owner, reference);

			if (available != null) {
				// In the default case, don't constrain the result
				existingObjects.retainAll(available);
			}

			return existingObjects;
		}

		private Collection<? extends EObject> getExistingElementsFor(EObject owner, EReference eReference) {
			// Check, if the target is EDataType
			if (owner instanceof EAttribute && eReference.equals(EcorePackage.Literals.ETYPED_ELEMENT__ETYPE)) {
				return getExistingDataTypesFor((EAttribute) owner, eReference);
			}
			if (eReference.equals(EcorePackage.Literals.ECLASS__ESUPER_TYPES)) {
				return getExistingSuperTypesFor((EClass) owner, eReference);
			}
			if (eReference.equals(EcorePackage.Literals.EREFERENCE__EOPPOSITE)) {
				return getExistingOppositesFor((EReference) owner, eReference);
			}
			if (eReference.equals(EcorePackage.Literals.EANNOTATION__REFERENCES)) {
				return getExistingEAnnotationEReferencesFor((EAnnotation) owner, eReference);
			}

			return null;
		}

		private Collection<EClass> getExistingSuperTypesFor(EClass domainClass, EReference eReference) {
			final List<EClass> classes = ResourceSetHelpers.findAllOfTypeInResourceSet(
				domainClass, EClass.class, false);

			// Subtract already present SuperTypes from the List
			// The cast is fine, as we know that the eReference must be manyValued.
			classes.removeAll((List<?>) domainClass.eGet(eReference));

			// Subtract domain model from the List to avoid self-inheritance
			classes.remove(domainClass);

			// Subtract sub-types from List to avoid circular inheritance
			final List<EClass> subTypes = new ArrayList<EClass>();
			for (final EClass eClass : classes) {
				if (domainClass.isSuperTypeOf(eClass)) {
					subTypes.add(eClass);
				}
			}
			classes.removeAll(subTypes);

			return classes;
		}

		private Collection<EDataType> getExistingDataTypesFor(EAttribute owner, EReference eReference) {
			final List<EDataType> dataTypes = ResourceSetHelpers
				.findAllOfTypeInResourceSet(owner, EDataType.class, true);
			return dataTypes;
		}

		private Collection<ENamedElement> getExistingEAnnotationEReferencesFor(EAnnotation annotation,
			EReference eReference) {
			final List<ENamedElement> namedElements = ResourceSetHelpers
				.findAllOfTypeInResourceSet(annotation, ENamedElement.class, true);
			return namedElements;
		}

		private Collection<EReference> getExistingOppositesFor(EReference editReference, EReference eReference) {
			final List<EReference> allReferences = ResourceSetHelpers
				.findAllOfTypeInResourceSet(editReference, EReference.class, false);

			// Remove the DomainModel from the List, as it can't be its own opposite
			allReferences.remove(editReference);

			// Remove all references which do not reference our target type
			// If the reference type is null, allow all references and set the type
			// on selection later on.
			if (editReference.getEReferenceType() != null) {
				final Iterator<EReference> iterator = allReferences.iterator();
				while (iterator.hasNext()) {
					final EReference ref = iterator.next();
					if (!editReference.getEReferenceType().equals(
						ref.getEContainingClass())) {
						iterator.remove();
					}
				}
			}

			return allReferences;
		}
	}

}
