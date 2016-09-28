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
package org.eclipse.emfforms.internal.core.services.domainexpander.mapping;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsDMRExpander} for expanding {@link VMappingDomainModelReference VMappingDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsMappingDMRExpander")
public class EMFFormsMappingDMRExpander implements EMFFormsDMRExpander {

	private ReportService reportService;
	private EMFFormsDomainExpander domainExpander;
	private BundleContext bundleContext;
	private ServiceReference<EMFFormsDomainExpander> eMFFormsDomainExpanderServiceReference;

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
	 * Called by the framework when the component gets activated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * Called by the framework when the component gets deactivated.
	 *
	 * @param bundleContext The {@link BundleContext}
	 */
	@Deactivate
	protected void deactivate(BundleContext bundleContext) {
		if (eMFFormsDomainExpanderServiceReference != null) {
			bundleContext.ungetService(eMFFormsDomainExpanderServiceReference);
			domainExpander = null;
		}
	}

	private EMFFormsDomainExpander getEMFFormsDomainExpander() {
		if (domainExpander == null) {
			eMFFormsDomainExpanderServiceReference = bundleContext.getServiceReference(EMFFormsDomainExpander.class);
			if (eMFFormsDomainExpanderServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDomainExpander available!"); //$NON-NLS-1$
			}
			domainExpander = bundleContext.getService(eMFFormsDomainExpanderServiceReference);
		}
		return domainExpander;
	}

	/**
	 * Helper method for tests. This is quite ugly!
	 *
	 * @param domainExpander The EMFFormsDomainExpander to use
	 */
	void setEMFFormsDomainExpander(EMFFormsDomainExpander domainExpander) {
		this.domainExpander = domainExpander;
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
		Assert.create(domainModelReference).ofClass(VMappingDomainModelReference.class);

		final VMappingDomainModelReference mappingReference = (VMappingDomainModelReference) domainModelReference;

		if (mappingReference.getDomainModelEFeature() == null) {
			throw new EMFFormsExpandingFailedException(
				"The mapping domain model reference's domain model e feature must not be null."); //$NON-NLS-1$
		}
		checkMapType(mappingReference.getDomainModelEFeature());

		// expand first part of the mapping dmr. A new reference needs to be created because we only want to expand the
		// first part of the mapping dmr in this step.
		final VFeaturePathDomainModelReference firstReference = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		firstReference.setDomainModelEFeature(mappingReference.getDomainModelEFeature());
		firstReference.getDomainModelEReferencePath().addAll(mappingReference.getDomainModelEReferencePath());
		getEMFFormsDomainExpander().prepareDomainObject(firstReference, domainObject);

		// get the EObject that contains the map
		EObject eObject = domainObject;
		for (final EReference eRef : firstReference.getDomainModelEReferencePath()) {
			eObject = (EObject) domainObject.eGet(eRef);
		}

		// Need unchecked conversion because EObject#eGet always returns an Object
		@SuppressWarnings("unchecked")
		final EMap<EClass, EObject> map = (EMap<EClass, EObject>) eObject.eGet(mappingReference
			.getDomainModelEFeature());
		if (!map.containsKey(mappingReference.getMappedClass())) {
			map.put(mappingReference.getMappedClass(), EcoreUtil.create(mappingReference.getMappedClass()));
		}
		final EObject mappedEObject = map.get(mappingReference.getMappedClass());

		// expand second part of the mapping dmr
		getEMFFormsDomainExpander().prepareDomainObject(mappingReference.getDomainModelReference(), mappedEObject);
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
		if (VMappingDomainModelReference.class.isInstance(domainModelReference)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * Checks whether the given structural feature references a proper map.
	 *
	 * @param structuralFeature The feature to check
	 * @throws EMFFormsExpandingFailedException if the structural feature doesn't reference a proper map.
	 */
	private void checkMapType(EStructuralFeature structuralFeature) throws EMFFormsExpandingFailedException {
		if (!structuralFeature.getEType().getInstanceClassName().equals("java.util.Map$Entry")) { //$NON-NLS-1$
			throw new EMFFormsExpandingFailedException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
		if (structuralFeature.getLowerBound() != 0 || structuralFeature.getUpperBound() != -1) {
			throw new EMFFormsExpandingFailedException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}

		final EClass eClass = (EClass) structuralFeature.getEType();
		final EStructuralFeature keyFeature = eClass.getEStructuralFeature("key"); //$NON-NLS-1$
		final EStructuralFeature valueFeature = eClass.getEStructuralFeature("value"); //$NON-NLS-1$
		if (keyFeature == null || valueFeature == null) {
			throw new EMFFormsExpandingFailedException(
				"The VMappingDomainModelReference's domainModelEFeature must reference a map."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(valueFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The values of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EObjects."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(keyFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The keys of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EClasses."); //$NON-NLS-1$
		}
		if (!EClass.class.isAssignableFrom(((EReference) keyFeature).getEReferenceType().getInstanceClass())) {
			throw new EMFFormsExpandingFailedException(
				"The keys of the map referenced by the VMappingDomainModelReference's domainModelEFeature must be referenced EClasses."); //$NON-NLS-1$
		}
	}

}
