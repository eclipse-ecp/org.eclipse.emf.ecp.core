/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt.cell;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.edit.spi.swt.table.ECPCellEditorTester;
import org.junit.Before;
import org.junit.Test;

public class MultiReferenceCellEditorTester_Test {

	private MultiReferenceCellEditorTester tester;

	@Before
	public void setUp() throws Exception {
		tester = new MultiReferenceCellEditorTester();
	}

	@Test
	public void testIsApplicable() {
		assertEquals(ECPCellEditorTester.NOT_APPLICABLE,
			tester.isApplicable(null, EcorePackage.eINSTANCE.getENamedElement_Name(), null));
		assertEquals(ECPCellEditorTester.NOT_APPLICABLE,
			tester.isApplicable(null, EcorePackage.eINSTANCE.getEClassifier_EPackage(), null));
		assertEquals(5, tester.isApplicable(null, EcorePackage.eINSTANCE.getEPackage_EClassifiers(), null));

	}

}
