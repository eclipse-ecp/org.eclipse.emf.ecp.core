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
package org.eclipse.emfforms.internal.core.services.emfspecificservice;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService;

/**
 * Implementation of {@link EMFSpecificService}.
 *
 * @author Lucas Koehler
 * @noextend This interface is not intended to be extended by clients.
 * @noreference This method is not intended to be referenced by clients.
 */
public class EMFSpecificServiceImpl implements EMFSpecificService {

	// private ComposedAdapterFactory composedAdapterFactory; // TODO: discuss: need dispose?
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see
	// org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService#getComposedAdapterFactory()
	// */
	// @Override
	// public ComposedAdapterFactory getComposedAdapterFactory() {
	// if (composedAdapterFactory == null) {
	// composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
	// new ReflectiveItemProviderAdapterFactory(),
	// new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
	// }
	// return composedAdapterFactory;
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see
	// org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService#getAdapterFactoryItemDelegator()
	// */
	// @Override
	// public AdapterFactoryItemDelegator getAdapterFactoryItemDelegator() {
	// return new AdapterFactoryItemDelegator(getComposedAdapterFactory());
	// }

	/**
	 * {@inheritDoc}
	 *
	 * @see EMFSpecificService#getIItemPropertyDescriptor(EObject, EStructuralFeature)
	 */
	@Override
	public IItemPropertyDescriptor getIItemPropertyDescriptor(EObject eObject, EStructuralFeature eStructuralFeature) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		AdapterFactoryItemDelegator itemDelegator;
		if (editingDomain != null && AdapterFactoryEditingDomain.class.isInstance(editingDomain)) {
			itemDelegator = getAdapterFactoryItemDelegator(AdapterFactoryEditingDomain.class.cast(editingDomain)
				.getAdapterFactory());
		} else {
			final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
				// new CustomReflectiveItemProvider(), <-- move to common bundle,
				// then remove the ReflectiveItemProviderAdapterFactory
				new ReflectiveItemProviderAdapterFactory()
			});
			itemDelegator = getAdapterFactoryItemDelegator(composedAdapterFactory);
			composedAdapterFactory.dispose();
		}

		return itemDelegator.getPropertyDescriptor(eObject, eStructuralFeature);
	}

	private AdapterFactoryItemDelegator getAdapterFactoryItemDelegator(AdapterFactory adapterFactory) {
		return new AdapterFactoryItemDelegator(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emfforms.spi.core.services.emfspecificservice.EMFSpecificService#getIItemPropertySource(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public IItemPropertySource getIItemPropertySource(EObject eObject) {
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		IItemPropertySource propertySource;
		if (editingDomain != null && AdapterFactoryEditingDomain.class.isInstance(editingDomain)) {
			propertySource = (IItemPropertySource) AdapterFactoryEditingDomain.class.cast(editingDomain)
				.getAdapterFactory().adapt(eObject, IItemPropertySource.class);
		} else {
			final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
				// new CustomReflectiveItemProvider(), <-- move to common bundle,
				// then remove the ReflectiveItemProviderAdapterFactory
				new ReflectiveItemProviderAdapterFactory()
			});
			propertySource = (IItemPropertySource) composedAdapterFactory.adapt(eObject, IItemPropertySource.class);
			composedAdapterFactory.dispose();
		}

		return propertySource;
	}
}
