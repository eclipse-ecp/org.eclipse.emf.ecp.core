/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH, and others.
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
 * Christian W. Damus - bug 529542
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.ui.view.swt.test;

import org.eclipse.emf.ecp.ui.view.swt.DefaultReferenceService_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all tests.
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ECPSWTViewRenderer_PTest.class,
	DefaultReferenceService_PTest.class,
	DefaultCreateNewModelElementStrategyProvider_PTest.class
})
public class AllTests {

}
