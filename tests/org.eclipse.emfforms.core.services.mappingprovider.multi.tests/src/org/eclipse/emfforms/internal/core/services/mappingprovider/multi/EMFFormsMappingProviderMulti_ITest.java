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
package org.eclipse.emfforms.internal.core.services.mappingprovider.multi;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.mappingprovider.EMFFormsMappingProvider;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * JUnit integration tests for {@link EMFFormsMappingProviderMulti}.
 *
 * @author Lucas Koehler
 *
 */
public class EMFFormsMappingProviderMulti_ITest {

	private static BundleContext bundleContext;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(EMFFormsMappingProviderMulti_ITest.class).getBundleContext();
	}

	private EMFFormsDatabindingEMF databinding;
	private ServiceRegistration<EMFFormsDatabindingEMF> databindingRegistration;
	private ReportService reportService;
	private ServiceRegistration<ReportService> reportServiceRegistration;
	private EMFFormsMappingProvider mappingProvider;
	private ServiceReference<EMFFormsMappingProvider> serviceReference;

	@Before
	public void setUp() throws InvalidSyntaxException {
		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);
		databinding = mock(EMFFormsDatabindingEMF.class);
		databindingRegistration = bundleContext.registerService(EMFFormsDatabindingEMF.class, databinding,
			dictionary);
		reportService = mock(ReportService.class);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);

		final Collection<ServiceReference<EMFFormsMappingProvider>> serviceReferences = bundleContext
			.getServiceReferences(EMFFormsMappingProvider.class, null);
		mappingProvider = null;
		serviceReference = null;
		for (final ServiceReference<EMFFormsMappingProvider> curRef : serviceReferences) {
			final EMFFormsMappingProvider curService = bundleContext.getService(curRef);
			if (EMFFormsMappingProviderMulti.class.isInstance(curService)) {
				mappingProvider = curService;
				serviceReference = curRef;
				break;
			}
			bundleContext.ungetService(curRef);
		}

		assertNotNull("EMFFormsMappingProviderMulti was not registered as an EMFFormsMappingProvider.", //$NON-NLS-1$
			mappingProvider);
	}

	@After
	public void tearDown() {
		if (serviceReference != null) {
			bundleContext.ungetService(serviceReference);
		}
		databindingRegistration.unregister();
		reportServiceRegistration.unregister();
	}

	@Test
	public void testDatabindingIntegration() throws DatabindingFailedException {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		final EReference listEReference = TestPackage.eINSTANCE.getB_CList();
		multiSegment.setDomainModelFeature(listEReference.getName());
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		dmr.getSegments().add(multiSegment);

		final B domain = TestFactory.eINSTANCE.createB();

		final Setting domainSetting = ((InternalEObject) domain).eSetting(listEReference);
		when(databinding.getSetting(dmr, domain)).thenReturn(domainSetting);

		mappingProvider.getMappingFor(dmr, domain);

		verify(databinding).getSetting(dmr, domain);
	}

	@Test
	public void testReportServiceIntegration() throws DatabindingFailedException {
		mappingProvider.isApplicable(null, null);
		verify(reportService).report(any(AbstractReport.class));
	}
}
