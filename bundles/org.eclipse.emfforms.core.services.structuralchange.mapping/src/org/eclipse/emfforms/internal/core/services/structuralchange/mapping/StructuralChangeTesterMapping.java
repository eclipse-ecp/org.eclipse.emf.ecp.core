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
package org.eclipse.emfforms.internal.core.services.structuralchange.mapping;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Structural change tester for VMappingDomainModelReference.
 *
 * @author Eugen Neufeld
 */
@Component
public class StructuralChangeTesterMapping implements StructuralChangeTesterInternal {

	private EMFFormsStructuralChangeTester emfFormsStructuralChangeTester;
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference reference) {
		if (VMappingDomainModelReference.class.isInstance(reference)) {
			return 5;
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

		if (notification.getRawNotification().isTouch()) {
			return false;
		}
		if (EAttribute.class.isInstance(notification.getStructuralFeature())) {
			return false;
		}

		final VMappingDomainModelReference mappingDMR = VMappingDomainModelReference.class.cast(reference);
		boolean relevantChange = false;
		EObject lastResolvedEObject = domainRootObject;
		for (final EReference eReference : mappingDMR.getDomainModelEReferencePath()) {
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

		@SuppressWarnings("unchecked")
		final EMap<EClass, EObject> map = (EMap<EClass, EObject>) lastResolvedEObject
			.eGet(mappingDMR.getDomainModelEFeature());

		if (!map.containsKey(mappingDMR.getMappedClass())) {
			return false;
		}

		relevantChange = getEMFFormsStructuralChangeTester().isStructureChanged(mappingDMR.getDomainModelReference(),
			map.get(mappingDMR.getMappedClass()),
			notification);
		return relevantChange;
	}

}
