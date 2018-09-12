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
package org.eclipse.emfforms.swt.core.tests;

import org.eclipse.emfforms.internal.swt.core.EMFFormsRendererFactory_ITest;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer_PTest;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for integration tests of the simple.swt .
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	AbstractSWTRenderer_PTest.class,
	EMFFormsRendererFactory_ITest.class,
	SWTDataElementIdHelper_ITest.class
})
public class AllIntegrationTests {

}
