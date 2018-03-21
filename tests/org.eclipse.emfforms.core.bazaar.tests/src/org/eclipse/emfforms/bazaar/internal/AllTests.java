/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 529542
 ******************************************************************************/
package org.eclipse.emfforms.bazaar.internal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for bazaar Tests.
 *
 * @author Jonas Helming
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ Bazaar_ITest.class,
	BazaarBuilderTest.class,
	ThreadSafeBazaar_ITest.class,
	Bazaar_PTest.class
})
public class AllTests {

}
