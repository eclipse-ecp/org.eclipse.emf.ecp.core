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
package org.eclipse.emfforms.internal.core.services.databinding.multi;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.databinding.IEMFListProperty;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfforms.core.services.databinding.featurepath.FeatureDomainModelReferenceSegmentConverter;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.B;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.DomainModelReferenceSegmentConverterEMF;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link MultiDomainModelReferenceSegmentConverter}. As the converter is currently implemented by
 * extending the {@link FeatureDomainModelReferenceSegmentConverter}, these tests only test different/extended aspects.
 *
 * @author Lucas Koehler
 *
 */
public class MultiDomainModelReferenceSegmentConverter_Test {

	private MultiDomainModelReferenceSegmentConverter converter;
	private DefaultRealm realm;
	private EditingDomain editingDomain;

	@Before
	public void setUp() {
		converter = new MultiDomainModelReferenceSegmentConverter();
		realm = new DefaultRealm();
		editingDomain = mock(EditingDomain.class);
	}

	@After
	public void tearDown() {
		realm.dispose();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicable() {
		final double applicable = converter.isApplicable(mock(VMultiDomainModelReferenceSegment.class));
		assertEquals(10d, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableNullSegment() {
		final double applicable = converter.isApplicable(null);
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#isApplicable(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment)}.
	 */
	@Test
	public void testIsApplicableWrongSegmentType() {
		final double applicable = converter.isApplicable(mock(VFeatureDomainModelReferenceSegment.class));
		assertEquals(DomainModelReferenceSegmentConverterEMF.NOT_APPLICABLE, applicable, 0d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToValueProperty() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getB();
		final B b = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_CList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		final IEMFValueProperty valueProperty = converter.convertToValueProperty(segment, segmentRoot, editingDomain);
		assertEquals(feature, valueProperty.getStructuralFeature());
		assertEquals(b.getCList(), valueProperty.getValue(b));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertyMultiAttribute() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_YList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToValueProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertySingleReference() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToValueProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToValueProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToValuePropertySingleAttribute() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_X();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToValueProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvertToListProperty() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getB();
		final B b = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_CList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		final IEMFListProperty listProperty = converter.convertToListProperty(segment, segmentRoot, editingDomain);
		assertEquals(feature, listProperty.getStructuralFeature());
		assertEquals(b.getCList(), listProperty.getList(b));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertyMultiAttribute() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_YList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToListProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertySingleReference() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToListProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#convertToListProperty(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EClass, org.eclipse.emf.edit.domain.EditingDomain)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testConvertToListPropertySingleAttribute() throws DatabindingFailedException {
		final EClass segmentRoot = TestPackage.eINSTANCE.getD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_X();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.convertToListProperty(segment, segmentRoot, editingDomain);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test
	public void testGetSetting() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_CList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		final Setting setting = converter.getSetting(segment, b);
		assertEquals(b.getCList(), setting.get(true));
		assertEquals(feature, setting.getEStructuralFeature());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetSettingMultiAttribute() throws DatabindingFailedException {
		final D d = TestFactory.eINSTANCE.createD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_YList();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.getSetting(segment, d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetSettingSingleAttribute() throws DatabindingFailedException {
		final D d = TestFactory.eINSTANCE.createD();
		final EAttribute feature = TestPackage.eINSTANCE.getD_X();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.getSetting(segment, d);
	}

	/**
	 * Test method for
	 * {@link org.eclipse.emfforms.internal.core.services.databinding.multi.MultiDomainModelReferenceSegmentConverter#getSetting(org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment, org.eclipse.emf.ecore.EObject)}.
	 *
	 * @throws DatabindingFailedException
	 */
	@Test(expected = DatabindingFailedException.class)
	public void testGetSettingSingleReference() throws DatabindingFailedException {
		final B b = TestFactory.eINSTANCE.createB();
		final EReference feature = TestPackage.eINSTANCE.getB_C();
		final VMultiDomainModelReferenceSegment segment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		segment.setDomainModelFeature(feature.getName());

		converter.getSetting(segment, b);
	}
}
