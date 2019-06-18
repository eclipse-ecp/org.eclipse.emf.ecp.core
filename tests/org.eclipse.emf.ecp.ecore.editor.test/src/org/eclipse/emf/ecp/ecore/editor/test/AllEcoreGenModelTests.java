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
 * Edgar Mueller - initial API and implementation
 *
 *******************************************************************************/

package org.eclipse.emf.ecp.ecore.editor.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all ECore and genmodel linker related tests.
 *
 * @author emueller
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	EcoreGenModelLinker_PTest.class,
	ProjectName_Test.class
})
public class AllEcoreGenModelTests {

}