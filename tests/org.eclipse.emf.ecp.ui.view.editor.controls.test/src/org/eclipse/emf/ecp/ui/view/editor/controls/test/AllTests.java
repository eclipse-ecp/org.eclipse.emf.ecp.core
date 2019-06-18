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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.ui.view.editor.controls.test;

import org.eclipse.emf.ecp.view.internal.editor.controls.ExpectedValueControlRenderer_Test;
import org.eclipse.emf.ecp.view.internal.editor.controls.LeafConditionControlRenderer_Test;
import org.eclipse.emf.ecp.view.internal.editor.controls.LeafConditionSegmentControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.editor.controls.TableColumnsDMRTableControl_Test;
import org.eclipse.emf.ecp.view.internal.editor.handler.ControlGenerator_PTest;
import org.eclipse.emf.ecp.view.internal.editor.handler.GenerateControlsHandler_Test;
import org.eclipse.emf.ecp.view.internal.editor.handler.GenerateTableColumnSegmentDmrsHandler_Test;
import org.eclipse.emf.ecp.view.internal.editor.handler.LeafConditionDmrNewModelElementStrategyProvider_Test;
import org.eclipse.emf.ecp.view.internal.editor.handler.LeafConditionDmrOpenInNewContextStrategyProvider_Test;
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
	LeafConditionSegmentControlRenderer_PTest.class,
	LeafConditionDmrNewModelElementStrategyProvider_Test.class,
	LeafConditionDmrOpenInNewContextStrategyProvider_Test.class,
	GenerateTableColumnSegmentDmrsHandler_Test.class
})
public class AllTests {

}
