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
package org.eclipse.emfforms.core.services.databinding.integrationtest;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.spi.model.VDMRSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * JUnit test to test the integration of the databinding services.
 *
 * @author Lucas Koehler
 *
 */
public class DatabindingIntegration_ITest {

	private static BundleContext bundleContext;
	private EMFFormsDatabinding databindingService;
	private ServiceReference<EMFFormsDatabinding> serviceReference;

	@BeforeClass
	public static void setUpBeforeClass() {
		bundleContext = FrameworkUtil.getBundle(DatabindingIntegration_ITest.class).getBundleContext();
	}

	@Before
	public void setUp() {
		serviceReference = bundleContext
			.getServiceReference(EMFFormsDatabinding.class);
		databindingService = bundleContext.getService(serviceReference);
	}

	@After
	public void tearDown() {
		bundleContext.ungetService(serviceReference);
	}

	@Test
	public void testIntegrationValue() throws DatabindingFailedException {
		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();

		// create segment path to the attribute
		final VDMRSegment segment1 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("b"); //$NON-NLS-1$
		final VDMRSegment segment2 = VViewFactory.eINSTANCE.createDMRSegment();
		segment2.setPropertyName("c"); //$NON-NLS-1$
		final VDMRSegment segment3 = VViewFactory.eINSTANCE.createDMRSegment();
		segment3.setPropertyName("d"); //$NON-NLS-1$
		final VDMRSegment segment4 = VViewFactory.eINSTANCE.createDMRSegment();
		segment4.setPropertyName("x"); //$NON-NLS-1$

		domainModelReference.getSegments().add(segment1);
		domainModelReference.getSegments().add(segment2);
		domainModelReference.getSegments().add(segment3);
		domainModelReference.getSegments().add(segment4);

		final EClass rootEClass = TestPackage.eINSTANCE.getA();

		final IValueProperty valueProperty = databindingService.getValueProperty(domainModelReference, rootEClass);

		// Check EStructuralFeature of the property.
		assertEquals(TestPackage.eINSTANCE.getD_X(), valueProperty.getValueType());

		// Check correct path.
		final String expected = "A.b<B> => B.c<C> => C.d<D> => D.x<EString>"; //$NON-NLS-1$
		assertEquals(expected, valueProperty.toString());

	}

	@Test
	public void testIntegrationList() throws DatabindingFailedException {
		final VDomainModelReference domainModelReference = VViewFactory.eINSTANCE.createDomainModelReference();

		// create segment path to the attribute
		final VDMRSegment segment1 = VViewFactory.eINSTANCE.createDMRSegment();
		segment1.setPropertyName("b"); //$NON-NLS-1$
		final VDMRSegment segment2 = VViewFactory.eINSTANCE.createDMRSegment();
		segment2.setPropertyName("c"); //$NON-NLS-1$
		final VDMRSegment segment3 = VViewFactory.eINSTANCE.createDMRSegment();
		segment3.setPropertyName("d"); //$NON-NLS-1$
		final VDMRSegment segment4 = VViewFactory.eINSTANCE.createDMRSegment();
		segment4.setPropertyName("yList"); //$NON-NLS-1$

		domainModelReference.getSegments().add(segment1);
		domainModelReference.getSegments().add(segment2);
		domainModelReference.getSegments().add(segment3);
		domainModelReference.getSegments().add(segment4);

		final EClass rootEClass = TestPackage.eINSTANCE.getA();

		final IListProperty listProperty = databindingService.getListProperty(domainModelReference, rootEClass);

		// Check EStructuralFeature of the property.
		assertEquals(TestPackage.eINSTANCE.getD_YList(), listProperty.getElementType());

		// Check correct path.
		final String expected = "A.b<B> => B.c<C> => C.d<D> => D.yList[]<EInt>"; //$NON-NLS-1$
		assertEquals(expected, listProperty.toString());

	}
}
