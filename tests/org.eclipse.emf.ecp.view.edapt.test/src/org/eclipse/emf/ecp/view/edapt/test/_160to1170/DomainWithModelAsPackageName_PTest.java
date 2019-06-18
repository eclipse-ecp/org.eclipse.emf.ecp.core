/*******************************************************************************
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test._160to1170;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.eclipse.emf.ecp.view.edapt.test.AbstractMigrationTest;
import org.eclipse.emf.ecp.view.edapt.test.model.ModelPackage;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;

public class DomainWithModelAsPackageName_PTest extends AbstractMigrationTest {

	@Override
	protected void performTest() throws Exception {
		assertFalse(getMigrator().checkMigration(getURI()));
		getMigrator().performMigration(getURI());
		final VView view = getMigratedView();
		assertEquals(1, view.getChildren().size());
		final VControl control = VControl.class.cast(view.getChildren().get(0));
		final VFeaturePathDomainModelReference dmr = VFeaturePathDomainModelReference.class
			.cast(control.getDomainModelReference());
		assertSame(ModelPackage.eINSTANCE.getFoo_Bar(), dmr.getDomainModelEFeature());
		assertEquals(1, view.getEcorePaths().size());
		assertEquals("/org.eclipse.emf.ecp.view.edapt.test/model/EdaptTestModel.ecore", view.getEcorePaths().get(0));
	}

	@Override
	protected String getPath() {
		return "160/DomainWithModelName.view";
	}

}
