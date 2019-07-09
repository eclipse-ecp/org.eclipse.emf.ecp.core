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
package org.eclipse.emf.ecp.ui.view.internal.swt;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecp.view.internal.swt.ECPSWTViewImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the {@link ECPSWTViewImpl} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ECPSWTViewImpl_PTest {

	@Mock
	private ViewModelContext context;
	private Shell shell;

	/**
	 * Initializes me.
	 */
	public ECPSWTViewImpl_PTest() {
		super();
	}

	@Test
	public void instantiation() {
		final ECPSWTViewImpl swtView = new ECPSWTViewImpl(shell, context);

		verify(context).addContextUser(swtView);
	}

	@Test
	public void dispose() {
		final ECPSWTViewImpl swtView = new ECPSWTViewImpl(shell, context);

		swtView.dispose();

		assertThat("Shell not disposed", shell.isDisposed(), is(true));
		verify(context).removeContextUser(swtView);
		verify(context, never()).dispose();
	}

	//
	// Test framework
	//

	@Before
	public void createShell() {
		shell = new Shell();
	}

	@After
	public void destroyShell() {
		// Just in case
		if (!shell.isDisposed()) {
			shell.dispose();
		}
		shell = null;
	}

}
