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
package org.eclipse.emf.ecp.ui.view.spi.swt.layout;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.eclipse.emf.ecp.view.spi.swt.layout.PageLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link PageLayout} class.
 */
public class PageLayout_PTest {

	private Shell shell;
	private ScrolledComposite scrollPane;
	private Composite pageBook;
	private PageLayout fixture;

	private Label label;
	private Table table;

	/**
	 * Initializes me.
	 */
	public PageLayout_PTest() {
		super();
	}

	@Test
	public void showPage() {
		fixture.showPage(label);
		updateScrollPane();

		assertThat(fixture.getCurrentPage(), is(label));
		assertThat(label, visible());
		assertThat(table, not(visible()));

		// No scroll bars
		assertThat("Horizontal scroll bar visible", scrollPane.getHorizontalBar(), not(visible()));
		assertThat("Vertical scroll bar visible", scrollPane.getVerticalBar(), not(visible()));

		fixture.showPage(table);
		updateScrollPane();

		assertThat(fixture.getCurrentPage(), is(table));
		assertThat(label, not(visible()));
		assertThat(table, visible());

		// Scroll bars
		assertThat("Horizontal scroll bar not visible", scrollPane.getHorizontalBar(), visible());
		assertThat("Vertical scroll bar not visible", scrollPane.getVerticalBar(), visible());

		fixture.showPage(label);
		updateScrollPane();

		// No scroll bars again
		assertThat("Horizontal scroll bar visible", scrollPane.getHorizontalBar(), not(visible()));
		assertThat("Vertical scroll bar visible", scrollPane.getVerticalBar(), not(visible()));
	}

	@Test
	public void disposeControl() {
		fixture.showPage(label);

		assumeThat(fixture.getCurrentPage(), is(label));
		assumeThat(label, visible());
		assumeThat(table, not(visible()));

		label.dispose();

		assertThat(fixture.getCurrentPage(), is(table));
		assertThat(table, visible());

	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		scrollPane = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		pageBook = new Composite(scrollPane, SWT.NONE);
		fixture = new PageLayout(pageBook);
		scrollPane.setContent(pageBook);

		shell.setSize(100, 100);
		label = new Label(pageBook, SWT.NONE);
		label.setText("A");
		table = new Table(pageBook, SWT.NONE);
		new TableColumn(table, SWT.LEFT).setWidth(300);
		table.setItemCount(100);
		scrollPane.setAlwaysShowScrollBars(false);
		scrollPane.setExpandHorizontal(true);
		scrollPane.setExpandVertical(true);

		shell.open();
	}

	@After
	public void destroyFixture() {
		shell.dispose();
	}

	void updateScrollPane() {
		scrollPane.setMinSize(pageBook.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	Matcher<Widget> visible() {
		return new TypeSafeMatcher<Widget>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("visible");
			}

			@Override
			protected boolean matchesSafely(Widget item) {
				if (item instanceof Control) {
					return ((Control) item).isVisible();
				} else if (item instanceof ScrollBar) {
					return ((ScrollBar) item).isVisible();
				}
				return false;
			}
		};
	}

}
