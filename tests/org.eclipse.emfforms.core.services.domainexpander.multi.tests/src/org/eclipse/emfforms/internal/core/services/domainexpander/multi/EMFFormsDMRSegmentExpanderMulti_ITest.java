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
package org.eclipse.emfforms.internal.core.services.domainexpander.multi;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.C;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDMRSegmentExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsDomainExpander;
import org.eclipse.emfforms.spi.core.services.domainexpander.EMFFormsExpandingFailedException;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * All JUnit integration tests for {@link EMFFormsDMRSegmentExpanderMulti}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsDMRSegmentExpanderMulti_ITest {

	private static BundleContext bundleContext;
	private EMFFormsDMRSegmentExpander multiConverter;
	private ServiceReference<EMFFormsDMRSegmentExpander> serviceReference;
	private EMFFormsDomainExpander domainExpander;
	private ServiceRegistration<EMFFormsDomainExpander> domainExpanderRegistration;
	private ReportService reportService;
	private ServiceRegistration<ReportService> reportServiceRegistration;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(EMFFormsDMRSegmentExpanderMulti_ITest.class).getBundleContext();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);
		domainExpander = mock(EMFFormsDomainExpander.class);
		domainExpanderRegistration = bundleContext.registerService(EMFFormsDomainExpander.class, domainExpander,
			dictionary);
		reportService = mock(ReportService.class);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);

		final Collection<ServiceReference<EMFFormsDMRSegmentExpander>> serviceReferences = bundleContext
			.getServiceReferences(EMFFormsDMRSegmentExpander.class, null);
		multiConverter = null;
		serviceReference = null;
		for (final ServiceReference<EMFFormsDMRSegmentExpander> curRef : serviceReferences) {
			final EMFFormsDMRSegmentExpander curService = bundleContext.getService(curRef);
			if (EMFFormsDMRSegmentExpanderMulti.class.isInstance(curService)) {
				multiConverter = curService;
				serviceReference = curRef;
				break;
			}
			bundleContext.ungetService(curRef);
		}

		assertNotNull("EMFFormsDMRSegmentExpanderMulti was not registered as an EMFFormsDMRSegmentExpander.", //$NON-NLS-1$
			multiConverter);
	}

	@After
	public void tearDown() {
		if (serviceReference != null) {
			bundleContext.ungetService(serviceReference);
		}
		domainExpanderRegistration.unregister();
		reportServiceRegistration.unregister();
	}

	@Test
	public void testDomainExpanderIntegration() throws EMFFormsExpandingFailedException {
		final EReference eReference = TestPackage.eINSTANCE.getB_CList();
		final B domain = TestFactory.eINSTANCE.createB();
		final C child = TestFactory.eINSTANCE.createC();
		domain.getCList().add(child);
		final VDomainModelReference childDMR = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(eReference.getName());
		multiSegment.getChildDomainModelReferences().add(childDMR);

		multiConverter.prepareDomainObject(multiSegment, domain);

		verify(domainExpander).prepareDomainObject(childDMR, child);
	}

	@Test
	public void testReportServiceIntegration() {
		multiConverter.isApplicable(null);
		verify(reportService).report(any(AbstractReport.class));
	}
}
