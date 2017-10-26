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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.table.model.VEnablementConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;

/**
 * Tests the correct migration of a TableControl's {@link VEnablementConfiguration VEnablementConfigurations}.
 *
 * @author Lucas Koehler
 *
 */
public class TableDmrEnablementConfigurationTest extends AbstractMigrationTest {

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

		assertEquals(2, tableControl.getColumnConfigurations().size());
		final VEnablementConfiguration aEnableConfig = (VEnablementConfiguration) tableControl.getColumnConfigurations()
			.get(0);
		final VEnablementConfiguration xEnableConfig = (VEnablementConfiguration) tableControl.getColumnConfigurations()
			.get(1);

		final VDomainModelReference aDmr = multiSegment.getChildDomainModelReferences().get(0);
		final VDomainModelReference xDmr = multiSegment.getChildDomainModelReferences().get(1);

		assertEquals(aDmr, aEnableConfig.getColumnDomainReference());
		assertEquals("a name", aEnableConfig.getName());
		assertTrue(aEnableConfig.isReadonly());
		assertEquals(xDmr, xEnableConfig.getColumnDomainReference());
		assertEquals("x name", xEnableConfig.getName());
		assertFalse(xEnableConfig.isReadonly());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest#getPath()
	 */
	@Override
	protected String getPath() {
		return "200/TableEnablementConfig.view";
	}

}
