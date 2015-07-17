/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.swt.core.di.tests;

import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererFactory_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit test suite containing all plugin tests for swt.core.di.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFFormsDIRendererFactory_PTest.class })
public class AllPluginTests {

}
