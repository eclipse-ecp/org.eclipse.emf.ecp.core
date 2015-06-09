/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
