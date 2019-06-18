/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.core.swt.tests;

import org.eclipse.emf.ecp.view.internal.core.swt.renderer.DateTimeControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.EnumComboViewerRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.LinkControlSWTRenderer_Containment_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.LinkControlSWTRenderer_CrossReference_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.NumberControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.ViewRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.core.swt.renderer.XMLDateControlRenderer_PTest;
import org.eclipse.emf.ecp.view.spi.core.swt.AbstractControlSWTRenderer_PTest;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlRendererUnsettable_PTest;
import org.eclipse.emf.ecp.view.spi.core.swt.renderer.TextControlRenderer_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for OSGi Tests.
 *
 * @author Eugen Neufeld
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CorrectDipose_PTest.class, DynamicDMR_PTest.class, ViewRenderer_PTest.class,
	TextControlRendererUnsettable_PTest.class, NumberControlRenderer_PTest.class, XMLDateControlRenderer_PTest.class,
	TextControlRenderer_PTest.class, DateTimeControlRenderer_PTest.class, AbstractControlSWTRenderer_PTest.class,
	LinkControlSWTRenderer_CrossReference_PTest.class, LinkControlSWTRenderer_Containment_PTest.class,
	EnumComboViewerRenderer_PTest.class })
public class AllPluginTests {

}
