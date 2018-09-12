/*******************************************************************************
 * Copyright (c) 2011-2017 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mat Hansen - initial API and implementation
 ******************************************************************************/
package org.eclipse.emf.ecp.view.internal.table.nebula.grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.databinding.internal.EMFValueProperty;
import org.eclipse.emf.databinding.internal.EMFValuePropertyDecorator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.LabelAlignment;
import org.eclipse.emf.ecp.view.spi.model.VDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.renderer.NoPropertyDescriptorFoundExeption;
import org.eclipse.emf.ecp.view.spi.renderer.NoRendererFoundException;
import org.eclipse.emf.ecp.view.spi.table.model.VTableColumnConfiguration;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableDomainModelReference;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridControlSWTRenderer;
import org.eclipse.emf.ecp.view.spi.table.nebula.grid.GridTableViewerComposite;
import org.eclipse.emf.ecp.view.spi.table.swt.action.AddRowAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.DuplicateRowAction;
import org.eclipse.emf.ecp.view.spi.table.swt.action.RemoveRowAction;
import org.eclipse.emf.ecp.view.spi.util.swt.ImageRegistryService;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditFactory;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.AuditPackage;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Member;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.Organization;
import org.eclipse.emf.ecp.view.table.ui.nebula.grid.test.model.audit.RegisteredUser;
import org.eclipse.emf.ecp.view.template.model.VTStyleProperty;
import org.eclipse.emf.ecp.view.template.model.VTViewTemplateProvider;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBinding;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeyBindings;
import org.eclipse.emf.ecp.view.template.style.keybinding.model.VTKeybindingFactory;
import org.eclipse.emfforms.common.Optional;
import org.eclipse.emfforms.spi.common.converter.EStructuralFeatureValueConverterService;
import org.eclipse.emfforms.spi.common.report.ReportService;
import org.eclipse.emfforms.spi.core.services.databinding.DatabindingFailedException;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.emfforms.spi.core.services.editsupport.EMFFormsEditSupport;
import org.eclipse.emfforms.spi.core.services.label.EMFFormsLabelProvider;
import org.eclipse.emfforms.spi.core.services.label.NoLabelFoundException;
import org.eclipse.emfforms.spi.localization.EMFFormsLocalizationService;
import org.eclipse.emfforms.spi.swt.core.layout.SWTGridCell;
import org.eclipse.emfforms.spi.swt.table.AbstractTableViewerComposite;
import org.eclipse.emfforms.spi.swt.table.ColumnConfiguration;
import org.eclipse.emfforms.spi.swt.table.TableConfiguration;
import org.eclipse.emfforms.swt.common.test.AbstractControl_PTest;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Plugin Test for Grid Table.
 *
 * Uses two different data sets:
 * 1) SimpleDataSet, consist of three members of the same type (RegisteredUser)
 * * which have three fields each: name, isActive, joinDate
 * 2) ComplexDataSet, consist of different member types (RegisteredUser, AdminUser, Bot)
 * * which have one unique field each: login, executionInterval, createdBy
 *
 * @author Mat Hansen <mhansen@eclipsesource.com>
 *
 */
@SuppressWarnings("restriction")
@RunWith(Parameterized.class)
public class GridControlRenderer_PTest extends AbstractControl_PTest<VTableControl> {

	private DefaultRealm realm;

	private static final String MEMBER1_NAME = "Kliver Oahn";
	private static final String MEMBER2_NAME = "Branz Feckenbauer";
	private static final String MEMBER3_NAME = "Vudi Roeller";

	private static final Map<String, EClass> MEMBERS;
	static {

		final Map<String, EClass> tmp = new LinkedHashMap<String, EClass>();
		tmp.put(MEMBER1_NAME, AuditPackage.eINSTANCE.getRegisteredUser());
		tmp.put(MEMBER2_NAME, AuditPackage.eINSTANCE.getAdminUser());
		tmp.put(MEMBER3_NAME, AuditPackage.eINSTANCE.getBot());
		MEMBERS = Collections.unmodifiableMap(tmp);
	}

	private int expectedRows = -1;
	private int expectedColumns = -1;

	public enum DataSet {
		Simple, Complex
	}

