/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.model;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Eugen
 *
 */
@Component
public class TableDMRConverter implements DomainModelReferenceConverter {
	private EMFFormsDatabinding emfFormsDatabinding;
	private ServiceReference<EMFFormsDatabinding> databindingServiceReference;

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverter} is activated. It
	 * retrieves the {@link EMFFormsDatabinding EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Activate
	protected final void activate(BundleContext bundleContext) {
		databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabinding.class);
		emfFormsDatabinding = bundleContext.getService(databindingServiceReference);

	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverter} is deactivated.
	 * It frees the {@link EMFFormsDatabinding EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Deactivate
	protected final void deactivate(BundleContext bundleContext) {
		bundleContext.ungetService(databindingServiceReference);
		emfFormsDatabinding = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (VTableDomainModelReference.class.isInstance(domainModelReference)
			&& VTableDomainModelReference.class.cast(domainModelReference).getDomainModelReference() != null) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,
	 *      EObject)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VTableDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VTableDomainModelReference tableDomainModelReference = VTableDomainModelReference.class
			.cast(domainModelReference);
		if (tableDomainModelReference.getDomainModelReference() == null) {
			throw new DatabindingFailedException(
				"The field domainModelReference of the given VTableDomainModelReference must not be null."); //$NON-NLS-1$
		}
		return emfFormsDatabinding.getValueProperty(tableDomainModelReference.getDomainModelReference(), object);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(VDomainModelReference,
	 *      EObject)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VTableDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VTableDomainModelReference tableDomainModelReference = VTableDomainModelReference.class
			.cast(domainModelReference);
		if (tableDomainModelReference.getDomainModelReference() == null) {
			throw new DatabindingFailedException(
				"The field domainModelReference of the given VTableDomainModelReference must not be null."); //$NON-NLS-1$
		}
		return emfFormsDatabinding.getListProperty(tableDomainModelReference.getDomainModelReference(), object);
	}

}
