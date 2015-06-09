/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.categorization.swt.test;

import org.eclipse.emf.ecp.view.spi.categorization.swt.CategorizationRenderer_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Categorization_PTest.class, CategorizationCorrectTester_PTest.class,
	CategorizationTreeContentRefresh_PTest.class, CategorizationTreeRefresh_PTest.class,
	CategorizationRenderer_PTest.class })
public class AllTests {

}
