/*******************************************************************************
 * Copyright (c) 2011-2015 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.table.ui.swt.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.internal.EMFValueProperty;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecp.view.internal.context.ViewModelContextImpl;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.swt.layout.SWTGridCell;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.swt.TableControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * JUnit tests for {@link TableControlSWTRenderer} testing the data binding of the table.
 *
 * @author Lucas Koehler
 *
 */
@SuppressWarnings("restriction")
@RunWith(DatabindingClassRunner.class)
public class SWTTableDatabindingLabel_PTest {
	private static final IObservableValue DISPLAYNAME = Observables.constantObservableValue("displayname");
	private static final IObservableValue DISPLAYNAME_COLUMNS = Observables
		.constantObservableValue("displayname-columns");
	private static final IObservableValue DESCRIPTION = Observables.constantObservableValue("description");
	private static final IObservableValue DESCRIPTION_COLUMNS = Observables
		.constantObservableValue("description-columns");
	private EMFFormsDatabinding databindingService;
	private TableControlSWTRenderer renderer;
	private Shell shell;
	private EClass domainModel;
	private VTableControl vTableControl;
	private EMFFormsLabelProvider labelProvider;

	/**
	 * Set up executed before every test.
	 * Mocks and registers the databinding and label service.
	 * Creates a new {@link TableControlSWTRenderer} to be tested. Mocks needed parameters and contents (e.g.
	 * VControl, ViewModelContext).
	 *
	 * @throws DatabindingFailedException
	 * @throws NoLabelFoundException
	 */
	@Before
	public void setUp() throws DatabindingFailedException, NoLabelFoundException {
		databindingService = mock(EMFFormsDatabinding.class);
		labelProvider = mock(EMFFormsLabelProvider.class);

		when(labelProvider.getDescription(any(VDomainModelReference.class))).thenReturn(DESCRIPTION_COLUMNS);
		when(labelProvider.getDescription(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			DESCRIPTION);
		when(labelProvider.getDisplayName(any(VDomainModelReference.class))).thenReturn(DISPLAYNAME_COLUMNS);
		when(labelProvider.getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			DISPLAYNAME);

		shell = new Shell();

		domainModel = EcoreFactory.eINSTANCE.createEClass();
		final EStructuralFeature eStructuralFeature = EcorePackage.eINSTANCE.getEClass_ESuperTypes();

		final VTableDomainModelReference tableDomainModelReference = createTableDomainModelReference(eStructuralFeature);
		vTableControl = VTableFactory.eINSTANCE.createTableControl();
		vTableControl.setDomainModelReference(tableDomainModelReference);

		final IValueProperty valueProperty = new EMFValueProperty(EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		final IObservableValue observableValue = valueProperty.observe(domainModel);
		when(databindingService.getObservableValue(vTableControl.getDomainModelReference(), domainModel)).thenReturn(
			observableValue);

		final ReportService reportservice = mock(ReportService.class);
		final VTViewTemplateProvider vtViewTemplateProvider = mock(VTViewTemplateProvider.class);
		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);
		final EMFFormsEditSupport emfFormsEditSupport = mock(EMFFormsEditSupport.class);
		final ViewModelContext viewContext = new ViewModelContextImpl(vTableControl, domainModel);

		renderer = new TableControlSWTRenderer(vTableControl, viewContext, reportservice, databindingService,
			labelProvider, vtViewTemplateProvider, imageRegistryService, emfFormsEditSupport);
		renderer.init();
	}

	private VTableDomainModelReference createTableDomainModelReference(EStructuralFeature eStructuralFeature) {
		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(eStructuralFeature);

		final VFeaturePathDomainModelReference columnReference1 = VViewFactory.eINSTANCE
			.createFeaturePathDomainModelReference();
		columnReference1.setDomainModelEFeature(EcorePackage.eINSTANCE.getEClass_Abstract());

		tableDomainModelReference.getColumnDomainModelReferences().add(columnReference1);

		return tableDomainModelReference;
	}

	@Test
	public void testLabelServiceUsage() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		DatabindingFailedException {
		final IValueProperty columnValueProperty = new EMFValueProperty(EcorePackage.eINSTANCE.getEClass_Abstract());
		final VDomainModelReference columnDMR = ((VTableDomainModelReference) vTableControl.getDomainModelReference())
			.getColumnDomainModelReferences().get(0);
		when(databindingService.getValueProperty(columnDMR)).thenReturn(columnValueProperty);

		final Control renderedControl = renderer.render(new SWTGridCell(0, 0, renderer), shell);
		final Composite composite = (Composite) renderedControl;
		final Composite titleComposite = (Composite) composite.getChildren()[0];
		final Label titleLabel = (Label) titleComposite.getChildren()[0];

		assertEquals(DISPLAYNAME.getValue(), titleLabel.getText());

		final Control tableControl = getTable(renderedControl);
		assertTrue(Table.class.isInstance(tableControl));
		final Table table = (Table) tableControl;
		final TableColumn column = table.getColumn(1);
		assertEquals(DISPLAYNAME_COLUMNS.getValue(), column.getText());
		assertEquals(DESCRIPTION_COLUMNS.getValue(), column.getToolTipText());
	}

	/**
	 * Tests the initial binding from the model to the table.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinidng failed
	 */
	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<EClass> initialList = createInitialList();

		final WritableList mockedObservableList = new WritableList(initialList, EClass.class);

		final Table table = setUpDatabindingTests(mockedObservableList, initialList.get(0), initialList.get(1));

		assertDatabinding(mockedObservableList, table);
	}

