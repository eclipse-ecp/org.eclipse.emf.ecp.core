/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.ui.editor.test;

import org.eclipse.emf.ecp.view.ui.editor.test.controls.ControlsSWTBotTest;
import org.eclipse.emf.ecp.view.ui.editor.test.controls.TableControlSWTBotTest;
import org.eclipse.emf.ecp.view.ui.editor.test.controls.XmlDateControlSWTBotTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author jfaltermeier
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	OpenEditorTest.class,
	// ModifyNavigatorTest.class,
	DynamicContainmentTreeSWTBotTest.class,
	ControlsSWTBotTest.class,
	TableControlSWTBotTest.class,
	XmlDateControlSWTBotTest.class
})
public class AllUITests {

}
