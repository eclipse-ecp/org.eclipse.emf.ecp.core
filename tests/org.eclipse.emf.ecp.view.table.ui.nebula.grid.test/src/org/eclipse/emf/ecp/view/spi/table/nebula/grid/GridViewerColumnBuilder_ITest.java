/*******************************************************************************
 * Copyright (c) 2011-2018 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Eugen Neufeld - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.spi.table.nebula.grid;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.common.Property;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DatabindingClassRunner.class)
public class GridViewerColumnBuilder_ITest {

	private EMFDataBindingContext dataBindingContext;
	private GridTableViewer tableViewer;
	private Shell shell;
	private ColumnConfiguration config;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void setUp() throws Exception {
		dataBindingContext = new EMFDataBindingContext();
		shell = new Shell();
		tableViewer = new GridTableViewer(shell);

		config = mock(ColumnConfiguration.class);
		final CellLabelProvider labelProvider = mock(CellLabelProvider.class);
		when(config.createLabelProvider(tableViewer)).thenReturn(labelProvider);
		final Optional<EditingSupport> editingSupport = Optional.empty();
		when(config.createEditingSupport(tableViewer)).thenReturn(editingSupport);
		final Property visibleProperty = mock(Property.class);
		when(visibleProperty.getValue()).thenReturn(Boolean.TRUE);
		when(config.visible()).thenReturn(visibleProperty);
		final Property showFilterProperty = mock(Property.class);
		when(showFilterProperty.getValue()).thenReturn(Boolean.FALSE);
		when(config.showFilterControl()).thenReturn(showFilterProperty);
		final Property matchFilterProperty = mock(Property.class);
		when(matchFilterProperty.getValue()).thenReturn(Boolean.FALSE);
		when(config.matchFilter()).thenReturn(matchFilterProperty);
	}

	@After
	public void tearDown() throws Exception {
		shell.dispose();
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testConfigureDatabinding() {

		final IObservableValue tooltipObservable = mock(IObservableValue.class);
		final String tooltip = "Foo";
		when(tooltipObservable.getValue()).thenReturn(tooltip);
		when(tooltipObservable.getRealm()).thenReturn(dataBindingContext.getValidationRealm());
		when(config.getColumnTooltip()).thenReturn(tooltipObservable);

		final IObservableValue textObservable = mock(IObservableValue.class);
		when(textObservable.getValue()).thenReturn("Bar");
		when(textObservable.getValueType()).thenReturn(String.class);
		when(textObservable.getRealm()).thenReturn(dataBindingContext.getValidationRealm());
		when(config.getColumnText()).thenReturn(textObservable);

		final GridViewerColumnBuilder builder = new GridViewerColumnBuilder(config);
		builder.withDatabinding(dataBindingContext);
		final GridViewerColumn column = builder.build(tableViewer);
		assertEquals(tooltip, column.getColumn().getHeaderTooltip());
	}

}
