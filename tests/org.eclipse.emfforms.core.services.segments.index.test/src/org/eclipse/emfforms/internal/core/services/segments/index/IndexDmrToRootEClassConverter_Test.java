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
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReference;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexdmrFactory;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.eclipse.emfforms.spi.core.services.segments.LegacyDmrToRootEClass;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lucas Koehler
 *
 */
public class IndexDmrToRootEClassConverter_Test {

	private IndexDmrToRootEClassConverter converter;
	private LegacyDmrToRootEClass dmrToRootEClass;
	private VIndexDomainModelReference indexDmr;

	@Before
	public void setUp() {
		converter = new IndexDmrToRootEClassConverter();
		dmrToRootEClass = mock(LegacyDmrToRootEClass.class);
		converter.setLegacyDmrToRootEClass(dmrToRootEClass);
		indexDmr = VIndexdmrFactory.eINSTANCE.createIndexDomainModelReference();
	}

	@Test
	public void getRootEClass_noPrefixDmr_withEReferencePath() {
		indexDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		indexDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		indexDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(indexDmr);
		assertSame(EcorePackage.Literals.EREFERENCE, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRootEClass_noPrefixDmr_noDomainModelEFeature() {
		converter.getRootEClass(indexDmr);
	}

	@Test
	public void getRootEClass_noPrefixDmr_noEReferencePath() {
		indexDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(indexDmr);
		assertSame(EcorePackage.Literals.ESTRUCTURAL_FEATURE, result);
	}

	@Test
	public void getRootEClass_prefixDmr() {
		final VDomainModelReference prefixDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		indexDmr.setPrefixDMR(prefixDmr);
		final EClass eClass = mock(EClass.class);
		when(dmrToRootEClass.getRootEClass(prefixDmr)).thenReturn(Optional.of(eClass));

		final EClass result = converter.getRootEClass(indexDmr);

		assertSame(eClass, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRootEClass_prefixDmr_notRootEClass() {
		final VDomainModelReference prefixDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		indexDmr.setPrefixDMR(prefixDmr);
		when(dmrToRootEClass.getRootEClass(prefixDmr)).thenReturn(Optional.empty());

		converter.getRootEClass(indexDmr);
	}

	@Test
	public void isApplicable_indexDmr() {
		assertEquals(3d, converter.isApplicable(indexDmr), 0d);
	}

	@Test
	public void isApplicable_noIndexDmr() {
		assertEquals(DmrToRootEClassConverter.NOT_APPLICABLE,
			converter.isApplicable(mock(VFeaturePathDomainModelReference.class)),
			0d);
	}
}
