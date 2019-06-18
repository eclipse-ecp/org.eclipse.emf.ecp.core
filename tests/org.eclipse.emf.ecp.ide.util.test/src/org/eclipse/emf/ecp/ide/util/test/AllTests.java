/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
	EcoreHelperCyclicDependencies_PTest.class,
	EcoreHelperDefaultPackageRegistryContents_PTest.class,
	EcoreHelperLoadEcoreExceptions_PTest.class,
	EcoreHelperNoDependencies_PTest.class,
	EcoreHelperOneDependency_PTest.class,
	EcoreHelperRegistryDependencies_PTest.class,
	EcoreHelperSubpackages_PTest.class,
	EcoreHelperTwoDependencies_PTest.class,
})
public class AllTests {

}
