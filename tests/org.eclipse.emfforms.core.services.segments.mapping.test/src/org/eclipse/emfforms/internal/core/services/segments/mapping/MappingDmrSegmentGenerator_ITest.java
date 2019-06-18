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

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReference;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.AbstractReport;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.segments.DmrSegmentGenerator;
import org.eclipse.emfforms.spi.core.services.segments.EMFFormsSegmentGenerator;
import org.junit.AfterClass;
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
 * Intergration test cases {@link MappingDmrSegmentGenerator}.
 *
 * @author Lucas Koehler
 *
 */
public class MappingDmrSegmentGenerator_ITest {

	private static BundleContext bundleContext;
	private static EMFFormsSegmentGenerator emfFormsSegmentGenerator;
	private static ReportService reportService;
	private static ServiceRegistration<EMFFormsSegmentGenerator> segmentGeneratorRegistration;
	private static ServiceRegistration<ReportService> reportServiceRegistration;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(MappingDmrSegmentGenerator_ITest.class).getBundleContext();

		emfFormsSegmentGenerator = mock(EMFFormsSegmentGenerator.class);
		reportService = mock(ReportService.class);

		final Dictionary<String, Object> dictionary = new Hashtable<String, Object>();
		dictionary.put(Constants.SERVICE_RANKING, 5000);

		segmentGeneratorRegistration = bundleContext
			.registerService(EMFFormsSegmentGenerator.class, emfFormsSegmentGenerator, dictionary);
		reportServiceRegistration = bundleContext.registerService(ReportService.class, reportService, dictionary);
	}

	@AfterClass
	public static void tearDownAfterClass() {
		segmentGeneratorRegistration.unregister();
		reportServiceRegistration.unregister();
	}

	@Before
	public void setUp() {
		reset(emfFormsSegmentGenerator);
		reset(reportService);
	}

	@Test
	public void serviceRegistered() throws InvalidSyntaxException {
		final Collection<ServiceReference<DmrSegmentGenerator>> references = bundleContext
			.getServiceReferences(DmrSegmentGenerator.class, null);

		final boolean registered = references.stream().map(ref -> bundleContext.getService(ref))
			.anyMatch(service -> MappingDmrSegmentGenerator.class.equals(service.getClass()));
		references.forEach(ref -> bundleContext.ungetService(ref));

		assertTrue("The MappingDmrSegmentGenerator was not registered as a service", registered); //$NON-NLS-1$
	}

	@Test
	public void integration() throws InvalidSyntaxException {
		final Collection<ServiceReference<DmrSegmentGenerator>> references = bundleContext
			.getServiceReferences(DmrSegmentGenerator.class, null);
		final Optional<DmrSegmentGenerator> generator = references.stream().map(ref -> bundleContext.getService(ref))
			.filter(service -> MappingDmrSegmentGenerator.class.equals(service.getClass())).findFirst();
		assertTrue(generator.isPresent());

		final VMappingDomainModelReference mappingDmr = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReference();
		mappingDmr.setDomainModelEFeature(TestPackage.Literals.C__ECLASS_TO_A);
		mappingDmr.setMappedClass(TestPackage.Literals.A);
		final VFeaturePathDomainModelReference targetDmr = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		targetDmr.setDomainModelEFeature(TestPackage.Literals.A__B);
		mappingDmr.setDomainModelReference(targetDmr);

		final VDomainModelReferenceSegment segment = VViewFactory.eINSTANCE.createFeatureDomainModelReferenceSegment();
		final List<VDomainModelReferenceSegment> segments = new LinkedList<>();
		segments.add(segment);
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class))).thenReturn(segments);
		generator.get().generateSegments(mappingDmr);

		// Do not validate whether the generation works correctly: this is done extensively in the unit test
		verify(emfFormsSegmentGenerator, times(1)).generateSegments(targetDmr);
		verify(reportService, never()).report(any(AbstractReport.class));
	}

	@Test
	public void integration_failure() throws InvalidSyntaxException {
		final Collection<ServiceReference<DmrSegmentGenerator>> references = bundleContext
			.getServiceReferences(DmrSegmentGenerator.class, null);
		final Optional<DmrSegmentGenerator> generator = references.stream().map(ref -> bundleContext.getService(ref))
			.filter(service -> MappingDmrSegmentGenerator.class.equals(service.getClass())).findFirst();
		assertTrue(generator.isPresent());

		final VMappingDomainModelReference mappingDmr = VMappingdmrFactory.eINSTANCE
			.createMappingDomainModelReference();
		when(emfFormsSegmentGenerator.generateSegments(any(VDomainModelReference.class)))
			.thenReturn(Collections.emptyList());
		generator.get().generateSegments(mappingDmr);

		// Do not validate whether the generation works correctly: this is done extensively in the unit test
		verify(reportService, times(1)).report(any(AbstractReport.class));
	}

}
