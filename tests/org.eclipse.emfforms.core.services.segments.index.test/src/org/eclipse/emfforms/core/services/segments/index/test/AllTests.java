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

import org.eclipse.emfforms.internal.core.services.segments.index.EMFIndexedValueProperty_Test;
import org.eclipse.emfforms.internal.core.services.segments.index.IndexDmrSegmentGenerator_Test;
import org.eclipse.emfforms.internal.core.services.segments.index.IndexSegmentConverter_Test;
import org.eclipse.emfforms.internal.core.services.segments.index.IndexSegmentStructuralChangeTester_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All unit tests for org.eclipse.emfforms.core.services.segments.index.
 *
 * @author Lucas Koehler
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFIndexedValueProperty_Test.class, IndexDmrSegmentGenerator_Test.class,
	IndexSegmentConverter_Test.class, IndexSegmentStructuralChangeTester_Test.class })
public class AllTests {
}
