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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.custom.model.impl;

import java.util.Set;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.DomainModelReferenceConverter;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceConverterEMF;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
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
@Component(service = { DomainModelReferenceConverterEMF.class, DomainModelReferenceConverter.class })
public class CustomDMRConverter implements DomainModelReferenceConverterEMF {
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
		if (VCustomDomainModelReference.class.isInstance(domainModelReference)) {
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
		if (!VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			throw new IllegalArgumentException(
				"DomainModelReference must be an instance of VTableDomainModelReference."); //$NON-NLS-1$
		}

		final VCustomDomainModelReference tableDomainModelReference = VCustomDomainModelReference.class
			.cast(domainModelReference);
		if (!tableDomainModelReference.getDomainModelReferences().isEmpty()) {
			final VDomainModelReference nextDmr = tableDomainModelReference.getDomainModelReferences().iterator()
				.next();
			if (rootEClass == null) {
				return getEMFFormsDatabindingEMF().getValueProperty(nextDmr, (EObject) null);
			}
			return getEMFFormsDatabindingEMF().getValueProperty(nextDmr, rootEClass, editingDomain);
		}
		final ECPHardcodedReferences customControl = loadObject(tableDomainModelReference.getBundleName(),
			tableDomainModelReference.getClassName());
		if (customControl == null) {
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s cannot be resolved.", //$NON-NLS-1$
						tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName()));
		}
		final Set<VDomainModelReference> neededDomainModelReferences = customControl.getNeededDomainModelReferences();
		if (neededDomainModelReferences.isEmpty()) {
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s doesn't define any DomainModelReferences.", //$NON-NLS-1$
						tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName()));
		}
		final VDomainModelReference nextNeededDmr = neededDomainModelReferences.iterator().next();
		if (rootEClass == null) {
			return getEMFFormsDatabindingEMF().getValueProperty(nextNeededDmr, (EObject) null);
		}
		return getEMFFormsDatabindingEMF().getValueProperty(nextNeededDmr, rootEClass, editingDomain);
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz)
		throws DatabindingFailedException {
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

	@Override
	public IEMFListProperty convertToListProperty(VDomainModelReference domainModelReference, EObject object)
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
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s cannot be resolved.", //$NON-NLS-1$
						tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName()));
		}
		final Set<VDomainModelReference> neededDomainModelReferences = customControl.getNeededDomainModelReferences();
		if (neededDomainModelReferences.isEmpty()) {
			throw new DatabindingFailedException(
				String
					.format(
						"The provided ECPHardcodedReferences from Bundle %1$s Class %2$s doesn't define any DomainModelReferences.", //$NON-NLS-1$
						tableDomainModelReference.getBundleName(), tableDomainModelReference.getClassName()));
		}
		return getEMFFormsDatabindingEMF().getListProperty(neededDomainModelReferences.iterator().next(), object);
	}

	@Override
	public Setting getSetting(VDomainModelReference domainModelReference, EObject object)
		throws DatabindingFailedException {
		final IEMFValueProperty valueProperty = convertToValueProperty(domainModelReference, object);
		final IObservableValue observableValue = valueProperty.observe(object);
		final EObject eObject = (EObject) IObserving.class.cast(observableValue).getObserved();
		final EStructuralFeature eStructuralFeature = valueProperty.getStructuralFeature();
		return InternalEObject.class.cast(eObject).eSetting(eStructuralFeature);
	}

}