	// BEGIN COMPLEX CODE
	// Field must be public for JUnit's @Parameter to work: https://github.com/junit-team/junit4/pull/737
	@Parameter
	public DataSet dataset;
	// END COMPLEX CODE

	@Parameters(name = "{0}")
	public static DataSet[] data() {
		return DataSet.values();
	}

	@Before
	public void before() throws DatabindingFailedException {
		realm = new DefaultRealm();
		final ReportService reportService = mock(ReportService.class);

		setDatabindingService(mock(EMFFormsDatabindingEMF.class));
		setLabelProvider(mock(EMFFormsLabelProvider.class));
		setTemplateProvider(mock(VTViewTemplateProvider.class));

		final EMFFormsEditSupport editSupport = mock(EMFFormsEditSupport.class);
		final EStructuralFeatureValueConverterService converterService = mock(
			EStructuralFeatureValueConverterService.class);
		final ImageRegistryService imageRegistryService = mock(ImageRegistryService.class);
		final EMFFormsLocalizationService localizationService = mock(EMFFormsLocalizationService.class);

		setup();

		setRenderer(new GridControlSWTRenderer(
			getvControl(), getContext(), reportService,
			(EMFFormsDatabindingEMF) getDatabindingService(),
			getLabelProvider(), getTemplateProvider(), imageRegistryService, editSupport, converterService,
			localizationService));

		getRenderer().init();
	}

	@After
	public void testTearDown() {
		realm.dispose();
		dispose();
	}

	@Override
	protected void setup() throws DatabindingFailedException {
		setvControl(Mockito.mock(VTableControl.class));
		mockControl();
		setContext(Mockito.mock(ViewModelContext.class));
		when(getContext().getDomainModel()).thenReturn(mock(EObject.class));
		setShell(new Shell(Display.getDefault(), SWT.NONE));

		final IObservableValue testDescription = Observables.constantObservableValue("test-description", String.class);
		final IObservableValue testDisplayName = Observables.constantObservableValue("test-displayname", String.class);

		try {

			when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				Observables.constantObservableValue("antiException", String.class));
			setMockLabelAlignment(LabelAlignment.LEFT);

			when(getLabelProvider().getDescription(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				testDescription);
			when(getLabelProvider().getDescription(any(VDomainModelReference.class))).thenReturn(
				testDescription);
			when(getLabelProvider().getDisplayName(any(VDomainModelReference.class), any(EObject.class))).thenReturn(
				testDisplayName);
			when(getLabelProvider().getDisplayName(any(VDomainModelReference.class))).thenReturn(
				testDisplayName);
		} catch (final NoLabelFoundException ex) {
			Assert.fail(ex.getMessage());
		}

		when(getvControl().getColumnConfigurations())
			.thenReturn(new BasicEList<VTableColumnConfiguration>());

	}

