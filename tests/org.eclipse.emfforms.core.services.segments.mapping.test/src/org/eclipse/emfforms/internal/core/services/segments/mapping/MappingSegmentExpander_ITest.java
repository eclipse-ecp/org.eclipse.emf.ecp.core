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
package org.eclipse.emfforms.internal.core.services.segments.mapping;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Lucas Koehler
 *
 */
public class MappingSegmentExpander_ITest {

	@Test
	public void testServiceRegistrationAndType() throws InvalidSyntaxException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(MappingSegmentExpander_ITest.class)
			.getBundleContext();

		final Collection<ServiceReference<EMFFormsDMRSegmentExpander>> serviceReferences = bundleContext
			.getServiceReferences(EMFFormsDMRSegmentExpander.class, null);
		EMFFormsDMRSegmentExpander mappingExpander = null;
		ServiceReference<EMFFormsDMRSegmentExpander> serviceReference = null;
		for (final ServiceReference<EMFFormsDMRSegmentExpander> curRef : serviceReferences) {
			final EMFFormsDMRSegmentExpander curService = bundleContext.getService(curRef);
			if (MappingSegmentExpander.class.isInstance(curService)) {
				mappingExpander = curService;
				serviceReference = curRef;
				break;
			}
			bundleContext.ungetService(curRef);
		}

		assertNotNull(mappingExpander);
		bundleContext.ungetService(serviceReference);
	}

}
