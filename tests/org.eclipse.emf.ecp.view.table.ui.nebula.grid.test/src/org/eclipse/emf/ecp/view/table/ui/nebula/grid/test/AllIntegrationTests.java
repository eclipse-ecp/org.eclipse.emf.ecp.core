/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.nebula.grid.test;

import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridControlRenderer_PTest;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridPasteKeyListener_Test;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.GridTable_PTest;
import org.eclipse.emf.ecp.view.internal.table.nebula.grid.KeyListenerUtil_PTest;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridControlDetailPanelRenderer_PTest;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridViewerColumnBuilder_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All integration/plugin tests for org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ GridControlRenderer_PTest.class, GridPasteKeyListener_Test.class, GridTable_PTest.class,
	GridControlDetailPanelRenderer_PTest.class, GridViewerColumnBuilder_ITest.class, KeyListenerUtil_PTest.class })
public class AllIntegrationTests {

}
