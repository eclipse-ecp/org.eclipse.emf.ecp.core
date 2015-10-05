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
package org.eclipse.emfforms.internal.core.services.domainexpander.index;

import java.util.List;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsDMRExpander} for {@link VIndexDomainModelReference VIndexDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsIndexDMRExpander")
public class EMFFormsIndexDMRExpander implements EMFFormsDMRExpander {

	private ReportService reportService;
	private EMFFormsDomainExpander domainExpander;
	private EMFFormsDatabinding databindingService;

	/**
	 * Called by the framework to set the {@link ReportService}.
	 *
	 * @param reportService The {@link ReportService}
	 */
	@Reference
	protected void setReportService(ReportService reportService) {
		this.reportService = reportService;

	}

	/**
	 * Called by the framework to set the {@link EMFFormsDomainExpander}.
	 *
	 * @param emfFormsDomainExpander The {@link EMFFormsDomainExpander}
	 */
	@Reference
	protected void setEMFFormsDomainExpander(EMFFormsDomainExpander emfFormsDomainExpander) {
		domainExpander = emfFormsDomainExpander;
	}

	/**
	 * Called by the framework to set the {@link EMFFormsDatabinding}.
	 *
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 */
	@Reference
	protected void setEMFFormsDatabinding(EMFFormsDatabinding emfFormsDatabinding) {
		databindingService = emfFormsDatabinding;
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
		Assert.create(domainModelReference).ofClass(VIndexDomainModelReference.class);

		final VIndexDomainModelReference indexReference = VIndexDomainModelReference.class.cast(domainModelReference);
		final VDomainModelReference targetDMR = indexReference.getTargetDMR();

		VDomainModelReference prefixDMR;
		if (indexReference.getPrefixDMR() != null) {
			prefixDMR = indexReference.getPrefixDMR();
		} else {
			if (indexReference.getDomainModelEFeature() == null) {
				throw new EMFFormsExpandingFailedException(
					"The index domain model reference's domain model e feature must not be null when the reference's prefix dmr is null."); //$NON-NLS-1$
			}
			final VFeaturePathDomainModelReference prefixFeatureDMR = VViewFactory.eINSTANCE
				.createFeaturePathDomainModelReference();
			prefixFeatureDMR.setDomainModelEFeature(indexReference.getDomainModelEFeature());
			prefixFeatureDMR.getDomainModelEReferencePath().addAll(indexReference.getDomainModelEReferencePath());
			prefixDMR = prefixFeatureDMR;
		}

		IValueProperty valueProperty;
		try {
			valueProperty = databindingService.getValueProperty(prefixDMR, domainObject);
		} catch (final DatabindingFailedException ex) {
			throw new EMFFormsExpandingFailedException(
				"Domain Expansion failed due to a failed databinding: " + ex.getMessage()); //$NON-NLS-1$
		}

		final EStructuralFeature listEStructuralFeature = (EStructuralFeature) valueProperty.getValueType();

		checkListType(listEStructuralFeature);
		domainExpander.prepareDomainObject(prefixDMR, domainObject);

		// type of the list items
		final EClass featureEClass = EClass.class.cast(listEStructuralFeature.getEType());

		// need unchecked type cast because IValueProperty#getValue always returns an Object.
		@SuppressWarnings("unchecked")
		final List<EObject> list = (List<EObject>) valueProperty.getValue(domainObject);
		EObject targetDomainObject;
		// fill up all indices of the list with new instances up to the index defined by the index dmr.
		if (!featureEClass.isAbstract() && !featureEClass.isInterface()) {
			for (int i = 0; i <= indexReference.getIndex(); i++) {
				if (list.size() <= i) {
					list.add(EcoreUtil.create(featureEClass));
				} else if (list.get(i) == null) {
					list.set(i, EcoreUtil.create(featureEClass));
				}
			}
			targetDomainObject = list.get(indexReference.getIndex());
		} else {
			throw new EMFFormsExpandingFailedException(
				"Could not expand the VIndexDomainModelReference because the type of the prefix DMR's domain model e feature is either abstract or an interface. Hence, no new instances of this type can be created."); //$NON-NLS-1$
		}

		domainExpander.prepareDomainObject(targetDMR, targetDomainObject);
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
		if (VIndexDomainModelReference.class.isInstance(domainModelReference)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * Checks whether the given structural feature references a proper list.
	 *
	 * @param structuralFeature The feature to check
	 * @throws EMFFormsExpandingFailedException if the structural feature doesn't reference a proper list.
	 */
	private void checkListType(EStructuralFeature structuralFeature) throws EMFFormsExpandingFailedException {
		if (!structuralFeature.isMany()) {
			throw new EMFFormsExpandingFailedException(
				"The VIndexDomainModelReference's domainModelEFeature must reference a list."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(structuralFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The VIndexDomainModelReference's domainModelEFeature must reference a list of EObjects."); //$NON-NLS-1$
		}
	}
}
