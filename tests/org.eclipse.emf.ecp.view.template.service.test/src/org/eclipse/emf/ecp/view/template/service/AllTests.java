/*******************************************************************************
 * Copyright (c) 2011-2014 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Muenchen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.template.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ViewTemplateService_Test.class, DomainModelReferenceSelector_PTest.class, AlignmentStyle_Test.class,
	FontPropertiesStyle_Test.class, ViewModelElementSelector_Test.class })
public class AllTests {

}
