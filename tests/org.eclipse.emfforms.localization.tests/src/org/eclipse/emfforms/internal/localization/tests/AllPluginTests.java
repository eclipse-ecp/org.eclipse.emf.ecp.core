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
package org.eclipse.emfforms.internal.localization.tests;

import org.eclipse.emfforms.spi.localization.LocalizationServiceHelper_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Plugin Test Suite for emfforms.localization .
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ LocalizationServiceHelper_PTest.class })
public class AllPluginTests {
	// JUnit 4 Test Suite
}
