/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
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

import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.table.model.VReadOnlyColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * Tests the correct migration of a TableControl's {@link VReadOnlyColumnConfiguration readonly configurations}.
 *
 * @author Lucas Koehler
 *
 */
public class TableDmrReadonlyColumnConfigurationTest extends AbstractMigrationTest {

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
		final VMultiDomainModelReferenceSegment multiSegment = (VMultiDomainModelReferenceSegment) tableDmr
			.getSegments().get(0);

		assertEquals(1, tableControl.getColumnConfigurations().size());
		final VReadOnlyColumnConfiguration readOnlyConfig = (VReadOnlyColumnConfiguration) tableControl
			.getColumnConfigurations().get(0);

		assertEquals(2, readOnlyConfig.getColumnDomainReferences().size());

		final VDomainModelReference aDmr = multiSegment.getChildDomainModelReferences().get(0);
		assertEquals(1, aDmr.getSegments().size());
		final VDomainModelReference xDmr = multiSegment.getChildDomainModelReferences().get(1);
		assertEquals(2, xDmr.getSegments().size());

		assertEquals(xDmr, readOnlyConfig.getColumnDomainReferences().get(0));
		assertEquals(aDmr, readOnlyConfig.getColumnDomainReferences().get(1));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/TableReadOnlyColumnConfig.view";
	}

}
