/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.datatemplate.test;

import org.eclipse.emfforms.internal.core.services.datatemplate.BlankTemplateProvider_Test;
import org.eclipse.emfforms.internal.core.services.datatemplate.TemplateCreateNewModelElementStrategyProvider_Test;
import org.eclipse.emfforms.internal.core.services.datatemplate.XmiTemplateProvider_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for Unit Tests.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ BlankTemplateProvider_Test.class, TemplateCreateNewModelElementStrategyProvider_Test.class,
	XmiTemplateProvider_Test.class })
public class AllTests {

}
