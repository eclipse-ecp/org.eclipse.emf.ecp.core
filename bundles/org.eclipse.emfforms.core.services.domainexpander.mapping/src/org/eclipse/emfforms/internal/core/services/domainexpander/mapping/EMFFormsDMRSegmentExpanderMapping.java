/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.mapping;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsDMRSegmentExpander} for {@link VMappingDomainModelReferenceSegment
 * VMappingDomainModelReferenceSegments}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsDMRSegmentExpanderMapping")
public class EMFFormsDMRSegmentExpanderMapping implements EMFFormsDMRSegmentExpander {

	/**
	 * The name of the structural feature that describes the values of a map.
	 */
	private static final String VALUE = "value"; //$NON-NLS-1$
	/**
	 * The name of the structural feature that describes the keys of a map.
	 */
	private static final String KEY = "key"; //$NON-NLS-1$
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
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)
	 */
	@Override
	public double isApplicable(VDomainModelReferenceSegment segment) {
		if (segment == null) {
			reportService.report(new AbstractReport("Warning: The given domain model reference segment was null.")); //$NON-NLS-1$
			return NOT_APPLICABLE;
		}
		if (VMappingDomainModelReferenceSegment.class.isInstance(segment)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander#prepareDomainObject(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EObject prepareDomainObject(VDomainModelReferenceSegment segment, EObject domainObject)
		throws EMFFormsExpandingFailedException {
		Assert.create(segment).notNull().ofClass(VMappingDomainModelReferenceSegment.class);
		Assert.create(domainObject).notNull();

		final VMappingDomainModelReferenceSegment mappingSegment = (VMappingDomainModelReferenceSegment) segment;
		final EStructuralFeature structuralFeature = domainObject.eClass()
			.getEStructuralFeature(mappingSegment.getDomainModelFeature());

		if (structuralFeature == null) {
			throw new EMFFormsExpandingFailedException(
				String.format("The given domain object does not contain the segment's feature. " //$NON-NLS-1$
					+ "The segment was %1$s. The domain object was %2$s.", segment, domainObject)); //$NON-NLS-1$
		}

		checkMapType(structuralFeature);

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EReference valueReference = (EReference) eClass.getEStructuralFeature(VALUE);

		final EMap<EClass, EObject> map = (EMap<EClass, EObject>) domainObject.eGet(structuralFeature);
		final EClass mappedClass = mappingSegment.getMappedClass();
		if (map.get(mappedClass) == null) {
			checkReferenceInstantiable(mappingSegment, valueReference);
			final EObject newElement = EcoreUtil.create(valueReference.getEReferenceType());
			map.put(mappedClass, newElement);
		}

		return map.get(mappedClass);
	}

	/**
	 * Checks that the type of the given {@link EReference} is instantiable.
	 *
	 * @param segment The source segment (only for the exception message)
	 * @param reference The {@link EReference} to check
	 * @throws EMFFormsExpandingFailedException If the given {@link EReference} is not instantiable
	 */
	private void checkReferenceInstantiable(VDomainModelReferenceSegment segment, final EReference reference)
		throws EMFFormsExpandingFailedException {
		if (reference.getEReferenceType().isAbstract() || reference.getEReferenceType().isInterface()) {
			throw new EMFFormsExpandingFailedException(String.format(
				"The reference type of the segment's map's value feature is either abstract or an interface. " //$NON-NLS-1$
					+ "Therefore, no instance can be created. The segment was %1$s.", //$NON-NLS-1$
				segment));
		}
	}

	/**
	 * Checks whether the given structural feature references a proper map.
	 *
	 * @param structuralFeature The feature to check
	 * @throws EMFFormsExpandingFailedException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws EMFFormsExpandingFailedException {
		checkStructuralFeature(structuralFeature);

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature(KEY);
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature(VALUE);
		if (keyFeature == null || valueFeature == null) {
			throw new EMFFormsExpandingFailedException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The keys of the map referenced by the segment's structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(valueFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The values of the map referenced by the segment's structural feature must be referenced EObjects."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new EMFFormsExpandingFailedException(
				"The keys of the map referenced by the segment's structural feature must be referenced EClasses."); //$NON-NLS-1$
		}
	}

	/**
	 * Checks basic required properties of the given {@link EStructuralFeature}.
	 *
	 * @param structuralFeature The {@link EStructuralFeature} to check
	 * @throws EMFFormsExpandingFailedException if something's wrong with the feature
	 */
	private void checkStructuralFeature(EStructuralFeature structuralFeature) throws EMFFormsExpandingFailedException {
		if (structuralFeature.getEType() == null) {
			throw new EMFFormsExpandingFailedException(
				"The EType of the segment's structural feature was null."); //$NON-NLS-1$
		}
		if (structuralFeature.getEType().getInstanceClassName() == null) {
			throw new EMFFormsExpandingFailedException(
				"The InstanceClassName of the segment's structural feature's EType was null."); //$NON-NLS-1$
		}
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new EMFFormsExpandingFailedException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new EMFFormsExpandingFailedException(
				"The segment's structural feature must reference a map."); //$NON-NLS-1$
		}
	}
}
