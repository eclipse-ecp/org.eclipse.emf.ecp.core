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
 * EclipseSource - initial API and implementation
 * Christian W. Damus - bug 544499
 ******************************************************************************/
package org.eclipse.emfforms.ide.builder.test;

import org.eclipse.emfforms.ide.builder.ValidationBuilder_PTest;
import org.eclipse.emfforms.ide.builder.ViewModelBuilder_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All plugin tests for org.eclipse.emfforms.ide.builder.
 */
@RunWith(Suite.class)
@SuiteClasses({
	ViewModelBuilder_PTest.class,
	ValidationBuilder_PTest.class,
})
public class AllPluginTests {

}
