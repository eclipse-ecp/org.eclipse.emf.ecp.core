/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test._190to200;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecp.view.edapt.test.AbstractTemplateMigrationTest;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.template.model.VTStyle;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplate;
import org.eclipse.emf.ecp.view.template.selector.domainmodelreference.model.VTDomainModelReferenceSelector;

import test.localmodel.localmodel.LocalmodelPackage;

/**
 * Tests the migration of a dmr selector whose dmr uses a feature from a locally referenced ecore.
 * 
 * @author Lucas Koehler
 *
 */
public class DmrSelector_LocalEcore extends AbstractTemplateMigrationTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#performTest()
	 */
	@Override
	protected void performTest() throws Exception {
		assertFalse(getMigrator().checkMigration(getURI()));
		getMigrator().performMigration(getURI());
		final VTViewTemplate migratedViewTemplate = getMigratedViewTemplate();

		assertNotNull(migratedViewTemplate);
		assertEquals(1, migratedViewTemplate.getStyles().size());
		final VTStyle style = migratedViewTemplate.getStyles().get(0);
		assertNotNull(style.getSelector());
		assertTrue(VTDomainModelReferenceSelector.class.isInstance(style.getSelector()));
		final VTDomainModelReferenceSelector dmrSelector = (VTDomainModelReferenceSelector) style.getSelector();

		final EClass dmrRootEClass = dmrSelector.getDmrRootEClass();
		assertNotNull(dmrRootEClass);
		assertEquals(LocalmodelPackage.eNS_URI, dmrRootEClass.getEPackage().getNsURI());
		assertEquals(LocalmodelPackage.eNS_PREFIX, dmrRootEClass.getEPackage().getNsPrefix());
		assertEquals(LocalmodelPackage.eNAME, dmrRootEClass.getEPackage().getName());
		assertEquals("TestClass", dmrRootEClass.getName());

		// Check that a dmr in a dmr selector is migrated
		final VDomainModelReference dmr = dmrSelector.getDomainModelReference();
		assertNotNull(dmr);
		final EList<VDomainModelReferenceSegment> segments = dmr.getSegments();
		assertEquals(1, segments.size());
		final VDomainModelReferenceSegment domainModelReferenceSegment = segments.get(0);
		assertTrue(VFeatureDomainModelReferenceSegment.class.isInstance(domainModelReferenceSegment));
		final VFeatureDomainModelReferenceSegment featureSegment = (VFeatureDomainModelReferenceSegment) domainModelReferenceSegment;
		assertEquals("testAttribute", featureSegment.getDomainModelFeature());

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/dmrSelector/localModel.template";
	}

}
