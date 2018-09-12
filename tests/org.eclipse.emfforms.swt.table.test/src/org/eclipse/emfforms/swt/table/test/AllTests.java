/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.table.test;

import org.eclipse.emfforms.internal.swt.table.action.KeyBindingManager_PTest;
import org.eclipse.emfforms.spi.swt.table.action.TableActionBar_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All unit tests for org.eclipse.emfforms.swt.table.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ KeyBindingManager_PTest.class, TableActionBar_PTest.class })
public class AllTests {

}
