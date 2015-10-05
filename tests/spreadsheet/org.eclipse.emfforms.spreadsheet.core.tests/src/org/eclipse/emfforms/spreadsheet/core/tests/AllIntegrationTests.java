/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.spreadsheet.core.tests;

import org.eclipse.emfforms.internal.spreadsheet.core.EMFFormsSpreadsheetExporterImpl_ITest;
import org.eclipse.emfforms.internal.spreadsheet.core.EMFFormsSpreadsheetImportImpl_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Integration Test Suite for spreadsheet.core.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFFormsSpreadsheetExporterImpl_ITest.class, EMFFormsSpreadsheetImportImpl_ITest.class })
public class AllIntegrationTests {
	// JUnit 4 TestSuite
}
