/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.common.validation.tests;

import org.eclipse.emfforms.common.internal.validation.ValidationServiceImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Master test suite for the {@code efforms.common.validation} bundle.
 *
 * @author Christian W. Damus
 */
@RunWith(Suite.class)
@SuiteClasses({ //
	ValidationServiceImpl_Test.class, //
})
public class AllTests {
	// Specified in the annotations
}
