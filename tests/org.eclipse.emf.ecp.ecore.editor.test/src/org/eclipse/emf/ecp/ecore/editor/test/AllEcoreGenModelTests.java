/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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