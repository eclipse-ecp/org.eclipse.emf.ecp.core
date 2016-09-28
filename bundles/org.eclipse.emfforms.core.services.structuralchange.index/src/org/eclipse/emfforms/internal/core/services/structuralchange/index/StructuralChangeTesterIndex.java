/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.structuralchange.index;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Structural change tester for VIndexDomainModelReferences.
 *
 * @author Eugen Neufeld
 */
@Component
public class StructuralChangeTesterIndex implements StructuralChangeTesterInternal {

	private EMFFormsStructuralChangeTester emfFormsStructuralChangeTester;
	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private BundleContext bundleContext;
	private ServiceReference<EMFFormsStructuralChangeTester> emfFormsStructuralChangeTesterServiceReference;

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
		if (emfFormsStructuralChangeTesterServiceReference != null) {
			bundleContext.ungetService(emfFormsStructuralChangeTesterServiceReference);
			emfFormsStructuralChangeTester = null;
		}
	}

	private EMFFormsStructuralChangeTester getEMFFormsStructuralChangeTester() {
		if (emfFormsStructuralChangeTester == null) {
			emfFormsStructuralChangeTesterServiceReference = bundleContext
				.getServiceReference(EMFFormsStructuralChangeTester.class);
			if (emfFormsStructuralChangeTesterServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDomainExpander available!"); //$NON-NLS-1$
			}
			emfFormsStructuralChangeTester = bundleContext.getService(emfFormsStructuralChangeTesterServiceReference);
		}
		return emfFormsStructuralChangeTester;
	}

	/**
	 * Helper method for tests. This is quite ugly!
	 *
	 * @param emfFormsStructuralChangeTester The EMFFormsStructuralChangeTester to use
	 */
	void setEMFFormsStructuralChangeTester(EMFFormsStructuralChangeTester emfFormsStructuralChangeTester) {
		this.emfFormsStructuralChangeTester = emfFormsStructuralChangeTester;
	}

	/**
	 * Sets the {@link EMFFormsDatabindingEMF} service.
	 *
	 * @param emfFormsDatabinding The emf databinding service
	 */
	@Reference(unbind = "-")
	protected void setEMFFormsDatabindingEMF(EMFFormsDatabindingEMF emfFormsDatabinding) {
		this.emfFormsDatabinding = emfFormsDatabinding;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isApplicable(VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference reference) {
		if (VIndexDomainModelReference.class.isInstance(reference)) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isStructureChanged(VDomainModelReference,
	 *      EObject, ModelChangeNotification)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isStructureChanged(VDomainModelReference reference, EObject domainRootObject,
		ModelChangeNotification notification) {
		Assert.create(reference).notNull();
		Assert.create(notification).notNull();
		Assert.create(reference).ofClass(VFeaturePathDomainModelReference.class);

		if (notification.getRawNotification().isTouch()) {
			return false;
		}
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return false;
		}

		final VIndexDomainModelReference indexDMR = VIndexDomainModelReference.class.cast(reference);
		boolean relevantChange = false;
		EObject lastResolvedEObject = domainRootObject;
		if (indexDMR.getPrefixDMR() == null) {
			for (final EReference eReference : indexDMR.getDomainModelEReferencePath()) {
				if (lastResolvedEObject == null) {
					return false;
				}
				relevantChange |= eReference.equals(notification.getStructuralFeature())
					&& lastResolvedEObject == notification.getNotifier();
				if (relevantChange) {
					return true;
				}
				lastResolvedEObject = (EObject) lastResolvedEObject.eGet(eReference);
			}
			relevantChange |= notification.getStructuralFeature().equals(indexDMR.getDomainModelEFeature())
				&& lastResolvedEObject == notification.getNotifier();
			if (relevantChange) {
				return true;
			}
			lastResolvedEObject = ((EList<EObject>) lastResolvedEObject.eGet(indexDMR.getDomainModelEFeature()))
				.get(indexDMR.getIndex());
		} else {
			relevantChange = getEMFFormsStructuralChangeTester().isStructureChanged(indexDMR.getPrefixDMR(),
				domainRootObject, notification);
			if (relevantChange) {
				return true;
			}
			try {
				lastResolvedEObject = ((EList<EObject>) emfFormsDatabinding
					.getSetting(indexDMR.getPrefixDMR(), domainRootObject).get(true)).get(indexDMR.getIndex());
			} catch (final DatabindingFailedException ex) {
				throw new IllegalStateException(ex);
			}
		}

		relevantChange = getEMFFormsStructuralChangeTester().isStructureChanged(indexDMR.getTargetDMR(),
			lastResolvedEObject,
			notification);
		return relevantChange;
	}

}
