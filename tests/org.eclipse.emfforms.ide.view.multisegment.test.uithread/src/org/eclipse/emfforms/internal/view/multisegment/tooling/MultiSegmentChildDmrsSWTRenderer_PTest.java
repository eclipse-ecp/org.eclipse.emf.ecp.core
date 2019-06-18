/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.internal.view.multisegment.tooling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.databinding.edit.IEMFEditListProperty;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeatureDomainModelReferenceSegment;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTViewTestHelper;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultiDomainModelReferenceSegment;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentFactory;
import org.eclipse.emfforms.view.spi.multisegment.model.VMultisegmentPackage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;

/**
 * Tests for {@link MultiSegmentChildDmrsSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(DatabindingClassRunner.class)
public class MultiSegmentChildDmrsSWTRenderer_PTest {

	private static final String TEST_DESCRIPTION = "Test-Description"; //$NON-NLS-1$
	private static final String TEST_NAME = "Test-Name"; //$NON-NLS-1$
	private VView view;
	private VTableControl domainObject;
	private ViewModelContext context;
	private ReportService reportService;
	private EMFFormsDatabinding databinding;
	private EMFFormsLabelProvider labelProvider;
	private MultiSegmentChildDmrsSWTRenderer renderer;
	/** The dmr of the domain object which contains the multi segment. */
	private VDomainModelReference multiDmr;
	private Shell shell;

	@Before
	public void setUp() throws DatabindingFailedException, NoLabelFoundException {
		// Create view model for the renderer's view model context
		view = VViewFactory.eINSTANCE.createView();
		final VControl control = VViewFactory.eINSTANCE.createControl();
		final VDomainModelReference dmr = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(dmr, VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE.getName());
		control.setDomainModelReference(dmr);
		view.getChildren().add(control);

		// Configure table control for which child dmrs are rendered
		final VView domainView = VViewFactory.eINSTANCE.createView();
		domainView.setRootEClass(EcorePackage.Literals.ECLASS);
		domainObject = VTableFactory.eINSTANCE.createTableControl();
		domainView.getChildren().add(domainObject);
		multiDmr = VViewFactory.eINSTANCE.createDomainModelReference();
		domainObject.setDomainModelReference(multiDmr);

		// Add the domainObject object to a resource with editing domainObject
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(domainObject);

		context = mock(ViewModelContext.class);
		when(context.getDomainModel()).thenReturn(domainObject);
		when(context.getViewModel()).thenReturn(domainView);

		reportService = mock(ReportService.class);
		databinding = mock(EMFFormsDatabinding.class);
		labelProvider = mock(EMFFormsLabelProvider.class);

		// mock basic databinding
		final IValueProperty<?, ?> valueProperty = mock(IValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE);
		when(databinding.getValueProperty(dmr, domainObject)).thenReturn(valueProperty);
		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databinding.getObservableValue(dmr, domainObject)).thenReturn(observableValue);
		when(observableValue.getObserved()).thenReturn(domainObject);
		when(observableValue.getValueType()).thenReturn(VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE);

		// mock label provider for table column header and tooltip
		when(labelProvider.getDisplayName(dmr, domainObject))
			.thenReturn(Observables.constantObservableValue(TEST_NAME));
		when(labelProvider.getDescription(dmr, domainObject))
			.thenReturn(Observables.constantObservableValue(TEST_DESCRIPTION));

		// mock localization service
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);
		when(localizationService.getString(any(Bundle.class), eq("_UI_DomainModelReference_type"))) //$NON-NLS-1$
			.thenReturn("Domain Model Reference"); //$NON-NLS-1$

		renderer = new MultiSegmentChildDmrsSWTRenderer(control, context, reportService, databinding,
			labelProvider, mock(VTViewTemplateProvider.class), mock(ImageRegistryService.class),
			localizationService);
		renderer.init();
		renderer.getGridDescription(new SWTGridDescription());

		shell = SWTViewTestHelper.createShell();
		shell.open();
	}

	@After
	public void tearDown() {
		if (shell != null && !shell.isDisposed()) {
			shell.dispose();
			shell = null;
		}
	}

	/**
	 * Tests that the table and buttons are rendererd correctly and that removing a dmr from the table works.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void render()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES.getName());
		multiDmr.getSegments().add(multiSegment);

		final VDomainModelReference childDmr0 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr0, "name"); //$NON-NLS-1$
		multiSegment.getChildDomainModelReferences().add(childDmr0);

		final IEMFEditListProperty listProperty = EMFEditProperties.list(
			AdapterFactoryEditingDomain.getEditingDomainFor(domainObject),
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES);
		@SuppressWarnings("unchecked")
		final IObservableList<?> childObservable = listProperty.observe(multiSegment);
		when(databinding.getObservableList(any(VDomainModelReference.class), same(multiSegment)))
			.thenReturn(childObservable);

		final Control render = renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);

		assertTrue(render instanceof Composite);
		final Button moveUp = SWTTestUtil.findControl(render, 0, Button.class);
		assertEquals("Move Up", moveUp.getToolTipText()); //$NON-NLS-1$
		final Button moveDown = SWTTestUtil.findControl(render, 1, Button.class);
		assertEquals("Move Down", moveDown.getToolTipText()); //$NON-NLS-1$
		final Button add = SWTTestUtil.findControl(render, 2, Button.class);
		assertEquals("Create and link new Domain Model Reference", add.getToolTipText()); //$NON-NLS-1$
		final Button delete = SWTTestUtil.findControl(render, 3, Button.class);
		assertEquals("Delete", delete.getToolTipText()); //$NON-NLS-1$
		final Button sort = SWTTestUtil.findControl(render, 4, Button.class);
		assertEquals("Sort", sort.getText()); //$NON-NLS-1$

		final Table dmrTable = SWTTestUtil.findControl(render, 0, Table.class);
		assertEquals("Table column count", 1, dmrTable.getColumnCount()); //$NON-NLS-1$

		final TableColumn dmrColumn = dmrTable.getColumn(0);
		assertEquals(TEST_NAME, dmrColumn.getText());
		assertEquals(TEST_DESCRIPTION, dmrColumn.getToolTipText());

		assertEquals("Number of shown existing child DMRs.", 1, dmrTable.getItemCount()); //$NON-NLS-1$

		// Test remove
		SWTTestUtil.selectTableItem(dmrTable, 0);
		SWTTestUtil.clickButton(delete);
		assertEquals("Number of child DMRs in the multi segment after remove.", 0, //$NON-NLS-1$
			multiSegment.getChildDomainModelReferences().size());
		assertEquals("Number of shown existing child DMRs after remove.", 0, dmrTable.getItemCount()); //$NON-NLS-1$
	}

	private VFeatureDomainModelReferenceSegment createFeatureSegmentInDmr(final VDomainModelReference dmr,
		String featureName) {
		final VFeatureDomainModelReferenceSegment childDmr0Segment = VViewFactory.eINSTANCE
			.createFeatureDomainModelReferenceSegment();
		childDmr0Segment.setDomainModelFeature(featureName);
		dmr.getSegments().add(childDmr0Segment);
		return childDmr0Segment;
	}

	/**
	 * First renders for a table control with an empty dmr. Then replaces the dmr with one that has a multi segment with
	 * a child dmr and checks that the table is updated correctly.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws NoRendererFoundException
	 */
	@Test
	public void domainChange()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final VDomainModelReference dmrWithChildren = VViewFactory.eINSTANCE.createDomainModelReference();
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES.getName());
		final VDomainModelReference childDmr0 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr0, "name"); //$NON-NLS-1$
		multiSegment.getChildDomainModelReferences().add(childDmr0);
		dmrWithChildren.getSegments().add(multiSegment);

		final IEMFEditListProperty listProperty = EMFEditProperties.list(
			AdapterFactoryEditingDomain.getEditingDomainFor(domainObject),
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES);
		@SuppressWarnings("unchecked")
		final IObservableList<?> childObservable = listProperty.observe(multiSegment);
		when(databinding.getObservableList(any(VDomainModelReference.class), same(multiSegment)))
			.thenReturn(childObservable);

		final Control render = renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);
		final Button add = SWTTestUtil.findControl(render, 2, Button.class);
		final Button sort = SWTTestUtil.findControl(render, 4, Button.class);

		final Table dmrTable = SWTTestUtil.findControl(render, 0, Table.class);
		assertEquals("Table column count", 1, dmrTable.getColumnCount()); //$NON-NLS-1$

		final TableColumn dmrColumn = dmrTable.getColumn(0);
		assertEquals(TEST_NAME, dmrColumn.getText());
		assertEquals(TEST_DESCRIPTION, dmrColumn.getToolTipText());

		assertEquals("Number of shown child DMRs with empty dmr.", 0, dmrTable.getItemCount()); //$NON-NLS-1$
		assertFalse("Add button should not be enabled if there is no multi segment", add.isEnabled()); //$NON-NLS-1$
		assertFalse("Sort button should not be enabled if there is no multi segment", sort.isEnabled()); //$NON-NLS-1$

		// Change dmr of tableControl => child dmr should be shown
		domainObject.setDomainModelReference(dmrWithChildren);
		assertEquals("Number of shown child DMRs for dmr with child.", 1, dmrTable.getItemCount()); //$NON-NLS-1$
		assertTrue("Add button must be enabled if there is a multi segment", add.isEnabled()); //$NON-NLS-1$
		assertTrue("Sort button must be enabled if there is a multi segment", sort.isEnabled()); //$NON-NLS-1$
	}

	/**
	 * Tests the 'Sort' button It's supposed to actually sort the DMRs in the multi segment.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws NoRendererFoundException
	 */
	@Test
	public void sort() throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES.getName());
		final VDomainModelReference childDmr0 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr0, "name"); //$NON-NLS-1$
		final VDomainModelReference childDmr1 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr1, "changeable"); //$NON-NLS-1$
		final VDomainModelReference childDmr2 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr2, "derived"); //$NON-NLS-1$
		multiSegment.getChildDomainModelReferences().add(childDmr0);
		multiSegment.getChildDomainModelReferences().add(childDmr1);
		multiSegment.getChildDomainModelReferences().add(childDmr2);
		multiDmr.getSegments().add(multiSegment);

		final IEMFEditListProperty listProperty = EMFEditProperties.list(
			AdapterFactoryEditingDomain.getEditingDomainFor(domainObject),
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES);
		@SuppressWarnings("unchecked")
		final IObservableList<?> childObservable = listProperty.observe(multiSegment);
		when(databinding.getObservableList(any(VDomainModelReference.class), same(multiSegment)))
			.thenReturn(childObservable);

		final Control render = renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);

		final Table dmrTable = SWTTestUtil.findControl(render, 0, Table.class);
		assertEquals("Table column count", 1, dmrTable.getColumnCount()); //$NON-NLS-1$

		final TableColumn dmrColumn = dmrTable.getColumn(0);
		assertEquals(TEST_NAME, dmrColumn.getText());
		assertEquals(TEST_DESCRIPTION, dmrColumn.getToolTipText());

		assertEquals("Number of shown child DMRs", 3, dmrTable.getItemCount()); //$NON-NLS-1$
		// After rendering items are sorted by insertion
		assertTableItemOrder(dmrTable, childDmr0, childDmr1, childDmr2);

		final Button sort = SWTTestUtil.findControl(render, 4, Button.class);
		assertEquals("Sort", sort.getText()); //$NON-NLS-1$

		// clicking the button once should sort ascending in the table and the multi segment
		SWTTestUtil.clickButton(sort);
		assertTableItemOrder(dmrTable, childDmr1, childDmr2, childDmr0);
		assertSame(childDmr1, multiSegment.getChildDomainModelReferences().get(0));
		assertSame(childDmr2, multiSegment.getChildDomainModelReferences().get(1));
		assertSame(childDmr0, multiSegment.getChildDomainModelReferences().get(2));

		// clicking the button a second time should sort descending in the table and the multi segment
		SWTTestUtil.clickButton(sort);
		assertTableItemOrder(dmrTable, childDmr0, childDmr2, childDmr1);
		assertSame(childDmr0, multiSegment.getChildDomainModelReferences().get(0));
		assertSame(childDmr2, multiSegment.getChildDomainModelReferences().get(1));
		assertSame(childDmr1, multiSegment.getChildDomainModelReferences().get(2));
	}

	/**
	 * Tests drag and drop to move dmrs in the table.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoPropertyDescriptorFoundExeption
	 * @throws NoRendererFoundException
	 */
	@Test
	public void dnd() throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		final VMultiDomainModelReferenceSegment multiSegment = VMultisegmentFactory.eINSTANCE
			.createMultiDomainModelReferenceSegment();
		multiSegment.setDomainModelFeature(EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES.getName());
		final VDomainModelReference childDmr0 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr0, "name"); //$NON-NLS-1$
		final VDomainModelReference childDmr1 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr1, "changeable"); //$NON-NLS-1$
		final VDomainModelReference childDmr2 = VViewFactory.eINSTANCE.createDomainModelReference();
		createFeatureSegmentInDmr(childDmr2, "derived"); //$NON-NLS-1$
		multiSegment.getChildDomainModelReferences().add(childDmr0);
		multiSegment.getChildDomainModelReferences().add(childDmr1);
		multiSegment.getChildDomainModelReferences().add(childDmr2);
		multiDmr.getSegments().add(multiSegment);

		final IEMFEditListProperty listProperty = EMFEditProperties.list(
			AdapterFactoryEditingDomain.getEditingDomainFor(domainObject),
			VMultisegmentPackage.Literals.MULTI_DOMAIN_MODEL_REFERENCE_SEGMENT__CHILD_DOMAIN_MODEL_REFERENCES);
		@SuppressWarnings("unchecked")
		final IObservableList<?> childObservable = listProperty.observe(multiSegment);
		when(databinding.getObservableList(any(VDomainModelReference.class), same(multiSegment)))
			.thenReturn(childObservable);

		final Control render = renderer.render(new SWTGridCell(0, 2, renderer), shell);
		renderer.finalizeRendering(shell);

		final Table dmrTable = SWTTestUtil.findControl(render, 0, Table.class);
		assertEquals("Table column count", 1, dmrTable.getColumnCount()); //$NON-NLS-1$

		final TableColumn dmrColumn = dmrTable.getColumn(0);
		assertEquals(TEST_NAME, dmrColumn.getText());
		assertEquals(TEST_DESCRIPTION, dmrColumn.getToolTipText());

		assertEquals("Number of shown child DMRs", 3, dmrTable.getItemCount()); //$NON-NLS-1$
		// After rendering items are sorted by insertion
		assertTableItemOrder(dmrTable, childDmr0, childDmr1, childDmr2);

		final SWTBot bot = new SWTBot(dmrTable);
		final SWTBotTable botTable = bot.table();
		final SWTBotTableItem item0 = botTable.getTableItem(0);
		final SWTBotTableItem item2 = botTable.getTableItem(2);

		// Drag dmr0 onto dmr2 and verify new order in table and multi segment
		item0.dragAndDrop(item2);
		assertTableItemOrder(dmrTable, childDmr1, childDmr0, childDmr2);
		assertSame(childDmr1, multiSegment.getChildDomainModelReferences().get(0));
		assertSame(childDmr0, multiSegment.getChildDomainModelReferences().get(1));
		assertSame(childDmr2, multiSegment.getChildDomainModelReferences().get(2));
	}

	private static void assertTableItemOrder(Table table, Object... objects) {
		assertEquals(objects.length, table.getItemCount());
		final TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			assertSame(objects[i], items[i].getData());
		}
	}

	/** Helper Interface for mocking. */
	public interface TestObservableValue extends IObservableValue<Object>, IObserving {
	}
}
