/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.model.util;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.internal.table.generator.TableColumnGenerator;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
public class TableColumnGenerator_Test {

	private EClass subClass;
	private EAttribute foo;
	private EAttribute bar;

	@Before
	public void setUp() throws Exception {
		final EClass baseClass = EcoreFactory.eINSTANCE.createEClass();
		foo = EcoreFactory.eINSTANCE.createEAttribute();
		foo.setEType(EcorePackage.eINSTANCE.getEString());
		baseClass.getEStructuralFeatures().add(foo);

		subClass = EcoreFactory.eINSTANCE.createEClass();
		bar = EcoreFactory.eINSTANCE.createEAttribute();
		bar.setEType(EcorePackage.eINSTANCE.getEString());
		subClass.getEStructuralFeatures().add(bar);

		subClass.getESuperTypes().add(baseClass);
	}

	@Test
	public void testGenerateColumns() {

		final VTableControl control = VTableFactory.eINSTANCE.createTableControl();
		final VTableDomainModelReference dmr = VTableFactory.eINSTANCE.createTableDomainModelReference();
		control.setDomainModelReference(dmr);
		TableColumnGenerator.generateColumns(subClass, control);

		final EList<VDomainModelReference> columns = dmr.getColumnDomainModelReferences();

		assertEquals(2, columns.size());
		assertEquals(foo, ((VFeaturePathDomainModelReference) columns.get(0)).getDomainModelEFeature());
		assertEquals(bar, ((VFeaturePathDomainModelReference) columns.get(1)).getDomainModelEFeature());

	}

}
