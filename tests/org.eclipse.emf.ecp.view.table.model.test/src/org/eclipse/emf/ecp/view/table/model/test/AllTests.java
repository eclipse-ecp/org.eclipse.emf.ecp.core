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
package org.eclipse.emf.ecp.view.table.model.test;

import org.eclipse.emf.ecp.view.spi.table.model.util.TableColumnGenerator_Test;
import org.eclipse.emf.ecp.view.spi.table.model.util.TableDMRValidation_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite for ecp.view.table.model .
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ TableDMRValidation_Test.class, TableColumnGenerator_Test.class })
public class AllTests {
	// JUnit 4 Test suite
}