	@Override
	protected void mockControl() throws DatabindingFailedException {

		final TestObservableValue mockedObservableValue = mock(TestObservableValue.class);

		when(mockedObservableValue.getRealm()).thenReturn(realm);
		final EObject mockedEObject = mock(EObject.class);
		when(mockedEObject.eIsSet(any(EStructuralFeature.class))).thenReturn(true);
		when(mockedObservableValue.getObserved()).thenReturn(mockedEObject);
		final EStructuralFeature mockedEStructuralFeature = mock(EStructuralFeature.class);
		when(mockedEStructuralFeature.isUnsettable()).thenReturn(false);
		when(mockedObservableValue.getValueType()).thenReturn(mockedEStructuralFeature);
		when(getDatabindingService().getObservableValue(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				mockedObservableValue);

		when(getvControl().getColumnConfigurations())
			.thenReturn(new BasicEList<VTableColumnConfiguration>());

	}

	private void mockDataset() throws DatabindingFailedException {

		switch (dataset) {
		case Simple:
			mockDataSetSimple();
			break;
		case Complex:
			mockDataSetComplex();
			break;
		default:
			throw new IllegalArgumentException("Unsupported dataset");
		}

	}

	/**
	 * Provide a simple data set which has no abstract types.
	 */
	protected void mockDataSetSimple() throws DatabindingFailedException {
		final Organization org = AuditFactory.eINSTANCE.createOrganization();

		// add members
		for (final String memberName : MEMBERS.keySet()) {
			final Member member = AuditFactory.eINSTANCE.createRegisteredUser();
			member.setName(memberName);
			org.getMembers().add(member);

			mockSetting(mock(Setting.class), member, AuditPackage.eINSTANCE.getMember_Name());
		}

		final WritableList<Member> memberList = new WritableList<Member>(org.getMembers(),
			AuditPackage.eINSTANCE.getOrganization_Members());

		when(getDatabindingService().getObservableList(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				memberList);

		final Setting memberSetting = mock(Setting.class);
		mockSetting(memberSetting, org, AuditPackage.eINSTANCE.getOrganization_Members());

		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(AuditPackage.eINSTANCE.getOrganization_Members());

		// add three columns
		for (final EStructuralFeature eStructuralFeature : Arrays.asList(
			AuditPackage.eINSTANCE.getMember_Name(),
			AuditPackage.eINSTANCE.getMember_IsActive(),
			AuditPackage.eINSTANCE.getMember_JoinDate() //
		)) {

			final VFeaturePathDomainModelReference dmr = //
				VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
			dmr.setDomainModelEFeature(eStructuralFeature);
			tableDomainModelReference.getColumnDomainModelReferences().add(dmr);

			mockColumnFeature(dmr);
		}

		when(((EMFFormsDatabindingEMF) getDatabindingService())
			.getSetting(any(VDomainModelReference.class),
				any(EObject.class)))
					.thenReturn(memberSetting);

		when(getvControl().getDomainModelReference()).thenReturn(
			tableDomainModelReference);

		expectedRows = MEMBERS.size();
		expectedColumns = 3;
	}

	protected void mockDataSetComplex() throws DatabindingFailedException {
		final Organization org = AuditFactory.eINSTANCE.createOrganization();

		// add three players
		for (final Entry<String, EClass> m : MEMBERS.entrySet()) {

			final String name = m.getKey();
			final Member member = (Member) AuditFactory.eINSTANCE.create(m.getValue());
			member.setName(name);

			if (RegisteredUser.class.isInstance(member)) { // will include MEMBER1 and MEMBER2, as MEMBER3 is a Bot
				final RegisteredUser user = RegisteredUser.class.cast(member);
				user.setLogin(name.toLowerCase().replaceAll(" ", ""));
			}

			org.getMembers().add(member);

			mockSetting(mock(Setting.class), member, AuditPackage.eINSTANCE.getMember_Name());
		}

		final WritableList<Member> memberList = new WritableList<Member>(org.getMembers(),
			AuditPackage.eINSTANCE.getOrganization_Members());

		when(getDatabindingService().getObservableList(any(VDomainModelReference.class), any(EObject.class)))
			.thenReturn(
				memberList);

		final Setting memberSetting = mock(Setting.class);
		mockSetting(memberSetting, org, AuditPackage.eINSTANCE.getOrganization_Members());

		final VTableDomainModelReference tableDomainModelReference = VTableFactory.eINSTANCE
			.createTableDomainModelReference();
		tableDomainModelReference.setDomainModelEFeature(AuditPackage.eINSTANCE.getOrganization_Members());

		// add three columns
		for (final EStructuralFeature eStructuralFeature : Arrays.asList(
			AuditPackage.eINSTANCE.getMember_Name(),
			AuditPackage.eINSTANCE.getMember_IsActive(),
			AuditPackage.eINSTANCE.getMember_JoinDate() //
		)) {

			final VFeaturePathDomainModelReference dmr = //
				VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
			dmr.setDomainModelEFeature(eStructuralFeature);
			tableDomainModelReference.getColumnDomainModelReferences().add(dmr);

			mockColumnFeature(dmr);
		}

		for (final EStructuralFeature eStructuralFeature : Arrays.asList(
			AuditPackage.eINSTANCE.getRegisteredUser_Login(),
			AuditPackage.eINSTANCE.getBot_ExecutionIntervalSeconds(),
			AuditPackage.eINSTANCE.getAdminUser_CreatedBy() //
		)) {

			final VFeaturePathDomainModelReference dmr = //
				VViewFactory.eINSTANCE.createFeaturePathDomainModelReference();
			dmr.setDomainModelEFeature(eStructuralFeature);
			tableDomainModelReference.getColumnDomainModelReferences().add(dmr);

			mockColumnFeature(dmr);
		}

		when(((EMFFormsDatabindingEMF) getDatabindingService())
			.getSetting(any(VDomainModelReference.class),
				any(EObject.class)))
					.thenReturn(memberSetting);

		when(getvControl().getDomainModelReference()).thenReturn(
			tableDomainModelReference);

		expectedRows = MEMBERS.size();
		expectedColumns = 6;
	}

	/**
	 * @param dmr
	 * @throws DatabindingFailedException
	 */
	private void mockColumnFeature(final VFeaturePathDomainModelReference dmr) throws DatabindingFailedException {
		when(getDatabindingService().getValueProperty(Matchers.argThat(new BaseMatcher<VDomainModelReference>() {

			@Override
			public boolean matches(Object item) {
				if (item == null) {
					return false;
				}
				final VFeaturePathDomainModelReference cast = VFeaturePathDomainModelReference.class.cast(item);
				return cast.getDomainModelEFeature() == dmr.getDomainModelEFeature();
			}

			@Override
			public void describeTo(Description description) {
			}

		}), any(EObject.class)))
			.thenReturn(new EMFValuePropertyDecorator(
				new EMFValueProperty(dmr.getDomainModelEFeature()), dmr.getDomainModelEFeature()));
	}

	/**
	 * @param setting
	 * @param player_Name
	 * @throws DatabindingFailedException
	 */
	private void mockSetting(final Setting setting, final EObject eObject,
		final EStructuralFeature eStructuralFeature) throws DatabindingFailedException {

		when(setting.getEObject()).thenReturn(eObject);
		when(setting.getEStructuralFeature()).thenReturn(eStructuralFeature);

	}

	private void assertControl(Control tableViewerComposite) {
		assertTrue(AbstractTableViewerComposite.class.isInstance(tableViewerComposite));
		final Control gridComposite = Composite.class.cast(tableViewerComposite).getChildren()[0];
		final Control grid = Composite.class.cast(Composite.class.cast(gridComposite).getChildren()[1])
			.getChildren()[0];

		assertTrue(Grid.class.isInstance(grid));
	}

	private Grid getGrid(Control tableViewerComposite) {
		final Control gridComposite = Composite.class.cast(tableViewerComposite).getChildren()[0];
		return (Grid) Composite.class.cast(Composite.class.cast(gridComposite).getChildren()[1])
			.getChildren()[0];
	}

	private ColumnConfiguration extractGridColumnConfigByIndex(Grid grid, int i) {
		final GridColumn colum = grid.getColumn(i);
		final Object data = colum.getData(ColumnConfiguration.ID);
		assertTrue(ColumnConfiguration.class.isInstance(data));

		final ColumnConfiguration columnConfiguration = ColumnConfiguration.class.cast(data);
		return columnConfiguration;
	}

	@Test
	public void testColumnHideShow()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		mockDataset();

		final Control rendered = renderControl(new SWTGridCell(0, 2, getRenderer()));
		assertControl(rendered);

		final GridTableViewerComposite tableViewerComposite = (GridTableViewerComposite) rendered;
		assertTrue(tableViewerComposite.getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_HIDE_SHOW));

		final Grid grid = getGrid(rendered);
		assertEquals(expectedColumns + 1, grid.getColumns().length); // columns defined by mockDataset()
																		// +1 for validation column
		final GridColumn nameColumn = grid.getColumn(1);
		assertTrue(nameColumn.isVisible());

		final Object data = nameColumn.getData(ColumnConfiguration.ID);
		assertTrue(ColumnConfiguration.class.isInstance(data));

		final ColumnConfiguration columnConfiguration = extractGridColumnConfigByIndex(grid, 1); // name column
		assertTrue("Feature not enabled/supported. Check ColumnConfiguration.FEATURES?",
			columnConfiguration.getEnabledFeatures()
				.contains(ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW));

		columnConfiguration.visible().setValue(Boolean.FALSE);
		assertFalse(nameColumn.isVisible());

		columnConfiguration.visible().resetToDefault();
		assertTrue(nameColumn.isVisible());

		// TODO: check context menu items
	}

