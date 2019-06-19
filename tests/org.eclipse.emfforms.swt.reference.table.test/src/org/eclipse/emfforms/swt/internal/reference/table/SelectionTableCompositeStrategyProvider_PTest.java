/*******************************************************************************
 * Copyright (c) 2019 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - initial API and implementation
 ******************************************************************************/
package org.eclipse.emfforms.swt.internal.reference.table;

import static org.eclipse.emfforms.swt.internal.reference.table.SelectionTableCompositeStrategyProvider.VIEW_FILTER_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecp.spi.common.ui.composites.SelectionComposite;
import org.eclipse.emf.ecp.test.common.DefaultRealm;
import org.eclipse.emf.ecp.ui.view.swt.reference.SelectionCompositeStrategy;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.context.ViewModelContextFactory;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewModelProperties;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.provider.EMFFormsViewService;
import org.eclipse.emf.ecp.view.spi.provider.IFilteredViewProvider;
import org.eclipse.emf.ecp.view.spi.provider.IViewProvider;
import org.eclipse.emfforms.spi.core.services.databinding.emf.EMFFormsDatabindingEMF;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Black-box tests for the {@link SelectionTableCompositeStrategyProvider} class.
 */
@RunWith(Parameterized.class)
public class SelectionTableCompositeStrategyProvider_PTest {

	private static final EReference REFERENCE = EcorePackage.Literals.ETYPED_ELEMENT__ETYPE;

	/** Parameterized name of the "outer" view. */
	private final String mainView;
	/** Parameterized name of the view containing the table control for the table composite. */
	private final String tableCompositeView;

	private DefaultRealm realm;

	private ViewModelContext context;
	private EMFFormsViewService viewService;
	private IViewProvider viewProvider;

	private VView eParameterView;
	private VView eTypedElementETypeView;

	private EParameter domainModel;

	private SelectionTableCompositeStrategyProvider fixture;
	private Shell shell;

	/**
	 * Initializes me.
	 */
	public SelectionTableCompositeStrategyProvider_PTest(String name, String mainView, String tableComposite) {
		super();
		this.mainView = mainView;
		tableCompositeView = tableComposite;
	}

	@Parameters(name = "{0}")
	public static Object[] parameters() {
		return new Object[][] {
			{ "legacy", "legacy_EParameter.view", "legacy_ETypedElement_eType.view" },
			{ "segments", "segments_EParameter.view", "segments_ETypedElement_eType.view" }
		};
	}

	@Test
	public void provides() {
		assertThat("Expected to provide", fixture.provides(domainModel, REFERENCE), notNullValue());
	}

	@Test
	public void provides_not() {
		final EPackage oneOffPackage = EcoreFactory.eINSTANCE.createEPackage();
		oneOffPackage.setName("oneoff");
		oneOffPackage.setNsURI("http://oneoff");
		final EClass oneOffClass = EcoreFactory.eINSTANCE.createEClass();
		oneOffClass.setName("OneOff");
		oneOffPackage.getEClassifiers().add(oneOffClass);
		final EReference other = EcoreFactory.eINSTANCE.createEReference();
		other.setName("other");
		other.setEType(oneOffClass);
		oneOffClass.getEStructuralFeatures().add(other);
		final EObject oneOff = EcoreUtil.create(oneOffClass);

		assertThat("Expected not to provide", fixture.provides(oneOff, other), nullValue());
	}

	@Test
	public void provides_not_byRequiredFilter() {
		viewService.removeProvider(viewProvider);
		viewProvider = createLegacyViewProvider();
		viewService.addProvider(viewProvider);

		assertThat("Expected to provide", fixture.provides(domainModel, REFERENCE), nullValue());
	}

