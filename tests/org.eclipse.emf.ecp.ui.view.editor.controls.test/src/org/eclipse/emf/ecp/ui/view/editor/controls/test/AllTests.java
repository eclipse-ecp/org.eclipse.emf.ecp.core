/*******************************************************************************
 * Copyright (c) 2011-2013 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.editor.controls.test;

import org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer_Test;
import org.eclipse.emf.ecp.view.internal.editor.controls.LeafConditionControlRenderer_Test;
import org.eclipse.emf.ecp.view.internal.editor.controls.LeafConditionSegmentControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.editor.controls.TableColumnsDMRTableControl_Test;
import org.eclipse.emf.ecp.view.internal.editor.handler.GenerateControlsHandler_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	SelectAttributes_Test.class,
	ControlGenerator_PTest.class,
	Helper_Test.class,
	GenerateControlsHandler_Test.class,
	ExpectedValueControlRenderer_Test.class,
	LeafConditionControlRenderer_Test.class,
	TableColumnsDMRTableControl_Test.class,
	LeafConditionSegmentControlRenderer_PTest.class
})
public class AllTests {

}
