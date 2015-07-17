/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.application.e4.fx;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.controls.fx.util.DialogsUtil;
import org.eclipse.emf.ecp.edit.spi.ReferenceService;
import org.eclipse.emf.ecp.internal.edit.ECPControlHelper;
import org.eclipse.emf.ecp.util.e4.fx.ModelElementOpenerFX;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.emfstore.client.ESLocalProject;
import org.eclipse.emf.emfstore.fx.util.EmfStoreUtil;

/**
 * Implementation of a {@link ReferenceService} for the JavaFX renderer.
 *
 * @author Lucas Koehler
 */
@SuppressWarnings("restriction")
public class ReferenceServiceFX implements ReferenceService {

	private ViewModelContext context;
	private ESLocalProject emfStoreProject;

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#instantiate(org.eclipse.emf.ecp.view.spi.context.ViewModelContext)
	 */
	@Override
	public void instantiate(ViewModelContext context) {
		this.context = context;
		emfStoreProject = EmfStoreUtil.getLocalProjectforModelObject(context.getDomainModel());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#dispose()
	 */
	@Override
	public void dispose() {
		context = null;
		emfStoreProject = null;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.spi.context.ViewModelService#getPriority()
	 */
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void addModelElement(EObject eObject, EReference eReference) {
		if (eReference == null) {
			return;
		}
		if (!eReference.isContainment()) {
			emfStoreProject.getModelElements().add(eObject);
		}
		if (eReference.isContainer()) {
			// TODO: log properly
			System.err.println("ReferenceServiceFX#addModelElement: Error: " //$NON-NLS-1$
				+ "Operation not permitted for container references!"); //$NON-NLS-1$
			return;
		}
		// add model element in reference
		ECPControlHelper.addModelElementInReference(context.getDomainModel(), eObject, eReference,
			AdapterFactoryEditingDomain.getEditingDomainFor(context.getDomainModel()));

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#openInNewContext(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void openInNewContext(EObject eObject) {
		if (eObject != null) {
			ModelElementOpenerFX.openModelElement(eObject);
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#addNewModelElements(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EReference)
	 */
	@Override
	public void addNewModelElements(EObject eObject, EReference eReference) {
		// TODO: The code to get the sub classes of the reference type already exists in ECPUtil but can't be used yet
		// because a required bundle of ECPUtil's bundle isn't part of FX's target platform.
		final EClass superClass = eReference.getEReferenceType();
		final Collection<EClass> classes = new HashSet<EClass>();
		for (final String nsURI : Registry.INSTANCE.keySet()) {
			final EPackage ePackage = Registry.INSTANCE.getEPackage(nsURI);
			for (final EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					final EClass eClass = (EClass) eClassifier;
					if (superClass.isSuperTypeOf(eClass) && !eClass.isAbstract() && !eClass.isInterface()) {
						classes.add(eClass);
					}
				}
			}
		}

		final EClass eClass = DialogsUtil.selectExistingModelElement(classes);

		if (eClass != null) {
			addModelElement(EcoreUtil.create(eClass), eReference);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.edit.spi.ReferenceService#addExistingModelElements(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.emf.ecore.EReference)
	 */
	@Override
	public void addExistingModelElements(EObject eObject, EReference eReference) {
		@SuppressWarnings("unchecked")
		final Class<? extends EObject> referenceClass = (Class<? extends EObject>) eReference.getEReferenceType()
			.getInstanceClass();
		final Set<? extends EObject> modelElements = emfStoreProject.getAllModelElementsByClass(referenceClass, true);

		final EObject selectedEObject = DialogsUtil.selectExistingModelElement(modelElements);
		if (selectedEObject != null) {
			addModelElement(selectedEObject, eReference);
		}
	}

}
