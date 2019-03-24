/*******************************************************************************
 * Copyright (c) 2011-2019 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Johannes Faltermeier - initial API and implementation
 * Christian W. Damus - bug 543348
 ******************************************************************************/
package org.eclipse.emfforms.spi.view.control.multiattribute;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.model.common.AbstractGridCell.Alignment;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStylePropertyFactory;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Game;
import org.eclipse.emf.emfstore.bowling.TournamentType;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MultiAttributeSWTRenderer_ITest {

	private static final String UUID_REMOVE = "UUID#remove"; //$NON-NLS-1$
	private static final String UUID_ADD = "UUID#add"; //$NON-NLS-1$
	private static final String UUID_DOWN = "UUID#down"; //$NON-NLS-1$
	private static final String UUID_UP = "UUID#up"; //$NON-NLS-1$
	private DefaultRealm realm;
	private Shell shell;

	private VControl vElement;
	private ViewModelContext viewContext;
	private ReportService reportService;
	private EMFFormsDatabinding emfFormsDatabinding;
	private EMFFormsLabelProvider emfFormsLabelProvider;
	private VTViewTemplateProvider vtViewTemplateProvider;
	private ImageRegistryService imageRegistryService;

	@Before
	public void before() throws NoLabelFoundException, DatabindingFailedException {
		realm = new DefaultRealm();
		shell = new Shell();
		GridLayoutFactory.fillDefaults().applyTo(shell);

		vElement = Mockito.mock(VControl.class);
		viewContext = Mockito.mock(ViewModelContext.class);
		reportService = Mockito.mock(ReportService.class);
		emfFormsDatabinding = Mockito.mock(EMFFormsDatabinding.class);
		emfFormsLabelProvider = Mockito.mock(EMFFormsLabelProvider.class);
		vtViewTemplateProvider = Mockito.mock(VTViewTemplateProvider.class);

		imageRegistryService = Mockito.mock(ImageRegistryService.class);

		// mock databinding to return a value property with changeable structural feature.
		// Necessary due to the implementation of Bug 536250
		final EStructuralFeature changeableFeature = mock(EStructuralFeature.class);
		when(changeableFeature.isChangeable()).thenReturn(true);
		final IValueProperty<?, ?> valueProperty = mock(IValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(changeableFeature);
		when(emfFormsDatabinding.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(valueProperty);

		final IObservableValue<Serializable> displayName = Observables.constantObservableValue(
			"Display Name", //$NON-NLS-1$
			String.class);
		Mockito.doReturn(displayName).when(emfFormsLabelProvider)
			.getDisplayName(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final IObservableValue<Serializable> description = Observables.constantObservableValue(
			"Description", //$NON-NLS-1$
			String.class);
		Mockito.doReturn(description).when(emfFormsLabelProvider)
			.getDescription(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		Mockito.doReturn("UUID").when(vElement).getUuid(); //$NON-NLS-1$
	}

	public void setupCompact() {
		final Set<VTStyleProperty> properties = new HashSet<VTStyleProperty>();
		final VTTableStyleProperty tableStyleProperty = VTTableStylePropertyFactory.eINSTANCE
			.createTableStyleProperty();
		tableStyleProperty.setRenderMode(RenderMode.COMPACT_VERTICALLY);
		properties.add(tableStyleProperty);
		Mockito.when(vtViewTemplateProvider.getStyleProperties(Matchers.any(VElement.class),
			Matchers.any(ViewModelContext.class))).thenReturn(properties);
	}

	@After
	public void after() {
		shell.dispose();
		realm.dispose();
	}

	private MultiAttributeSWTRenderer createRenderer() {
		final MultiAttributeSWTRenderer renderer = new MultiAttributeSWTRenderer(
			vElement,
			viewContext,
			reportService,
			emfFormsDatabinding,
			emfFormsLabelProvider,
			vtViewTemplateProvider,
			imageRegistryService);
		renderer.init();
		return renderer;
	}

	private void createEditingDomain(final EObject object) {
		final EObject rootObject = EcoreUtil.getRootContainer(object);
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
			adapterFactory,
			new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(domain));
		final Resource resource = rs.createResource(URI.createURI("VIRTAUAL_URI")); //$NON-NLS-1$
		if (resource != null) {
			resource.getContents().add(rootObject);
		}
	}

	private static int getVisibleRows(Table table) {
		final Rectangle clientArea = table.getClientArea();
		final int itemHeight = table.getItemHeight();
		final int headerHeight = table.getHeaderHeight();
		return (clientArea.height - headerHeight + itemHeight - 1) / itemHeight;
	}

	@Test
	public void selectionAfterMove_Up()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final Game game = BowlingFactory.eINSTANCE.createGame();
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(2);
		game.getFrames().add(2);

		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		final Table table = SWTTestUtil.findControl(control, 0, Table.class);
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);

		/* act */
		table.setSelection(2);
		SWTTestUtil.waitForUIThread();

		SWTTestUtil.clickButton(upButton);
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertEquals(1, table.getSelectionIndex());
	}

	@Test
	public void selectionAfterMove_Down()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final Game game = BowlingFactory.eINSTANCE.createGame();
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(2);
		game.getFrames().add(2);

		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		final Table table = SWTTestUtil.findControl(control, 0, Table.class);
		final Button downButton = SWTTestUtil.findControl(control, 1, Button.class);

		/* act */
		table.setSelection(1);
		SWTTestUtil.waitForUIThread();

		SWTTestUtil.clickButton(downButton);
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertEquals(2, table.getSelectionIndex());
	}

	@Test
	public void elementVisibleAfterMove()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final Game game = BowlingFactory.eINSTANCE.createGame();
		for (int i = 0; i < 50; i++) {
			game.getFrames().add(i);
			game.getFrames().add(i);
		}
		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(control);

		shell.layout(true, true);
		SWTTestUtil.waitForUIThread();

		final Table table = SWTTestUtil.findControl(control, 0, Table.class);
		final Button downButton = SWTTestUtil.findControl(control, 1, Button.class);

		/* act */
		table.setSelection(0);
		SWTTestUtil.waitForUIThread();

		for (int i = 0; i < 25; i++) {
			SWTTestUtil.clickButton(downButton);
			SWTTestUtil.waitForUIThread();
		}

		/* assert */
		assertEquals(25, table.getSelectionIndex());
		final int visibleRows = getVisibleRows(table);
		final int topRowIndex = table.getTopIndex();

		/* selection index should be between top element index and top element index plus visible item count */
		assertTrue(topRowIndex <= table.getSelectionIndex());
		assertTrue(topRowIndex + visibleRows >= table.getSelectionIndex());
	}

	@Test
	public void labelsAreDisplayedForInput()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final Game game = BowlingFactory.eINSTANCE.createGame();
		for (int i = 0; i < 100; i++) {
			game.getFrames().add(i);
		}
		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render/act */
		final Control control = renderer.render(lastGridCell, shell);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(control);

		shell.layout(true, true);
		SWTTestUtil.waitForUIThread();

		final Table table = SWTTestUtil.findControl(control, 0, Table.class);

		/* assert */
		assertEquals(100, table.getItems().length);
		for (int i = 0; i < 100; i++) {
			final String text = table.getItems()[i].getText();
			assertEquals(String.valueOf(i), text);
		}

	}

	@Test
	public void buttonsUpDown_enableOnSelection()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final IObservableList<?> observeList = createBasicDomain();
		Mockito.doReturn(true).when(vElement).isEffectivelyEnabled();
		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		final Button upButton = SWTTestUtil.findControlById(control, UUID_UP, Button.class);
		final Button downButton = SWTTestUtil.findControlById(control, UUID_DOWN, Button.class);

		/* by default, up is disabled (no selection) */
		checkControlEnablement(upButton, false);
		checkControlEnablement(downButton, false);

		/* select first element in list */
		renderer.getTableViewer().setSelection(new StructuredSelection(observeList.get(0)), true);
		SWTTestUtil.waitForUIThread();
		checkControlEnablement(upButton, false);
		checkControlEnablement(downButton, true);

		/* select an element in the middle of the list */
		renderer.getTableViewer().setSelection(new StructuredSelection(observeList.get(3)), true);
		SWTTestUtil.waitForUIThread();
		checkControlEnablement(upButton, true);
		checkControlEnablement(downButton, true);

		/* select last element in list */
		renderer.getTableViewer().setSelection(new StructuredSelection(observeList.get(4)), true);
		SWTTestUtil.waitForUIThread();
		checkControlEnablement(upButton, true);
		checkControlEnablement(downButton, false);

		/* remove selection */
		renderer.getTableViewer().setSelection(StructuredSelection.EMPTY);
		SWTTestUtil.waitForUIThread();
		checkControlEnablement(upButton, false);
		checkControlEnablement(downButton, false);
	}

	private void checkControlEnablement(Control control, boolean expectedEnable) {
		assertNotNull("control does not exists", control); //$NON-NLS-1$
		assertThat(control.getEnabled(), is(expectedEnable));
	}

	private IObservableList<?> createBasicDomain() throws DatabindingFailedException {
		final Game game = BowlingFactory.eINSTANCE.createGame();
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(1);
		game.getFrames().add(2);
		game.getFrames().add(3);

		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));
		return observeList;
	}

	@Test
	public void testButtonRemovedOnReadOnly()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final IObservableList<?> observeList = createBasicDomain();
		final MultiAttributeSWTRenderer renderer = createRenderer();
		Mockito.doReturn(true).when(vElement).isEffectivelyReadonly();
		when(vElement.isEffectivelyEnabled()).thenReturn(true);
		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		// check control visibility (getVisible rather than isVisible as the shell is not visible itself)
		assertTrue("Control should be visible", control.getVisible()); //$NON-NLS-1$
		assertTrue("Control may still be enabled when read-only", control.isEnabled());//$NON-NLS-1$
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);
		assertFalse("Up button shall not be visible when read-only", upButton.getVisible());//$NON-NLS-1$

		/* select an element in list */
		renderer.getTableViewer().setSelection(new StructuredSelection(observeList.get(0)), true);
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertFalse("Up button shall not be visible when read-only", upButton.getVisible());//$NON-NLS-1$

		/* act */
		renderer.getTableViewer().setSelection(StructuredSelection.EMPTY);
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertFalse("Up button shall not be visible when read-only", upButton.getVisible());//$NON-NLS-1$

	}

	@Test
	public void testButtonsDeactivateOnDisable()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final IObservableList<?> observeList = createBasicDomain();
		// set to disable
		Mockito.doReturn(false).when(vElement).isEffectivelyEnabled();
		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);

		/* by default, up is disabled (no selection) */
		assertFalse(upButton.getEnabled());

		/* select an element in list */
		renderer.getTableViewer().setSelection(new StructuredSelection(observeList.get(0)), true);
		SWTTestUtil.waitForUIThread();

		/* up should still be disabled */
		assertFalse("button is enabled even if VElement is not enabled", upButton.getEnabled());//$NON-NLS-1$

		/* act */
		renderer.getTableViewer().setSelection(StructuredSelection.EMPTY);
		SWTTestUtil.waitForUIThread();

		/* assert */
		assertFalse("button is enabled even if VElement is not enabled", upButton.getEnabled());//$NON-NLS-1$
	}

	@Test
	public void compactRendererDescription() {
		setupCompact();
		final MultiAttributeSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);

		assertThat(gridDescription.getColumns(), is(equalTo(2)));

		final SWTGridCell validationCell = gridDescription.getGrid().get(0);
		assertThat(validationCell.getPreferredSize(), notNullValue());
		assertThat(validationCell.isHorizontalGrab(), is(false));
		assertThat(validationCell.getVerticalAlignment(), is(Alignment.BEGINNING));

		final SWTGridCell mainCell = gridDescription.getGrid().get(1);
		assertThat(mainCell.getPreferredSize(), nullValue());
		assertThat(mainCell.isHorizontalGrab(), is(true));
		assertThat(mainCell.isVerticalGrab(), is(true));
	}

	@Test
	public void buttonData()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		/* setup domain */
		final Game game = BowlingFactory.eINSTANCE.createGame();
		createEditingDomain(game);

		/* setup classes */
		@SuppressWarnings("rawtypes")
		final IObservableList observeList = EMFObservables.observeList(
			realm,
			game,
			BowlingPackage.eINSTANCE.getGame_Frames());
		Mockito.doReturn(observeList).when(emfFormsDatabinding)
			.getObservableList(Matchers.any(VDomainModelReference.class), Matchers.any(EObject.class));

		final MultiAttributeSWTRenderer renderer = createRenderer();

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);

		/* assert */
		assertEquals(UUID_UP, SWTTestUtil.findControl(control, 0, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_DOWN, SWTTestUtil.findControl(control, 1, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_ADD, SWTTestUtil.findControl(control, 2, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_REMOVE, SWTTestUtil.findControl(control, 3, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
	}

	/**
	 * Verify that when adding an enum value, the default value of the <em>attribute</em> is
	 * preferred over the default literal of the enumeration (which is always the first literal),
	 * if the attribute specifies a default.
	 */
	@Test
	@SuppressWarnings("nls")
	public void addEnumValue()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		final EPackage fakePackage = EcoreFactory.eINSTANCE.createEPackage();
		final EClass fakeClass = EcoreFactory.eINSTANCE.createEClass();
		fakePackage.getEClassifiers().add(fakeClass);
		final EAttribute allowedTypes = EcoreFactory.eINSTANCE.createEAttribute();
		allowedTypes.setName("allowedTypes");
		allowedTypes.setUpperBound(TournamentType.values().length);
		allowedTypes.setEType(BowlingPackage.Literals.TOURNAMENT_TYPE);
		allowedTypes.setDefaultValue(TournamentType.AMATEUR); // Not the type's intrinsic default
		fakeClass.getEStructuralFeatures().add(allowedTypes);

		/* setup domain */
		final EObject object = EcoreUtil.create(fakeClass);
		createEditingDomain(object);

		/* setup classes */
		@SuppressWarnings("unchecked")
		final IObservableList<TournamentType> list = EMFObservables.observeList(
			realm,
			object,
			allowedTypes);
		when(emfFormsDatabinding.getObservableList(Matchers.any(VDomainModelReference.class),
			Matchers.any(EObject.class)))
				.thenReturn(list);
		when(viewContext.getDomainModel()).thenReturn(object);

		/* setup rendering */
		final MultiAttributeSWTRenderer renderer = createRenderer();
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		final Table table = SWTTestUtil.findControl(control, 0, Table.class);
		final Button addButton = SWTTestUtil.findControl(control, 2, Button.class);

		/* act */
		table.setSelection(2);
		SWTTestUtil.waitForUIThread();

		SWTTestUtil.clickButton(addButton);
		SWTTestUtil.waitForUIThread();

		/* verify the added value */
		assertThat("Wrong default value", list, hasItem(TournamentType.AMATEUR));
	}

}
