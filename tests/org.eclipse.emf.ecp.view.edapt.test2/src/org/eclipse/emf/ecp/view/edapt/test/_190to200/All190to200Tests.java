/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test._190to200;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Contains all JUnit tests testing the custom migration which migrates the "old" DMRs to "new" ones with segments.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ FeaturePathDMRTest.class, MappingDMRTest.class, TableDmrTest.class, CascadedTableDmrTest.class,
	CascadedChildTableDmrTest.class, TableDmrWidthConfigurationTest.class,
	TableDmrReadonlyColumnConfigurationTest.class })
public class All190to200Tests {

}
