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
import static org.junit.Assume.assumeThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecp.view.spi.swt.selection.MasterDetailSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test cases for the {@link MasterDetailSelectionProvider} class.
 */
@RunWith(MockitoJUnitRunner.class)
public class MasterDetailSelectionProvider_Test {

	private final SimplePostSelectionProvider master = new SimplePostSelectionProvider();
	private final DetailToggle detail = new DetailToggle();

	private final MasterDetailSelectionProvider fixture = new MasterDetailSelectionProvider(master);

	@Mock
	private ISelectionChangedListener listener;

	@Mock
	private ISelectionChangedListener postListener;

	/**
	 * Initializes me.
	 */
	public MasterDetailSelectionProvider_Test() {
		super();
	}

	@Test
	public void getSelection_master() {
		master.select("a");
		assertThat(fixture.getSelection(), selected("a"));
	}

	@Test
	public void getSelection_detail() {
		master.select("a");
		detail.get().select("b");
		fixture.setDetailSelectionProvider(detail.get());

		assertThat(fixture.getSelection(), selected("b"));

		fixture.setDetailSelectionProvider(null);
		assertThat(fixture.getSelection(), selected("a"));
	}

	@Test
	public void getSelection_detail2() {
		fixture.setDetailSelectionProvider(detail.get());

		detail.get().select("b");
		master.select("a");

		assertThat(fixture.getSelection(), selected("b"));

		fixture.setDetailSelectionProvider(null);
		assertThat(fixture.getSelection(), selected("a"));
	}

	@Test
	public void setSelection_master() {
		fixture.setSelection(new StructuredSelection("a"));

		assertThat(master.getSelection(), selected("a"));
	}

	@Test
	public void setSelection_detail() {
		fixture.setDetailSelectionProvider(detail.get());
		fixture.setSelection(new StructuredSelection("b"));

		assertThat(master.getSelection(), isEmpty());
		assertThat(detail.get().getSelection(), selected("b"));
	}

	@Test
	public void changeDetail() {
		master.select("a");
		detail.get().select("b");
		detail.cycle().select("c");
		detail.cycle().select("d");

		assumeThat(fixture.getSelection(), selected("a"));

		fixture.setDetailSelectionProvider(detail.reset());
		assumeThat(fixture.getSelection(), selected("b"));

		fixture.setDetailSelectionProvider(detail.cycle());
		assertThat(fixture.getSelection(), selected("c"));

		fixture.setDetailSelectionProvider(detail.cycle());
		assertThat(fixture.getSelection(), selected("d"));

		fixture.setDetailSelectionProvider(detail.cycle());
		assertThat(fixture.getSelection(), isEmpty());

		fixture.setDetailSelectionProvider(null);
		assertThat(fixture.getSelection(), selected("a"));
	}

	@Test
	public void selectionChanged() {
		selectionChanged(listener);
	}

	void selectionChanged(ISelectionChangedListener listener) {
		master.select("a");
		verify(listener).selectionChanged(argThat(changedTo("a")));

		// Focused on the master
		detail.get().select("b");
		verifyNoMoreInteractions(listener);

		// Change to the detail
		fixture.setDetailSelectionProvider(detail.get());
		verify(listener).selectionChanged(argThat(changedTo("b")));

		// Update the detail
		detail.get().select("c");
		verify(listener).selectionChanged(argThat(changedTo("c")));

		// Change a different detail
		detail.cycle().select("d");
		verifyNoMoreInteractions(listener);

		// Change to that detail
		fixture.setDetailSelectionProvider(detail.get());
		verify(listener).selectionChanged(argThat(changedTo("d")));

		// Idempotent
		fixture.setDetailSelectionProvider(detail.get());
		verifyNoMoreInteractions(listener);

		// Focused on the detail
		master.select("z");
		verifyNoMoreInteractions(listener);

		// Change back to the master
		fixture.setDetailSelectionProvider(null);
		verify(listener).selectionChanged(argThat(changedTo("z")));
	}

	@Test
	public void postSelectionChanged() {
		selectionChanged(postListener);
	}

	//
	// Test framework
	//

	@Before
	public void setup() {
		assumeThat(fixture.getSelection(), isEmpty());

		fixture.addSelectionChangedListener(listener);
		fixture.addPostSelectionChangedListener(postListener);
	}

	static Matcher<ISelection> isEmpty() {
		return new CustomTypeSafeMatcher<ISelection>("empty") {
			@Override
			protected boolean matchesSafely(ISelection item) {
				return item.isEmpty();
			}
		};
	}

	static Matcher<ISelection> selected(Object selection) {
		return new CustomTypeSafeMatcher<ISelection>("selected " + selection) {
			@Override
			protected boolean matchesSafely(ISelection item) {
				return item instanceof IStructuredSelection
					&& ((IStructuredSelection) item).toList().contains(selection);
			}
		};
	}

	Matcher<SelectionChangedEvent> changedTo(Object selection) {
		return new FeatureMatcher<SelectionChangedEvent, ISelection>(selected(selection), "selection", "selection") {
			@Override
			protected ISelection featureValueOf(SelectionChangedEvent actual) {
				assertThat("Wrong event source", actual.getSelectionProvider(), is(fixture));
				return actual.getSelection();
			}
		};
	}

	//
	// Nested types
	//

	private final class DetailToggle implements Supplier<SimplePostSelectionProvider> {
		private final List<SimplePostSelectionProvider> details;

		private int index;

		DetailToggle() {
			super();

			details = Stream.generate(SimplePostSelectionProvider::new)
				.limit(10L).collect(Collectors.toList());
		}

		SimplePostSelectionProvider cycle() {
			return switchTo((index + 1) % details.size());
		}

		SimplePostSelectionProvider reset() {
			return switchTo(0);
		}

		SimplePostSelectionProvider switchTo(int detail) {
			index = detail;
			return get();
		}

		@Override
		public SimplePostSelectionProvider get() {
			return details.get(index);
		}
	}

}
