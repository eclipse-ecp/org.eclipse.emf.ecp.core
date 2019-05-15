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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spi.core.services.emfspecificservice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;

/**
 * The {@link EMFSpecificService} offers EMF specific functionality. Thereby, it provides the following things:
 * <ul>
 * <li>{@link org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator}</li>
 * <li>{@link org.eclipse.emf.edit.provider.ComposedAdapterFactory}</li>
 * <ul>
 *
 * @author Lucas Koehler
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface EMFSpecificService {
	// /**
	// * Returns a {@link ComposedAdapterFactory}.
	// *
	// * @return The {@link ComposedAdapterFactory}
	// */
	// ComposedAdapterFactory getComposedAdapterFactory();
	//
	// /**
	// * Returns a {@link AdapterFactoryItemDelegator}.
	// *
	// * @return The {@link AdapterFactoryItemDelegator}
	// */
	// AdapterFactoryItemDelegator getAdapterFactoryItemDelegator();

	IItemPropertyDescriptor getIItemPropertyDescriptor(EObject eObject, EStructuralFeature eStructuralFeature);

	IItemPropertySource getIItemPropertySource(EObject eObject);
}
