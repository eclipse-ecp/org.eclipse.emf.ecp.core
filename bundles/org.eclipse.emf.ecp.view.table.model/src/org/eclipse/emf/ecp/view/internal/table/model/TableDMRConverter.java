/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.model;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * This is the DomainModelReferenceConverterEMF for {@link VTableDomainModelReference}.
 *
 * @author Eugen Neufeld
 *
 */
@Component(service = { DomainModelReferenceConverterEMF.class, DomainModelReferenceConverter.class })
public class TableDMRConverter implements DomainModelReferenceConverterEMF {
	private EMFFormsDatabindingEMF emfFormsDatabinding;
	private ServiceReference<EMFFormsDatabindingEMF> databindingServiceReference;
	private BundleContext bundleContext;

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverterEMF} is activated. It
	 * retrieves the {@link EMFFormsDatabindingEMF EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Activate
	protected final void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * This method is called by the OSGI framework when this {@link DomainModelReferenceConverterEMF} is deactivated.
	 * It frees the {@link EMFFormsDatabindingEMF EMF Forms databinding service}.
	 *
	 * @param bundleContext The {@link BundleContext} of this classes bundle.
	 */
	@Deactivate
	protected final void deactivate(BundleContext bundleContext) {
		if (databindingServiceReference != null) {
			bundleContext.ungetService(databindingServiceReference);
			emfFormsDatabinding = null;
		}
	}

	private EMFFormsDatabindingEMF getEMFFormsDatabindingEMF() {
		if (emfFormsDatabinding == null) {
			databindingServiceReference = bundleContext.getServiceReference(EMFFormsDatabindingEMF.class);
			if (databindingServiceReference == null) {
				throw new IllegalStateException("No EMFFormsDatabindingEMF available!"); //$NON-NLS-1$
			}
			emfFormsDatabinding = bundleContext.getService(databindingServiceReference);
		}
		return emfFormsDatabinding;
	}

	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (VTableDomainModelReference.class.isInstance(domainModelReference)
			&& VTableDomainModelReference.class.cast(domainModelReference).getDomainModelReference() != null) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (object == null) {
			return convertToValueProperty(domainModelReference, null, null);
		}
		return convertToValueProperty(domainModelReference, object.eClass(), getEditingDomain(object));
	}

	private EditingDomain getEditingDomain(EObject object) throws DatabindingFailedException {
		return AdapterFactoryEditingDomain.getEditingDomainFor(object);
	}

	@Override
	public IEMFValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EClass rootEClass,
		EditingDomain editingDomain) throws DatabindingFailedException {
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
		if (rootEClass == null) {
			return getEMFFormsDatabindingEMF().getValueProperty(tableDomainModelReference.getDomainModelReference(),
				(EObject) null);
		}
		return getEMFFormsDatabindingEMF().getValueProperty(tableDomainModelReference.getDomainModelReference(),
			rootEClass, editingDomain);
	}

	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
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
		return getEMFFormsDatabindingEMF().getListProperty(tableDomainModelReference.getDomainModelReference(), object);
	}

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
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
		final Setting setting = getEMFFormsDatabindingEMF().getSetting(
			tableDomainModelReference.getDomainModelReference(),
			object);
		if (!setting.getEStructuralFeature().isMany()
			|| !EReference.class.isInstance(setting.getEStructuralFeature())) {
			throw new DatabindingFailedException(
				"The field domainModelReference of the given VTableDomainModelReference must be a multi reference."); //$NON-NLS-1$
		}
		return setting;
	}

}
