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
package org.eclipse.emf.ecp.view.edapt.test._190to200;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;

/**
 * JUnit test to test the migration of a FeaturePathDomainModelReference to a {@link VDomainModelReference} with
 * segments.
 *
 * @author Lucas Koehler
 *
 */
public class FeaturePathDMRTest extends AbstractMigrationTest {

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

		final VControl nameControl = (VControl) migratedView.getChildren().get(0);
		final VControl favMerchNameControl = (VControl) migratedView.getChildren().get(1);
		final VControl noPrefixDmrControl = (VControl) migratedView.getChildren().get(2);
		final VControl prefixDmrControl = (VControl) migratedView.getChildren().get(3);
		final VControl cascadedIndexDmrControl = (VControl) migratedView.getChildren().get(4);

		final VDomainModelReference nameDMR = nameControl.getDomainModelReference();
		assertEquals(1, nameDMR.getSegments().size());
		final VFeatureDomainModelReferenceSegment nameSegment = (VFeatureDomainModelReferenceSegment) nameDMR
			.getSegments().get(0);
		assertEquals("name", nameSegment.getDomainModelFeature());

		final VDomainModelReference favMerchNameDMR = favMerchNameControl.getDomainModelReference();
		assertEquals(2, favMerchNameDMR.getSegments().size());
		final VFeatureDomainModelReferenceSegment firstSegment = (VFeatureDomainModelReferenceSegment) favMerchNameDMR
			.getSegments().get(0);
		final VFeatureDomainModelReferenceSegment secondSegment = (VFeatureDomainModelReferenceSegment) favMerchNameDMR
			.getSegments().get(1);
		assertEquals("favouriteMerchandise", firstSegment.getDomainModelFeature());
		assertEquals("name", secondSegment.getDomainModelFeature());

		// Check No Prefix Index Dmr
		final VDomainModelReference noPrefixDmr = noPrefixDmrControl.getDomainModelReference();
		assertEquals(2, noPrefixDmr.getSegments().size());
		final VIndexDomainModelReferenceSegment noPrefixFirstSegment = (VIndexDomainModelReferenceSegment) noPrefixDmr
			.getSegments().get(0);
		assertEquals(1, noPrefixFirstSegment.getIndex());
		assertEquals("visitedTournaments", noPrefixFirstSegment.getDomainModelFeature());
		final VFeatureDomainModelReferenceSegment noPrefixSecondSegment = (VFeatureDomainModelReferenceSegment) noPrefixDmr
			.getSegments().get(1);
		assertEquals("type", noPrefixSecondSegment.getDomainModelFeature());

		// Check index dmr with prefix dmr
		final VDomainModelReference prefixDmr = prefixDmrControl.getDomainModelReference();
		assertEquals(2, prefixDmr.getSegments().size());
		final VIndexDomainModelReferenceSegment prefixFirstSegment = (VIndexDomainModelReferenceSegment) prefixDmr
			.getSegments().get(0);
		assertEquals(2, prefixFirstSegment.getIndex());
		assertEquals("visitedTournaments", prefixFirstSegment.getDomainModelFeature());
		final VFeatureDomainModelReferenceSegment prefixSecondSegment = (VFeatureDomainModelReferenceSegment) prefixDmr
			.getSegments().get(1);
		assertEquals("type", prefixSecondSegment.getDomainModelFeature());

		// Check index dmr with cascaded index dmr
		final VDomainModelReference cascadedDmr = cascadedIndexDmrControl.getDomainModelReference();
		assertEquals(3, cascadedDmr.getSegments().size());
		final VIndexDomainModelReferenceSegment outerIndexSegment = (VIndexDomainModelReferenceSegment) cascadedDmr
			.getSegments().get(0);
		assertEquals("visitedTournaments", outerIndexSegment.getDomainModelFeature());
		assertEquals(1, outerIndexSegment.getIndex());
		final VIndexDomainModelReferenceSegment innerIndexSegment = (VIndexDomainModelReferenceSegment) cascadedDmr
			.getSegments().get(1);
		assertEquals("players", innerIndexSegment.getDomainModelFeature());
		assertEquals(2, innerIndexSegment.getIndex());
		final VFeatureDomainModelReferenceSegment innerFeatureSegment = (VFeatureDomainModelReferenceSegment) cascadedDmr
			.getSegments().get(2);
		assertEquals("name", innerFeatureSegment.getDomainModelFeature());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/Fan.view";
	}

}
