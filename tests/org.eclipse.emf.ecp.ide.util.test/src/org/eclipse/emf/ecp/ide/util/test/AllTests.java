/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.emf.ecp.ide.util.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EcoreHelperDefaultPackageRegistryContents_PTest.class,
	EcoreHelperNoDependencies_PTest.class,
	EcoreHelperOneDependency_PTest.class,
	EcoreHelperTwoDependencies_PTest.class,
	EcoreHelperCyclicDependencies_PTest.class,
	EcoreHelperSubpackages_PTest.class,
	EcoreHelperLoadEcoreExceptions_PTest.class })
public class AllTests {

}
