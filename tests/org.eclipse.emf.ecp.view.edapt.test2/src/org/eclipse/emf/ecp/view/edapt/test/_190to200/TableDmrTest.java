/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * JUnit test to test the migration of table dmrs to multi segments.
 *
 * @author Lucas Koehler
 *
 */
public class TableDmrTest extends AbstractMigrationTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#performTest()
	 */
	@Override
	protected void performTest() throws Exception {
		getMigrator().performMigration(getURI());
		final VView migratedView = getMigratedView();

		final VTableControl playersControl = (VTableControl) migratedView.getChildren().get(0);

		final VDomainModelReference playersDmr = playersControl.getDomainModelReference();
		assertEquals(VViewPackage.eINSTANCE.getDomainModelReference(), playersDmr.eClass());
		assertEquals(1, playersDmr.getSegments().size());

		final VMultiDomainModelReferenceSegment playersMultiSegment = (VMultiDomainModelReferenceSegment) playersDmr
			.getSegments().get(0);
		final EList<VDomainModelReference> playersChildDmrs = playersMultiSegment.getChildDomainModelReferences();
		assertEquals(3, playersChildDmrs.size());

		final VDomainModelReference playersChildDmr0 = playersChildDmrs.get(0);
		assertEquals(VViewPackage.eINSTANCE.getDomainModelReference(), playersChildDmr0.eClass());
		final VDomainModelReference playersChildDmr1 = playersChildDmrs.get(1);
		assertEquals(VViewPackage.eINSTANCE.getDomainModelReference(), playersChildDmr1.eClass());
		final VDomainModelReference playersChildDmr2 = playersChildDmrs.get(2);
		assertEquals(VViewPackage.eINSTANCE.getDomainModelReference(), playersChildDmr2.eClass());

		final VFeatureDomainModelReferenceSegment playersNameSegment = (VFeatureDomainModelReferenceSegment) playersChildDmr0
			.getSegments().get(0);
		assertEquals("name", playersNameSegment.getDomainModelFeature());

		final VFeatureDomainModelReferenceSegment playersDateOfBirthSegment = (VFeatureDomainModelReferenceSegment) playersChildDmr1
			.getSegments().get(0);
		assertEquals("dateOfBirth", playersDateOfBirthSegment.getDomainModelFeature());
		final VFeatureDomainModelReferenceSegment playersGenderSegment = (VFeatureDomainModelReferenceSegment) playersChildDmr2
			.getSegments().get(0);
		assertEquals("gender", playersGenderSegment.getDomainModelFeature());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/League.view";
	}

}
