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
 * Johannes Faltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.control.text.autocomplete.tests;

import org.eclipse.emfforms.internal.swt.control.text.autocomplete.renderer.AutocompleteTextControlSWTRendererService_Test;
import org.eclipse.emfforms.internal.swt.control.text.autocomplete.renderer.AutocompleteTextControlSWTRenderer_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AutocompleteTextControlSWTRenderer_Test.class,
	AutocompleteTextControlSWTRendererService_Test.class })
public class AllTests {

}
