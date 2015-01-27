/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * jfaltermeier - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.internal.table.swt.TablePOJO.AddButtonSelectionAdapter;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AddButtonSelectionAdapter_Test {

	private Shell parent;

	@Before
	public void before() {
		parent = new Shell();
	}

	// hinzufügen
	// nachhonzufügen disablement

	// enabled -> enabled
	// enabled -> disabled

	@Test
	public void testWidgetSelected() {
		final List<String> items = new ArrayList<String>();

		final Button button = new Button(parent, SWT.PUSH);
		final TablePOJO tablePOJO = mock(TablePOJO.class);

		final DatabindingService databindingService = mock(DatabindingService.class);
		final IObservableList observableList = mock(IObservableList.class);
		when(databindingService.getObservableList(any(VDomainModelReference.class))).thenReturn(observableList);
		final EReference reference = mock(EReference.class);
		when(observableList.getElementType()).thenReturn(reference);
		when(reference.getUpperBound()).thenReturn(2);
		when(observableList.size()).thenReturn(items.size());
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				items.add("");
				return null;
			}
		}).when(tablePOJO).addRow();

		final AddButtonSelectionAdapter selectionAdapter = new TablePOJO.AddButtonSelectionAdapter(tablePOJO, button);
		selectionAdapter.widgetSelected(mock(SelectionEvent.class));
		verify(tablePOJO, times(1)).addRow();
	}

	@Test
	public void testWidgetSelectedDisabledAfterAdd() {
		final List<String> items = new ArrayList<String>();
		items.add("");

		final Button button = new Button(parent, SWT.PUSH);
		final TablePOJO tablePOJO = mock(TablePOJO.class);

		final DatabindingService databindingService = mock(DatabindingService.class);
		final IObservableList observableList = mock(IObservableList.class);
		when(databindingService.getObservableList(any(VDomainModelReference.class))).thenReturn(observableList);
		final EReference reference = mock(EReference.class);
		when(observableList.getElementType()).thenReturn(reference);
		when(reference.getUpperBound()).thenReturn(2);
		when(observableList.size()).thenReturn(items.size());
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				items.add("");
				return null;
			}
		}).when(tablePOJO).addRow();

		final AddButtonSelectionAdapter selectionAdapter = new TablePOJO.AddButtonSelectionAdapter(tablePOJO, button);
		selectionAdapter.widgetSelected(mock(SelectionEvent.class));
		verify(tablePOJO, times(1)).addRow();
		assertFalse(button.getEnabled());
	}

}
