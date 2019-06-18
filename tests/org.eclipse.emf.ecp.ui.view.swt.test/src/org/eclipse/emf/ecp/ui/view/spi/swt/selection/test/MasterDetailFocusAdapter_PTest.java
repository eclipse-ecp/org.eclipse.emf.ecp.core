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

import static org.eclipse.emf.ecp.ui.view.spi.swt.selection.test.MasterDetailSelectionProvider_Test.isEmpty;
import static org.eclipse.emf.ecp.ui.view.spi.swt.selection.test.MasterDetailSelectionProvider_Test.selected;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.emf.ecp.view.spi.swt.selection.IMasterDetailSelectionProvider;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailFocusAdapter;
import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailSelectionProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test cases for the {@link MasterDetailFocusAdapter} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class MasterDetailFocusAdapter_PTest {

	private Shell shell;
	private Tree tree;
	private Viewer viewer;
	private Composite detail;
	private Text textInDetail;

	private IMasterDetailSelectionProvider mdSelectionProvider;

	@Mock
	private ITreeContentProvider content;

	@Mock
	private ISelectionChangedListener listener;

	/**
	 * Initializes me.
	 */
	public MasterDetailFocusAdapter_PTest() {
		super();
	}

	@Test
	public void switchFocus() {
		viewer.getControl().setFocus();
		SWTTestUtil.waitForUIThread();

		assertThat(mdSelectionProvider.getSelection(), isEmpty());

		SWTTestUtil.selectTreeItem(tree, 1);
		SWTTestUtil.waitForUIThread();

		assertThat(mdSelectionProvider.getSelection(), selected("b"));

		textInDetail.setFocus();
		SWTTestUtil.waitForUIThread();

		assertThat(mdSelectionProvider.getSelection(), isEmpty());

		viewer.getControl().setFocus();
		SWTTestUtil.waitForUIThread();

		assertThat(mdSelectionProvider.getSelection(), selected("b"));

		verify(listener, times(2)).selectionChanged(argThat(changedTo(selected("b"))));
		verify(listener).selectionChanged(argThat(changedTo(isEmpty())));
	}

	//
	// Test framework
	//

	@Before
	public void createUI() {
		shell = new Shell();

		final SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
		viewer = new TreeViewer(sashForm);
		tree = ((TreeViewer) viewer).getTree();
		detail = new Composite(sashForm, SWT.NONE);
		detail.setLayout(new FillLayout());
		final Composite detailContent = new Composite(detail, SWT.NONE);
		detailContent.setLayout(new GridLayout(2, false));
		new Label(detailContent, SWT.NONE).setText("Text:");
		textInDetail = new Text(detailContent, SWT.BORDER | SWT.SINGLE);

		when(content.getElements(any())).thenReturn(new Object[] { "a", "b", "c", "d", "e" });
		when(content.getChildren(any())).thenReturn(new Object[0]);
		((TreeViewer) viewer).setContentProvider(content);
		viewer.setInput(new Object());

		mdSelectionProvider = new MasterDetailSelectionProvider(viewer);
		mdSelectionProvider.addSelectionChangedListener(listener);
		viewer.getControl().addFocusListener(new MasterDetailFocusAdapter(mdSelectionProvider, () -> detail));

		shell.pack();
		shell.open();
	}

	@After
	public void disposeUI() {
		shell.close();
		shell.dispose();
	}

	Matcher<SelectionChangedEvent> changedTo(Matcher<? super ISelection> selectionMatcher) {
		return new FeatureMatcher<SelectionChangedEvent, ISelection>(selectionMatcher, "selection", "selection") {
			@Override
			protected ISelection featureValueOf(SelectionChangedEvent actual) {
				return actual.getSelection();
			}
		};
	}

}
