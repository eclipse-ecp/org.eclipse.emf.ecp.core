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
 * Christian W. Damus - bug 527686
 ******************************************************************************/
package org.eclipse.emfforms.internal.core.services.tests;

import org.eclipse.emfforms.core.services.view.EMFFormsContextTracker_Test;
import org.eclipse.emfforms.internal.core.services.scoped.EMFFormsScopedServicesFactoryImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	EMFFormsScopedServicesFactoryImpl_Test.class,
	EMFFormsContextTracker_Test.class,
})
public class AllTests {

}
