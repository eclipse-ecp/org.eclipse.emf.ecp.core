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
 * Lucas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.domainexpander.defaultheuristic.tests;

import org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDMRExpanderDefaultHeuristic_Test;
import org.eclipse.emfforms.internal.core.services.domainexpander.defaultheuristic.EMFFormsDomainExpanderImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All JUnit tests for core.services.domainexpander.default
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFFormsDMRExpanderDefaultHeuristic_Test.class, EMFFormsDomainExpanderImpl_Test.class })
public class AllTests {

}
