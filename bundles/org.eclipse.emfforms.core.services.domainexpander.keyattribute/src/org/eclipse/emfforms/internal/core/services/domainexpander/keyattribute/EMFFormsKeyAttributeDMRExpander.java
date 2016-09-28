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
package org.eclipse.emfforms.internal.core.services.domainexpander.keyattribute;

import java.util.List;

import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.keyattributedmr.model.VKeyAttributeDomainModelReference;
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
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * An {@link EMFFormsDMRExpander} for {@link VKeyAttributeDomainModelReference VKeyAttributeDomainModelReferences}.
 *
 * @author Lucas Koehler
 *
 */
@Component(name = "EMFFormsKeyAttributeDMRExpander")
public class EMFFormsKeyAttributeDMRExpander implements EMFFormsDMRExpander {

	private ReportService reportService;
	private EMFFormsDomainExpander domainExpander;
	private EMFFormsDatabinding databindingService;
	private BundleContext bundleContext;
	private ServiceReference<EMFFormsDomainExpander> emfFormsDomainExpanderServiceReference;

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
		if (emfFormsDomainExpanderServiceReference != null) {
			bundleContext.ungetService(emfFormsDomainExpanderServiceReference);
			domainExpander = null;
		}
	}

	private EMFFormsDomainExpander getEMFFormsDomainExpander() {
		if (domainExpander == null) {
			emfFormsDomainExpanderServiceReference = bundleContext.getServiceReference(EMFFormsDomainExpander.class);
			if (emfFormsDomainExpanderServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDomainExpander available!"); //$NON-NLS-1$
			}
			domainExpander = bundleContext.getService(emfFormsDomainExpanderServiceReference);
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
	 * Called by the framework to set the {@link EMFFormsDatabinding}.
	 *
	 * @param emfFormsDatabinding The {@link EMFFormsDatabinding}
	 */
	@Reference(unbind = "-")
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
		Assert.create(domainModelReference).ofClass(VKeyAttributeDomainModelReference.class);

		final VKeyAttributeDomainModelReference keyAttributeDMR = VKeyAttributeDomainModelReference.class
			.cast(domainModelReference);

		// Create a new feature path dmr to only expand the feature path part of the key attribute dmr.
		final VFeaturePathDomainModelReference featurePathDMR = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		featurePathDMR.setDomainModelEFeature(keyAttributeDMR.getDomainModelEFeature());
		featurePathDMR.getDomainModelEReferencePath().addAll(keyAttributeDMR.getDomainModelEReferencePath());

		getEMFFormsDomainExpander().prepareDomainObject(featurePathDMR, domainObject);

		IValueProperty valueProperty;
		try {
			valueProperty = databindingService.getValueProperty(featurePathDMR, domainObject);
		} catch (final DatabindingFailedException ex) {
			throw new EMFFormsExpandingFailedException(
				"Domain Expansion failed due to a failed databinding: " + ex.getMessage()); //$NON-NLS-1$
		}

		final EStructuralFeature listEStructuralFeature = (EStructuralFeature) valueProperty.getValueType();

		checkListType(listEStructuralFeature);

		// need unchecked type cast because IValueProperty#getValue always returns an Object.
		@SuppressWarnings("unchecked")
		final List<EObject> eObjectsList = (List<EObject>) valueProperty.getValue(domainObject);

		/*
		 * Iterate over all EObjects of the list referenced by the feature path DMR part of the key attribute DMR.
		 * Check for every EObject whether the key DMR references the key value defined by the key attribute DMR.
		 * If this is the case, expand the value DMR for this EObject.
		 */
		for (final EObject targetDomainObject : eObjectsList) {
			IValueProperty keyValueProperty;
			try {
				keyValueProperty = databindingService.getValueProperty(keyAttributeDMR.getKeyDMR(),
					targetDomainObject);
			} catch (final DatabindingFailedException ex) {
				throw new EMFFormsExpandingFailedException(
					"Domain Expansion failed due to an invalid key attribute dmr: " + ex.getMessage()); //$NON-NLS-1$
			}
			final Object potentialKey = keyValueProperty.getValue(targetDomainObject);

			final boolean foundKey = keyAttributeDMR.getKeyValue().equals(potentialKey);
			if (foundKey) {
				domainExpander.prepareDomainObject(keyAttributeDMR.getValueDMR(), targetDomainObject);
			}
		}
	}

	/**
	 * Checks whether the given structural feature references a list of {@link EObject EObjects}.
	 *
	 * @param structuralFeature The feature to check
	 * @throws EMFFormsExpandingFailedException if the structural feature doesn't reference a list of {@link EObject
	 *             EObjects}.
	 */
	private void checkListType(EStructuralFeature structuralFeature) throws EMFFormsExpandingFailedException {
		if (!structuralFeature.isMany()) {
			throw new EMFFormsExpandingFailedException(
				"The VKeyAttributeDomainModelReference's domainModelEFeature must reference a list."); //$NON-NLS-1$
		}
		if (!EReference.class.isInstance(structuralFeature)) {
			throw new EMFFormsExpandingFailedException(
				"The VKeyAttributeDomainModelReference's domainModelEFeature must reference a list of EObjects."); //$NON-NLS-1$
		}
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
		if (VKeyAttributeDomainModelReference.class.isInstance(domainModelReference)) {
			return 5d;
		}
		return NOT_APPLICABLE;
	}

}
