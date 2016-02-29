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
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.ModelChangeNotification;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.structuralchange.EMFFormsStructuralChangeTester;
import org.eclipse.emfforms.spi.core.services.structuralchange.StructuralChangeTesterInternal;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Tester for structural changes of CustomDMRs.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
@Component
public class StructuralChangeCustomDMRTester implements StructuralChangeTesterInternal {

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
		if (VCustomDomainModelReference.class.isInstance(reference)) {
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
		final VCustomDomainModelReference customDMR = VCustomDomainModelReference.class.cast(reference);
		if (!customDMR.isControlChecked()) {
			return false;
		}
		boolean result = true;
		for (final VDomainModelReference dmr : customDMR.getDomainModelReferences()) {
			result &= getEMFFormsStructuralChangeTester().isStructureChanged(dmr, domainRootObject, notification);
		}
		return result;
	}

}
