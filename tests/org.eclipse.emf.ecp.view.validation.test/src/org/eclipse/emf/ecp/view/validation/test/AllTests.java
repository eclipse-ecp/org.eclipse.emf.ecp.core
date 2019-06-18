/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Jonas Helming - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.validation.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ValidationServiceGC_PTest.class,
	ValidationService_PTest.class,
	ViewValidation_PTest.class,
	VDiagnosticHelper_Test.class,
	ValidationProvider_PTest.class,
	SubstitutionLabel_PTest.class,
	TableValidation_PTest.class,
	ValidationPerformance_ITest.class,
	DynamicDMR_PTest.class,
	PreSetValidationService_Test.class,
	ParallelValidationService_PTest.class
})
public class AllTests {

}
