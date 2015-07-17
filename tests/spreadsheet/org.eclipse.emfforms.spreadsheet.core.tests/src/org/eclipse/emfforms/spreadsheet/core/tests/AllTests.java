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

import org.eclipse.emfforms.internal.spreadsheet.core.EMFFormsSpreadsheetRendererFactoryImpl_Test;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.EMFFormsSpreadsheetMultiAttributeConverter_Test;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.EMFFormsSpreadsheetMultiReferenceConverter_Test;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.EMFFormsSpreadsheetSingleAttributeConverter_Test;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.EMFFormsSpreadsheetSingleReferenceConverter_Test;
import org.eclipse.emfforms.internal.spreadsheet.core.converter.EMFFormsSpreadsheetValueConverterRegistryImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for spreadsheet.core.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	EMFFormsSpreadsheetRendererFactoryImpl_Test.class,
	EMFFormsSpreadsheetValueConverterRegistryImpl_Test.class,
	EMFFormsSpreadsheetMultiAttributeConverter_Test.class,
	EMFFormsSpreadsheetMultiReferenceConverter_Test.class,
	EMFFormsSpreadsheetSingleReferenceConverter_Test.class,
	EMFFormsSpreadsheetSingleAttributeConverter_Test.class
})
public class AllTests {
	// JUnit 4 TestSuite
}