	@Test
	public void testColumnFilter()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		mockDataset();

		final Control rendered = renderControl(new SWTGridCell(0, 2, getRenderer()));
		assertControl(rendered);

		final GridTableViewerComposite tableViewerComposite = (GridTableViewerComposite) rendered;
		assertTrue(tableViewerComposite.getEnabledFeatures().contains(TableConfiguration.FEATURE_COLUMN_FILTER));

		final Grid grid = getGrid(rendered);
		final ColumnConfiguration columnConfiguration = extractGridColumnConfigByIndex(grid,
			DataSet.Simple.equals(dataset) ? 1 : 4); // name or login column

		assertTrue("Feature not enabled/supported. Check ColumnConfiguration.FEATURES?",
			columnConfiguration.getEnabledFeatures()
				.contains(ColumnConfiguration.FEATURE_COLUMN_FILTER));

		assertEquals(expectedRows, grid.getItems().length); // 3 players/rows defined in mockSampleDataSet()

		/*
		 * test filtering
		 */

		columnConfiguration.matchFilter().setValue("er");
		switch (dataset) {
		case Simple:
			assertEquals(3, filterVisible(grid.getItems()).length);
			break;
		default: // complex case, has only two logins (a bot doesn't have one)
			assertEquals(2, filterVisible(grid.getItems()).length);
		}

