/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.test.common;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

/**
 * Utility class providing common functionality for test.
 * <p>
 * For SWT and View related utils see <code>SWTTestUtil</code> and <code>SWTViewTestHelper</code>.
 *
 * @author Lucas Koehler
 *
 */
public final class TestUtil {
	private TestUtil() {
		// Do not instantiate util class
	}

	/**
	 * Creates a virtual {@link Resource} contained in a new {@link ResourceSet} with a working {@link EditingDomain}.
	 * <p>
	 * <strong>Note:</strong> The returned resource cannot be safed
	 *
	 * @return The created {@link Resource}
	 */
	public static Resource createResourceWithEditingDomain() {
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		// Set default xmi factory to guarantee reproducible results and make resource creation work in plain unit tests
		final Object oldFactory = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", //$NON-NLS-1$
			new XMIResourceFactoryImpl());
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		if (oldFactory != null) {
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", oldFactory); //$NON-NLS-1$
		}
		return resource;
	}
}
