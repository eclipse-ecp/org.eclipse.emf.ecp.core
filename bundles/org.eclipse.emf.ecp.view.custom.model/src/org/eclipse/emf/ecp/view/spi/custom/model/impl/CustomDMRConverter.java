/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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

import java.util.Set;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * DomainModelReferenceConverter for CustomDomainModelReferences.
 * 
 * @author Eugen Neufeld
 * @since 1.6
 *
 */
@Component
public class CustomDMRConverter implements DomainModelReferenceConverter {
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
		if (VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToValueProperty(VDomainModelReference,EObject)
	 */
	@Override
	public IValueProperty convertToValueProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VCustomDomainModelReference tableDomainModelReference = VCustomDomainModelReference.class
			.cast(domainModelReference);
		final ECPHardcodedReferences customControl = loadObject(tableDomainModelReference.getBundleName(),
			tableDomainModelReference.getClassName());
		if (customControl == null) {
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s cannot be resolved.", tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName())); //$NON-NLS-1$
		}
		final Set<VDomainModelReference> neededDomainModelReferences = customControl.getNeededDomainModelReferences();
		if (neededDomainModelReferences.isEmpty())
		{
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s doesn't define any DomainModelReferences.", tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName())); //$NON-NLS-1$
		}
		return emfFormsDatabinding.getValueProperty(neededDomainModelReferences.iterator().next(), object);
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz) throws DatabindingFailedException {
		final Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			new ClassNotFoundException(String.format(LocalizationServiceHelper.getString(
				VCustomDomainModelReferenceImpl.class, "BundleNotFound_ExceptionMessage"), clazz, bundleName)); //$NON-NLS-1$
			return null;
		}
		try {
			final Class<?> loadClass = bundle.loadClass(clazz);
			if (!ECPHardcodedReferences.class.isAssignableFrom(loadClass)) {
				return null;
			}
			return ECPHardcodedReferences.class.cast(loadClass.newInstance());
		} catch (final ClassNotFoundException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		} catch (final InstantiationException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		} catch (final IllegalAccessException ex) {
			throw new DatabindingFailedException(ex.getMessage());
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference,EObject)
	 */
	@Override
	public IListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		if (domainModelReference == null) {
			throw new IllegalArgumentException("The given VDomainModelReference must not be null."); //$NON-NLS-1$
		}
		if (!VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VCustomDomainModelReference tableDomainModelReference = VCustomDomainModelReference.class
			.cast(domainModelReference);
		final ECPHardcodedReferences customControl = loadObject(tableDomainModelReference.getBundleName(),
			tableDomainModelReference.getClassName());
		if (customControl == null) {
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s cannot be resolved.", tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName())); //$NON-NLS-1$
		}
		final Set<VDomainModelReference> neededDomainModelReferences = customControl.getNeededDomainModelReferences();
		if (neededDomainModelReferences.isEmpty())
		{
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s doesn't define any DomainModelReferences.", tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName())); //$NON-NLS-1$
		}
		return emfFormsDatabinding.getListProperty(neededDomainModelReferences.iterator().next(), object);
	}

}
