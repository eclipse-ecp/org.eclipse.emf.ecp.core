/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.core.services.emf.tests;

import org.eclipse.emfforms.internal.core.services.databinding.EMFFormsDatabindingImpl_ITest;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderDefaultImpl_ITest;
import org.eclipse.emfforms.internal.core.services.label.EMFFormsLabelProviderImpl_ITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite containing all integration tests for core.services.labelprovider.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ EMFFormsLabelProviderImpl_ITest.class, EMFFormsLabelProviderDefaultImpl_ITest.class,
	EMFFormsDatabindingImpl_ITest.class })
public class AllIntegrationTests {

}
