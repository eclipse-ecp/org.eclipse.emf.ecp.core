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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.ui.view.swt.reference.AttachmentStrategy;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.bazaar.Create;
import org.osgi.service.component.annotations.Component;

/**
 * Provider of object attachment strategy for specific use cases in Ecore models, such as
 * creation of opposite references in the context of a reference.
 *
 * @since 1.16
 */
@SuppressWarnings("restriction")
// Ranking as was for EcoreReferenceService
@Component(name = "ecoreAttachmentStrategyProvider", property = "service.ranking:Integer=3")
public class EcoreAttachmentStrategyProvider extends ReferenceServiceCustomizationVendor<AttachmentStrategy>
	implements AttachmentStrategy.Provider {

	/**
	 * Initializes me.
	 */
	public EcoreAttachmentStrategyProvider() {
		super();
	}

	@Override
	protected boolean handles(EObject owner, EReference reference) {
		return owner instanceof EModelElement;
	}

	/**
	 * Create the attachment strategy.
	 *
	 * @return the attachment strategy
	 */
	@Create
	public AttachmentStrategy createAttachmentStrategy() {
		return new AttachmentStrategy() {
			@Override
			public boolean addElementToModel(EObject owner, EReference reference, EObject object) {
				final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(owner);

				/*
				 * get the container for the existing reference. this will be the type for the newly created reference
				 */
				final EReference existingReference = (EReference) owner;
				final EClass existingType = (EClass) existingReference.eContainer();

				/* complete the new reference */
				final EReference newReference = (EReference) object;
				newReference.setName("");
				newReference.setEType(existingType);
				newReference.setEOpposite(existingReference);

				/* the reference type will contain the new reference */
				final EClass containerType = existingReference.getEReferenceType();

				/* add new reference to model */
				ECPControlHelper.addModelElementInReference(containerType, newReference,
					EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), editingDomain);

				return true;
			}
		};
	}
}
