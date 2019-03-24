/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * nicole.behlen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.edit.internal.swt.controls;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecp.edit.internal.swt.controls.TableControl.ECPTableEditingSupport;
import org.eclipse.emf.ecp.edit.internal.swt.controls.TableControl.ECPTableEditingSupport.EditingState;
import org.eclipse.jface.viewers.TableViewer;
import org.junit.Test;

/**
 * @author nicole.behlen
 *
 */
@SuppressWarnings({ "deprecation", "rawtypes" })
public class TableControl_Test {

	@Test
	public void isUpdateNeeded_BothNull_ReturnFalse() {
		/* setup */
		final TableViewer tblViewer = mock(TableViewer.class);
		final TableControl renderer = mock(TableControl.class);
		final IObservableValue<?> target = mock(IObservableValue.class);
		when(target.getValue()).thenReturn(null);
		final IObservableValue<?> model = mock(IObservableValue.class);
		when(model.getValue()).thenReturn(null);
		final ECPTableEditingSupport editingSupport = renderer.new ECPTableEditingSupport(tblViewer, null, null);
		final EditingState state = editingSupport.new EditingState(null, target, model);
		/* act */
		final boolean updateNeeded = state.isUpdateNeeded();
		/* assert */
		assertFalse(updateNeeded);
	}

	@Test
	public void isUpdateNeeded_OnlyModelNull_ReturnTrue() {
		/* setup */
		final TableViewer tblViewer = mock(TableViewer.class);
		final TableControl renderer = mock(TableControl.class);
		final IObservableValue target = mock(IObservableValue.class);
		when(target.getValue()).thenReturn(new Object());
		final IObservableValue<?> model = mock(IObservableValue.class);
		when(model.getValue()).thenReturn(null);
		final ECPTableEditingSupport editingSupport = renderer.new ECPTableEditingSupport(tblViewer, null, null);
		final EditingState state = editingSupport.new EditingState(null, target, model);
		/* act */
		final boolean updateNeeded = state.isUpdateNeeded();
		/* assert */
		assertTrue(updateNeeded);
	}

	@Test
	public void isUpdateNeeded_TargetModelNull_ReturnTrue() {
		/* setup */
		final TableViewer tblViewer = mock(TableViewer.class);
		final TableControl renderer = mock(TableControl.class);
		final IObservableValue target = mock(IObservableValue.class);
		when(target.getValue()).thenReturn(null);
		final IObservableValue model = mock(IObservableValue.class);
		when(model.getValue()).thenReturn(new Object());
		final ECPTableEditingSupport editingSupport = renderer.new ECPTableEditingSupport(tblViewer, null, null);
		final EditingState state = editingSupport.new EditingState(null, target, model);
		/* act */
		final boolean updateNeeded = state.isUpdateNeeded();
		/* assert */
		assertTrue(updateNeeded);
	}

	@Test
	public void isUpdateNeeded_Same_ReturnFalse() {
		/* setup */
		final TableViewer tblViewer = mock(TableViewer.class);
		final TableControl renderer = mock(TableControl.class);
		final IObservableValue target = mock(IObservableValue.class);
		when(target.getValue()).thenReturn(0);
		final IObservableValue model = mock(IObservableValue.class);
		when(model.getValue()).thenReturn(0);
		final ECPTableEditingSupport editingSupport = renderer.new ECPTableEditingSupport(tblViewer, null, null);
		final EditingState state = editingSupport.new EditingState(null, target, model);
		/* act */
		final boolean updateNeeded = state.isUpdateNeeded();
		/* assert */
		assertFalse(updateNeeded);
	}
}
