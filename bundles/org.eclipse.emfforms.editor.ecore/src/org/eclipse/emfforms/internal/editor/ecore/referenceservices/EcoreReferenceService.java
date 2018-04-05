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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.editor.helpers.ResourceSetHelpers;

/**
 * The ReferenceService provides all widgets with Ecore specific references.
 *
 * @deprecated As of 1.16, the responsibilities of this class are subsumed into the
 *             {@link EcoreReferenceStrategyProvider} and related classes.
 *
 * @see EcoreAttachmentStrategyProvider
 * @see EcoreEObjectSelectionStrategyProvider
 * @see EcoreOpenInNewContextStrategyProvider
 * @see EcoreReferenceStrategyProvider
 */
@Deprecated
@SuppressWarnings("restriction")
public class EcoreReferenceService extends DefaultReferenceService {

	private ViewModelContext context;
	private EditingDomain editingDomain;

	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(context.getDomainModel());
		super.instantiate(context);
	}

	private EObject getExistingSuperTypeFor(EReference eReference) {
		final EClass domainClass = EClass.class.cast(context.getDomainModel());

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

		return select(
			classes,
			"Select SuperType",
			"Select a SuperType to add to "
				+ ((ENamedElement) context.getDomainModel()).getName());
	}

	private EObject getExistingDataTypeFor(EReference eReference) {
		final List<EDataType> dataTypes = ResourceSetHelpers
			.findAllOfTypeInResourceSet(context.getDomainModel(),
				EDataType.class, true);
		return select(dataTypes, "Select Datatype", "Select the Datatype for "
			+ ((ENamedElement) context.getDomainModel()).getName());
	}

	private EObject getExistingEAnnotationEReferencesFor(EReference eReference) {
		final List<ENamedElement> namedElements = ResourceSetHelpers
			.findAllOfTypeInResourceSet(context.getDomainModel(),
				ENamedElement.class, true);
		return select(namedElements, "Select Reference", "Select Reference to add");
	}

	// Let the user select an item from a List using a dialog
	private EObject select(List<? extends EObject> elements, String title, String message) {
		final Set<EObject> selectedEObjects = SelectModelElementWizardFactory
			.openModelElementSelectionDialog(new LinkedHashSet<EObject>(elements), false);
		if (selectedEObjects.isEmpty()) {
			return null;
		}
		return selectedEObjects.iterator().next();
	}

	private EObject getExistingElementFor(EReference eReference) {
		// Check, if the target is EDataType
		if (context.getDomainModel() instanceof EAttribute
			&& eReference.getEReferenceType() != null) {
			return getExistingDataTypeFor(eReference);
		}
		if (eReference.equals(EcorePackage.eINSTANCE.getEClass_ESuperTypes())) {
			return getExistingSuperTypeFor(eReference);
		}
		if (eReference.equals(EcorePackage.eINSTANCE.getEReference_EOpposite())) {
			return getExistingOppositeFor(eReference);
		}
		if (eReference.equals(EcorePackage.eINSTANCE.getEAnnotation_References())) {
			return getExistingEAnnotationEReferencesFor(eReference);
		}
		return getExistingGenericType(eReference);
	}

	private EObject getExistingOppositeFor(EReference eReference) {
		final EReference editReference = (EReference) context.getDomainModel();

		final List<EReference> allReferences = ResourceSetHelpers
			.findAllOfTypeInResourceSet(context.getDomainModel(),
				EReference.class, false);

		// Remove the DomainModel from the List, as it can't be its own opposite
		allReferences.remove(context.getDomainModel());

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

		return select(allReferences, "Select EOpposite",
			"Select the opposite EReference");
	}

	@SuppressWarnings("unchecked")
	private EObject getExistingGenericType(EReference eReference) {
		final List<EObject> classes = (List<EObject>) ResourceSetHelpers
			.findAllOfTypeInResourceSet(context.getDomainModel(),
				eReference.getEReferenceType(), false);

		return select(classes, "Select " + eReference.getName(), "Select a "
			+ eReference.getEType().getName());

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public int getPriority() {
		return 3;
	}

	private void addModelElement(EObject eObject, EReference eReference) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(context.getDomainModel());
		// eObject.eSet(EcorePackage.eINSTANCE.getEAttribute_EAttributeType(),
		// eReference);

		// If we set the opposite and the current eReference does not have any
		// type set,
		// we can also set the type of the current eReference.

		if (EcorePackage.eINSTANCE.getEReference_EOpposite().equals(eReference)) {

			final EReference editReference = (EReference) context.getDomainModel();
			final EReference selectedReference = (EReference) eObject;
			// Set the opposite for the other reference as well
			editingDomain.getCommandStack().execute(
				SetCommand.create(AdapterFactoryEditingDomain.getEditingDomainFor(selectedReference),
					selectedReference, EcorePackage.Literals.EREFERENCE__EOPPOSITE, editReference));

			if (editReference.getEReferenceType() == null) {
				editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain, editReference, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE,
						selectedReference.getEContainingClass()));
			}
			editingDomain.getCommandStack().execute(
				SetCommand.create(editingDomain, editReference, EcorePackage.Literals.EREFERENCE__EOPPOSITE, eObject));

			return;
		}

		ECPControlHelper.addModelElementInReference(context.getDomainModel(), eObject, eReference, editingDomain);
	}

	@Override
	public void openInNewContext(EObject eObject) {
		// no op. stay inside editor
	}

	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		final EObject selectedElement = getExistingElementFor(eReference);
		if (selectedElement != null) {
			addModelElement(selectedElement, eReference);
		}
	}

	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		addNewModelElements(eObject, eReference, true);
	}

	@Override
	public Optional<EObject> addNewModelElements(EObject eObject, EReference eReference, boolean openInNewContext) {
		if (eReference == EcorePackage.eINSTANCE.getEReference_EOpposite()) {
			handleEOpposite(eObject, eReference);
			return Optional.empty();
		}
		return super.addNewModelElements(eObject, eReference, openInNewContext);
	}

	private void handleEOpposite(EObject eObject, EReference eReference) {
		/* get the container for the existing reference. this will be the type for the newly created reference */
		final EReference existingReference = EReference.class.cast(eObject);
		final EClass existingType = (EClass) existingReference.eContainer();

		/* create the new reference */
		final EReference newReference = EcoreFactory.eINSTANCE.createEReference();
		newReference.setName("");
		newReference.setEType(existingType);
		newReference.setEOpposite(existingReference);

		/* the reference type will contain the new reference */
		final EClass containerType = existingReference.getEReferenceType();

		/* add new reference to model */
		ECPControlHelper.addModelElementInReference(containerType, newReference,
			EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), editingDomain);

		/* set eopposite */
		ECPControlHelper.addModelElementInReference(eObject, newReference,
			eReference, editingDomain);
	}
}
