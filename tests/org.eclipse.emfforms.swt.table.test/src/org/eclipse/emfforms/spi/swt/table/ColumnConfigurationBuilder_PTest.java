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
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emfforms.common.Feature;
import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ColumnConfigurationBuilder} class.
 */
@SuppressWarnings("nls")
public class ColumnConfigurationBuilder_PTest {

	private ColumnConfigurationBuilder fixture;

	/**
	 * Initializes me.
	 */
	public ColumnConfigurationBuilder_PTest() {
		super();
	}

	@Test
	public void showHide() {
		fixture.showHide(true);
		assertThat("show/hide feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW));
		fixture.showHide(false);
		assertThat("show/hide feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW)));
	}

	@Test
	public void substringFilter() {
		fixture.substringFilter(true);
		assertThat("filter feature not enabled", enabledFeatures(), hasItem(TableConfiguration.FEATURE_COLUMN_FILTER));
		fixture.substringFilter(false);
		assertThat("filter feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_FILTER)));
	}

	@Test
	public void regexFilter() {
		fixture.regexFilter(true);
		assertThat("regex filter feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER));
		fixture.regexFilter(false);
		assertThat("regex filter feature still enabled", enabledFeatures(),
			not(hasItem(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER)));
	}

	@Test
	public void from_TableViewerSWTBuilder() {
		final Composite parent = mock(Composite.class);
		final IObservableList<?> input = mock(IObservableList.class);
		final TableViewerSWTBuilder viewerBuilder = TableViewerFactory.fillDefaults(parent, 0, input);

		viewerBuilder.showHideColumns(true);
		viewerBuilder.columnSubstringFilter(true);
		viewerBuilder.columnRegexFilter(true);

		fixture = ColumnConfigurationBuilder.from(viewerBuilder);

		assertThat("show/hide feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW));
		assertThat("filter feature not enabled", enabledFeatures(), hasItem(TableConfiguration.FEATURE_COLUMN_FILTER));
		assertThat("regex filter feature not enabled", enabledFeatures(),
			hasItem(TableConfiguration.FEATURE_COLUMN_REGEX_FILTER));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = ColumnConfigurationBuilder.usingDefaults();
	}

	Set<Feature> enabledFeatures() {
		return fixture.build().getEnabledFeatures();
	}

}
