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
 * Christian W. Damus - bugs 527826, 543160
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.test;

import org.eclipse.emf.ecp.view.spi.model.DiagnosticMessageExtractor_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test suite for ecp.view.model .
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	DomainModelReference_Test.class,
	DiagnosticMessageExtractor_Test.class,
	DefaultViewModelProperties_Test.class,
	VElement_Test.class,
	VDiagnostic_Test.class,
})
public class AllTests {
	// JUnit 4 Test Suite
}