		columnConfiguration.matchFilter().setValue("foo");
		assertEquals(0, filterVisible(grid.getItems()).length);

		columnConfiguration.matchFilter().resetToDefault();
		assertEquals(expectedRows, filterVisible(grid.getItems()).length);

		columnConfiguration.matchFilter().setValue("Kliver");
		assertEquals(1, filterVisible(grid.getItems()).length);

		columnConfiguration.matchFilter().resetToDefault();
		columnConfiguration.matchFilter().setValue("branzfeckenbauer");

		switch (dataset) {
		case Simple:
			assertEquals(0, filterVisible(grid.getItems()).length);
			break;
		default: // complex case, there is exactly one matching login
			assertEquals(1, filterVisible(grid.getItems()).length);
		}

		/*
		 * test clearing of filter
		 */

		columnConfiguration.showFilterControl().setValue(Boolean.FALSE);

		assertEquals(3, grid.getItems().length);
		assertEquals(3, filterVisible(grid.getItems()).length);

		// TODO: check context menu items
	}

	@Test
	public void testColumnFilterWithColumnHideShow()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		mockDataset();

		final Control rendered = renderControl(new SWTGridCell(0, 2, getRenderer()));
		final Grid grid = getGrid(rendered);
		final GridColumn nameColumn = grid.getColumn(1); // name column

		final Object data = nameColumn.getData(ColumnConfiguration.ID);
		final ColumnConfiguration columnConfiguration = ColumnConfiguration.class.cast(data);

		assertTrue("Feature not enabled/supported. Check ColumnConfiguration.FEATURES?",
			columnConfiguration.getEnabledFeatures()
				.contains(ColumnConfiguration.FEATURE_COLUMN_HIDE_SHOW));
		assertTrue("Feature not enabled/supported. Check ColumnConfiguration.FEATURES?",
			columnConfiguration.getEnabledFeatures()
				.contains(ColumnConfiguration.FEATURE_COLUMN_FILTER));

		/*
		 * test clearing of filter
		 */

		columnConfiguration.showFilterControl().setValue(Boolean.TRUE);
		columnConfiguration.matchFilter().setValue("foo");

		assertEquals(0, filterVisible(grid.getItems()).length);

		columnConfiguration.visible().setValue(Boolean.FALSE);

		assertEquals(expectedRows, filterVisible(grid.getItems()).length);

		/*
		 * test for Gerrit #110529 (filter again after filters have been hidden)
		 */

		columnConfiguration.visible().setValue(Boolean.TRUE);
		columnConfiguration.showFilterControl().setValue(Boolean.TRUE);
		columnConfiguration.matchFilter().setValue("bar");

		assertEquals(0, filterVisible(grid.getItems()).length);

		columnConfiguration.matchFilter().resetToDefault();
		columnConfiguration.showFilterControl().setValue(Boolean.FALSE);
		// will result in a NPE without Gerrit #110529
		columnConfiguration.visible().setValue(Boolean.FALSE);

		assertEquals(expectedRows, filterVisible(grid.getItems()).length);

	}

	private GridItem[] filterVisible(GridItem[] items) {
		final List<GridItem> visibleItems = new ArrayList<GridItem>();
		for (final GridItem item : items) {
			if (!item.isVisible()) {
				continue;
			}
			visibleItems.add(item);
		}
		return visibleItems.toArray(new GridItem[] {});
	}

	@Override
	public void renderValidationIconLabelAlignmentLeft()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
	}

	@Override
	public void renderValidationIconLabelAlignmentNone()
		throws NoRendererFoundException, NoPropertyDescriptorFoundExeption {
	}

	@Override
	public void testGridDescriptionLabelAlignmentLeft() {
	}

	@Override
	public void testGridDescriptionLabelAlignmentNone() {
	}

	@Test
	public void testEffectivelyReadOnlyHidesAddRemoveButtons()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		mockDataset();

		when(getvControl().isAddRemoveDisabled()).thenReturn(false);
		when(getvControl().isEffectivelyEnabled()).thenReturn(true);
		when(getvControl().isEffectivelyReadonly()).thenReturn(true);
		when(getvControl().isVisible()).thenReturn(true);

		getShell().open();
		final Control rendered = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertControl(rendered);
		final GridTableViewerComposite tableViewerComposite = (GridTableViewerComposite) rendered;
		final Composite gridComposite = Composite.class.cast(tableViewerComposite.getChildren()[0]);
		final Composite buttonComposite = (Composite) Composite.class.cast(gridComposite.getChildren()[0])
			.getChildren()[2];

		// there should be no buttons in case the table is read-only
		assertEquals(0, buttonComposite.getChildren().length);

		final GridControlSWTRenderer swtRenderer = GridControlSWTRenderer.class.cast(getRenderer());

		final Optional<Control> addRowButton = swtRenderer.getControlForAction(AddRowAction.ACTION_ID);
		final Optional<Control> removeRowButton = swtRenderer.getControlForAction(RemoveRowAction.ACTION_ID);
		final Optional<Control> duplicateRowButton = swtRenderer.getControlForAction(DuplicateRowAction.ACTION_ID);

		assertFalse(addRowButton.isPresent());
		assertFalse(removeRowButton.isPresent());
		assertFalse(duplicateRowButton.isPresent());
	}

	public void testActionKeyBindings()
		throws DatabindingFailedException, NoRendererFoundException, NoPropertyDescriptorFoundExeption {

		mockDataset();

		final Set<VTStyleProperty> properties = new LinkedHashSet<VTStyleProperty>();
		final VTKeyBindings keyBindings = VTKeybindingFactory.eINSTANCE.createKeyBindings();
		addKeyBinding(keyBindings, AddRowAction.ACTION_ID, "M1+e");
		properties.add(keyBindings);

		final VTViewTemplateProvider viewTemplateProvider = mock(VTViewTemplateProvider.class);
		when(viewTemplateProvider.getStyleProperties(getvControl(), getContext()))
			.thenReturn(properties);

		setTemplateProvider(viewTemplateProvider);

		getShell().open();
		final Control rendered = renderControl(new SWTGridCell(0, 2, getRenderer()));
		getRenderer().finalizeRendering(getShell());
		assertControl(rendered);
		final GridTableViewerComposite tableViewerComposite = (GridTableViewerComposite) rendered;
		final Composite gridComposite = Composite.class.cast(tableViewerComposite.getChildren()[0]);
		final Composite buttonComposite = (Composite) Composite.class.cast(gridComposite.getChildren()[0])
			.getChildren()[2];

		final GridControlSWTRenderer swtRenderer = GridControlSWTRenderer.class.cast(getRenderer());

	}

	private void addKeyBinding(VTKeyBindings bindings, String actionId, String sequence) {
		final VTKeyBinding binding = VTKeybindingFactory.eINSTANCE.createKeyBinding();
		binding.setId(actionId);
		binding.setKeySequence(sequence);

		bindings.getBindings().add(binding);
	}
}
