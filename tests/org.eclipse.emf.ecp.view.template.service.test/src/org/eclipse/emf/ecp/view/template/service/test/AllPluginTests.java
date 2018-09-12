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
package org.eclipse.emf.ecp.view.template.service.test;

import org.eclipse.emf.ecp.spi.view.template.service.ViewTemplateSupplierUtil_PTest;
import org.eclipse.emf.ecp.view.template.service.DomainModelReferenceSelector_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All plugin tests for bundle org.eclipse.emf.ecp.view.template.service.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ DomainModelReferenceSelector_PTest.class, ViewTemplateSupplierUtil_PTest.class })
public class AllPluginTests {

}
