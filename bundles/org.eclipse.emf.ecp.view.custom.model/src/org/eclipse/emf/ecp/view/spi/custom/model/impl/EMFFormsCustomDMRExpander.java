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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.spi.asserts.Assert;
import org.eclipse.emf.ecp.view.spi.custom.model.ECPHardcodedReferences;
import org.eclipse.emf.ecp.view.spi.custom.model.VCustomDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * DMR Expander for VCustomDMR implementation of {@link EMFFormsDMRExpander}.
 *
 * @author Eugen Neufeld
 * @since 1.8
 *
 */
@Component(name = "EMFFormsCustomDMRExpander", service = EMFFormsDMRExpander.class)
public class EMFFormsCustomDMRExpander implements EMFFormsDMRExpander {

	private EMFFormsDomainExpander domainExpander;
	private BundleContext bundleContext;
	private ServiceReference<EMFFormsDomainExpander> eMFFormsDomainExpanderServiceReference;

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
		Assert.create(domainModelReference).ofClass(VCustomDomainModelReference.class);

		final VCustomDomainModelReference customReference = VCustomDomainModelReference.class
			.cast(domainModelReference);

		if (customReference.getBundleName() == null || customReference.getClassName() == null) {
			throw new EMFFormsExpandingFailedException(
				"The custom domain model reference's bundle name and classname must not be null."); //$NON-NLS-1$
		}
		final ECPHardcodedReferences customControl = loadObject(customReference.getBundleName(),
			customReference.getClassName());
		if (customControl == null) {
			throw new EMFFormsExpandingFailedException(
				"The custom domain model cannot be loaded."); //$NON-NLS-1$
		}
		if (!customReference.isControlChecked()) {
			// read stuff from control
			final Set<VDomainModelReference> controlReferences = new LinkedHashSet<VDomainModelReference>();
			controlReferences.addAll(customControl.getNeededDomainModelReferences());
			controlReferences.addAll(customReference.getDomainModelReferences());
			customReference.getDomainModelReferences().clear();
			customReference.getDomainModelReferences().addAll(controlReferences);
			customReference.setControlChecked(true);
		}
		// resolve references from control
		for (final VDomainModelReference dmr : customReference.getDomainModelReferences()) {
			getEMFFormsDomainExpander().prepareDomainObject(dmr, domainObject);
		}
	}

	private static ECPHardcodedReferences loadObject(String bundleName, String clazz) {
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
			return null;
		} catch (final InstantiationException ex) {
			return null;
		} catch (final IllegalAccessException ex) {
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRExpander#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReference)
	 */
	@Override
	public double isApplicable(VDomainModelReference domainModelReference) {
		if (VCustomDomainModelReference.class.isInstance(domainModelReference)) {
			return 5;
		}
		return NOT_APPLICABLE;
	}

}
