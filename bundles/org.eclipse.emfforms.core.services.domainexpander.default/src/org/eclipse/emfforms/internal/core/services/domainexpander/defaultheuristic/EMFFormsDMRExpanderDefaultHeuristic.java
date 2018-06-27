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
package org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Default implementation of {@link EMFFormsDMRExpander}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDomainExpanderDefaultHeuristic")
public class EMFFormsDMRExpanderDefaultHeuristic implements EMFFormsDMRExpander {

	private ReportService reportService;

	/**
	 * Called by the framework to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference(unbind = "-")
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void prepareDomainObject(VDomainModelReference domainModelReference, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(domainModelReference).notNull();
		Assert.create(domainObject).notNull();
		Assert.create(domainModelReference).ofClass(VFeaturePathDomainModelReference.class);

		final VFeaturePathDomainModelReference featurePathDMR = VFeaturePathDomainModelReference.class
			.cast(domainModelReference);
		if (featurePathDMR.getDomainModelEFeature() == null) {
			throw new EMFFormsExpandingFailedException(
				"The domain model feature of the given feature path DMR must not be null."); //$NON-NLS-1$
		}
		EObject currentResolvedEObject = domainObject;
		final ArrayList<EReference> currentLeftReferences = new ArrayList<EReference>(
			featurePathDMR.getDomainModelEReferencePath());
		for (final EReference eReference : featurePathDMR.getDomainModelEReferencePath()) {
			if (!currentResolvedEObject.eClass().getEAllReferences().contains(eReference)) {
				break;
			}
			if (eReference.isMany()) {
				break;
			}
			if (!eReference.getEContainingClass().isInstance(currentResolvedEObject)) {
				throw new EMFFormsExpandingFailedException(
					"The containing class of a reference does not match the actual object."); //$NON-NLS-1$
			}
			EObject child = (EObject) currentResolvedEObject.eGet(eReference);
			if (child == null) {
				child = createMissingPathElementIfPossible(featurePathDMR, currentResolvedEObject,
					currentLeftReferences, eReference, child);
			}
			if (child == null) {
				break;
			}
			currentResolvedEObject = child;
			currentLeftReferences.remove(eReference);
		}
	}

	/**
	 * @return the newly created object. may be <code>null</code> if creation was not possible
	 */
	private EObject createMissingPathElementIfPossible(final VFeaturePathDomainModelReference featurePathDMR,
		EObject currentResolvedEObject, final ArrayList<EReference> currentLeftReferences, final EReference eReference,
		EObject child) {
		if (!eReference.getEReferenceType().isAbstract() && !eReference.getEReferenceType().isInterface()) {
			child = EcoreUtil.create(eReference.getEReferenceType());
		} else if (currentLeftReferences.size() == 1
			&& !featurePathDMR.getDomainModelEFeature().getEContainingClass().isAbstract()
			&& !featurePathDMR.getDomainModelEFeature().getEContainingClass().isInterface()) {
			child = EcoreUtil.create(featurePathDMR.getDomainModelEFeature().getEContainingClass());
		}
		if (child != null) {
			/*
			 * only set the reference if we could create a child. otherwise we could end up in a infinite loop,
			 * because a null-to-null set produces a non-touch notification. This might trigger resolve again.
			 */
			currentResolvedEObject.eSet(eReference, child);
		}
		return child;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (domainModelReference == null) {
			reportService.report(new AbstractReport("Warning: The given domain model reference was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (VFeaturePathDomainModelReference.class.isInstance(domainModelReference)) {
			return 1d;
		}
		return NOT_APPLICABLE;
	}
}
