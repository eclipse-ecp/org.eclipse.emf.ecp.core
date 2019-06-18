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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.ui.editor.test;

import org.eclipse.emf.ecp.view.ui.editor.test.controls.ControlsSWTBot_PTest;
import org.eclipse.emf.ecp.view.ui.editor.test.controls.TableControlSWTBot_PTest;
import org.eclipse.emf.ecp.view.ui.editor.test.controls.XmlDateControlSWTBotPTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author jfaltermeier
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	OpenEditor_PTest.class,
	// ModifyNavigatorTest.class,
	DynamicContainmentTreeSWTBot_PTest.class,
	ControlsSWTBot_PTest.class,
	TableControlSWTBot_PTest.class,
	XmlDateControlSWTBotPTest.class,
	CellReadOnly_PTest.class
})
public class AllUITests {

}
