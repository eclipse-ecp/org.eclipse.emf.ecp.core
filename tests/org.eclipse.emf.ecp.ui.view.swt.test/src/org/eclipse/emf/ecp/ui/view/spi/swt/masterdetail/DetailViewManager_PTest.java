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
package org.eclipse.emf.ecp.ui.view.spi.swt.masterdetail;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailRenderingFunction;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewCache;
import org.eclipse.emf.ecp.view.spi.swt.masterdetail.DetailViewManager;
import org.eclipse.emf.ecp.view.test.common.spi.EMFMockingRunner;
import org.eclipse.emf.ecp.view.test.common.spi.EMock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

/**
 * Unit tests for the {@link DetailViewManager} class.
 */
@RunWith(EMFMockingRunner.class)
public class DetailViewManager_PTest {

	@Mock
	private ViewModelContext context;

	@EMock
	private EObject detailObject;

	@EMock
	private VView detailView;

	@Mock
	private DetailViewCache cache;

	@Mock
	private ECPSWTView renderedDetail;

	@Mock
	private DetailRenderingFunction renderer;

	private Shell shell;
	private Control detailControl;
	private DetailViewManager manager;

	/**
	 * Initializes me.
	 */
	public DetailViewManager_PTest() {
		super();
	}

	@Test
	public void render() throws ECPRendererException {
		manager.render(context, renderer);

		verify(cache).isCached(detailObject);
		verify(renderer).render(any(), argThat(is(context)));
	}

	@Test
	public void cacheCurrentDetail() {
		manager.render(context, renderer);
		manager.cacheCurrentDetail();

		verify(cache).cacheView(renderedDetail);
	}

	@Test
	public void setDetailReadOnly() throws ECPRendererException {
		manager.render(context, renderer);
		manager.setDetailReadOnly(true);

		verify(cache).clear();
		verify(renderer, times(2)).render(any(), argThat(is(context)));
	}

	//
	// Test framework
	//

	@Before
	public void createTestFixture() {
		shell = new Shell();
		manager = new DetailViewManager(shell, __ -> detailView);
		manager.setCache(cache);
	}

	@Before
	public void configureMocks() throws ECPRendererException {
		when(context.getDomainModel()).thenReturn(detailObject);
		when(context.getViewModel()).thenReturn(detailView);

		when(renderer.render(any(), any())).then(invocation -> {
			if (detailControl == null) {
				detailControl = new Label((Composite) invocation.getArguments()[0], SWT.NONE);
				when(renderedDetail.getSWTControl()).thenReturn(detailControl);
				when(renderedDetail.getViewModelContext()).thenReturn(context);
			}
			return renderedDetail;
		});
	}

	@After
	public void destroyTestFixture() {
		manager.dispose();
		manager = null;

		shell.dispose();
		shell = null;
	}

}
