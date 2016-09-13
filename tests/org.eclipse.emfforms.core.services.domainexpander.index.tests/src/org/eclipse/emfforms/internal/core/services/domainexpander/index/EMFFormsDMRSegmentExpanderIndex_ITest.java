/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.domainexpander.index;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * JUnit integration test for {@link EMFFormsDMRSegmentExpanderIndex}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDMRSegmentExpanderIndex_ITest {

	@Test
	public void testServiceRegistrationAndType() throws InvalidSyntaxException {
		final BundleContext bundleContext = FrameworkUtil.getBundle(EMFFormsDMRSegmentExpanderIndex_ITest.class)
			.getBundleContext();

		final Collection<ServiceReference<EMFFormsDMRSegmentExpander>> serviceReferences = bundleContext
			.getServiceReferences(EMFFormsDMRSegmentExpander.class, null);
		EMFFormsDMRSegmentExpander indexExpander = null;
		ServiceReference<EMFFormsDMRSegmentExpander> serviceReference = null;
		for (final ServiceReference<EMFFormsDMRSegmentExpander> curRef : serviceReferences) {
			final EMFFormsDMRSegmentExpander curService = bundleContext.getService(curRef);
			if (EMFFormsDMRSegmentExpanderIndex.class.isInstance(curService)) {
				indexExpander = curService;
				serviceReference = curRef;
				break;
			}
			bundleContext.ungetService(curRef);
		}

		assertNotNull(indexExpander);
		bundleContext.ungetService(serviceReference);
	}

}