	/**
	 * Tests the binding from the model to the table for the addition of a new element to the model.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinidng failed
	 */
	@Test
	public void testDatabindingServiceUsageAddToModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<EClass> initialList = createInitialList();

		final WritableList mockedObservableList = new WritableList(initialList, EClass.class);

		final Table table = setUpDatabindingTests(mockedObservableList, initialList.get(0), initialList.get(1));

		final EClass class3 = EcoreFactory.eINSTANCE.createEClass();
		class3.setAbstract(true);
		mockedObservableList.add(class3);

		assertDatabinding(mockedObservableList, table);
	}

	/**
	 * Tests the binding from the model to the table for the removal of a element from the model .
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinidng failed
	 */
	@Test
	public void testDatabindingServiceUsageRemoveFromModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<EClass> initialList = createInitialList();

		final WritableList mockedObservableList = new WritableList(initialList, EClass.class);

		final Table table = setUpDatabindingTests(mockedObservableList, initialList.get(0), initialList.get(1));

		mockedObservableList.remove(1);

		assertDatabinding(mockedObservableList, table);
	}

	/**
	 * Tests the binding from the model to the table for the change of an element of the model.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinidng failed
	 */
	@Test
	public void testDatabindingServiceUsageChangeModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<EClass> initialList = createInitialList();

		final WritableList mockedObservableList = new WritableList(initialList, EClass.class);

		final Table table = setUpDatabindingTests(mockedObservableList, initialList.get(0), initialList.get(1));

		((EClass) mockedObservableList.get(0)).setAbstract(true);

		assertDatabinding(mockedObservableList, table);
	}

	private void assertDatabinding(final WritableList mockedObservableList, final Table table) {
		assertEquals(mockedObservableList.size(), table.getItemCount());
		for (int i = 0; i < mockedObservableList.size(); i++) {
			assertEquals(Boolean.toString(((EClass) mockedObservableList.get(i)).isAbstract()), table.getItem(i)
				.getText(1));
		}
	}

	/**
	 * @return The initial list for data binding tests containing two {@link EClass} objects.
	 */
	private List<EClass> createInitialList() {
		final List<EClass> initialList = new LinkedList<EClass>();
		final EClass class1 = EcoreFactory.eINSTANCE.createEClass();
		class1.setAbstract(false);
		final EClass class2 = EcoreFactory.eINSTANCE.createEClass();
		class2.setAbstract(true);
		initialList.add(class1);
		initialList.add(class2);
		return initialList;
	}

	private Table setUpDatabindingTests(IObservableList mockedObservableList, EClass class1, EClass class2)
		throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {

		final IValueProperty columnValueProperty = new EMFValueProperty(EcorePackage.eINSTANCE.getEClass_Abstract());
		final VDomainModelReference columnDMR = ((VTableDomainModelReference) vTableControl.getDomainModelReference())
			.getColumnDomainModelReferences().get(0);
		when(databindingService.getValueProperty(columnDMR)).thenReturn(columnValueProperty);

		when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			mockedObservableList);

		final Control renderedControl = renderer.render(new SWTGridCell(0, 0, renderer), shell);
		assertTrue(Composite.class.isInstance(renderedControl));
		final Control tableControl = getTable(renderedControl);
		assertTrue(Table.class.isInstance(tableControl));
		return (Table) tableControl;
	}

	private Control getTable(Control render) {
		Composite composite = (Composite) render;
		composite = (Composite) composite.getChildren()[1];
		return composite.getChildren()[0];
	}
}
