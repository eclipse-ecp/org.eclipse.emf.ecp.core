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
package org.eclipse.emfforms.spi.swt.table;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the {@link TableViewerSWTBuilder} class.
 */
@SuppressWarnings("nls")
@RunWith(MockitoJUnitRunner.class)
public class TableViewerSWTBuilder_PTest {

	@Mock
	private Composite parent;

	@Mock
	private IObservableList<?> input;

	private TableViewerSWTBuilder fixture;

	/**
	 * Initializes me.
	 */
	public TableViewerSWTBuilder_PTest() {
		super();
	}

	@Test
	public void showHideColumns() {
		fixture.showHideColumns(true);
		assertThat("show/hide feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW));
		fixture.showHideColumns(false);
		assertThat("show/hide feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)));
	}

	@Test
	public void columnSubstringFilter() {
		fixture.columnSubstringFilter(true);
		assertThat("filter feature not enabled", enabledFeatures(), hasItem(TableConfiguration.FEATURE_COLUMN_FILTER));
		fixture.columnSubstringFilter(false);
		assertThat("filter feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_FILTER)));
	}

	@Test
	public void columnRegexFilter() {
		fixture.columnRegexFilter(true);
		assertThat("regex filter feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER));
		fixture.columnRegexFilter(false);
		assertThat("regex filter feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER)));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = new TableViewerSWTBuilder(parent, 0, input, constant("title"), constant("tooltip"));
	}

	/**
	 * Like {@link Observables#constantObservableValue(Object, Object)} except with the correct
	 * generic signature.
	 *
	 * @see <a href="http://eclip.se/512630">bug 512630</a>
	 */
	static <T> IObservableValue<T> constant(T value, Object type) {
		return Observables.constantObservableValue(Realm.getDefault(), value, type);
	}

	static <T> IObservableValue<T> constant(T value) {
		return constant(value, value.getClass());
	}

	@SuppressWarnings("deprecation")
	Set<Feature> enabledFeatures() {
		return fixture.getEnabledFeatures();
	}

}
