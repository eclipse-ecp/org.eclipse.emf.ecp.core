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
package org.eclipse.emfforms.internal.core.services.segments.featurepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emfforms.spi.core.services.segments.DmrToRootEClassConverter;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link FeatureDmrToRootEClassConverter}.
 *
 * @author Lucas Koehler
 *
 */
public class FeatureDmrToRootEClassConverter_Test {

	private FeatureDmrToRootEClassConverter converter;
	private VFeaturePathDomainModelReference featureDmr;

	@Before
	public void setUp() {
		converter = new FeatureDmrToRootEClassConverter();
		featureDmr = VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
	}

	@Test
	public void getRootEClass_withEReferencePath() {
		featureDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.EREFERENCE__EREFERENCE_TYPE);
		featureDmr.getDomainModelEReferencePath().add(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES);
		featureDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(featureDmr);
		assertSame(EcorePackage.Literals.EREFERENCE, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRootEClass_noDomainModelEFeature() {
		converter.getRootEClass(featureDmr);
	}

	@Test
	public void getRootEClass_noEReferencePath() {
		featureDmr.setDomainModelEFeature(EcorePackage.Literals.ESTRUCTURAL_FEATURE__CHANGEABLE);
		final EClass result = converter.getRootEClass(featureDmr);
		assertSame(EcorePackage.Literals.ESTRUCTURAL_FEATURE, result);
	}

	@Test
	public void isApplicable_featureDmr() {
		assertEquals(1d, converter.isApplicable(featureDmr), 0d);
	}

	@Test
	public void isApplicable_noFeatureDmr() {
		assertEquals(DmrToRootEClassConverter.NOT_APPLICABLE, converter.isApplicable(mock(VDomainModelReference.class)),
			0d);
	}
}
