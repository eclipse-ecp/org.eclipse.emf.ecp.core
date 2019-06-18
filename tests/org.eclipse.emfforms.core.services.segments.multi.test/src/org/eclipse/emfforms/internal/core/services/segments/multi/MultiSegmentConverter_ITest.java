/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emfforms.internal.core.services.segments.multi;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * All JUnit integration tests for {@link MultiSegmentConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class MultiSegmentConverter_ITest {

	@Test
	public void testServiceRegistrationAndType() throws InvalidSyntaxException {
		final BundleContext bundleContext = FrameworkUtil
			.getBundle(MultiSegmentConverter_ITest.class)
			.getBundleContext();

		final Collection<ServiceReference<DomainModelReferenceSegmentConverterEMF>> serviceReferences = bundleContext
			.getServiceReferences(DomainModelReferenceSegmentConverterEMF.class, null);
		DomainModelReferenceSegmentConverterEMF multiConverter = null;
		ServiceReference<DomainModelReferenceSegmentConverterEMF> serviceReference = null;
		for (final ServiceReference<DomainModelReferenceSegmentConverterEMF> curRef : serviceReferences) {
			final DomainModelReferenceSegmentConverterEMF curService = bundleContext.getService(curRef);
			if (MultiSegmentConverter.class.isInstance(curService)) {
				multiConverter = curService;
				serviceReference = curRef;
				break;
			}
			bundleContext.ungetService(curRef);
		}

		assertNotNull(multiConverter);
		bundleContext.ungetService(serviceReference);
	}

}
