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
 ******************************************************************************/
package org.eclipse.emf.ecp.view.test.common.spi;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;

/**
 * A JUnit runner for injection of Mockito mocks with {@link EMock @EMock} fields.
 *
 * @since 1.22
 */
public class EMFMockingRunner extends BlockJUnit4ClassRunner {

	/**
	 * Initializes me.
	 *
	 * @param classs the test class to initialize
	 * @throws InitializationError on any failure to initialize the test class
	 */
	public EMFMockingRunner(Class<?> classs) throws InitializationError {
		super(classs);
	}

	@Override
	protected Object createTest() throws Exception {
		final Object result = super.createTest();

		MockitoAnnotations.initMocks(result);

		EMFMocking.initEMocks(result);

		return result;
	}

}
