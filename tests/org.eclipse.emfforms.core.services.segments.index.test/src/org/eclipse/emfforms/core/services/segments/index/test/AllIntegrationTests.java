/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.segments.index.test;

import org.eclipse.emfforms.internal.core.services.segments.index.IndexDmrSegmentGenerator_ITest;
import org.eclipse.emfforms.internal.core.services.segments.index.IndexSegmentStructuralChangeTester_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Integration tests for org.eclipse.emfforms.core.services.segments.index.
 *
 * @author Lucas Koehler
 */
@RunWith(Suite.class)
@SuiteClasses({ IndexDmrSegmentGenerator_ITest.class, IndexSegmentStructuralChangeTester_ITest.class })
public class AllIntegrationTests {
}
