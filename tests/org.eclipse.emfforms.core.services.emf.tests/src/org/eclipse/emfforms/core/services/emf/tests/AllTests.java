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
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.emf.tests;

import org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl_Test;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderDefaultImpl_Test;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderImpl_Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EMFFormsLabelProviderImpl_Test.class, EMFFormsLabelProviderDefaultImpl_Test.class,
	EMFFormsDatabindingImpl_Test.class })
public class AllTests {
	// JUnit 4 Test Suite
}
