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
import org.eclipse.emf.ecp.view.spi.indexdmr.model.VIndexDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * JUnit test that tests that a cascaded child dmr which is not a simple feature path dmr is migrated correctly.
 *
 * @author Lucas Koehler
 *
 */
public class CascadedChildTableDmrTest extends AbstractMigrationTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#performTest()
	 */
	@Override
	protected void performTest() throws Exception {
		getMigrator().performMigration(getURI());
		final VView migratedView = getMigratedView();

		final VTableControl tableControl = (VTableControl) migratedView.getChildren().get(0);

		final VDomainModelReference tableDmr = tableControl.getDomainModelReference();
		final EList<VDomainModelReferenceSegment> segments = tableDmr.getSegments();
		assertEquals(1, segments.size());
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) segments.get(0);
		assertEquals("cList", multiSegment.getDomainModelFeature());
		assertEquals(1, multiSegment.getChildDomainModelReferences().size());
		final VDomainModelReference childDmr = multiSegment.getChildDomainModelReferences().get(0);
		final EList<VDomainModelReferenceSegment> childSegments = childDmr.getSegments();
		assertEquals(4, childSegments.size());
		final VIndexDomainModelReferenceSegment childIndexSegment = (VIndexDomainModelReferenceSegment) childSegments
			.get(2);
		assertEquals(2, childIndexSegment.getIndex());
		assertEquals("cList", childIndexSegment.getDomainModelFeature());

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/CascadedChildDmr.view";
	}

}
