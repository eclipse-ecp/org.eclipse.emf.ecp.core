/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 *******************************************************************************/

package org.eclipse.emfforms.swt.reference.table.test;

import org.eclipse.emfforms.swt.internal.reference.table.DelegatingDmrSegmentConverter_PTest;
import org.eclipse.emfforms.swt.internal.reference.table.DelegatingDomainModelReferenceConverter_PTest;
import org.eclipse.emfforms.swt.internal.reference.table.SelectionTableCompositeStrategyProvider_PTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The suite of all tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ //
	DelegatingDomainModelReferenceConverter_PTest.class,
	SelectionTableCompositeStrategyProvider_PTest.class,
	DelegatingDmrSegmentConverter_PTest.class
})
public class AllTests {
	// Nothing more to specify
}
