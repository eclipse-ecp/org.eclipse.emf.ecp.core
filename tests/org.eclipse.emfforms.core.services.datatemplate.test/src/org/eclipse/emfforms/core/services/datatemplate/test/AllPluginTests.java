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

import org.eclipse.emfforms.internal.core.services.datatemplate.SelectSubclassAndTemplateWizard_PTest;
import org.eclipse.emfforms.internal.core.services.datatemplate.XmiTemplateProvider_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite for OSGi Tests.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ XmiTemplateProvider_PTest.class, SelectSubclassAndTemplateWizard_PTest.class })
public class AllPluginTests {

}
