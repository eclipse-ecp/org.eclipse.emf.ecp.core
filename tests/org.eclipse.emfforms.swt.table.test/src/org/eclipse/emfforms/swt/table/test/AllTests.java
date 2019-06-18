/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 530314
 ******************************************************************************/
package org.eclipse.emfforms.swt.table.test;

import org.eclipse.emfforms.internal.swt.table.action.KeyBindingManager_PTest;
import org.eclipse.emfforms.spi.swt.table.ColumnConfigurationBuilder_PTest;
import org.eclipse.emfforms.spi.swt.table.TableConfigurationBuilder_PTest;
import org.eclipse.emfforms.spi.swt.table.TableViewerSWTBuilder_PTest;
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
@SuiteClasses({
	KeyBindingManager_PTest.class,
	TableActionBar_PTest.class,
	TableViewerSWTBuilder_PTest.class,
	TableConfigurationBuilder_PTest.class,
	ColumnConfigurationBuilder_PTest.class,
})
public class AllTests {

}
