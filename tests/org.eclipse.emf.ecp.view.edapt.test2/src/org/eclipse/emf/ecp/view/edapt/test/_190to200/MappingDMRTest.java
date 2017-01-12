/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test._190to200;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.mappingdmr.model.VMappingDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;

/**
 * @author Lucas
 *
 */
public class MappingDMRTest extends AbstractMigrationTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#performTest()
	 */
	@Override
	protected void performTest() throws Exception {
		assertFalse(getMigrator().checkMigration(getURI()));
		getMigrator().performMigration(getURI());
		final VView migratedView = getMigratedView();

		final VControl simpleMapping = (VControl) migratedView.getChildren().get(0);
		final VControl cascadedMapping = (VControl) migratedView.getChildren().get(1);

		// Check simple Mapping DMR
		final VDomainModelReference simpleMappingDmr = simpleMapping.getDomainModelReference();
		final EList<VDomainModelReferenceSegment> simpleSegments = simpleMappingDmr.getSegments();
		assertEquals("b", ((VFeatureDomainModelReferenceSegment) simpleSegments.get(0)).getDomainModelFeature());
		assertEquals("c", ((VFeatureDomainModelReferenceSegment) simpleSegments.get(1)).getDomainModelFeature());
		final VMappingDomainModelReferenceSegment simpleMappingSegment = (VMappingDomainModelReferenceSegment) simpleSegments
			.get(2);
		assertEquals("eClassToA", simpleMappingSegment.getDomainModelFeature());
		assertEquals(TestPackage.eINSTANCE.getA(), simpleMappingSegment.getMappedClass());
		assertEquals(4, simpleSegments.size());

		// check Mapping DMR with one cascaded Mapping DMR
		final VDomainModelReference cascadedMappingDmr = cascadedMapping.getDomainModelReference();
		final EList<VDomainModelReferenceSegment> cascadedSegments = cascadedMappingDmr.getSegments();
		assertEquals(7, cascadedSegments.size());
		final VMappingDomainModelReferenceSegment firstMappingSegment = (VMappingDomainModelReferenceSegment) cascadedSegments
			.get(2);
		assertEquals("eClassToA", firstMappingSegment.getDomainModelFeature());
		assertEquals(TestPackage.eINSTANCE.getD(), firstMappingSegment.getMappedClass());
		final VMappingDomainModelReferenceSegment cascadedMappingSegment = (VMappingDomainModelReferenceSegment) cascadedSegments
			.get(5);
		assertEquals("eClassToA", cascadedMappingSegment.getDomainModelFeature());
		assertEquals(TestPackage.eINSTANCE.getA(), cascadedMappingSegment.getMappedClass());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/A.view";
	}

}
