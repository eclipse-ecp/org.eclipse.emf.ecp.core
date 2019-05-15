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
 * Jonas
 *
 *******************************************************************************/
package org.eclipse.emf.ecp.view.model.provider.xmi.test;

import org.eclipse.emf.ecp.view.model.provider.xmi.Migration_ITest;
import org.eclipse.emf.ecp.view.model.provider.xmi.ViewModelFileExtensionsManager_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ViewModelFileExtensionsManager_ITest.class,
	Migration_ITest.class
})
public class AllTests {

}
