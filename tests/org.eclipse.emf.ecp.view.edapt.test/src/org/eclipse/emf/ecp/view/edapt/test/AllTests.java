/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.edapt.test;

import org.eclipse.emf.ecp.view.edapt.test._1160to1200.All1160to1200Tests;
import org.eclipse.emf.ecp.view.edapt.test._140to1170.All140to1170Tests;
import org.eclipse.emf.ecp.view.edapt.test._160to1170.All160to1170Tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	All140to1170Tests.class,
	All160to1170Tests.class,
	All1160to1200Tests.class
})
public class AllTests {

}
