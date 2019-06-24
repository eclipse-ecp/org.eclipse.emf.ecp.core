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
 * Jonas - initial API and implementation
 * Lucas Koehler - added SWTTable_Test
 * Christian W. Damus - add TableControlSWTRenderer_ITest
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import org.eclipse.emf.ecp.view.internal.table.swt.TableDetailRevealProvider_PTest;
import org.eclipse.emf.ecp.view.internal.table.swt.TableRevealProvider_PTest;
import org.eclipse.emf.ecp.view.internal.table.swt.cell.MultiReferenceCellEditor_PTest;
import org.eclipse.emf.ecp.view.internal.table.swt.cell.MultiReferenceTooltipModifier_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.ItemProviderEnumCellEditor_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.LocalizedEnumeratorComparator_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.SWTTableDatabindingLabel_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.SWTTable_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlDetailPanelRenderer_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlRendererSort_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRendererPerformance_PTest;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SWTTable_PTest.class, SWTTableDatabindingLabel_PTest.class, RunnableManagerTest.class,
	TableControlDetailPanelRenderer_PTest.class, MultiReferenceTooltipModifier_PTest.class,
	MultiReferenceCellEditor_PTest.class, TableControlSWTRendererPerformance_PTest.class,
	TableControlSWTRenderer_ITest.class, TableControlRendererSort_PTest.class,
	ItemProviderEnumCellEditor_PTest.class, LocalizedEnumeratorComparator_PTest.class,
	TableRevealProvider_PTest.class, TableDetailRevealProvider_PTest.class,
})
public class AllTests {

}
