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
 * Eugen Neufeld - initial API and implementation
 * Christian W. Damus - bug 548592
 ******************************************************************************/
package org.eclipse.emf.ecp.view.context.tests;

import org.eclipse.emf.ecp.view.internal.context.ViewModelContextContextFunction_ITest;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ViewModelContextImpl_ITest.class,
	ViewModelContextContextFunction_ITest.class,
})
public class AllIntegrationTests {

}
