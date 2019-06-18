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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.ui.view.swt.reference.ReferenceServiceCustomizationVendor;
import org.eclipse.emf.ecp.view.spi.model.VView;
import org.eclipse.emf.ecp.view.spi.model.VViewFactory;
import org.eclipse.emf.ecp.view.spi.model.VViewPackage;
import org.eclipse.emf.ecp.view.spi.table.model.VTableControl;
import org.eclipse.emf.ecp.view.spi.table.model.VTableFactory;
import org.eclipse.emf.ecp.view.spi.table.model.VTablePackage;
import org.eclipse.emf.ecp.view.test.common.swt.spi.SWTTestUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Abstract base class for unit tests for multi segment dmr reference strategy providers.
 *
 * @author Lucas Koehler
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public abstract class AbstractMultiDmrStrategyProviderTest<T extends ReferenceServiceCustomizationVendor<?> & TestableStrategyProvider> {

	private T strategyProvider;
	private VTableControl tableControl;
	private EClass testEClass;

	/** @return the tested strategy provider */
	protected T getStrategyProvider() {
		return strategyProvider;
	}

	/** @see #getStrategyProvider() */
	protected void setStrategyProvider(T strategyProvider) {
		this.strategyProvider = strategyProvider;
	}

	/** @return the table control containing the multi dmr */
	protected VTableControl getTableControl() {
		return tableControl;
	}

	/** @see #getTableControl() */
	protected void setTableControl(VTableControl tableControl) {
		this.tableControl = tableControl;
	}

	/** @return The root EClass for the VView (indirectly) containing the multi dmr. */
	protected EClass getTestEClass() {
		return testEClass;
	}

	/** @see #getTestEClass() */
	protected void setTestEClass(EClass testEClass) {
		this.testEClass = testEClass;
	}

	@Before
	public void setUp() {
		testEClass = EcoreFactory.eINSTANCE.createEClass();
		testEClass.setName("TestClass"); //$NON-NLS-1$
		// ePackage.getEClassifiers().add(eClass);
		final EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setUpperBound(-1);
		eReference.setName("TestReference"); //$NON-NLS-1$
		eReference.setEType(EcorePackage.Literals.ECLASS);
		testEClass.getEStructuralFeatures().add(eReference);

		// Create view model with a multi segment
		final VView view = VViewFactory.eINSTANCE.createView();
		view.setRootEClass(testEClass);
		tableControl = VTableFactory.eINSTANCE.createTableControl();
		view.getChildren().add(tableControl);

		// Add the view to a resource with editing domain
		final ResourceSet rs = new ResourceSetImpl();
		final ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(new AdapterFactory[] {
			new ReflectiveItemProviderAdapterFactory(),
			new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE) });
		final AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, new BasicCommandStack(), rs);
		rs.eAdapters().add(new AdapterFactoryEditingDomain.EditingDomainProvider(editingDomain));
		final Resource resource = rs.createResource(URI.createURI("VIRTUAL_URI")); //$NON-NLS-1$
		resource.getContents().add(view);

		initStrategyProvider();

		// Wait for the UI harness to be ready before starting the test execution
		UIThreadRunnable.syncExec(SWTTestUtil::waitForUIThread);
	}

	@After
	public void tearDown() {
		// Shell 0 and 1 must not be disposed. They belong to the UI harness around the tests
		UIThreadRunnable.syncExec(Display.getDefault(), () -> {
			final Shell[] shells = Display.getDefault().getShells();
			for (int i = 2; i < shells.length; i++) {
				if (shells[i] != null && !shells[i].isDisposed()) {
					shells[i].dispose();
				}
			}
		});
	}

	protected abstract void initStrategyProvider();

	protected abstract boolean executeHandles(EObject owner, EReference reference);

	@Test
	public void handles_generationEnabled_true() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertTrue(executeHandles(tableControl, VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectReference() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(executeHandles(tableControl, VTablePackage.Literals.TABLE_CONTROL__COLUMN_CONFIGURATIONS));
	}

	@Test
	public void handles_generationEnabled_incorrectParent() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(executeHandles(VViewFactory.eINSTANCE.createControl(),
			VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE));
	}

	@Test
	public void handles_generationEnabled_incorrectBoth() {
		strategyProvider.setSegmentToolingEnabled(true);
		assertFalse(
			executeHandles(VViewFactory.eINSTANCE.createControl(), EcorePackage.Literals.ECLASS__ESTRUCTURAL_FEATURES));
	}

	@Test
	public void handles_generationDisabled() {
		strategyProvider.setSegmentToolingEnabled(false);
		assertFalse(executeHandles(tableControl, VViewPackage.Literals.CONTROL__DOMAIN_MODEL_REFERENCE));
	}

}
