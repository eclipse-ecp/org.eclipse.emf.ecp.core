/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonas - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.model.common;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.junit.After;
import org.junit.Test;

/**
 * @author Jonas
 *
 */
public class AbstractRenderer_Test {

	private AbstractRenderer<VElement> abstractRenderer;

	@After
	public void dispose() {
		abstractRenderer = null;
	}

	@Test
	public void testInitNotNull() {
		final VElement vElement = mock(VElement.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, viewModelContext) {
		};
		assertSame(vElement, abstractRenderer.getVElement());
		assertSame(viewModelContext, abstractRenderer.getViewModelContext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitVElementNull() {
		final VElement vElement = mock(VElement.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, null) {
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitVContextNull() {
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(null, viewModelContext) {
		};
	}

	@Test(expected = IllegalStateException.class)
	public void testDispose() {
		final VElement vElement = mock(VElement.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, viewModelContext) {
		};
		abstractRenderer.dispose();
		abstractRenderer.checkRenderer();
	}

	@Test(expected = IllegalStateException.class)
	public void testDisposeGetVElement() {
		final VElement vElement = mock(VElement.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, viewModelContext) {
		};
		abstractRenderer.dispose();
		abstractRenderer.getVElement();
	}

	@Test(expected = IllegalStateException.class)
	public void testDisposeGetVContext() {
		final VElement vElement = mock(VElement.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, viewModelContext) {
		};
		abstractRenderer.dispose();
		abstractRenderer.getViewModelContext();
	}

	@Test
	public void testNotDisposes() {
		final VElement vElement = mock(VElement.class);
		final ViewModelContext viewModelContext = mock(ViewModelContext.class);
		abstractRenderer = new AbstractRenderer<VElement>(vElement, viewModelContext) {
		};
		abstractRenderer.checkRenderer();
	}

}
