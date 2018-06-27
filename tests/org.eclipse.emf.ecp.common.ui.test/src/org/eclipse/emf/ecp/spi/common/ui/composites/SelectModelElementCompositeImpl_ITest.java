/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.spi.common.ui.composites;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;

public class SelectModelElementCompositeImpl_ITest {

	private Shell shell;

	@Before
	public void setUp() throws Exception {
		shell = new Shell();
	}

	@Test
	public void testCreateViewerComposite() {
		final List<EClass> input = new ArrayList<EClass>();
		final EClass eClassB = EcoreFactory.eINSTANCE.createEClass();
		eClassB.setName("B");
		input.add(eClassB);

		final EClass eClassA = EcoreFactory.eINSTANCE.createEClass();
		eClassA.setName("A");
		input.add(eClassA);

		final SelectModelElementCompositeImpl s = new SelectModelElementCompositeImpl(input, false);
		final TableViewer tableViewer = s.createViewer(shell);
		final EClass first = (EClass) tableViewer.getElementAt(0);
		assertEquals(eClassA, first);
		final EClass second = (EClass) tableViewer.getElementAt(1);
		assertEquals(eClassB, second);
	}

}
