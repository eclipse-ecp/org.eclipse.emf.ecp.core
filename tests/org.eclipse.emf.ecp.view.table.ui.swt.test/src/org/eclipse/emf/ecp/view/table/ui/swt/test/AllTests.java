/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 * Lucas Koehler - added SWTTable_Test
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import org.eclipse.emf.ecp.view.internal.table.swt.cell.MultiReferenceCellEditor_PTest;
import org.eclipse.emf.ecp.view.internal.table.swt.cell.MultiReferenceTooltipModifier_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.SWTTableDatabindingLabel_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.SWTTable_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SWTTable_PTest.class, SWTTableDatabindingLabel_PTest.class, RunnableManagerTest.class,
	TableControlDetailPanelRenderer_PTest.class, MultiReferenceTooltipModifier_PTest.class, MultiReferenceCellEditor_PTest.class })
public class AllTests {

}
