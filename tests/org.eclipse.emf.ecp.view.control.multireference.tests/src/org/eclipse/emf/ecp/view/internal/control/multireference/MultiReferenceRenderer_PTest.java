/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Lucas Koehler - initial API and implementation
 * Christian W. Damus - bug 527736
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.control.multireference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.view.model.common.AbstractGridCell.Alignment;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceFactory;
import org.eclipse.emf.ecp.view.template.style.reference.model.VTReferenceStyleProperty;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Fan;
import org.eclipse.emf.emfstore.bowling.League;
import org.eclipse.emf.emfstore.bowling.Player;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.D;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestFactory;
import org.eclipse.emfforms.core.services.databinding.testmodel.test.model.TestPackage;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.EMFFormsDatabinding;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.SWTDataElementIdHelper;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridDescription;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.osgi.framework.Bundle;

/**
 * JUnit plugin tests for {@link MultiReferenceSWTRenderer}.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(DatabindingClassRunner.class)
public class MultiReferenceRenderer_PTest {
	private static final String UUID_LINK = "UUID#link"; //$NON-NLS-1$
	private static final String UUID_ADD = "UUID#add"; //$NON-NLS-1$
	private static final String UUID_DELETE = "UUID#delete"; //$NON-NLS-1$
	private static final String UUID_DOWN = "UUID#down"; //$NON-NLS-1$
	private static final String UUID_UP = "UUID#up"; //$NON-NLS-1$
	private static final String TEST_DESCRIPTION = "test-description"; //$NON-NLS-1$
	private static final String TEST_DISPLAYNAME = "test-displayName"; //$NON-NLS-1$
	private static Realm realm;
	private EMFFormsDatabinding databindingService;
	private MultiReferenceSWTRenderer renderer;
	private MultiReferenceSWTRenderer compactVerticallyRenderer;
	private Shell shell;
	private EMFFormsLabelProvider labelProvider;
	private boolean showMoveButtons;
	private VTViewTemplateProvider templateProvider;

	/**
	 * Get {@link Realm} for the tests.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		realm = Realm.getDefault();

	}

	/**
	 * Set up executed before every test.
	 * Mocks and registers the databinding and label services.
	 * Creates a new {@link MultiReferenceSWTRenderer} to be tested. Mocks needed parameters and contents (e.g.
	 * VControl, ViewModelContext).
	 *
	 * @throws DatabindingFailedException if the databinding failed
	 * @throws NoLabelFoundException
	 */
	@Before
	public void setUp() throws DatabindingFailedException, NoLabelFoundException {
		databindingService = mock(EMFFormsDatabinding.class);
		labelProvider = mock(EMFFormsLabelProvider.class);
		when(labelProvider.getDescription(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue(TEST_DESCRIPTION));
		when(labelProvider.getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			Observables.constantObservableValue(TEST_DISPLAYNAME));

		shell = new Shell();

		final D eObject = TestFactory.eINSTANCE.createD();
		final EStructuralFeature eStructuralFeature = TestPackage.eINSTANCE.getD_YList();

		final ReportService reportService = mock(ReportService.class);
		final ViewModelContext viewContext = mock(ViewModelContext.class);
		final VControl vControl = Mockito.mock(VControl.class);
		final Setting setting = mock(Setting.class);
		final VDomainModelReference domainModelReference = mock(VDomainModelReference.class);

		when(viewContext.getDomainModel()).thenReturn(eObject);
		when(viewContext.getViewModel()).thenReturn(vControl);

		when(vControl.getDomainModelReference()).thenReturn(domainModelReference);

		when(setting.getEObject()).thenReturn(eObject);
		when(setting.getEStructuralFeature()).thenReturn(eStructuralFeature);

		Mockito.doReturn("UUID").when(vControl).getUuid(); //$NON-NLS-1$

		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);
		templateProvider = mock(VTViewTemplateProvider.class);
		final EMFFormsLocalizationService l10n = mock(EMFFormsLocalizationService.class);
		when(l10n.getString(any(Bundle.class), matches("_UI_\\w+_type"))).then(new Answer<String>() { //$NON-NLS-1$
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				final java.util.regex.Matcher m = Pattern.compile("_UI_(\\w+)_type") //$NON-NLS-1$
					.matcher((String) invocation.getArguments()[1]);
				if (m.matches()) { // It should, else we wouldn't be here
					return m.group(1);
				}
				return null;
			}
		});
		when(viewContext.getService(EMFFormsLocalizationService.class)).thenReturn(l10n);

		// mock databinding to return a value property with changeable structural feature.
		// Necessary due to the implementation of Bug 536250
		final EStructuralFeature changeableFeature = mock(EStructuralFeature.class);
		when(changeableFeature.isChangeable()).thenReturn(true);
		final IValueProperty<?, ?> valueProperty = mock(IValueProperty.class);
		when(valueProperty.getValueType()).thenReturn(changeableFeature);
		when(databindingService.getValueProperty(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(valueProperty);

		renderer = new MultiReferenceSWTRenderer(vControl, viewContext, reportService, databindingService,
			labelProvider, templateProvider, imageRegistryService, l10n) {
			@Override
			protected boolean showMoveDownButton() {
				return showMoveButtons;
			}

			@Override
			protected boolean showMoveUpButton() {
				return showMoveButtons;
			}
		};
		renderer.init();
		renderer.getGridDescription(new SWTGridDescription());

		final VTViewTemplateProvider compactTemplateProvider = mock(VTViewTemplateProvider.class);
		final VTTableStyleProperty tableStyleProperty = mock(VTTableStyleProperty.class);
		when(compactTemplateProvider.getStyleProperties(vControl, viewContext))
			.thenReturn(Collections.singleton((VTStyleProperty) tableStyleProperty));
		when(tableStyleProperty.getRenderMode()).thenReturn(RenderMode.COMPACT_VERTICALLY);
		compactVerticallyRenderer = new MultiReferenceSWTRenderer(vControl, viewContext, reportService,
			databindingService, labelProvider, compactTemplateProvider, imageRegistryService);
		compactVerticallyRenderer.init();
		compactVerticallyRenderer.getGridDescription(new SWTGridDescription());
	}

	/**
	 *
	 * Unregister databinding and label service after every test.
	 */
	@After
	public void tearDown() {
	}

	/**
	 * Test if the initial data binding is working.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testDatabindingServiceUsageInitialBinding() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<Integer> initialList = new LinkedList<Integer>();
		initialList.add(2);
		initialList.add(4);
		final WritableList mockedObservableList = new WritableList(realm, initialList, Integer.class);

		final Table table = setUpDatabindingTests(mockedObservableList);

		assertEquals(mockedObservableList.size(), table.getItemCount());
		for (int i = 0; i < mockedObservableList.size(); i++) {
			assertEquals(mockedObservableList.get(i).toString(), table.getItems()[i].getText(0));
		}
	}

	/**
	 * Tests whether adding values to the model is reflected in the control.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testDatabindingServiceUsageAddToModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<Integer> initialList = new LinkedList<Integer>();
		initialList.add(2);
		initialList.add(4);
		final WritableList mockedObservableList = new WritableList(realm, initialList, Integer.class);

		final Table table = setUpDatabindingTests(mockedObservableList);

		mockedObservableList.add(new Integer(6));

		assertEquals(mockedObservableList.size(), table.getItemCount());
		for (int i = 0; i < mockedObservableList.size(); i++) {
			assertEquals(mockedObservableList.get(i).toString(), table.getItems()[i].getText(0));
		}
	}

	/**
	 * Tests whether removing values to the model is reflected in the control.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testDatabindingServiceUsageRemoveFromModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<Integer> initialList = new LinkedList<Integer>();
		initialList.add(2);
		initialList.add(4);
		final WritableList mockedObservableList = new WritableList(realm, initialList, Integer.class);

		final Table table = setUpDatabindingTests(mockedObservableList);

		mockedObservableList.remove(0);

		assertEquals(mockedObservableList.size(), table.getItemCount());
		for (int i = 0; i < mockedObservableList.size(); i++) {
			assertEquals(mockedObservableList.get(i).toString(), table.getItems()[i].getText(0));
		}
	}

	/**
	 * Tests whether changing values of the model is reflected in the control.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testDatabindingServiceUsageChangeModel() throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final List<Integer> initialList = new LinkedList<Integer>();
		initialList.add(2);
		initialList.add(4);
		final WritableList mockedObservableList = new WritableList(realm, initialList, Integer.class);

		final Table table = setUpDatabindingTests(mockedObservableList);

		mockedObservableList.set(1, 7);

		assertEquals(mockedObservableList.size(), table.getItemCount());
		for (int i = 0; i < mockedObservableList.size(); i++) {
			assertEquals(mockedObservableList.get(i).toString(), table.getItems()[i].getText(0));
		}
	}

	private Table setUpDatabindingTests(IObservableList mockedObservableList) throws NoRendererFoundException,
		NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			mockedObservableList);
		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		final Composite controlComposite = (Composite) composite.getChildren()[1];
		final Table table = (Table) controlComposite.getChildren()[0];

		return table;
	}

	/**
	 * Tests whether a {@link EMFFormsLabelProvider} is used to get labels.
	 *
	 * @throws NoRendererFoundException Renderer could not be found
	 * @throws NoPropertyDescriptorFoundExeption Property descriptor could not be found
	 * @throws DatabindingFailedException if the databinding failed
	 */
	@Test
	public void testLabelServiceUsage() throws NoRendererFoundException, NoPropertyDescriptorFoundExeption,
		DatabindingFailedException {

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));

		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		final Composite controlComposite = (Composite) composite.getChildren()[1];
		final Table table = (Table) controlComposite.getChildren()[0];

		final TableColumn column = table.getColumn(0);
		assertEquals(TEST_DISPLAYNAME, column.getText());
		assertEquals(TEST_DESCRIPTION, column.getToolTipText());
	}

	@Test
	public void testRenderModeDefaultGridDescription() {
		final SWTGridDescription description = renderer.getGridDescription(new SWTGridDescription());
		assertEquals(1, description.getColumns());
	}

	@Test
	public void testRenderModeCompactVerticallyGridDescription() {
		final SWTGridDescription description = compactVerticallyRenderer.getGridDescription(new SWTGridDescription());
		assertEquals(2, description.getColumns());
	}

	@Test
	public void testRenderModeCompactVerticallyRenderValidation()
		throws NoPropertyDescriptorFoundExeption, NoRendererFoundException {
		final Control renderedControl = compactVerticallyRenderer
			.render(new SWTGridCell(0, 0, compactVerticallyRenderer), shell);
		assertTrue(Label.class.isInstance(renderedControl));
		assertTrue(String.class.isInstance(renderedControl.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)));
		assertTrue(
			String.class.cast(renderedControl.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY)).contains("validation")); //$NON-NLS-1$
	}

	@Test
	public void testRenderModeCompactVerticallyRenderTable()
		throws NoPropertyDescriptorFoundExeption, NoRendererFoundException, DatabindingFailedException {

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));

		final Control renderedControl = compactVerticallyRenderer
			.render(new SWTGridCell(0, 1, compactVerticallyRenderer), shell);
		assertTrue(Composite.class.isInstance(renderedControl));

		final Composite tableAndButtonsComposite = Composite.class.cast(renderedControl);
		assertTrue(Composite.class.isInstance(tableAndButtonsComposite.getChildren()[0]));

		final Composite tableComposite = Composite.class.cast(tableAndButtonsComposite.getChildren()[0]);
		assertTrue(Composite.class.isInstance(tableComposite.getChildren()[0]));

		final Composite tableControlComposite = Composite.class.cast(tableComposite.getChildren()[0]);
		assertTrue(Table.class.isInstance(tableControlComposite.getChildren()[0]));
	}

	@Test
	public void testRenderModeCompactVerticallyRenderButtons()
		throws NoPropertyDescriptorFoundExeption, NoRendererFoundException, DatabindingFailedException {

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));

		final Control renderedControl = compactVerticallyRenderer
			.render(new SWTGridCell(0, 1, compactVerticallyRenderer), shell);
		assertTrue(Composite.class.isInstance(renderedControl));

		final Composite tableAndButtonsComposite = Composite.class.cast(renderedControl);
		assertTrue(Composite.class.isInstance(tableAndButtonsComposite.getChildren()[1]));

		final Composite buttonsComposite = Composite.class.cast(tableAndButtonsComposite.getChildren()[1]);
		assertTrue(Button.class.isInstance(buttonsComposite.getChildren()[0]));
	}

	@Test
	public void testRenderModeCompactGridDescription() {
		final SWTGridDescription gridDescription = compactVerticallyRenderer.getGridDescription(null);

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

	/**
	 * Tests the tool-tip on the "Link" button.
	 *
	 * @see <a href="http://eclip.se/527736">bug 527736</a>
	 */
	@Test
	public void testLinkButtonTooltip() {
		final Table table = createLeaguePlayersTable();
		final Button linkButton = SWTTestUtil.findControl(table.getParent().getParent(), 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Player")); //$NON-NLS-1$
	}

	/**
	 * Tests the tool-tip on the "Link" button.
	 *
	 * @see <a href="http://eclip.se/527736">bug 527736</a>
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testLinkButtonTooltipReflective() {
		final EPackage leaguePackage = EcoreFactory.eINSTANCE.createEPackage();
		leaguePackage.setName("Example"); //$NON-NLS-1$
		leaguePackage.setNsPrefix("example"); //$NON-NLS-1$
		leaguePackage.setNsURI("http:///example.ecore"); //$NON-NLS-1$

		final EClass leagueClass = EcoreFactory.eINSTANCE.createEClass();
		leagueClass.setName("League"); //$NON-NLS-1$
		final EClass playerClass = EcoreFactory.eINSTANCE.createEClass();
		playerClass.setName("Player"); //$NON-NLS-1$
		final EReference playersReference = EcoreFactory.eINSTANCE.createEReference();
		playersReference.setEType(playerClass);
		playersReference.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		leagueClass.getEStructuralFeatures().add(playersReference);

		leaguePackage.getEClassifiers().add(leagueClass);
		leaguePackage.getEClassifiers().add(playerClass);

		final EObject league = EcoreUtil.create(leagueClass);

		final IObservableList<?> observableList = EMFProperties.list(playersReference).observe(league);

		Table table = null;

		try {
			// Re-stub the domain model as our league
			when(renderer.getViewModelContext().getDomainModel()).thenReturn(league);
			when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				observableList);
			final TestObservableValue observableValue = mock(TestObservableValue.class);
			when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
				.thenReturn(observableValue);
			when(observableValue.getObserved()).thenReturn(league);
			when(observableValue.getValueType()).thenReturn(BowlingPackage.Literals.LEAGUE__PLAYERS);
			final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
			final Composite controlComposite = (Composite) composite.getChildren()[1];
			table = (Table) controlComposite.getChildren()[0];
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			e.printStackTrace();
			fail("Failed to render multi-reference table: " + e.getMessage()); //$NON-NLS-1$
		}

		final Button linkButton = SWTTestUtil.findControl(table.getParent().getParent(), 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Player")); //$NON-NLS-1$
	}

	/**
	 * Tests the tool-tip on the "Create and link new" button.
	 *
	 * @see <a href="http://eclip.se/527736">bug 527736</a>
	 */
	@Test
	public void testCreateAndLinkNewButtonTooltip() {
		final Table table = createLeaguePlayersTable();
		final Button linkButton = SWTTestUtil.findControl(table.getParent().getParent(), 1, Button.class);
		assertThat(linkButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
	}

	@Test
	public void testButtonData() {
		showMoveButtons = true;
		final Table table = createLeaguePlayersTable();
		assertEquals(UUID_UP, SWTTestUtil.findControl(table.getParent().getParent(), 0, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_DOWN, SWTTestUtil.findControl(table.getParent().getParent(), 1, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_LINK, SWTTestUtil.findControl(table.getParent().getParent(), 2, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_ADD, SWTTestUtil.findControl(table.getParent().getParent(), 3, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
		assertEquals(UUID_DELETE, SWTTestUtil.findControl(table.getParent().getParent(), 4, Button.class)
			.getData(SWTDataElementIdHelper.ELEMENT_ID_KEY));
	}

	/**
	 * Tests the tool-tip on the "Link" button.
	 *
	 * @see <a href="http://eclip.se/527736">bug 527736</a>
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateAndLinkNewButtonTooltipReflective() {
		final EPackage leaguePackage = EcoreFactory.eINSTANCE.createEPackage();
		leaguePackage.setName("Example"); //$NON-NLS-1$
		leaguePackage.setNsPrefix("example"); //$NON-NLS-1$
		leaguePackage.setNsURI("http:///example.ecore"); //$NON-NLS-1$

		final EClass leagueClass = EcoreFactory.eINSTANCE.createEClass();
		leagueClass.setName("League"); //$NON-NLS-1$
		final EClass playerClass = EcoreFactory.eINSTANCE.createEClass();
		playerClass.setName("Player"); //$NON-NLS-1$
		final EReference playersReference = EcoreFactory.eINSTANCE.createEReference();
		playersReference.setEType(playerClass);
		playersReference.setUpperBound(ETypedElement.UNBOUNDED_MULTIPLICITY);
		leagueClass.getEStructuralFeatures().add(playersReference);

		leaguePackage.getEClassifiers().add(leagueClass);
		leaguePackage.getEClassifiers().add(playerClass);

		final EObject league = EcoreUtil.create(leagueClass);

		final IObservableList<?> observableList = EMFProperties.list(playersReference).observe(league);

		Table table = null;

		try {
			// Re-stub the domain model as our league
			when(renderer.getViewModelContext().getDomainModel()).thenReturn(league);
			when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				observableList);
			final TestObservableValue observableValue = mock(TestObservableValue.class);
			when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
				.thenReturn(observableValue);
			when(observableValue.getObserved()).thenReturn(league);
			when(observableValue.getValueType()).thenReturn(BowlingPackage.Literals.LEAGUE__PLAYERS);
			final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
			final Composite controlComposite = (Composite) composite.getChildren()[1];
			table = (Table) controlComposite.getChildren()[0];
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			e.printStackTrace();
			fail("Failed to render multi-reference table: " + e.getMessage()); //$NON-NLS-1$
		}

		final Button linkButton = SWTTestUtil.findControl(table.getParent().getParent(), 1, Button.class);
		assertThat(linkButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
	}

	protected Table createLeaguePlayersTable() {
		final League league = BowlingFactory.eINSTANCE.createLeague();
		return createLeaguePlayersTable(league);
	}

	protected Table createLeaguePlayersTable(final League league) {
		final EditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		final Resource bowling = domain.getResourceSet().createResource(URI.createURI("foo.ecore")); //$NON-NLS-1$
		bowling.getContents().add(league);

		final IObservableList<?> observableList = EMFEditProperties
			.list(domain, BowlingPackage.Literals.LEAGUE__PLAYERS).observe(league);

		Table result = null;

		try {
			// Re-stub the domain model as our league
			when(renderer.getViewModelContext().getDomainModel()).thenReturn(league);
			when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				observableList);
			final TestObservableValue observableValue = mock(TestObservableValue.class);
			when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
				.thenReturn(observableValue);
			when(observableValue.getObserved()).thenReturn(league);
			when(observableValue.getValueType()).thenReturn(BowlingPackage.Literals.LEAGUE__PLAYERS);
			final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
			final Composite controlComposite = (Composite) composite.getChildren()[1];
			result = (Table) controlComposite.getChildren()[0];
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			e.printStackTrace();
			fail("Failed to render multi-reference table: " + e.getMessage()); //$NON-NLS-1$
		}

		return result;
	}

	protected Composite createFanVisitedTournaments() {
		final EditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		final Resource bowling = domain.getResourceSet().createResource(URI.createURI("foo.ecore")); //$NON-NLS-1$
		final Fan fan = BowlingFactory.eINSTANCE.createFan();
		bowling.getContents().add(fan);

		@SuppressWarnings("unchecked")
		final IObservableList<?> observableList = EMFEditProperties
			.list(domain, BowlingPackage.Literals.FAN__VISITED_TOURNAMENTS).observe(fan);

		try {
			// Re-stub the domain model as our league
			when(renderer.getViewModelContext().getDomainModel()).thenReturn(fan);
			when(databindingService.getObservableList(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				observableList);
			final TestObservableValue observableValue = mock(TestObservableValue.class);
			when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
				.thenReturn(observableValue);
			when(observableValue.getObserved()).thenReturn(fan);
			when(observableValue.getValueType()).thenReturn(BowlingPackage.Literals.FAN__VISITED_TOURNAMENTS);
			return (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
			// BEGIN COMPLEX CODE
		} catch (final Exception e) {
			// END COMPLEX CODE
			e.printStackTrace();
			fail("Failed to render multi-reference table: " + e.getMessage()); //$NON-NLS-1$
			return null; // Cannot happen but is necessary for Java to be happy
		}
	}

	/**
	 * Test that the control is disabled when it's set to read only.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testReadOnlyNotDisablesControl()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		showMoveButtons = true;
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(true);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);
		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		// only buttons shall be not visible
		// main composite shall still be enabled
		assertTrue(composite.isEnabled());
	}

	/**
	 * Test that the control and buttons are visible when it's not read only.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testNotReadOnlyButtonsVisibility()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		showMoveButtons = true;
		when(renderer.getVElement().isReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);

		checkButton(composite, UUID_UP, true, false);
		checkButton(composite, UUID_DOWN, true, false);
		checkButton(composite, UUID_DELETE, true, false);
		checkNoButton(composite, UUID_LINK);
		checkNoButton(composite, UUID_ADD);
	}

	/**
	 * Test that the control is disabled when it's effectively set to read only because a parent is read only.
	 * Test also buttons visibility
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testEffectivelyReadOnlyDisablesControl()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		showMoveButtons = true;
		when(renderer.getVElement().isReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(true);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);
		// Simulate control's parent that is set to readOnly
		final VView parent = mock(VView.class);
		when(parent.isReadonly()).thenReturn(true);
		when(renderer.getVElement().eContainer()).thenReturn(parent);

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		// read only does not disable control, but hides button
		assertTrue(composite.isEnabled());
		checkButton(composite, UUID_UP, false, false);
		checkButton(composite, UUID_DOWN, false, false);
		checkButton(composite, UUID_DELETE, false, false);
		checkNoButton(composite, UUID_LINK);
		checkNoButton(composite, UUID_ADD);
	}

	/**
	 * Test that the control is disabled when VElement enablement is set to false
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testControlDisabled()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		showMoveButtons = true;
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		when(renderer.getVElement().isEnabled()).thenReturn(false);

		// Simulate control's parent that is set to readOnly
		final VView parent = mock(VView.class);
		when(parent.isReadonly()).thenReturn(true);
		when(renderer.getVElement().eContainer()).thenReturn(parent);

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		assertFalse(composite.isEnabled());

		checkButton(composite, UUID_UP, true, false);
		checkButton(composite, UUID_DOWN, true, false);
		checkNoButton(composite, UUID_LINK);
		checkButton(composite, UUID_DELETE, true, false);
	}

	/**
	 * Test that the control is enabled when VElement enablement is set to true
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testControlEnabled()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		showMoveButtons = true;
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);
		when(renderer.getVElement().isEnabled()).thenReturn(true);
		when(renderer.getVElement().isVisible()).thenReturn(true);

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final League league = BowlingFactory.eINSTANCE.createLeague();
		createPlayers(league, "player1", "player2", "player3"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Table table = createLeaguePlayersTable(league);
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		renderer.finalizeRendering(shell);
		SWTTestUtil.findControl(control, 0, Table.class);

		// check table itself
		assertTrue(table.getVisible());
		assertTrue(table.isEnabled());

		final Button upButton = SWTTestUtil.findControlById(control, UUID_UP, Button.class);
		checkControl(upButton, true, false);

		final Button deleteButton = SWTTestUtil.findControlById(control, UUID_DELETE, Button.class);
		checkControl(deleteButton, true, false);

		final IObservableList<?> list = databindingService.getObservableList(null, league);
		renderer.getTableViewer().setSelection(new StructuredSelection(list.get(0)), true);
		SWTTestUtil.waitForUIThread();

		// up may be false, as first element is selected (not yet implemented)
		checkControl(upButton, true, false);
		checkControl(deleteButton, true, true);

		renderer.getTableViewer().setSelection(new StructuredSelection(list.get(1)), true);
		SWTTestUtil.waitForUIThread();

		// up may be false, as first element is selected (not yet implemented)
		checkControl(upButton, true, true);
		checkControl(deleteButton, true, true);
	}

	private void createPlayers(League league, String... names) {
		for (final String name : names) {
			final Player p = BowlingFactory.eINSTANCE.createPlayer();
			p.setName(name);
			league.getPlayers().add(p);
		}
	}

	/**
	 * By default, the 'create and link new' button should be shown for cross references (legacy behavior).
	 */
	@Test
	public void createAndLinkButton_crossRefNoReferenceStyle() {
		final Composite rendered = createFanVisitedTournaments();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Tournament")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Tournament")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	@Test
	public void createAndLinkButton_crossRefReferenceStyleTrue() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createFanVisitedTournaments();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Tournament")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Tournament")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	@Test
	public void createAndLinkButton_crossRefReferenceStyleFalse() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createFanVisitedTournaments();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Tournament")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$

		try {
			SWTTestUtil.findControl(rendered, 2, Button.class);
			fail(
				"There must not be a third button for a non-containment reference with disabled 'create and link' button."); //$NON-NLS-1$
		} catch (final NoSuchElementException ex) {
			// This is what we expect => Test is successful
			// Cannot use expected in @Test annotation because the test must not succeed if the link or the delete
			// button are not found.
		}
	}

	/**
	 * For a containment ref the 'create and link' must still be shown even if the reference style property is set to
	 * false.
	 */
	@Test
	public void createAndLinkButton_containmentRefReferenceStyleFalse() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowCreateAndLinkButtonForCrossReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createLeaguePlayersTable().getParent().getParent();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Player")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	/**
	 * For a cross ref the 'link' button must still be shown even if the reference style property is set to false.
	 */
	@Test
	public void linkButton_crossRefReferenceStyleFalse() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createFanVisitedTournaments();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Tournament")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Tournament")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	/**
	 * By default, the 'link new' button must be shown for containment references (legacy behavior).
	 */
	@Test
	public void linkButton_containmentRefNoReferenceStyle() {
		final Composite rendered = createLeaguePlayersTable().getParent().getParent();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Player")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	@Test
	public void linkButton_containmentRefReferenceStyleTrue() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createLeaguePlayersTable().getParent().getParent();
		final Button linkButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(linkButton.getToolTipText(), is("Link Player")); //$NON-NLS-1$
		final Button createButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 2, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$
	}

	@Test
	public void linkButton_containmentRefReferenceStyleFalse() {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(false);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		final Composite rendered = createLeaguePlayersTable().getParent().getParent();
		final Button createButton = SWTTestUtil.findControl(rendered, 0, Button.class);
		assertThat(createButton.getToolTipText(), is("Create and link new Player")); //$NON-NLS-1$
		final Button deleteButton = SWTTestUtil.findControl(rendered, 1, Button.class);
		assertThat(deleteButton.getToolTipText(), is("Delete")); //$NON-NLS-1$ NON-NLS-1$

		try {
			SWTTestUtil.findControl(rendered, 2, Button.class);
			fail(
				"There must not be a third button for a containment reference with disabled 'link' button."); //$NON-NLS-1$
		} catch (final NoSuchElementException ex) {
			// This is what we expect => Test is successful
			// Cannot use expected in @Test annotation because the test must not succeed if the 'create and link' or the
			// delete button are not found.
		}
	}

	@Test
	public void compare()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		showMoveButtons = false;

		// Label: Player a2
		final Player p1 = BowlingFactory.eINSTANCE.createPlayer();
		p1.setName("a2"); //$NON-NLS-1$

		// Label: Player a10
		final Player p2 = BowlingFactory.eINSTANCE.createPlayer();
		p2.setName("a10a"); //$NON-NLS-1$

		// Label: Player a10a
		final Player p3 = BowlingFactory.eINSTANCE.createPlayer();
		p3.setName("a10"); //$NON-NLS-1$

		final League league = BowlingFactory.eINSTANCE.createLeague();
		league.getPlayers().add(p1);
		league.getPlayers().add(p2);
		league.getPlayers().add(p3);

		final Table playersTable = createLeaguePlayersTable(league);

		// Initially, items should be sorted by insertion order
		assertEquals(SWT.NONE, playersTable.getSortDirection());
		assertItemOrder(playersTable, p1, p2, p3);

		SWTTestUtil.selectWidget(playersTable.getColumn(0));
		SWTTestUtil.waitForUIThread();

		// ascending
		assertEquals(SWT.UP, playersTable.getSortDirection());
		assertItemOrder(playersTable, p1, p3, p2);

		SWTTestUtil.selectWidget(playersTable.getColumn(0));
		SWTTestUtil.waitForUIThread();

		// descending
		assertEquals(SWT.DOWN, playersTable.getSortDirection());
		assertItemOrder(playersTable, p2, p3, p1);

		SWTTestUtil.selectWidget(playersTable.getColumn(0));
		SWTTestUtil.waitForUIThread();

		// insertion order again
		assertEquals(SWT.NONE, playersTable.getSortDirection());
		assertItemOrder(playersTable, p1, p2, p3);
	}

	private static void assertItemOrder(Table table, Object... objects) {
		assertEquals(objects.length, table.getItemCount());
		final TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			assertSame(objects[i], items[i].getData());
		}
	}

	@Test
	public void testVisibleOnWritable()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {

		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		createLeaguePlayersTable();
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		renderer.finalizeRendering(shell);
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);
		final Table table = SWTTestUtil.findControl(control, 0, Table.class);

		// check table itself
		assertTrue(table.getVisible());
		assertTrue(table.isEnabled());
		assertTrue(upButton.getEnabled());
	}

	@Test
	public void testActivateOnEnable()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		createLeaguePlayersTable();
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(false);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		renderer.finalizeRendering(shell);
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);
		final Table table = SWTTestUtil.findControl(control, 0, Table.class);

		// check table itself
		assertTrue(table.isEnabled());

		checkControl(upButton, true, true);
		assertTrue(upButton.getParent().getVisible());

	}

	@Test
	public void testButtonsNotVisibleOnReadOnly()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption, DatabindingFailedException {
		final VTReferenceStyleProperty property = VTReferenceFactory.eINSTANCE.createReferenceStyleProperty();
		property.setShowLinkButtonForContainmentReferences(true);
		when(templateProvider.getStyleProperties(any(VElement.class), any(ViewModelContext.class)))
			.thenReturn(Collections.<VTStyleProperty> singleton(property));

		createLeaguePlayersTable();
		when(renderer.getVElement().isEffectivelyReadonly()).thenReturn(true);
		when(renderer.getVElement().isEffectivelyEnabled()).thenReturn(true);

		/* setup rendering */
		final SWTGridDescription gridDescription = renderer.getGridDescription(null);
		final SWTGridCell lastGridCell = gridDescription.getGrid().get(gridDescription.getGrid().size() - 1);

		/* render */
		final Control control = renderer.render(lastGridCell, shell);
		renderer.finalizeRendering(shell);
		final Button upButton = SWTTestUtil.findControl(control, 0, Button.class);

		/* by default, up is disabled (no selection) */
		assertFalse(upButton.getVisible());
	}

	/**
	 * Helper Interface for mocking.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	public interface TestObservableValue extends IObservableValue, IObserving {
	}

	private void checkButton(Composite root, String elementId, boolean visible, boolean enabled) {
		final Button button = SWTTestUtil.findControlById(root, elementId, Button.class);
		checkControl(button, visible, enabled);
	}

	private void checkNoButton(Composite root, String elementId) {
		final Button button = SWTTestUtil.findControlById(root, elementId, Button.class);
		assertThat(button, nullValue());
	}

	private void checkControl(Control control, boolean visible, boolean enabled) {
		assertThat(control, notNullValue());
		assertThat(control.getVisible(), is(visible));
		// no selection
		assertThat(control.getEnabled(), is(enabled));
	}
}
