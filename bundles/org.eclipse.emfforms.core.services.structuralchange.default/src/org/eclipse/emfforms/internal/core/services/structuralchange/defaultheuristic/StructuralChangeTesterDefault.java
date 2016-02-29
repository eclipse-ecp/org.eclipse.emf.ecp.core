/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange.defaultheuristic;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.service.component.annotations.Component;

/**
 * Default Heuristic for testing structural changes to {@link VDomainModelReference VDomainModelReferences}.
 *
 * @author Lucas Köhler
 *
 */
@Component(name = "StructuralChangeTesterDefault")
public class StructuralChangeTesterDefault implements StructuralChangeTesterInternal {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference reference) {
		Assert.create(reference).notNull();
		if (VFeaturePathDomainModelReference.class.isInstance(reference)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isStructureChanged(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification)
	 */
	@Override
	public boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification) {
		Assert.create(reference).notNull();
		Assert.create(notification).notNull();
		Assert.create(reference).ofClass(VFeaturePathDomainModelReference.class);

		final VFeaturePathDomainModelReference featurePathDMR = (VFeaturePathDomainModelReference) reference;

		if (notification.getRawNotification().isTouch()) {
			return false;
		}
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return false;
		}
		boolean relevantChange = false;

		EObject lastResolvedEObject = domainRootObject;
		for (final EReference eReference : featurePathDMR.getDomainModelEReferencePath()) {
			relevantChange |= eReference.equals(notification.getStructuralFeature())
				&& lastResolvedEObject == notification.getNotifier();
			if (relevantChange) {
				return true;
			}
			lastResolvedEObject = (EObject) lastResolvedEObject.eGet(eReference);
		}
		relevantChange |= notification.getStructuralFeature().equals(featurePathDMR.getDomainModelEFeature())
			&& lastResolvedEObject == notification.getNotifier();

		return relevantChange;
	}

}
