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
package org.eclipse.emf.ecp.ui.view.spi.swt.selection.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.ecp.view.spi.swt.selection.NullSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Test;

/**
 * Test cases for the {@link NullSelectionProvider} class.
 */
public class NullSelectionProvider_Test {

	private final NullSelectionProvider fixture = NullSelectionProvider.INSTANCE;

	/**
	 * Initializes me.
	 */
	public NullSelectionProvider_Test() {
		super();
	}

	@Test
	public void selection() {
		ISelection sel = new StructuredSelection("Hello, world!");
		fixture.setSelection(sel);

		sel = fixture.getSelection();
		assertThat("Selection not empty", sel.isEmpty(), is(true));
	}

	@Test
	public void selectionListener() {
		final ISelectionChangedListener listener = mock(ISelectionChangedListener.class);
		fixture.addSelectionChangedListener(listener);

		final ISelection sel = new StructuredSelection("Hello, world!");
		fixture.setSelection(sel);

		verify(listener, never()).selectionChanged(any());
	}

	@Test
	public void postSelectionListener() {
		final ISelectionChangedListener listener = mock(ISelectionChangedListener.class);
		fixture.addPostSelectionChangedListener(listener);

		final ISelection sel = new StructuredSelection("Hello, world!");
		fixture.setSelection(sel);

		verify(listener, never()).selectionChanged(any());
	}

}
