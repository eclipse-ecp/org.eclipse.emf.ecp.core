/*******************************************************************************
 * Copyright (c) 2011-2021 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.sdk.integration.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

public class NoImmediateServices_PTest {

	@Test
	public void test() {
		final BundleContext bundleContext = FrameworkUtil.getBundle(NoImmediateServices_PTest.class).getBundleContext();
		final ServiceReference<ServiceComponentRuntime> serviceReference = bundleContext
			.getServiceReference(ServiceComponentRuntime.class);
		final ServiceComponentRuntime service = bundleContext.getService(serviceReference);
		final Collection<ComponentDescriptionDTO> components = service.getComponentDescriptionDTOs();
		final List<String> immediateComponents = new ArrayList<String>();
		for (final ComponentDescriptionDTO component : components) {
			if (component.immediate && component.implementationClass.contains("emf")) { //$NON-NLS-1$
				immediateComponents.add(component.implementationClass);
			}
		}
		bundleContext.ungetService(serviceReference);
		if (immediateComponents.size() > 0) {
			fail(String.format("There are immediate Services! %1$s", //$NON-NLS-1$
				immediateComponents.stream().collect(Collectors.joining(",")))); //$NON-NLS-1$
		}
	}

}