	@Test
	public void create() {
		assumeThat("Expected to provide", fixture.provides(domainModel, REFERENCE), notNullValue());

		final Set<EObject> extent = new HashSet<>();
		extent.addAll(EcorePackage.eINSTANCE.getEClassifiers());
		extent.addAll(XMLTypePackage.eINSTANCE.getEClassifiers());

		final SelectionCompositeStrategy strategy = fixture.create(domainModel, REFERENCE);
		final SelectionComposite<? extends StructuredViewer> composite = strategy.getSelectionViewer(domainModel,
			REFERENCE, extent);

		shell = new Shell();
		shell.setSize(500, 300);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		composite.createUI(shell);
		shell.open();

		final SWTBot bot = new SWTBot(shell);
		final SWTBotTable table = bot.table();
		assertThat("Wrong number of columns", table.columnCount(), is(4));

		// Note that the EPackage column name is provided explicitly by the view model.
		// Otherwise, it would also be "Name" (it's the EPackage name)
		assertThat(table.columns(), is(Arrays.asList("Selection", "Name", "Instance Type Name", "EPackage")));

		// Exercise the filter
		bot.text().setText("DocumentRoot");

		// There should be only one match
		assertThat("Filter failed", table.rowCount(), is(1));
	}

	//
	// Test framework
	//

	@Before
	public void createRealm() {
		realm = new DefaultRealm();
	}

	@Before
	public void createContext() {
		loadViewModels();
		domainModel = EcoreFactory.eINSTANCE.createEParameter();
		context = ViewModelContextFactory.INSTANCE.createViewModelContext(eParameterView, domainModel);

		viewService = context.getService(EMFFormsViewService.class);
		viewProvider = createFilteredViewProvider();
		viewService.addProvider(viewProvider);

		fixture = new SelectionTableCompositeStrategyProvider();
		fixture.setDatabinding(context.getService(EMFFormsDatabindingEMF.class));
	}

	@After
	public void destroyContext() {
		viewService.removeProvider(viewProvider);
		viewService = null;
		viewProvider = null;

		context.dispose();
		context = null;
	}

	@After
	public void closeShell() {
		if (shell != null) {
			shell.dispose();
		}
	}

	@After
	public void destroyRealm() {
		realm.dispose();
	}

	public void loadViewModels() {
		final ResourceSet rset = new ResourceSetImpl();
		Resource res = rset.getResource(getURI(mainView), true);
		eParameterView = (VView) EcoreUtil.getObjectByType(res.getContents(), VViewPackage.Literals.VIEW);
		res = rset.getResource(getURI(tableCompositeView), true);
		eTypedElementETypeView = (VView) EcoreUtil.getObjectByType(res.getContents(), VViewPackage.Literals.VIEW);
	}

	URI getURI(String fileName) {
		final URL url = SelectionTableCompositeStrategyProvider_PTest.class.getResource(fileName);
		return URI.createURI(url.toExternalForm());
	}

	private IViewProvider createFilteredViewProvider() {
		// Just delegate to an old-style provider
		final IViewProvider delegate = createLegacyViewProvider();

		return new IFilteredViewProvider() {

			@Override
			public double canProvideViewModel(EObject eObject, VViewModelProperties properties,
				Collection<String> requiredKeys) {

				return delegate.canProvideViewModel(eObject, properties);
			}

			@Override
			public VView provideViewModel(EObject eObject, VViewModelProperties properties,
				Collection<String> requiredKeys) {

				return delegate.provideViewModel(eObject, properties);
			}

		};
	}

	/**
	 * Create a legacy provider that requires adaptation to the new filtering API
	 * and so is not even consulted if there are required filter keys.
	 *
	 * @return the legacy view provider
	 */
	private IViewProvider createLegacyViewProvider() {
		return new IViewProvider() {

			@Override
			public double canProvideViewModel(EObject eObject, VViewModelProperties properties) {
				return eObject == domainModel
					&& REFERENCE.getName().equals(properties.get(VIEW_FILTER_KEY))
						? Double.POSITIVE_INFINITY
						: NOT_APPLICABLE;
			}

			@Override
			public VView provideViewModel(EObject eObject, VViewModelProperties properties) {
				final VView result = EcoreUtil.copy(eTypedElementETypeView);
				result.setLoadingProperties(EcoreUtil.copy(properties));
				return result;
			}

		};
	}

}
