/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
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
package org.eclipse.emf.ecp.view.core.swt.tests;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.BooleanControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.DateTimeControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.SimpleControlSWTRenderer_PTest;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for Unit Tests.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ BooleanControlRenderer_PTest.class,
	TimeBoundStringBuffer_Test.class,
	SimpleControlSWTRenderer_PTest.class,
	AbstractControlSWTRenderer_PTest.class,
	DateTimeControlRenderer_PTest.class
})
public class AllTests {
	// JUnit 4 Test Suite
}
