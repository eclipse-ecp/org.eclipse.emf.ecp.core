/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ValidationServiceGC_PTest.class,
	ViewValidation_PTest.class,
	VDiagnosticHelper_Test.class,
	ValidationProvider_PTest.class,
	SubstitutionLabel_PTest.class,
	TableValidation_PTest.class,
	ValidationPerformance_ITest.class,
	DynamicDMR_PTest.class
})
public class AllTests {

}
