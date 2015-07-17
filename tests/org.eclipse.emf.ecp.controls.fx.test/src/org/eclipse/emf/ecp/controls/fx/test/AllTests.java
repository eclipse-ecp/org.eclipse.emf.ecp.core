/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.controls.fx.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Lucas
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ BooleanRendererTest.class, DateRendererTest.class, EnumRendererTest.class, NumericalRendererTest.class,
	TextRendererTest.class, XmlDateRendererTest.class })
public class AllTests {

}
