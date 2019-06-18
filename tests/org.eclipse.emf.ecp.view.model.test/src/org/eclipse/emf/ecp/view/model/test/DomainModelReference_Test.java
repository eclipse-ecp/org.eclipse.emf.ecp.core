/*******************************************************************************
 * Copyright (c) 2014-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 543160
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.test;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.junit.Before;
import org.junit.Test;

public class DomainModelReference_Test {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFeaturePathDomainModelReferencePathWithNonUniqueReferences() {
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final LinkedList<EReference> referencePath = new LinkedList<EReference>();
		final EReference ref1 = EcoreFactory.eINSTANCE.createEReference();
		final EReference ref2 = EcoreFactory.eINSTANCE.createEReference();
		referencePath.add(ref1);
		referencePath.add(ref2);
		referencePath.add(ref1);
		final EReference feature = EcoreFactory.eINSTANCE.createEReference();
		control.setDomainModelReference(feature, referencePath);
		assertEquals(3, referencePath.size());
		assertEquals(3, ((VFeaturePathDomainModelReference) control.getDomainModelReference())
			.getDomainModelEReferencePath().size());

	}

}
