/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
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
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.core.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all tests.
 *
 * @author eneufeld
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ECPProviderRegistry_PTest.class,
	ECPProjectManager_PTest.class,
	ECPProject_PTest.class,
	ECPProvider_PTest.class,
	ECPRepository_PTest.class })
/*
 * deactivated, because hudson is 50 times slower than local machine.
 * ECPInitializationTest.class
 */
public class AllTests {

}