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
package org.eclipse.emfforms.internal.core.services.tests;

import org.eclipse.emfforms.internal.core.services.reveal.EMFFormsRevealServiceImpl_ITest;
import org.eclipse.emfforms.internal.core.services.scoped.EMFFormsScopedServicesFactoryImpl_ITest;
import org.eclipse.emfforms.internal.core.services.scoped.SettingToControlMapper_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EMFFormsScopedServicesFactoryImpl_ITest.class,
	SettingToControlMapper_ITest.class,
	EMFFormsRevealServiceImpl_ITest.class,
})
public class AllIntegrationTests {

}
