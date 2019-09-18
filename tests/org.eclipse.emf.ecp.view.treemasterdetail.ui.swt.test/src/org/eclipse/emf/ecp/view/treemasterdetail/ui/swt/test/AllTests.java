/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.test;

import org.eclipse.emf.ecp.view.spi.treemasterdetail.ui.swt.TreeMasterDetailRenderer_PTest;
import org.eclipse.emf.ecp.view.treemasterdetail.ui.swt.internal.TreeRevealProvider_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite of all tests in the bundle.
 */
@RunWith(Suite.class)
@SuiteClasses({
	TreeMasterDetailRenderer_PTest.class,
	TreeMasterDetailContextMenuViewModelService_PTest.class,
	TreeRevealProvider_PTest.class,
})
public class AllTests {

	/**
	 * Initializes me.
	 */
	public AllTests() {
		// Specification is in the annotations
	}

}
