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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
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
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.RenderMode;
import org.eclipse.emf.ecp.view.template.style.tableStyleProperty.model.VTTableStyleProperty;
import org.eclipse.emf.ecp.view.test.common.swt.spi.DatabindingClassRunner;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.emfstore.bowling.BowlingFactory;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.League;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
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
	private static final String TEST_DESCRIPTION = "test-description"; //$NON-NLS-1$
	private static final String TEST_DISPLAYNAME = "test-displayName"; //$NON-NLS-1$
	private static Realm realm;
	private EMFFormsDatabinding databindingService;
	private MultiReferenceSWTRenderer renderer;
	private MultiReferenceSWTRenderer compactVerticallyRenderer;
	private Shell shell;
	private EMFFormsLabelProvider labelProvider;

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

		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);
		final VTViewTemplateProvider templateProvider = mock(VTViewTemplateProvider.class);
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

		renderer = new MultiReferenceSWTRenderer(vControl, viewContext, reportService, databindingService,
			labelProvider, templateProvider, imageRegistryService, l10n);
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
		final EditingDomain domain = new AdapterFactoryEditingDomain(
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		final Resource bowling = domain.getResourceSet().createResource(URI.createURI("foo.ecore")); //$NON-NLS-1$
		final League league = BowlingFactory.eINSTANCE.createLeague();
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

	/**
	 * Test that the control is disabled when it's set to read only
	 * 
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testReadOnlyDisablesControl()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(renderer.getVElement().isReadonly()).thenReturn(true);
		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		System.out.println(renderer.getVElement().isReadonly());
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		assertFalse(composite.isEnabled());
	}

	/**
	 * Test that the control is disabled when it's effectively set to read only because a parent is read only.
	 *
	 * @throws DatabindingFailedException
	 * @throws NoRendererFoundException
	 * @throws NoPropertyDescriptorFoundExeption
	 */
	@Test
	public void testEffectivelyReadOnlyDisablesControl()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {
		when(renderer.getVElement().isReadonly()).thenReturn(false);

		// Simulate control's parent that is set to readOnly
		final VView parent = mock(VView.class);
		when(parent.isReadonly()).thenReturn(true);
		when(renderer.getVElement().eContainer()).thenReturn(parent);

		final TestObservableValue observableValue = mock(TestObservableValue.class);
		when(databindingService.getObservableValue(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
			observableValue);
		when(observableValue.getObserved()).thenReturn(mock(EObject.class));
		System.out.println(renderer.getVElement().isReadonly());
		final Composite composite = (Composite) renderer.render(new SWTGridCell(0, 0, renderer), shell);
		renderer.finalizeRendering(shell);
		assertFalse(composite.isEnabled());
	}

	/**
	 * Helper Interface for mocking.
	 *
	 * @author Eugen Neufeld
	 *
	 */
	public interface TestObservableValue extends IObservableValue, IObserving {
	}
}
