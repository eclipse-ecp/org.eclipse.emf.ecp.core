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
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.segments.multi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.eclipse.emfforms.spi.core.services.segments.LegacyDmrToRootEClass;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link TableDmrToRootEClassConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class TableDmrToRootEClassConverter_Test {

	private TableDmrToRootEClassConverter converter;
	private LegacyDmrToRootEClass dmrToRootEClass;
	private VTableDomainModelReference tableDmr;

	@Before
	public void setUp() {
		converter = new TableDmrToRootEClassConverter();
		dmrToRootEClass = mock(LegacyDmrToRootEClass.class);
		converter.setLegacyDmrToRootEClass(dmrToRootEClass);
		tableDmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
	}

	@Test
	public void getRootEClass_noInnerDmr_withEReferencePath() {
		tableDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		tableDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		tableDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(tableDmr);
		assertSame(EcorePackage.Literals.EREFERENCE, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRootEClass_noInnerDmr_noDomainModelEFeature() {
		converter.getRootEClass(tableDmr);
	}

	@Test
	public void getRootEClass_noInnerDmr_noEReferencePath() {
		tableDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(tableDmr);
		assertSame(EcorePackage.Literals.ESTRUCTURAL_FEATURE, result);
	}

	@Test
	public void getRootEClass_innerDmr() {
		final VDomainModelReference innerDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		tableDmr.setDomainModelReference(innerDmr);
		final EClass eClass = mock(EClass.class);
		when(dmrToRootEClass.getRootEClass(innerDmr)).thenReturn(Optional.of(eClass));

		final EClass result = converter.getRootEClass(tableDmr);

		assertSame(eClass, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRootEClass_innerDmr_notRootEClass() {
		final VDomainModelReference prefixDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		tableDmr.setDomainModelReference(prefixDmr);
		when(dmrToRootEClass.getRootEClass(prefixDmr)).thenReturn(Optional.empty());

		converter.getRootEClass(tableDmr);
	}

	@Test
	public void isApplicable_tableDmr() {
		assertEquals(3d, converter.isApplicable(tableDmr), 0d);
	}

	@Test
	public void isApplicable_noTableDmr() {
		assertEquals(DmrToRootEClassConverter.NOT_APPLICABLE,
			converter.isApplicable(mock(VFeaturePathDomainModelReference.class)),
			0d);
	}

}
