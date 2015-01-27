/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Eugen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.swt.AbstractSWTRenderer;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridDescription;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Eugen Neufeld
 * @author Johannes Faltermeier
 *
 */
@SuppressWarnings("restriction")
@RunWith(DatabindingClassRunner.class)
public class TablePOJO_Test {

	private static final String TABLE_LABEL = "TABLE_LABEL";

	private static final Answer<Object> EMPTY_ANSWER = new Answer<Object>() {
		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			return null;
		}
	};

	private Composite parent;

	@Before
	public void setup() {
		parent = new Shell();
	}

	@After
	public void tearDown() {
		parent.dispose();
	}

	@Test
	public void testConstructor() {
		final VTableControl tableControl = mock(VTableControl.class);
		final LabelService labelService = mock(LabelService.class);
		final DatabindingService databindingService = mock(DatabindingService.class);
		final TablePOJO tablePojo = new TablePOJO(tableControl, labelService, databindingService);
		assertSame(tableControl, tablePojo.tableControl);
		assertSame(labelService, tablePojo.labelService);
		assertSame(databindingService, tablePojo.databindingService);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorLabelServiceNull() {
		new TablePOJO(mock(VTableControl.class), null, mock(DatabindingService.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorTableControlNull() {
		new TablePOJO(null, mock(LabelService.class), mock(DatabindingService.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDatabindingServiceNull() {
		new TablePOJO(mock(VTableControl.class), mock(LabelService.class), null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetGridDescription() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		final AbstractSWTRenderer renderer = mock(AbstractSWTRenderer.class);
		final SWTGridDescription gridDescription = tablePojo.getGridDescription(renderer);
		assertEquals(1, gridDescription.getColumns());
		assertEquals(1, gridDescription.getRows());
		assertEquals(1, gridDescription.getGrid().size());
		assertSame(renderer, gridDescription.getGrid().get(0).getRenderer());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetGridDescriptionRendererNull() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		tablePojo.getGridDescription(null);
	}

	@Test
	public void testRender() {
		final TablePOJO tablePojo = spy(TablePojoBuilder.init().build());

		doAnswer(EMPTY_ANSWER).when(tablePojo).createTableComposite(any(Composite.class));
		doAnswer(EMPTY_ANSWER).when(tablePojo).createTitle(any(Composite.class));
		final Composite composite = tablePojo.render(parent);

		final InOrder inOrder = inOrder(tablePojo);
		inOrder.verify(tablePojo, times(1)).createTitle(composite);
		inOrder.verify(tablePojo, times(1)).createTableComposite(composite);

		assertEquals(0, composite.getChildren().length);
	}

	@Test
	public void testCreateTitle() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		final TablePOJO pojoSpy = spy(tablePojo);

		doAnswer(EMPTY_ANSWER).when(pojoSpy).createTitleLabel(any(Composite.class));
		doAnswer(EMPTY_ANSWER).when(pojoSpy).createValidationLabel(any(Composite.class));
		doAnswer(EMPTY_ANSWER).when(pojoSpy).createButtons(any(Composite.class));
		pojoSpy.createTitle(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Composite.class.isInstance(parent.getChildren()[0]));
		final Composite composite = Composite.class.cast(parent.getChildren()[0]);
		final InOrder inOrder = inOrder(pojoSpy);
		inOrder.verify(pojoSpy, times(1)).createTitleLabel(composite);
		inOrder.verify(pojoSpy, times(1)).createValidationLabel(composite);
		inOrder.verify(pojoSpy, times(1)).createButtons(composite);

		assertEquals(0, composite.getChildren().length);

	}

	@Test
	public void testCreateTitleLabel() {
		final VTableControl tableControl = mock(VTableControl.class);
		final LabelService labelService = mock(LabelService.class);
		when(labelService.getLabelText(tableControl)).thenReturn(TABLE_LABEL);
		final TablePOJO tablePojo = TablePojoBuilder.init().withLabelService(labelService)
			.withTableControl(tableControl).build();
		tablePojo.createTitleLabel(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Label.class.isInstance(parent.getChildren()[0]));
		final Label label = Label.class.cast(parent.getChildren()[0]);
		assertEquals(label.getText(), TABLE_LABEL);
	}

	@Test
	public void testCreateValidationLabel() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		tablePojo.createValidationLabel(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Label.class.isInstance(parent.getChildren()[0]));
		final Label label = Label.class.cast(parent.getChildren()[0]);
	}

	@Test
	public void testCreateButtons() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		final TablePOJO pojoSpy = spy(tablePojo);

		doAnswer(EMPTY_ANSWER).when(pojoSpy).createAddButton(any(Composite.class));
		doAnswer(EMPTY_ANSWER).when(pojoSpy).createRemoveButton(any(Composite.class));
		doAnswer(EMPTY_ANSWER).when(pojoSpy).addCustomButtons(any(Composite.class));
		pojoSpy.createButtons(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Composite.class.isInstance(parent.getChildren()[0]));
		final Composite composite = Composite.class.cast(parent.getChildren()[0]);

		final InOrder inOrder = inOrder(pojoSpy);
		inOrder.verify(pojoSpy, times(1)).createAddButton(composite);
		inOrder.verify(pojoSpy, times(1)).createRemoveButton(composite);
		inOrder.verify(pojoSpy, times(1)).addCustomButtons(composite);

		assertEquals(0, composite.getChildren().length);
	}

	@Test
	public void testCreateAddButton() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		tablePojo.createAddButton(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Button.class.isInstance(parent.getChildren()[0]));
		final Button button = Button.class.cast(parent.getChildren()[0]);
	}

	@Test
	public void testCreateRemoveButton() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		tablePojo.createRemoveButton(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Button.class.isInstance(parent.getChildren()[0]));
		final Button button = Button.class.cast(parent.getChildren()[0]);
	}

	@Test
	public void testAddAditionalButtons() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		tablePojo.addCustomButtons(parent);
		assertEquals(0, parent.getChildren().length);
	}

	@Test
	public void tetsCreateTableComposite() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		final TablePOJO pojoSpy = spy(tablePojo);

		doAnswer(EMPTY_ANSWER).when(pojoSpy).createTable(any(Composite.class));
		pojoSpy.createTableComposite(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Composite.class.isInstance(parent.getChildren()[0]));
		final Composite composite = Composite.class.cast(parent.getChildren()[0]);

		final InOrder inOrder = inOrder(pojoSpy);
		inOrder.verify(pojoSpy, times(1)).createTable(composite);

		assertEquals(0, composite.getChildren().length);
	}

	@Test
	public void testCreateTable() {
		final VTableControl tableControl = mock(VTableControl.class);
		final VTableDomainModelReference tableDMR = mock(VTableDomainModelReference.class);
		when(tableControl.getDomainModelReference()).thenReturn(tableDMR);
		final EList<VDomainModelReference> columnDMRs = new BasicEList<VDomainModelReference>();
		when(tableDMR.getColumnDomainModelReferences()).thenReturn(columnDMRs);
		final DatabindingService databindingService = mock(DatabindingService.class);
		final IObservableList observableList = Observables.emptyObservableList();
		when(databindingService.getObservableList(tableDMR)).thenReturn(observableList);

		final TablePOJO tablePojo = TablePojoBuilder.init().withDatabindingService(databindingService)
			.withTableControl(tableControl).build();
		tablePojo.createTable(parent);
		assertEquals(1, parent.getChildren().length);
		assertTrue(Table.class.isInstance(parent.getChildren()[0]));
		final Table table = Table.class.cast(parent.getChildren()[0]);
		assertEquals(0, table.getColumnCount());
		assertEquals(0, table.getItemCount());
	}

	@Test
	public void testCreateTableWithOneColumn() {
		final VTableControl tableControl = mock(VTableControl.class);
		final VTableDomainModelReference tableDMR = mock(VTableDomainModelReference.class);
		when(tableControl.getDomainModelReference()).thenReturn(tableDMR);
		final EList<VDomainModelReference> columnDMRs = new BasicEList<VDomainModelReference>();
		when(tableDMR.getColumnDomainModelReferences()).thenReturn(columnDMRs);
		final VDomainModelReference columnDMR = mock(VDomainModelReference.class);
		columnDMRs.add(columnDMR);

		final DatabindingService databindingService = mock(DatabindingService.class);
		final Object item1 = mock(Object.class);
		final Object item2 = mock(Object.class);
		final IObservableList observableList = Observables.staticObservableList(Arrays.asList(item1, item2),
			mock(EReference.class));
		when(databindingService.getObservableList(tableDMR)).thenReturn(observableList);
		final IValueProperty columnValueProperty = mock(IValueProperty.class);
		when(databindingService.getValueProperty(any(EReference.class), same(columnDMR))).thenReturn(
			columnValueProperty);
		final IObservableMap observableMap = mock(IObservableMap.class);
		when(columnValueProperty.observeDetail(any(IObservableSet.class))).thenReturn(observableMap);

		when(observableMap.get(eq(item1))).thenReturn("MyValue1");
		when(observableMap.get(eq(item2))).thenReturn("MyValue2");

		final TablePOJO tablePojo = TablePojoBuilder.init().withDatabindingService(databindingService)
			.withTableControl(tableControl).build();

		tablePojo.createTable(parent);
		final Table table = Table.class.cast(parent.getChildren()[0]);
		assertEquals(1, table.getColumnCount());
		assertEquals(2, table.getItemCount());
		final TableItem tableItem1 = table.getItem(0);
		final String value1 = tableItem1.getText(0);
		final TableItem tableItem2 = table.getItem(1);
		final String value2 = tableItem2.getText(0);
		assertEquals("MyValue1", value1);
		assertEquals("MyValue2", value2);
	}

	@Test
	public void testCreateTableWithMultipleColumn() {
		final VTableControl tableControl = mock(VTableControl.class);
		final VTableDomainModelReference tableDMR = mock(VTableDomainModelReference.class);
		when(tableControl.getDomainModelReference()).thenReturn(tableDMR);
		final EList<VDomainModelReference> columnDMRs = new BasicEList<VDomainModelReference>();
		when(tableDMR.getColumnDomainModelReferences()).thenReturn(columnDMRs);
		final VDomainModelReference columnDMR = mock(VDomainModelReference.class);
		columnDMRs.add(columnDMR);

		final DatabindingService databindingService = mock(DatabindingService.class);
		final Object item1 = mock(Object.class);
		final Object item2 = mock(Object.class);
		final IObservableList observableList = Observables.staticObservableList(Arrays.asList(item1, item2),
			mock(EReference.class));
		when(databindingService.getObservableList(tableDMR)).thenReturn(observableList);
		final IValueProperty columnValueProperty = mock(IValueProperty.class);
		when(databindingService.getValueProperty(any(EReference.class), same(columnDMR))).thenReturn(
			columnValueProperty);
		final IObservableMap observableMap = mock(IObservableMap.class);
		when(columnValueProperty.observeDetail(any(IObservableSet.class))).thenReturn(observableMap);

		when(observableMap.get(eq(item1))).thenReturn("MyValue1");
		when(observableMap.get(eq(item2))).thenReturn("MyValue2");

		final TablePOJO tablePojo = TablePojoBuilder.init().withDatabindingService(databindingService)
			.withTableControl(tableControl).build();

		tablePojo.createTable(parent);
		final Table table = Table.class.cast(parent.getChildren()[0]);
		assertEquals(1, table.getColumnCount());
		assertEquals(2, table.getItemCount());
		final TableItem tableItem1 = table.getItem(0);
		final String value1 = tableItem1.getText(0);
		final TableItem tableItem2 = table.getItem(1);
		final String value2 = tableItem2.getText(0);
		assertEquals("MyValue1", value1);
		assertEquals("MyValue2", value2);
	}

	@Test
	public void testRenderIntegration() {
		final TablePOJO tablePojo = TablePojoBuilder.init().build();
		final Composite composite = tablePojo.render(parent);
		assertEquals(2, composite.getChildren().length);
		// title
		final Composite titleComposite = Composite.class.cast(composite.getChildren()[0]);

		assertEquals(3, titleComposite.getChildren().length);
		assertTrue(Label.class.isInstance(titleComposite.getChildren()[0]));
		assertTrue(Label.class.isInstance(titleComposite.getChildren()[1]));
		assertTrue(Composite.class.isInstance(titleComposite.getChildren()[2]));

		final Composite buttonComposite = Composite.class.cast(titleComposite.getChildren()[2]);
		assertEquals(2, buttonComposite.getChildren().length);
		assertTrue(Button.class.isInstance(buttonComposite.getChildren()[0]));
		assertTrue(Button.class.isInstance(buttonComposite.getChildren()[1]));

		// control
		final Composite controlComposite = (Composite) composite.getChildren()[1];
		assertEquals(1, controlComposite.getChildren().length);
		assertTrue(Table.class.isInstance(controlComposite.getChildren()[0]));
	}
}
