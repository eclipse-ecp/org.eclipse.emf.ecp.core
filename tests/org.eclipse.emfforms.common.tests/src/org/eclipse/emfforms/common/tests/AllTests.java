/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
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
 * Christian W. Damus - bug 545686
 ******************************************************************************/
package org.eclipse.emfforms.common.tests;

import org.eclipse.emfforms.spi.common.locale.AbstractEMFFormsLocaleProvider_Test;
import org.eclipse.emfforms.spi.common.validation.DiagnosticFrequencyMap_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Junit Test cases for emfforms.common.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ AbstractEMFFormsLocaleProvider_Test.class,
	RankingHelper_Test.class,
	DiagnosticFrequencyMap_Test.class,
})
public class AllTests {
	// JUnit 4 Test Suite
}
